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
    public AvancePartidaResponseDTO registrar(AvancePartidaRequestDTO request, Integer idEmpleadoRegistro) {
        Proyecto proyecto     = getProyectoOrThrow(request.getIdProyecto());
        Cuadrilla cuadrilla   = request.getIdCuadrilla() != null ? getCuadrillaOrThrow(request.getIdCuadrilla()) : null;
        EstandarRendimiento estandar = request.getIdEstandar() != null ? getEstandarOrThrow(request.getIdEstandar()) : null;
        Empleado empleado     = getEmpleadoOrThrow(idEmpleadoRegistro);

        AvancePartida avance = AvancePartida.builder()
                .proyecto(proyecto)
                .cuadrilla(cuadrilla)
                .estandar(estandar)
                .nombrePartida(request.getNombrePartida())
                .fechaRegistro(request.getFechaRegistro())
                .cantidadEjecutada(request.getCantidadEjecutada())
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
    public AvancePartidaResponseDTO actualizar(Integer id, AvancePartidaRequestDTO request) {
        AvancePartida avance = getOrThrow(id);
        if (request.getIdProyecto() != null)       avance.setProyecto(getProyectoOrThrow(request.getIdProyecto()));
        if (request.getIdCuadrilla() != null)      avance.setCuadrilla(getCuadrillaOrThrow(request.getIdCuadrilla()));
        if (request.getIdEstandar() != null)       avance.setEstandar(getEstandarOrThrow(request.getIdEstandar()));
        if (request.getNombrePartida() != null)    avance.setNombrePartida(request.getNombrePartida());
        if (request.getFechaRegistro() != null)    avance.setFechaRegistro(request.getFechaRegistro());
        if (request.getCantidadEjecutada() != null) avance.setCantidadEjecutada(request.getCantidadEjecutada());
        return toResponse(avancePartidaRepository.save(avance));
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

    AvancePartidaResponseDTO toResponse(AvancePartida a) {
        return AvancePartidaResponseDTO.builder()
                .idAvance(a.getIdAvance())
                .idProyecto(a.getProyecto().getIdProyecto())
                .nombreProyecto(a.getProyecto().getNombreProyecto())
                .idCuadrilla(a.getCuadrilla() != null ? a.getCuadrilla().getIdCuadrilla() : null)
                .nombreCuadrilla(a.getCuadrilla() != null ? a.getCuadrilla().getNombreCuadrilla() : null)
                .idEstandar(a.getEstandar() != null ? a.getEstandar().getIdEstandar() : null)
                .nombreActividad(a.getEstandar() != null ? a.getEstandar().getNombreActividad() : null)
                .rendimientoEsperado(a.getEstandar() != null ? a.getEstandar().getRendimientoEsperado() : null)
                .nombrePartida(a.getNombrePartida())
                .fechaRegistro(a.getFechaRegistro())
                .cantidadEjecutada(a.getCantidadEjecutada())
                .build();
    }
}
