package com.example.demo.service.impl;

import com.example.demo.dto.request.AsignacionTrabajadorProyectoRequestDTO;
import com.example.demo.dto.response.AsignacionTrabajadorProyectoResponseDTO;
import com.example.demo.model.AsignacionProyectoContratista;
import com.example.demo.model.AsignacionTrabajadorProyecto;
import com.example.demo.model.Proyecto;
import com.example.demo.model.Trabajador;
import com.example.demo.repository.AsignacionProyectoContratistaRepository;
import com.example.demo.repository.AsignacionTrabajadorProyectoRepository;
import com.example.demo.repository.ProyectoRepository;
import com.example.demo.repository.TrabajadorRepository;
import com.example.demo.service.AsignacionTrabajadorProyectoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AsignacionTrabajadorProyectoServiceImpl implements AsignacionTrabajadorProyectoService {

    private final AsignacionTrabajadorProyectoRepository asignacionTpRepository;
    private final TrabajadorRepository trabajadorRepository;
    private final ProyectoRepository proyectoRepository;
    private final AsignacionProyectoContratistaRepository asignacionPcRepository;

    @Override
    public List<AsignacionTrabajadorProyectoResponseDTO> findByProyecto(Integer idProyecto) {
        return asignacionTpRepository.findByProyecto_IdProyecto(idProyecto)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<AsignacionTrabajadorProyectoResponseDTO> findByTrabajador(Integer idTrabajador) {
        return asignacionTpRepository.findByTrabajador_IdTrabajador(idTrabajador)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public AsignacionTrabajadorProyectoResponseDTO findById(Integer id) {
        return toResponse(getOrThrow(id));
    }

    @Override
    @Transactional
    public AsignacionTrabajadorProyectoResponseDTO create(AsignacionTrabajadorProyectoRequestDTO request) {
        Trabajador trabajador = trabajadorRepository.findById(request.getIdTrabajador())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Trabajador no encontrado con id: " + request.getIdTrabajador()));

        Proyecto proyecto = proyectoRepository.findById(request.getIdProyecto())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Proyecto no encontrado con id: " + request.getIdProyecto()));

        AsignacionProyectoContratista asignacionPc = asignacionPcRepository.findById(request.getIdAsignacionPc())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Asignación proyecto-contratista no encontrada con id: " + request.getIdAsignacionPc()));

        if (asignacionTpRepository.existsByTrabajador_IdTrabajadorAndProyecto_IdProyecto(
                request.getIdTrabajador(), request.getIdProyecto())) {
            throw new IllegalArgumentException("El trabajador ya está asignado a este proyecto.");
        }

        AsignacionTrabajadorProyecto asignacion = AsignacionTrabajadorProyecto.builder()
                .trabajador(trabajador)
                .proyecto(proyecto)
                .asignacionProyectoContratista(asignacionPc)
                .puestoEnProyecto(request.getPuestoEnProyecto())
                .fechaInicio(request.getFechaInicio())
                .fechaFinEstimada(request.getFechaFinEstimada())
                .estatusAsignacion(request.getEstatusAsignacion() != null
                        ? request.getEstatusAsignacion()
                        : AsignacionTrabajadorProyecto.EstatusAsignacion.ACTIVO)
                .observaciones(request.getObservaciones())
                .build();

        return toResponse(asignacionTpRepository.save(asignacion));
    }

    @Override
    @Transactional
    public AsignacionTrabajadorProyectoResponseDTO update(Integer id, AsignacionTrabajadorProyectoRequestDTO request) {
        AsignacionTrabajadorProyecto asignacion = getOrThrow(id);

        Trabajador trabajador = trabajadorRepository.findById(request.getIdTrabajador())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Trabajador no encontrado con id: " + request.getIdTrabajador()));

        Proyecto proyecto = proyectoRepository.findById(request.getIdProyecto())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Proyecto no encontrado con id: " + request.getIdProyecto()));

        AsignacionProyectoContratista asignacionPc = asignacionPcRepository.findById(request.getIdAsignacionPc())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Asignación proyecto-contratista no encontrada con id: " + request.getIdAsignacionPc()));

        asignacion.setTrabajador(trabajador);
        asignacion.setProyecto(proyecto);
        asignacion.setAsignacionProyectoContratista(asignacionPc);
        asignacion.setPuestoEnProyecto(request.getPuestoEnProyecto());
        asignacion.setFechaInicio(request.getFechaInicio());
        asignacion.setFechaFinEstimada(request.getFechaFinEstimada());
        if (request.getEstatusAsignacion() != null) asignacion.setEstatusAsignacion(request.getEstatusAsignacion());
        asignacion.setObservaciones(request.getObservaciones());

        return toResponse(asignacionTpRepository.save(asignacion));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        getOrThrow(id);
        asignacionTpRepository.deleteById(id);
    }

    private AsignacionTrabajadorProyecto getOrThrow(Integer id) {
        return asignacionTpRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Asignación trabajador-proyecto no encontrada con id: " + id));
    }

    AsignacionTrabajadorProyectoResponseDTO toResponse(AsignacionTrabajadorProyecto a) {
        return AsignacionTrabajadorProyectoResponseDTO.builder()
                .idAsignacionTp(a.getIdAsignacionTp())
                .idTrabajador(a.getTrabajador().getIdTrabajador())
                .nombreTrabajador(a.getTrabajador().getNombreTrabajador())
                .idProyecto(a.getProyecto().getIdProyecto())
                .nombreProyecto(a.getProyecto().getNombreProyecto())
                .idAsignacionPc(a.getAsignacionProyectoContratista().getIdAsignacionPc())
                .puestoEnProyecto(a.getPuestoEnProyecto())
                .fechaInicio(a.getFechaInicio())
                .fechaFinEstimada(a.getFechaFinEstimada())
                .estatusAsignacion(a.getEstatusAsignacion())
                .observaciones(a.getObservaciones())
                .build();
    }
}
