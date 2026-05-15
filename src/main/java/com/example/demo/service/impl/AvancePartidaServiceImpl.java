package com.example.demo.service.impl;

import com.example.demo.dto.request.AvancePartidaRequestDTO;
import com.example.demo.dto.response.AvancePartidaResponseDTO;
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
public class AvancePartidaServiceImpl implements AvancePartidaService {

    private final AvancePartidaRepository avancePartidaRepository;
    private final ProyectoRepository proyectoRepository;
    private final CuadrillaRepository cuadrillaRepository;
    private final EstandarRendimientoRepository estandarRepository;
    private final EmpleadoRepository empleadoRepository;

    // ─── FIND ALL ─────────────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<AvancePartidaResponseDTO> findAll() {
        return avancePartidaRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─── FIND BY ID ───────────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public AvancePartidaResponseDTO findById(Integer id) {
        return toResponse(getOrThrow(id));
    }

    // ─── FIND BY PROYECTO ─────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<AvancePartidaResponseDTO> findByProyecto(Integer idProyecto) {
        return avancePartidaRepository.findByProyecto_IdProyecto(idProyecto)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─── FIND BY CUADRILLA ────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<AvancePartidaResponseDTO> findByCuadrilla(Integer idCuadrilla) {
        return avancePartidaRepository.findByCuadrilla_IdCuadrilla(idCuadrilla)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─── FIND BY FECHA ────────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<AvancePartidaResponseDTO> findByFecha(LocalDate fecha) {
        return avancePartidaRepository.findByFechaRegistro(fecha)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─── CREATE ───────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public AvancePartidaResponseDTO create(AvancePartidaRequestDTO dto) {
        AvancePartida avance = new AvancePartida();
        mapDtoToEntity(dto, avance);
        return toResponse(avancePartidaRepository.save(avance));
    }

    // ─── UPDATE ───────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public AvancePartidaResponseDTO update(Integer id, AvancePartidaRequestDTO dto) {
        AvancePartida avance = getOrThrow(id);
        mapDtoToEntity(dto, avance);
        return toResponse(avancePartidaRepository.save(avance));
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public void delete(Integer id) {
        if (!avancePartidaRepository.existsById(id)) {
            throw new EntityNotFoundException("Avance de partida no encontrado con id: " + id);
        }
        avancePartidaRepository.deleteById(id);
    }

    // ─── HELPERS ──────────────────────────────────────────────────────────────
    private AvancePartida getOrThrow(Integer id) {
        return avancePartidaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Avance de partida no encontrado con id: " + id));
    }

    private void mapDtoToEntity(AvancePartidaRequestDTO dto, AvancePartida avance) {
        avance.setProyecto(proyectoRepository.findById(dto.getIdProyecto())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Proyecto no encontrado con id: " + dto.getIdProyecto())));

        if (dto.getIdCuadrilla() != null) {
            avance.setCuadrilla(cuadrillaRepository.findById(dto.getIdCuadrilla())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Cuadrilla no encontrada con id: " + dto.getIdCuadrilla())));
        } else {
            avance.setCuadrilla(null);
        }

        if (dto.getIdEstandar() != null) {
            avance.setEstandarRendimiento(estandarRepository.findById(dto.getIdEstandar())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Estándar de rendimiento no encontrado con id: " + dto.getIdEstandar())));
        } else {
            avance.setEstandarRendimiento(null);
        }

        avance.setNombrePartida(dto.getNombrePartida());
        avance.setFechaRegistro(dto.getFechaRegistro());
        avance.setCantidadEjecutada(dto.getCantidadEjecutada());
        avance.setEmpleadoRegistro(empleadoRepository.findById(dto.getIdEmpleadoRegistro())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Empleado no encontrado con id: " + dto.getIdEmpleadoRegistro())));
    }

    // ─── MAPPER ───────────────────────────────────────────────────────────────
    private AvancePartidaResponseDTO toResponse(AvancePartida a) {
        Proyecto p = a.getProyecto();
        Cuadrilla c = a.getCuadrilla();
        EstandarRendimiento e = a.getEstandarRendimiento();
        Empleado emp = a.getEmpleadoRegistro();

        return AvancePartidaResponseDTO.builder()
                .idAvance(a.getIdAvance())
                .idProyecto(p.getIdProyecto())
                .nombreProyecto(p.getNombreProyecto())
                .idCuadrilla(c != null ? c.getIdCuadrilla() : null)
                .nombreCuadrilla(c != null ? c.getNombreCuadrilla() : null)
                .idEstandar(e != null ? e.getIdEstandar() : null)
                .nombreActividad(e != null ? e.getNombreActividad() : null)
                .unidadMedida(e != null ? e.getUnidadMedida() : null)
                .nombrePartida(a.getNombrePartida())
                .fechaRegistro(a.getFechaRegistro())
                .cantidadEjecutada(a.getCantidadEjecutada())
                .idEmpleadoRegistro(emp.getIdEmpleado())
                .nombreEmpleadoRegistro(emp.getNombreEmpleado())
                .build();
    }
}
