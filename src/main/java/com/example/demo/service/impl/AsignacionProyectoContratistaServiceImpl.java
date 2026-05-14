package com.example.demo.service.impl;

import com.example.demo.dto.request.AsignacionProyectoContratistaRequestDTO;
import com.example.demo.dto.response.AsignacionProyectoContratistaResponseDTO;
import com.example.demo.model.AsignacionProyectoContratista;
import com.example.demo.model.AsignacionProyectoContratista.EstatusContrato;
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
    public AsignacionProyectoContratistaResponseDTO create(AsignacionProyectoContratistaRequestDTO dto) {
        Proyecto proyecto = proyectoRepository.findById(dto.getIdProyecto())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Proyecto no encontrado con id: " + dto.getIdProyecto()));

        Contratista contratista = contratistaRepository.findById(dto.getIdContratista())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Contratista no encontrado con id: " + dto.getIdContratista()));

        if (asignacionPcRepository.existsByProyecto_IdProyectoAndContratista_IdContratista(
                dto.getIdProyecto(), dto.getIdContratista())) {
            throw new IllegalArgumentException("El contratista ya está asignado a este proyecto.");
        }

        validarFechas(dto);

        AsignacionProyectoContratista asignacion = new AsignacionProyectoContratista();
        asignacion.setProyecto(proyecto);
        asignacion.setContratista(contratista);
        mapDtoToEntity(dto, asignacion);
        asignacion.setEstatusContrato(EstatusContrato.VIGENTE);
        return toResponse(asignacionPcRepository.save(asignacion));
    }

    @Override
    @Transactional
    public AsignacionProyectoContratistaResponseDTO update(Integer id,
                                                           AsignacionProyectoContratistaRequestDTO dto) {
        AsignacionProyectoContratista asignacion = getOrThrow(id);

        Proyecto proyecto = proyectoRepository.findById(dto.getIdProyecto())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Proyecto no encontrado con id: " + dto.getIdProyecto()));

        Contratista contratista = contratistaRepository.findById(dto.getIdContratista())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Contratista no encontrado con id: " + dto.getIdContratista()));

        boolean cambioRelacion = !asignacion.getProyecto().getIdProyecto().equals(dto.getIdProyecto())
                || !asignacion.getContratista().getIdContratista().equals(dto.getIdContratista());

        if (cambioRelacion && asignacionPcRepository.existsByProyecto_IdProyectoAndContratista_IdContratista(
                dto.getIdProyecto(), dto.getIdContratista())) {
            throw new IllegalArgumentException("El contratista ya está asignado a este proyecto.");
        }

        validarFechas(dto);
        asignacion.setProyecto(proyecto);
        asignacion.setContratista(contratista);
        mapDtoToEntity(dto, asignacion);
        if (dto.getEstatusContrato() != null) {
            asignacion.setEstatusContrato(dto.getEstatusContrato());
        }
        return toResponse(asignacionPcRepository.save(asignacion));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        getOrThrow(id);
        asignacionPcRepository.deleteById(id);
    }

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

    private void mapDtoToEntity(AsignacionProyectoContratistaRequestDTO dto,
                                AsignacionProyectoContratista asignacion) {
        asignacion.setNumeroContrato(dto.getNumeroContrato());
        asignacion.setFechaInicio(dto.getFechaInicio());
        asignacion.setFechaFinEstimada(dto.getFechaFinEstimada());
        asignacion.setPersonalAsignado(dto.getPersonalAsignado());
        asignacion.setObservaciones(dto.getObservaciones());
    }

    private void validarFechas(AsignacionProyectoContratistaRequestDTO dto) {
        if (dto.getFechaInicio() != null && dto.getFechaFinEstimada() != null
                && dto.getFechaFinEstimada().isBefore(dto.getFechaInicio())) {
            throw new IllegalArgumentException(
                    "La fecha fin del contrato no puede ser anterior a la fecha de inicio.");
        }
    }

    private AsignacionProyectoContratista getOrThrow(Integer id) {
        return asignacionPcRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Asignación proyecto-contratista no encontrada con id: " + id));
    }
}
