package com.example.demo.service.impl;

import com.example.demo.dto.avance.AvancePartidaRequest;
import com.example.demo.dto.avance.AvancePartidaResponse;
import com.example.demo.model.AvancePartida;
import com.example.demo.model.Cuadrilla;
import com.example.demo.model.Empleado;
import com.example.demo.model.EstandarRendimiento;
import com.example.demo.model.Proyecto;
import com.example.demo.repository.AvancePartidaRepository;
import com.example.demo.repository.CuadrillaRepository;
import com.example.demo.repository.EmpleadoRepository;
import com.example.demo.repository.EstandarRendimientoRepository;
import com.example.demo.repository.ProyectoRepository;
import com.example.demo.service.AvancePartidaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AvancePartidaServiceImpl implements AvancePartidaService {

    private final AvancePartidaRepository avancePartidaRepository;
    private final ProyectoRepository proyectoRepository;
    private final CuadrillaRepository cuadrillaRepository;
    private final EstandarRendimientoRepository estandarRepository;
    private final EmpleadoRepository empleadoRepository;

    @Override
    @Transactional
    public AvancePartidaResponse registrar(AvancePartidaRequest req, Integer idEmpleadoRegistro) {
        Proyecto proyecto   = getProyectoOrThrow(req.getIdProyecto());
        Cuadrilla cuadrilla = req.getIdCuadrilla() != null ? getCuadrillaOrThrow(req.getIdCuadrilla()) : null;
        EstandarRendimiento estandar = req.getIdEstandar() != null ? getEstandarOrThrow(req.getIdEstandar()) : null;
        Empleado empleado   = getEmpleadoOrThrow(idEmpleadoRegistro);

        AvancePartida avance = AvancePartida.builder()
                .proyecto(proyecto)
                .cuadrilla(cuadrilla)
                .estandar(estandar)
                .nombrePartida(req.getNombrePartida())
                .fechaRegistro(req.getFechaRegistro())
                .cantidadEjecutada(req.getCantidadEjecutada())
                .unidadMedida(EstandarRendimiento.UnidadMedida.valueOf(req.getUnidadMedida()))
                .cantidadProgramada(req.getCantidadProgramada())
                .observaciones(req.getObservaciones())
                .empleadoRegistro(empleado)
                .build();
        return AvancePartidaResponse.from(avancePartidaRepository.save(avance));
    }

    @Override
    public List<AvancePartidaResponse> listarPorProyecto(Integer idProyecto, LocalDate inicio, LocalDate fin) {
        List<AvancePartida> avances;
        if (inicio != null && fin != null) {
            avances = avancePartidaRepository.findByProyecto_IdProyectoAndFechaRegistroBetween(idProyecto, inicio, fin);
        } else {
            avances = avancePartidaRepository.findByProyecto_IdProyecto(idProyecto);
        }
        return avances.stream().map(AvancePartidaResponse::from).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AvancePartidaResponse actualizar(Integer id, AvancePartidaRequest req) {
        AvancePartida avance = getOrThrow(id);
        if (req.getIdProyecto() != null)       avance.setProyecto(getProyectoOrThrow(req.getIdProyecto()));
        if (req.getIdCuadrilla() != null)      avance.setCuadrilla(getCuadrillaOrThrow(req.getIdCuadrilla()));
        if (req.getIdEstandar() != null)       avance.setEstandar(getEstandarOrThrow(req.getIdEstandar()));
        if (req.getNombrePartida() != null)    avance.setNombrePartida(req.getNombrePartida());
        if (req.getFechaRegistro() != null)    avance.setFechaRegistro(req.getFechaRegistro());
        if (req.getCantidadEjecutada() != null) avance.setCantidadEjecutada(req.getCantidadEjecutada());
        if (req.getUnidadMedida() != null)
            avance.setUnidadMedida(EstandarRendimiento.UnidadMedida.valueOf(req.getUnidadMedida()));
        if (req.getCantidadProgramada() != null) avance.setCantidadProgramada(req.getCantidadProgramada());
        if (req.getObservaciones() != null)    avance.setObservaciones(req.getObservaciones());
        return AvancePartidaResponse.from(avancePartidaRepository.save(avance));
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        getOrThrow(id);
        avancePartidaRepository.deleteById(id);
    }

    private AvancePartida getOrThrow(Integer id) {
        return avancePartidaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Avance de partida no encontrado con id: " + id));
    }

    private Proyecto getProyectoOrThrow(Integer id) {
        return proyectoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proyecto no encontrado con id: " + id));
    }

    private Cuadrilla getCuadrillaOrThrow(Integer id) {
        return cuadrillaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cuadrilla no encontrada con id: " + id));
    }

    private EstandarRendimiento getEstandarOrThrow(Integer id) {
        return estandarRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estándar de rendimiento no encontrado con id: " + id));
    }

    private Empleado getEmpleadoOrThrow(Integer id) {
        return empleadoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado con id: " + id));
    }
}
