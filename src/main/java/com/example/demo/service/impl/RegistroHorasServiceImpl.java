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
    public RegistroHorasResponseDTO registrar(RegistroHorasRequestDTO dto, Integer idEmpleadoRegistro) {
        if (registroHorasRepository.existsByAsignacionTrabajadorProyecto_IdAsignacionTpAndFechaRegistro(
                dto.getIdAsignacionTp(), dto.getFechaRegistro())) {
            throw new IllegalArgumentException(
                "Ya existe un registro de horas para la asignación "
                + dto.getIdAsignacionTp() + " en la fecha " + dto.getFechaRegistro());
        }
        AsignacionTrabajadorProyecto asignacion = getAsignacionOrThrow(dto.getIdAsignacionTp());
        Cuadrilla cuadrilla = dto.getIdCuadrilla() != null ? getCuadrillaOrThrow(dto.getIdCuadrilla()) : null;
        Empleado empleado = getEmpleadoOrThrow(idEmpleadoRegistro);

        RegistroHoras registro = RegistroHoras.builder()
                .asignacionTrabajadorProyecto(asignacion)
                .cuadrilla(cuadrilla)
                .fechaRegistro(dto.getFechaRegistro())
                .horasTrabajadas(dto.getHorasTrabajadas())
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
    public RegistroHorasResponseDTO actualizar(Integer id, RegistroHorasRequestDTO dto) {
        RegistroHoras registro = getOrThrow(id);
        if (dto.getIdAsignacionTp() != null)
            registro.setAsignacionTrabajadorProyecto(getAsignacionOrThrow(dto.getIdAsignacionTp()));
        if (dto.getIdCuadrilla() != null)
            registro.setCuadrilla(getCuadrillaOrThrow(dto.getIdCuadrilla()));
        if (dto.getFechaRegistro() != null)   registro.setFechaRegistro(dto.getFechaRegistro());
        if (dto.getHorasTrabajadas() != null) registro.setHorasTrabajadas(dto.getHorasTrabajadas());
        return toResponse(registroHorasRepository.save(registro));
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        getOrThrow(id);
        registroHorasRepository.deleteById(id);
    }

    public RegistroHorasResponseDTO toResponse(RegistroHoras rh) {
        RegistroHorasResponseDTO.RegistroHorasResponseDTOBuilder b = RegistroHorasResponseDTO.builder()
                .idRegistro(rh.getIdRegistro())
                .idAsignacionTp(rh.getAsignacionTrabajadorProyecto().getIdAsignacionTp())
                .idTrabajador(rh.getAsignacionTrabajadorProyecto().getTrabajador().getIdTrabajador())
                .nombreTrabajador(rh.getAsignacionTrabajadorProyecto().getTrabajador().getNombreTrabajador())
                .idProyecto(rh.getAsignacionTrabajadorProyecto().getProyecto().getIdProyecto())
                .nombreProyecto(rh.getAsignacionTrabajadorProyecto().getProyecto().getNombreProyecto())
                .fechaRegistro(rh.getFechaRegistro())
                .horasTrabajadas(rh.getHorasTrabajadas());

        if (rh.getCuadrilla() != null) {
            b.idCuadrilla(rh.getCuadrilla().getIdCuadrilla())
             .nombreCuadrilla(rh.getCuadrilla().getNombreCuadrilla());
        }
        return b.build();
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
