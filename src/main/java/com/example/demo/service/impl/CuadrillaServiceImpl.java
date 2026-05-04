package com.example.demo.service.impl;

import com.example.demo.dto.cuadrilla.CuadrillaRequest;
import com.example.demo.dto.cuadrilla.CuadrillaResponse;
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
    @Transactional
    public CuadrillaResponse crear(CuadrillaRequest req) {
        if (cuadrillaRepository.existsByNombreCuadrillaAndProyecto_IdProyecto(
                req.getNombreCuadrilla(), req.getIdProyecto())) {
            throw new IllegalArgumentException(
                "Ya existe una cuadrilla con el nombre '" + req.getNombreCuadrilla()
                + "' en el proyecto con id: " + req.getIdProyecto());
        }
        Proyecto proyecto = getProyectoOrThrow(req.getIdProyecto());
        Cuadrilla cuadrilla = Cuadrilla.builder()
                .proyecto(proyecto)
                .nombreCuadrilla(req.getNombreCuadrilla())
                .frenteTrabajo(req.getFrenteTrabajo())
                .estatusCuadrilla(Cuadrilla.EstatusCuadrilla.ACTIVO)
                .observaciones(req.getObservaciones())
                .build();
        return CuadrillaResponse.from(cuadrillaRepository.save(cuadrilla));
    }

    @Override
    public List<CuadrillaResponse> listarPorProyecto(Integer idProyecto) {
        return cuadrillaRepository.findByProyecto_IdProyecto(idProyecto)
                .stream().map(CuadrillaResponse::from).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CuadrillaResponse actualizar(Integer id, CuadrillaRequest req) {
        Cuadrilla cuadrilla = getCuadrillaOrThrow(id);
        if (req.getIdProyecto() != null) {
            cuadrilla.setProyecto(getProyectoOrThrow(req.getIdProyecto()));
        }
        if (req.getNombreCuadrilla() != null) cuadrilla.setNombreCuadrilla(req.getNombreCuadrilla());
        if (req.getFrenteTrabajo() != null)   cuadrilla.setFrenteTrabajo(req.getFrenteTrabajo());
        if (req.getObservaciones() != null)   cuadrilla.setObservaciones(req.getObservaciones());
        return CuadrillaResponse.from(cuadrillaRepository.save(cuadrilla));
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        getCuadrillaOrThrow(id);
        cuadrillaRepository.deleteById(id);
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
