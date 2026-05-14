package com.example.demo.service.impl;

import com.example.demo.dto.request.CuadrillaRequestDTO;
import com.example.demo.dto.response.CuadrillaResponseDTO;
import com.example.demo.model.Cuadrilla;
import com.example.demo.model.Proyecto;
import com.example.demo.repository.CuadrillaRepository;
import com.example.demo.repository.ProyectoRepository;
import com.example.demo.service.CuadrillaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CuadrillaServiceImpl implements CuadrillaService {

    private final CuadrillaRepository cuadrillaRepository;
    private final ProyectoRepository proyectoRepository;

    @Override
    public List<CuadrillaResponseDTO> listarPorProyecto(Integer idProyecto) {
        return cuadrillaRepository.findByProyecto_IdProyecto(idProyecto)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CuadrillaResponseDTO crear(CuadrillaRequestDTO dto) {
        if (cuadrillaRepository.existsByNombreCuadrillaAndProyecto_IdProyecto(
                dto.getNombreCuadrilla(), dto.getIdProyecto())) {
            throw new IllegalArgumentException(
                "Ya existe una cuadrilla con el nombre '" + dto.getNombreCuadrilla()
                + "' en el proyecto con id: " + dto.getIdProyecto());
        }
        Proyecto proyecto = getProyectoOrThrow(dto.getIdProyecto());
        Cuadrilla cuadrilla = Cuadrilla.builder()
                .proyecto(proyecto)
                .nombreCuadrilla(dto.getNombreCuadrilla())
                .frenteTrabajo(dto.getFrenteTrabajo())
                .estatusCuadrilla(Cuadrilla.EstatusCuadrilla.ACTIVO)
                .build();
        return toResponse(cuadrillaRepository.save(cuadrilla));
    }

    @Override
    @Transactional
    public CuadrillaResponseDTO actualizar(Integer id, CuadrillaRequestDTO dto) {
        Cuadrilla cuadrilla = getCuadrillaOrThrow(id);
        if (dto.getIdProyecto() != null) {
            cuadrilla.setProyecto(getProyectoOrThrow(dto.getIdProyecto()));
        }
        if (dto.getNombreCuadrilla() != null) cuadrilla.setNombreCuadrilla(dto.getNombreCuadrilla());
        if (dto.getFrenteTrabajo() != null)   cuadrilla.setFrenteTrabajo(dto.getFrenteTrabajo());
        return toResponse(cuadrillaRepository.save(cuadrilla));
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        getCuadrillaOrThrow(id);
        cuadrillaRepository.deleteById(id);
    }

    public CuadrillaResponseDTO toResponse(Cuadrilla c) {
        return CuadrillaResponseDTO.builder()
                .idCuadrilla(c.getIdCuadrilla())
                .idProyecto(c.getProyecto().getIdProyecto())
                .nombreProyecto(c.getProyecto().getNombreProyecto())
                .nombreCuadrilla(c.getNombreCuadrilla())
                .frenteTrabajo(c.getFrenteTrabajo())
                .estatusCuadrilla(c.getEstatusCuadrilla() != null ? c.getEstatusCuadrilla().name() : null)
                .build();
    }

    private Cuadrilla getCuadrillaOrThrow(Integer id) {
        return cuadrillaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cuadrilla no encontrada con id: " + id));
    }

    private Proyecto getProyectoOrThrow(Integer id) {
        return proyectoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proyecto no encontrado con id: " + id));
    }
}
