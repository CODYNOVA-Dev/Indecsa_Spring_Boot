package com.example.demo.service.impl;

import com.example.demo.dto.request.RegistroHorasRequestDTO;
import com.example.demo.dto.response.RegistroHorasResponseDTO;
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
    public RegistroHorasResponseDTO registrar(RegistroHorasRequestDTO request, Integer idEmpleadoRegistro) {
        if (registroHorasRepository.existsByAsignacionTrabajadorProyecto_IdAsignacionTpAndFechaRegistro(
                request.getIdAsignacionTp(), request.getFechaRegistro())) {
            throw new IllegalArgumentException(
                "Ya existe un registro de horas para la asignación "
                + request.getIdAsignacionTp() + " en la fecha " + request.getFechaRegistro());
        }
        AsignacionTrabajadorProyecto asignacion = getAsignacionOrThrow(request.getIdAsignacionTp());
        Cuadrilla cuadrilla = request.getIdCuadrilla() != null
                ? getCuadrillaOrThrow(request.getIdCuadrilla()) : null;
        Empleado empleado = getEmpleadoOrThrow(idEmpleadoRegistro);

        RegistroHoras registro = RegistroHoras.builder()
                .asignacionTrabajadorProyecto(asignacion)
                .cuadrilla(cuadrilla)
                .fechaRegistro(request.getFechaRegistro())
                .horasTrabajadas(request.getHorasTrabajadas())
                .empleadoRegistro(empleado)
                .build();
        return toResponse(registroHorasRepository.save(registro));
    }

    @Override
    public List<RegistroHorasResponseDTO> listarPorProyecto(Integer idProyecto, LocalDate inicio, LocalDate fin) {
        List<RegistroHoras> registros;
        if (inicio != null && fin != null) {
            registros = registroHorasRepository
                    .findByAsignacionTrabajadorProyecto_Proyecto_IdProyectoAndFechaRegistroBetween(
                            idProyecto, inicio, fin);
        } else {
            registros = registroHorasRepository
                    .findByAsignacionTrabajadorProyecto_Proyecto_IdProyecto(idProyecto);
        }
        return registros.stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<RegistroHorasResponseDTO> listarPorTrabajador(Integer idTrabajador) {
        return registroHorasRepository
                .findByAsignacionTrabajadorProyecto_Trabajador_IdTrabajador(idTrabajador)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RegistroHorasResponseDTO actualizar(Integer id, RegistroHorasRequestDTO request) {
        RegistroHoras registro = getOrThrow(id);
        if (request.getIdAsignacionTp() != null)
            registro.setAsignacionTrabajadorProyecto(getAsignacionOrThrow(request.getIdAsignacionTp()));
        if (request.getIdCuadrilla() != null)
            registro.setCuadrilla(getCuadrillaOrThrow(request.getIdCuadrilla()));
        if (request.getFechaRegistro() != null)   registro.setFechaRegistro(request.getFechaRegistro());
        if (request.getHorasTrabajadas() != null) registro.setHorasTrabajadas(request.getHorasTrabajadas());
        return toResponse(registroHorasRepository.save(registro));
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

    RegistroHorasResponseDTO toResponse(RegistroHoras r) {
        AsignacionTrabajadorProyecto atp = r.getAsignacionTrabajadorProyecto();
        return RegistroHorasResponseDTO.builder()
                .idRegistro(r.getIdRegistro())
                .idAsignacionTp(atp.getIdAsignacionTp())
                .idTrabajador(atp.getTrabajador().getIdTrabajador())
                .nombreTrabajador(atp.getTrabajador().getNombreTrabajador())
                .idProyecto(atp.getProyecto().getIdProyecto())
                .nombreProyecto(atp.getProyecto().getNombreProyecto())
                .idCuadrilla(r.getCuadrilla() != null ? r.getCuadrilla().getIdCuadrilla() : null)
                .nombreCuadrilla(r.getCuadrilla() != null ? r.getCuadrilla().getNombreCuadrilla() : null)
                .fechaRegistro(r.getFechaRegistro())
                .horasTrabajadas(r.getHorasTrabajadas())
                .build();
    }
}
