package com.example.demo.service.impl;

import com.example.demo.dto.rendimiento.RendimientoIndicadorResponse;
import com.example.demo.model.AvancePartida;
import com.example.demo.model.Cuadrilla;
import com.example.demo.model.RegistroHoras;
import com.example.demo.repository.AvancePartidaRepository;
import com.example.demo.repository.CuadrillaRepository;
import com.example.demo.repository.RegistroHorasRepository;
import com.example.demo.service.RendimientoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RendimientoServiceImpl implements RendimientoService {

    private static final BigDecimal CIEN = new BigDecimal("100");
    private static final BigDecimal UMBRAL_VERDE   = new BigDecimal("-10");
    private static final BigDecimal UMBRAL_AMARILLO = new BigDecimal("-30");

    private final RegistroHorasRepository registroHorasRepository;
    private final AvancePartidaRepository avancePartidaRepository;
    private final CuadrillaRepository cuadrillaRepository;

    @Override
    public List<RendimientoIndicadorResponse> calcularPorProyecto(
            Integer idProyecto, LocalDate inicio, LocalDate fin) {

        List<RegistroHoras> horas = registroHorasRepository
                .findByAsignacionTrabajadorProyecto_Proyecto_IdProyectoAndFechaRegistroBetween(
                        idProyecto, inicio, fin);
        List<AvancePartida> avances = avancePartidaRepository
                .findByProyecto_IdProyectoAndFechaRegistroBetween(idProyecto, inicio, fin);

        // Agrupa horas por trabajador
        Map<Integer, List<RegistroHoras>> horasPorTrabajador = horas.stream()
                .collect(Collectors.groupingBy(
                        rh -> rh.getAsignacionTrabajadorProyecto().getTrabajador().getIdTrabajador()));

        List<RendimientoIndicadorResponse> resultado = new ArrayList<>();
        for (Map.Entry<Integer, List<RegistroHoras>> entry : horasPorTrabajador.entrySet()) {
            List<RegistroHoras> horasGrupo = entry.getValue();
            RegistroHoras sample = horasGrupo.get(0);

            BigDecimal totalHoras = sumarHoras(horasGrupo);
            BigDecimal totalAvance = sumarAvancesPorProyecto(avances);
            String unidad = resolverUnidad(avances);

            RendimientoIndicadorResponse r = buildIndicador(
                    sample.getAsignacionTrabajadorProyecto().getTrabajador().getIdTrabajador(),
                    sample.getAsignacionTrabajadorProyecto().getTrabajador().getNombreTrabajador(),
                    sample.getAsignacionTrabajadorProyecto().getProyecto().getIdProyecto(),
                    sample.getAsignacionTrabajadorProyecto().getProyecto().getNombreProyecto(),
                    null, null,
                    inicio, fin,
                    totalHoras, totalAvance, unidad,
                    resolverEstandarEsperado(avances));
            resultado.add(r);
        }
        return resultado;
    }

    @Override
    public List<RendimientoIndicadorResponse> calcularPorTrabajador(
            Integer idTrabajador, LocalDate inicio, LocalDate fin) {

        List<RegistroHoras> horas = registroHorasRepository
                .findByAsignacionTrabajadorProyecto_Trabajador_IdTrabajador(idTrabajador)
                .stream()
                .filter(rh -> !rh.getFechaRegistro().isBefore(inicio) && !rh.getFechaRegistro().isAfter(fin))
                .collect(Collectors.toList());

        // Agrupa por proyecto
        Map<Integer, List<RegistroHoras>> horasPorProyecto = horas.stream()
                .collect(Collectors.groupingBy(
                        rh -> rh.getAsignacionTrabajadorProyecto().getProyecto().getIdProyecto()));

        List<RendimientoIndicadorResponse> resultado = new ArrayList<>();
        for (Map.Entry<Integer, List<RegistroHoras>> entry : horasPorProyecto.entrySet()) {
            List<RegistroHoras> horasGrupo = entry.getValue();
            RegistroHoras sample = horasGrupo.get(0);
            Integer idProyecto = sample.getAsignacionTrabajadorProyecto().getProyecto().getIdProyecto();

            List<AvancePartida> avances = avancePartidaRepository
                    .findByProyecto_IdProyectoAndFechaRegistroBetween(idProyecto, inicio, fin);

            BigDecimal totalHoras  = sumarHoras(horasGrupo);
            BigDecimal totalAvance = sumarAvancesPorProyecto(avances);
            String unidad = resolverUnidad(avances);

            RendimientoIndicadorResponse r = buildIndicador(
                    sample.getAsignacionTrabajadorProyecto().getTrabajador().getIdTrabajador(),
                    sample.getAsignacionTrabajadorProyecto().getTrabajador().getNombreTrabajador(),
                    idProyecto,
                    sample.getAsignacionTrabajadorProyecto().getProyecto().getNombreProyecto(),
                    null, null,
                    inicio, fin,
                    totalHoras, totalAvance, unidad,
                    resolverEstandarEsperado(avances));
            resultado.add(r);
        }
        return resultado;
    }

    @Override
    public List<RendimientoIndicadorResponse> calcularPorCuadrilla(
            Integer idCuadrilla, LocalDate inicio, LocalDate fin) {

        Cuadrilla cuadrilla = cuadrillaRepository.findById(idCuadrilla)
                .orElseThrow(() -> new EntityNotFoundException("Cuadrilla no encontrada con id: " + idCuadrilla));

        List<RegistroHoras> horas = registroHorasRepository
                .findByAsignacionTrabajadorProyecto_Proyecto_IdProyectoAndFechaRegistroBetween(
                        cuadrilla.getProyecto().getIdProyecto(), inicio, fin)
                .stream()
                .filter(rh -> rh.getCuadrilla() != null
                        && rh.getCuadrilla().getIdCuadrilla().equals(idCuadrilla))
                .collect(Collectors.toList());

        List<AvancePartida> avances = avancePartidaRepository.findByCuadrilla_IdCuadrilla(idCuadrilla)
                .stream()
                .filter(a -> !a.getFechaRegistro().isBefore(inicio) && !a.getFechaRegistro().isAfter(fin))
                .collect(Collectors.toList());

        // Agrupa por trabajador dentro de la cuadrilla
        Map<Integer, List<RegistroHoras>> horasPorTrabajador = horas.stream()
                .collect(Collectors.groupingBy(
                        rh -> rh.getAsignacionTrabajadorProyecto().getTrabajador().getIdTrabajador()));

        List<RendimientoIndicadorResponse> resultado = new ArrayList<>();
        for (Map.Entry<Integer, List<RegistroHoras>> entry : horasPorTrabajador.entrySet()) {
            List<RegistroHoras> horasGrupo = entry.getValue();
            RegistroHoras sample = horasGrupo.get(0);

            BigDecimal totalHoras  = sumarHoras(horasGrupo);
            BigDecimal totalAvance = sumarAvancesPorProyecto(avances);
            String unidad = resolverUnidad(avances);

            RendimientoIndicadorResponse r = buildIndicador(
                    sample.getAsignacionTrabajadorProyecto().getTrabajador().getIdTrabajador(),
                    sample.getAsignacionTrabajadorProyecto().getTrabajador().getNombreTrabajador(),
                    cuadrilla.getProyecto().getIdProyecto(),
                    cuadrilla.getProyecto().getNombreProyecto(),
                    idCuadrilla,
                    cuadrilla.getNombreCuadrilla(),
                    inicio, fin,
                    totalHoras, totalAvance, unidad,
                    resolverEstandarEsperado(avances));
            resultado.add(r);
        }
        return resultado;
    }

    // ── helpers ──────────────────────────────────────────────────────────────

    private BigDecimal sumarHoras(List<RegistroHoras> lista) {
        return lista.stream()
                .map(RegistroHoras::getHorasTrabajadas)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal sumarAvancesPorProyecto(List<AvancePartida> lista) {
        return lista.stream()
                .map(AvancePartida::getCantidadEjecutada)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private String resolverUnidad(List<AvancePartida> lista) {
        return lista.stream()
                .filter(a -> a.getEstandar() != null && a.getEstandar().getUnidadMedida() != null)
                .map(a -> a.getEstandar().getUnidadMedida().name())
                .findFirst()
                .orElse(null);
    }

    private BigDecimal resolverEstandarEsperado(List<AvancePartida> lista) {
        return lista.stream()
                .filter(a -> a.getEstandar() != null)
                .map(a -> a.getEstandar().getRendimientoEsperado())
                .findFirst()
                .orElse(null);
    }

    private RendimientoIndicadorResponse buildIndicador(
            Integer idTrabajador, String nombreTrabajador,
            Integer idProyecto, String nombreProyecto,
            Integer idCuadrilla, String nombreCuadrilla,
            LocalDate inicio, LocalDate fin,
            BigDecimal totalHoras, BigDecimal totalAvance, String unidad,
            BigDecimal esperado) {

        RendimientoIndicadorResponse r = new RendimientoIndicadorResponse();
        r.setIdTrabajador(idTrabajador);
        r.setNombreTrabajador(nombreTrabajador);
        r.setIdProyecto(idProyecto);
        r.setNombreProyecto(nombreProyecto);
        r.setIdCuadrilla(idCuadrilla);
        r.setNombreCuadrilla(nombreCuadrilla);
        r.setPeriodoInicio(inicio);
        r.setPeriodoFin(fin);
        r.setTotalHorasTrabajadas(totalHoras);
        r.setTotalAvanceEjecutado(totalAvance);
        r.setUnidadMedida(unidad);

        BigDecimal rendimientoReal = null;
        if (totalHoras.compareTo(BigDecimal.ZERO) > 0) {
            rendimientoReal = totalAvance.divide(totalHoras, 4, RoundingMode.HALF_UP);
        }
        r.setRendimientoReal(rendimientoReal);
        r.setRendimientoEsperado(esperado);

        if (esperado != null && rendimientoReal != null
                && esperado.compareTo(BigDecimal.ZERO) != 0) {
            BigDecimal desviacion = rendimientoReal.subtract(esperado)
                    .divide(esperado, 4, RoundingMode.HALF_UP)
                    .multiply(CIEN)
                    .setScale(2, RoundingMode.HALF_UP);
            r.setPorcentajeDesviacion(desviacion);

            if (desviacion.compareTo(UMBRAL_VERDE) >= 0) {
                r.setIndicadorSemaforo("VERDE");
            } else if (desviacion.compareTo(UMBRAL_AMARILLO) >= 0) {
                r.setIndicadorSemaforo("AMARILLO");
            } else {
                r.setIndicadorSemaforo("ROJO");
            }
        } else {
            r.setIndicadorSemaforo("SIN_ESTANDAR");
        }

        return r;
    }
}
