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
public class RegistroHorasServiceImpl implements RegistroHorasService {

    private final RegistroHorasRepository registroHorasRepository;
    private final AsignacionTrabajadorProyectoRepository asignacionTpRepository;
    private final CuadrillaRepository cuadrillaRepository;
    private final EmpleadoRepository empleadoRepository;

    // ─── FIND ALL ─────────────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<RegistroHorasResponseDTO> findAll() {
        return registroHorasRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─── FIND BY ID ───────────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public RegistroHorasResponseDTO findById(Integer id) {
        return toResponse(getOrThrow(id));
    }

    // ─── FIND BY ASIGNACION ───────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<RegistroHorasResponseDTO> findByAsignacion(Integer idAsignacionTp) {
        return registroHorasRepository.findByAsignacionTrabajadorProyecto_IdAsignacionTp(idAsignacionTp)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─── FIND BY CUADRILLA ────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<RegistroHorasResponseDTO> findByCuadrilla(Integer idCuadrilla) {
        return registroHorasRepository.findByCuadrilla_IdCuadrilla(idCuadrilla)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─── FIND BY FECHA ────────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<RegistroHorasResponseDTO> findByFecha(LocalDate fecha) {
        return registroHorasRepository.findByFechaRegistro(fecha)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─── CREATE ───────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public RegistroHorasResponseDTO create(RegistroHorasRequestDTO dto) {
        AsignacionTrabajadorProyecto asignacion = getAsignacionOrThrow(dto.getIdAsignacionTp());

        if (registroHorasRepository.existsByAsignacionTrabajadorProyecto_IdAsignacionTpAndFechaRegistro(
                dto.getIdAsignacionTp(), dto.getFechaRegistro())) {
            throw new IllegalArgumentException(
                    "Ya existe un registro de horas para esta asignación en la fecha: "
                    + dto.getFechaRegistro());
        }

        RegistroHoras registro = new RegistroHoras();
        registro.setAsignacionTrabajadorProyecto(asignacion);
        mapDtoToEntity(dto, registro);
        return toResponse(registroHorasRepository.save(registro));
    }

    // ─── UPDATE ───────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public RegistroHorasResponseDTO update(Integer id, RegistroHorasRequestDTO dto) {
        RegistroHoras registro = getOrThrow(id);
        AsignacionTrabajadorProyecto asignacion = getAsignacionOrThrow(dto.getIdAsignacionTp());

        boolean cambioAsignacionOFecha =
                !registro.getAsignacionTrabajadorProyecto().getIdAsignacionTp().equals(dto.getIdAsignacionTp())
                || !registro.getFechaRegistro().equals(dto.getFechaRegistro());

        if (cambioAsignacionOFecha && registroHorasRepository
                .existsByAsignacionTrabajadorProyecto_IdAsignacionTpAndFechaRegistro(
                        dto.getIdAsignacionTp(), dto.getFechaRegistro())) {
            throw new IllegalArgumentException(
                    "Ya existe un registro de horas para esta asignación en la fecha: "
                    + dto.getFechaRegistro());
        }

        registro.setAsignacionTrabajadorProyecto(asignacion);
        mapDtoToEntity(dto, registro);
        return toResponse(registroHorasRepository.save(registro));
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public void delete(Integer id) {
        if (!registroHorasRepository.existsById(id)) {
            throw new EntityNotFoundException("Registro de horas no encontrado con id: " + id);
        }
        registroHorasRepository.deleteById(id);
    }

    // ─── HELPERS ──────────────────────────────────────────────────────────────
    private RegistroHoras getOrThrow(Integer id) {
        return registroHorasRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Registro de horas no encontrado con id: " + id));
    }

    private AsignacionTrabajadorProyecto getAsignacionOrThrow(Integer id) {
        return asignacionTpRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Asignación trabajador-proyecto no encontrada con id: " + id));
    }

    private void mapDtoToEntity(RegistroHorasRequestDTO dto, RegistroHoras registro) {
        if (dto.getIdCuadrilla() != null) {
            registro.setCuadrilla(cuadrillaRepository.findById(dto.getIdCuadrilla())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Cuadrilla no encontrada con id: " + dto.getIdCuadrilla())));
        } else {
            registro.setCuadrilla(null);
        }
        registro.setFechaRegistro(dto.getFechaRegistro());
        registro.setHorasTrabajadas(dto.getHorasTrabajadas());
        registro.setEmpleadoRegistro(empleadoRepository.findById(dto.getIdEmpleadoRegistro())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Empleado no encontrado con id: " + dto.getIdEmpleadoRegistro())));
    }

    // ─── MAPPER ───────────────────────────────────────────────────────────────
    private RegistroHorasResponseDTO toResponse(RegistroHoras r) {
        AsignacionTrabajadorProyecto atp = r.getAsignacionTrabajadorProyecto();
        Cuadrilla c = r.getCuadrilla();
        Empleado emp = r.getEmpleadoRegistro();

        return RegistroHorasResponseDTO.builder()
                .idRegistro(r.getIdRegistro())
                .idAsignacionTp(atp.getIdAsignacionTp())
                .nombreTrabajador(atp.getTrabajador().getNombreTrabajador())
                .idCuadrilla(c != null ? c.getIdCuadrilla() : null)
                .nombreCuadrilla(c != null ? c.getNombreCuadrilla() : null)
                .fechaRegistro(r.getFechaRegistro())
                .horasTrabajadas(r.getHorasTrabajadas())
                .idEmpleadoRegistro(emp.getIdEmpleado())
                .nombreEmpleadoRegistro(emp.getNombreEmpleado())
                .build();
    }
}
