package com.example.demo.service.impl;

import com.example.demo.dto.request.CuadrillaRequestDTO;
import com.example.demo.dto.response.CuadrillaResponseDTO;
import com.example.demo.model.Cuadrilla;
import com.example.demo.model.Cuadrilla.EstatusCuadrilla;
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
public class CuadrillaServiceImpl implements CuadrillaService {

    private final CuadrillaRepository cuadrillaRepository;
    private final ProyectoRepository proyectoRepository;

    // ─── FIND ALL ─────────────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<CuadrillaResponseDTO> findAll() {
        return cuadrillaRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─── FIND BY ID ───────────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public CuadrillaResponseDTO findById(Integer id) {
        return toResponse(getCuadrillaOrThrow(id));
    }

    // ─── FIND BY PROYECTO ─────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<CuadrillaResponseDTO> findByProyecto(Integer idProyecto) {
        return cuadrillaRepository.findByProyecto_IdProyecto(idProyecto)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─── FIND BY ESTATUS ──────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<CuadrillaResponseDTO> findByEstatus(EstatusCuadrilla estatus) {
        return cuadrillaRepository.findByEstatusCuadrilla(estatus)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─── CREATE ───────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public CuadrillaResponseDTO create(CuadrillaRequestDTO dto) {
        Proyecto proyecto = getProyectoOrThrow(dto.getIdProyecto());
        if (cuadrillaRepository.existsByNombreCuadrillaAndProyecto_IdProyecto(
                dto.getNombreCuadrilla(), dto.getIdProyecto())) {
            throw new IllegalArgumentException(
                    "Ya existe una cuadrilla con el nombre '" + dto.getNombreCuadrilla()
                    + "' en este proyecto.");
        }
        Cuadrilla cuadrilla = new Cuadrilla();
        cuadrilla.setProyecto(proyecto);
        mapDtoToEntity(dto, cuadrilla);
        cuadrilla.setEstatusCuadrilla(EstatusCuadrilla.ACTIVO);
        return toResponse(cuadrillaRepository.save(cuadrilla));
    }

    // ─── UPDATE ───────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public CuadrillaResponseDTO update(Integer id, CuadrillaRequestDTO dto) {
        Cuadrilla cuadrilla = getCuadrillaOrThrow(id);
        Proyecto proyecto = getProyectoOrThrow(dto.getIdProyecto());

        boolean cambioNombreOProyecto =
                !cuadrilla.getNombreCuadrilla().equals(dto.getNombreCuadrilla())
                || !cuadrilla.getProyecto().getIdProyecto().equals(dto.getIdProyecto());

        if (cambioNombreOProyecto && cuadrillaRepository.existsByNombreCuadrillaAndProyecto_IdProyecto(
                dto.getNombreCuadrilla(), dto.getIdProyecto())) {
            throw new IllegalArgumentException(
                    "Ya existe una cuadrilla con el nombre '" + dto.getNombreCuadrilla()
                    + "' en este proyecto.");
        }
        cuadrilla.setProyecto(proyecto);
        mapDtoToEntity(dto, cuadrilla);
        return toResponse(cuadrillaRepository.save(cuadrilla));
    }

    // ─── CAMBIAR ESTATUS ──────────────────────────────────────────────────────
    @Override
    @Transactional
    public CuadrillaResponseDTO cambiarEstatus(Integer id, EstatusCuadrilla estatus) {
        Cuadrilla cuadrilla = getCuadrillaOrThrow(id);
        cuadrilla.setEstatusCuadrilla(estatus);
        return toResponse(cuadrillaRepository.save(cuadrilla));
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public void delete(Integer id) {
        if (!cuadrillaRepository.existsById(id)) {
            throw new EntityNotFoundException("Cuadrilla no encontrada con id: " + id);
        }
        cuadrillaRepository.deleteById(id);
    }

    // ─── HELPERS ──────────────────────────────────────────────────────────────
    private Cuadrilla getCuadrillaOrThrow(Integer id) {
        return cuadrillaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cuadrilla no encontrada con id: " + id));
    }

    private Proyecto getProyectoOrThrow(Integer idProyecto) {
        return proyectoRepository.findById(idProyecto)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Proyecto no encontrado con id: " + idProyecto));
    }

    private void mapDtoToEntity(CuadrillaRequestDTO dto, Cuadrilla cuadrilla) {
        cuadrilla.setNombreCuadrilla(dto.getNombreCuadrilla());
        cuadrilla.setFrenteTrabajo(dto.getFrenteTrabajo());
    }

    // ─── MAPPER ───────────────────────────────────────────────────────────────
    private CuadrillaResponseDTO toResponse(Cuadrilla c) {
        return CuadrillaResponseDTO.builder()
                .idCuadrilla(c.getIdCuadrilla())
                .idProyecto(c.getProyecto().getIdProyecto())
                .nombreProyecto(c.getProyecto().getNombreProyecto())
                .nombreCuadrilla(c.getNombreCuadrilla())
                .frenteTrabajo(c.getFrenteTrabajo())
                .estatusCuadrilla(c.getEstatusCuadrilla())
                .build();
    }
}
