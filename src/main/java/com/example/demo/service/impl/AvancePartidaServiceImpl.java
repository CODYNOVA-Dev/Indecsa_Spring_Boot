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
@Transactional(readOnly = true)
public class AvancePartidaServiceImpl implements AvancePartidaService {

    private final AvancePartidaRepository avancePartidaRepository;
    private final ProyectoRepository proyectoRepository;
    private final CuadrillaRepository cuadrillaRepository;
    private final EstandarRendimientoRepository estandarRepository;
    private final EmpleadoRepository empleadoRepository;

    @Override
    @Transactional
    public AvancePartidaResponseDTO registrar(AvancePartidaRequestDTO dto, Integer idEmpleadoRegistro) {
        Proyecto proyecto   = getProyectoOrThrow(dto.getIdProyecto());
        Cuadrilla cuadrilla = dto.getIdCuadrilla() != null ? getCuadrillaOrThrow(dto.getIdCuadrilla()) : null;
        EstandarRendimiento estandar = dto.getIdEstandar() != null ? getEstandarOrThrow(dto.getIdEstandar()) : null;
        Empleado empleado   = getEmpleadoOrThrow(idEmpleadoRegistro);

        AvancePartida avance = AvancePartida.builder()
                .proyecto(proyecto)
                .cuadrilla(cuadrilla)
                .estandar(estandar)
                .nombrePartida(dto.getNombrePartida())
                .fechaRegistro(dto.getFechaRegistro())
                .cantidadEjecutada(dto.getCantidadEjecutada())
                .empleadoRegistro(empleado)
                .build();
        return toResponse(avancePartidaRepository.save(avance));
    }

    @Override
    public List<AvancePartidaResponseDTO> listarPorProyecto(Integer idProyecto, LocalDate inicio, LocalDate fin) {
        List<AvancePartida> avances;
        if (inicio != null && fin != null) {
            avances = avancePartidaRepository.findByProyecto_IdProyectoAndFechaRegistroBetween(idProyecto, inicio, fin);
        } else {
            avances = avancePartidaRepository.findByProyecto_IdProyecto(idProyecto);
        }
        return avances.stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AvancePartidaResponseDTO actualizar(Integer id, AvancePartidaRequestDTO dto) {
        AvancePartida avance = getOrThrow(id);
        if (dto.getIdProyecto() != null)       avance.setProyecto(getProyectoOrThrow(dto.getIdProyecto()));
        if (dto.getIdCuadrilla() != null)      avance.setCuadrilla(getCuadrillaOrThrow(dto.getIdCuadrilla()));
        if (dto.getIdEstandar() != null)       avance.setEstandar(getEstandarOrThrow(dto.getIdEstandar()));
        if (dto.getNombrePartida() != null)    avance.setNombrePartida(dto.getNombrePartida());
        if (dto.getFechaRegistro() != null)    avance.setFechaRegistro(dto.getFechaRegistro());
        if (dto.getCantidadEjecutada() != null) avance.setCantidadEjecutada(dto.getCantidadEjecutada());
        return toResponse(avancePartidaRepository.save(avance));
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        getOrThrow(id);
        avancePartidaRepository.deleteById(id);
    }

    public AvancePartidaResponseDTO toResponse(AvancePartida a) {
        AvancePartidaResponseDTO.AvancePartidaResponseDTOBuilder b = AvancePartidaResponseDTO.builder()
                .idAvance(a.getIdAvance())
                .idProyecto(a.getProyecto().getIdProyecto())
                .nombreProyecto(a.getProyecto().getNombreProyecto())
                .nombrePartida(a.getNombrePartida())
                .fechaRegistro(a.getFechaRegistro())
                .cantidadEjecutada(a.getCantidadEjecutada());

        if (a.getCuadrilla() != null) {
            b.idCuadrilla(a.getCuadrilla().getIdCuadrilla())
             .nombreCuadrilla(a.getCuadrilla().getNombreCuadrilla());
        }
        if (a.getEstandar() != null) {
            b.idEstandar(a.getEstandar().getIdEstandar())
             .nombreActividad(a.getEstandar().getNombreActividad())
             .rendimientoEsperado(a.getEstandar().getRendimientoEsperado());
        }
        return b.build();
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
                .orElseThrow(() -> new EntityNotFoundException("Estándar no encontrado con id: " + id));
    }

    private Empleado getEmpleadoOrThrow(Integer id) {
        return empleadoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado con id: " + id));
    }
}
