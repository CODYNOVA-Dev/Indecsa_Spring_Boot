package com.example.demo.service.impl;

import com.example.demo.dto.request.AsignacionProyectoContratistaRequestDTO;
import com.example.demo.dto.response.AsignacionProyectoContratistaResponseDTO;
import com.example.demo.model.AsignacionProyectoContratista;
import com.example.demo.model.Contratista;
import com.example.demo.model.Proyecto;
import com.example.demo.repository.AsignacionProyectoContratistaRepository;
import com.example.demo.repository.ContratistaRepository;
import com.example.demo.repository.ProyectoRepository;
import com.example.demo.service.AsignacionProyectoContratistaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AsignacionProyectoContratistaServiceImpl implements AsignacionProyectoContratistaService {

    private final AsignacionProyectoContratistaRepository asignacionPcRepository;
    private final ProyectoRepository proyectoRepository;
    private final ContratistaRepository contratistaRepository;
    private final ProyectoServiceImpl proyectoService;
    private final ContratistaServiceImpl contratistaService;

    @Override
    public List<AsignacionProyectoContratistaResponseDTO> findByProyecto(Integer idProyecto) {
        return asignacionPcRepository.findByProyecto_IdProyecto(idProyecto)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<AsignacionProyectoContratistaResponseDTO> findByContratista(Integer idContratista) {
        return asignacionPcRepository.findByContratista_IdContratista(idContratista)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public AsignacionProyectoContratistaResponseDTO findById(Integer id) {
        return toResponse(getOrThrow(id));
    }

    @Override
    @Transactional
    public AsignacionProyectoContratistaResponseDTO create(AsignacionProyectoContratistaRequestDTO request) {
        Proyecto proyecto = proyectoRepository.findById(request.getIdProyecto())
                .orElseThrow(() -> new EntityNotFoundException("Proyecto no encontrado con id: " + request.getIdProyecto()));

        Contratista contratista = contratistaRepository.findById(request.getIdContratista())
                .orElseThrow(() -> new EntityNotFoundException("Contratista no encontrado con id: " + request.getIdContratista()));

        if (asignacionRepository.existsByProyecto_IdProyectoAndContratista_IdContratista(
                dto.getIdProyecto(), dto.getIdContratista())) {
            throw new IllegalArgumentException("El contratista ya está asignado a este proyecto.");
        }

        validarFechas(dto);

        AsignacionProyectoContratista asignacion = new AsignacionProyectoContratista();
        asignacion.setProyecto(proyecto);
        asignacion.setContratista(contratista);
        mapDtoToEntity(dto, asignacion);
        asignacion.setEstatusContrato(EstatusContrato.VIGENTE);
        return toResponse(asignacionRepository.save(asignacion));
    }

    // ─── UPDATE ───────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public AsignacionProyectoContratistaResponseDTO update(Integer id,
                                                           AsignacionProyectoContratistaRequestDTO dto) {
        AsignacionProyectoContratista asignacion = getAsignacionOrThrow(id);

        Proyecto proyecto = proyectoRepository.findById(dto.getIdProyecto())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Proyecto no encontrado con id: " + dto.getIdProyecto()));

        Contratista contratista = contratistaRepository.findById(dto.getIdContratista())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Contratista no encontrado con id: " + dto.getIdContratista()));

        boolean cambioRelacion = !asignacion.getProyecto().getIdProyecto().equals(dto.getIdProyecto())
                || !asignacion.getContratista().getIdContratista().equals(dto.getIdContratista());

        if (cambioRelacion && asignacionRepository.existsByProyecto_IdProyectoAndContratista_IdContratista(
                dto.getIdProyecto(), dto.getIdContratista())) {
            throw new IllegalArgumentException("El contratista ya está asignado a este proyecto.");
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

        return toResponse(asignacionPcRepository.save(asignacion));
    }

    @Override
    @Transactional
    public AsignacionProyectoContratistaResponseDTO update(Integer id, AsignacionProyectoContratistaRequestDTO request) {
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
        if (request.getEstatusContrato() != null) asignacion.setEstatusContrato(request.getEstatusContrato());
        asignacion.setObservaciones(request.getObservaciones());

        return toResponse(asignacionPcRepository.save(asignacion));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        getOrThrow(id);
        asignacionPcRepository.deleteById(id);
    }

    // ─── HELPERS ──────────────────────────────────────────────────────────────
    private AsignacionProyectoContratista getAsignacionOrThrow(Integer id) {
        return asignacionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Asignación proyecto-contratista no encontrada con id: " + id));
    }

    private void validarFechas(AsignacionProyectoContratistaRequestDTO dto) {
        if (dto.getFechaInicio() != null && dto.getFechaFinEstimada() != null
                && dto.getFechaFinEstimada().isBefore(dto.getFechaInicio())) {
            throw new IllegalArgumentException(
                    "La fecha fin del contrato no puede ser anterior a la fecha de inicio.");
        }
    }

    private void mapDtoToEntity(AsignacionProyectoContratistaRequestDTO dto,
                                AsignacionProyectoContratista asignacion) {
        asignacion.setNumeroContrato(dto.getNumeroContrato());
        asignacion.setFechaInicio(dto.getFechaInicio());
        asignacion.setFechaFinEstimada(dto.getFechaFinEstimada());
        asignacion.setPersonalAsignado(dto.getPersonalAsignado());
        asignacion.setObservaciones(dto.getObservaciones());
    }

    // ─── MAPPER ───────────────────────────────────────────────────────────────
    public AsignacionProyectoContratistaResponseDTO toResponse(AsignacionProyectoContratista a) {
        return AsignacionProyectoContratistaResponseDTO.builder()
                .idAsignacionPc(a.getIdAsignacionPc())
                .proyecto(proyectoService.toResponse(a.getProyecto()))
                .contratista(contratistaService.toResponse(a.getContratista()))
                .numeroContrato(a.getNumeroContrato())
                .fechaInicio(a.getFechaInicio())
                .fechaFinEstimada(a.getFechaFinEstimada())
                .personalAsignado(a.getPersonalAsignado())
                .estatusContrato(a.getEstatusContrato())
                .observaciones(a.getObservaciones())
                .build();
    }
}
