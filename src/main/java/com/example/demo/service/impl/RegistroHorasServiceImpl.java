package com.example.demo.service.impl;

import com.example.demo.dto.registro.RegistroHorasRequest;
import com.example.demo.dto.registro.RegistroHorasResponse;
import com.example.demo.model.AsignacionTrabajadorProyecto;
import com.example.demo.model.Cuadrilla;
import com.example.demo.model.Empleado;
import com.example.demo.model.RegistroHoras;
import com.example.demo.repository.AsignacionTrabajadorProyectoRepository;
import com.example.demo.repository.CuadrillaRepository;
import com.example.demo.repository.EmpleadoRepository;
import com.example.demo.repository.RegistroHorasRepository;
import com.example.demo.service.RegistroHorasService;
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
public class RegistroHorasServiceImpl implements RegistroHorasService {

    private final RegistroHorasRepository registroHorasRepository;
    private final AsignacionTrabajadorProyectoRepository asignacionTpRepository;
    private final CuadrillaRepository cuadrillaRepository;
    private final EmpleadoRepository empleadoRepository;

    @Override
    @Transactional
    public RegistroHorasResponse registrar(RegistroHorasRequest req, Integer idEmpleadoRegistro) {
        if (registroHorasRepository.existsByAsignacionTrabajadorProyecto_IdAsignacionTpAndFechaRegistro(
                req.getIdAsignacionTp(), req.getFechaRegistro())) {
            throw new IllegalArgumentException(
                "Ya existe un registro de horas para la asignación "
                + req.getIdAsignacionTp() + " en la fecha " + req.getFechaRegistro());
        }
        AsignacionTrabajadorProyecto asignacion = getAsignacionOrThrow(req.getIdAsignacionTp());
        Cuadrilla cuadrilla = null;
        if (req.getIdCuadrilla() != null) {
            cuadrilla = getCuadrillaOrThrow(req.getIdCuadrilla());
        }
        Empleado empleado = getEmpleadoOrThrow(idEmpleadoRegistro);

        RegistroHoras registro = RegistroHoras.builder()
                .asignacionTrabajadorProyecto(asignacion)
                .cuadrilla(cuadrilla)
                .fechaRegistro(req.getFechaRegistro())
                .horasTrabajadas(req.getHorasTrabajadas())
                .tipoPeriodo(RegistroHoras.TipoPeriodo.valueOf(req.getTipoPeriodo()))
                .observaciones(req.getObservaciones())
                .empleadoRegistro(empleado)
                .build();
        return RegistroHorasResponse.from(registroHorasRepository.save(registro));
    }

    @Override
    public List<RegistroHorasResponse> listarPorProyecto(Integer idProyecto, LocalDate inicio, LocalDate fin) {
        List<RegistroHoras> registros;
        if (inicio != null && fin != null) {
            registros = registroHorasRepository
                    .findByAsignacionTrabajadorProyecto_Proyecto_IdProyectoAndFechaRegistroBetween(
                            idProyecto, inicio, fin);
        } else {
            registros = registroHorasRepository
                    .findByAsignacionTrabajadorProyecto_Proyecto_IdProyecto(idProyecto);
        }
        return registros.stream().map(RegistroHorasResponse::from).collect(Collectors.toList());
    }

    @Override
    public List<RegistroHorasResponse> listarPorTrabajador(Integer idTrabajador) {
        return registroHorasRepository
                .findByAsignacionTrabajadorProyecto_Trabajador_IdTrabajador(idTrabajador)
                .stream().map(RegistroHorasResponse::from).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RegistroHorasResponse actualizar(Integer id, RegistroHorasRequest req) {
        RegistroHoras registro = getOrThrow(id);
        if (req.getIdAsignacionTp() != null)
            registro.setAsignacionTrabajadorProyecto(getAsignacionOrThrow(req.getIdAsignacionTp()));
        if (req.getIdCuadrilla() != null) {
            registro.setCuadrilla(getCuadrillaOrThrow(req.getIdCuadrilla()));
        }
        if (req.getFechaRegistro() != null)    registro.setFechaRegistro(req.getFechaRegistro());
        if (req.getHorasTrabajadas() != null)  registro.setHorasTrabajadas(req.getHorasTrabajadas());
        if (req.getTipoPeriodo() != null)
            registro.setTipoPeriodo(RegistroHoras.TipoPeriodo.valueOf(req.getTipoPeriodo()));
        if (req.getObservaciones() != null)    registro.setObservaciones(req.getObservaciones());
        return RegistroHorasResponse.from(registroHorasRepository.save(registro));
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        getOrThrow(id);
        registroHorasRepository.deleteById(id);
    }

    private RegistroHoras getOrThrow(Integer id) {
        return registroHorasRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Registro de horas no encontrado con id: " + id));
    }

    private AsignacionTrabajadorProyecto getAsignacionOrThrow(Integer id) {
        return asignacionTpRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Asignación no encontrada con id: " + id));
    }

    private Cuadrilla getCuadrillaOrThrow(Integer id) {
        return cuadrillaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cuadrilla no encontrada con id: " + id));
    }

    private Empleado getEmpleadoOrThrow(Integer id) {
        return empleadoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado con id: " + id));
    }
}
