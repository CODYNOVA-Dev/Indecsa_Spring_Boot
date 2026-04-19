package com.indecsa.service.impl;

import com.indecsa.dto.asignacion.AsignacionProyectoContratistaRequest;
import com.indecsa.dto.asignacion.AsignacionProyectoContratistaResponse;
import com.indecsa.model.AsignacionProyectoContratista;
import com.indecsa.model.Contratista;
import com.indecsa.model.Proyecto;
import com.indecsa.repository.AsignacionProyectoContratistaRepository;
import com.indecsa.repository.ContratistaRepository;
import com.indecsa.repository.ProyectoRepository;
import com.indecsa.service.AsignacionProyectoContratistaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AsignacionProyectoContratistaServiceImpl implements AsignacionProyectoContratistaService {

    private final AsignacionProyectoContratistaRepository asignacionPcRepository;
    private final ProyectoRepository proyectoRepository;
    private final ContratistaRepository contratistaRepository;

    @Override
    public List<AsignacionProyectoContratistaResponse> findByProyecto(Integer idProyecto) {
        return asignacionPcRepository.findByProyecto_IdProyecto(idProyecto)
                .stream()
                .map(AsignacionProyectoContratistaResponse::from)
                .toList();
    }

    @Override
    public List<AsignacionProyectoContratistaResponse> findByContratista(Integer idContratista) {
        return asignacionPcRepository.findByContratista_IdContratista(idContratista)
                .stream()
                .map(AsignacionProyectoContratistaResponse::from)
                .toList();
    }

    @Override
    public AsignacionProyectoContratistaResponse findById(Integer id) {
        return AsignacionProyectoContratistaResponse.from(getOrThrow(id));
    }

    @Override
    @Transactional
    public AsignacionProyectoContratistaResponse create(AsignacionProyectoContratistaRequest request) {
        Proyecto proyecto = proyectoRepository.findById(request.getIdProyecto())
                .orElseThrow(() -> new EntityNotFoundException("Proyecto no encontrado con id: " + request.getIdProyecto()));

        Contratista contratista = contratistaRepository.findById(request.getIdContratista())
                .orElseThrow(() -> new EntityNotFoundException("Contratista no encontrado con id: " + request.getIdContratista()));

        if (asignacionPcRepository.existsByProyecto_IdProyectoAndContratista_IdContratista(
                request.getIdProyecto(), request.getIdContratista())) {
            throw new IllegalArgumentException("Ya existe una asignación para este proyecto y contratista.");
        }

        if (request.getFechaFinEstimada() != null
                && request.getFechaInicio() != null
                && request.getFechaFinEstimada().isBefore(request.getFechaInicio())) {
            throw new IllegalArgumentException("La fecha fin no puede ser anterior a la fecha de inicio.");
        }

        AsignacionProyectoContratista asignacion = AsignacionProyectoContratista.builder()
                .proyecto(proyecto)
                .contratista(contratista)
                .numeroContrato(request.getNumeroContrato())
                .fechaInicio(request.getFechaInicio())
                .fechaFinEstimada(request.getFechaFinEstimada())
                .personalAsignado(request.getPersonalAsignado())
                .puestosRequeridos(request.getPuestosRequeridos())
                .estatusContrato(request.getEstatusContrato() != null
                        ? request.getEstatusContrato()
                        : AsignacionProyectoContratista.EstatusContrato.VIGENTE)
                .observaciones(request.getObservaciones())
                .build();

        return AsignacionProyectoContratistaResponse.from(asignacionPcRepository.save(asignacion));
    }

    @Override
    @Transactional
    public AsignacionProyectoContratistaResponse update(Integer id, AsignacionProyectoContratistaRequest request) {
        AsignacionProyectoContratista asignacion = getOrThrow(id);

        Proyecto proyecto = proyectoRepository.findById(request.getIdProyecto())
                .orElseThrow(() -> new EntityNotFoundException("Proyecto no encontrado con id: " + request.getIdProyecto()));

        Contratista contratista = contratistaRepository.findById(request.getIdContratista())
                .orElseThrow(() -> new EntityNotFoundException("Contratista no encontrado con id: " + request.getIdContratista()));

        if (request.getFechaFinEstimada() != null
                && request.getFechaInicio() != null
                && request.getFechaFinEstimada().isBefore(request.getFechaInicio())) {
            throw new IllegalArgumentException("La fecha fin no puede ser anterior a la fecha de inicio.");
        }

        asignacion.setProyecto(proyecto);
        asignacion.setContratista(contratista);
        asignacion.setNumeroContrato(request.getNumeroContrato());
        asignacion.setFechaInicio(request.getFechaInicio());
        asignacion.setFechaFinEstimada(request.getFechaFinEstimada());
        asignacion.setPersonalAsignado(request.getPersonalAsignado());
        asignacion.setPuestosRequeridos(request.getPuestosRequeridos());
        if (request.getEstatusContrato() != null) {
            asignacion.setEstatusContrato(request.getEstatusContrato());
        }
        asignacion.setObservaciones(request.getObservaciones());

        return AsignacionProyectoContratistaResponse.from(asignacionPcRepository.save(asignacion));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        getOrThrow(id);
        asignacionPcRepository.deleteById(id);
    }

    private AsignacionProyectoContratista getOrThrow(Integer id) {
        return asignacionPcRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Asignación proyecto-contratista no encontrada con id: " + id));
    }
}
