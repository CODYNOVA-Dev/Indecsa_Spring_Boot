package com.example.demo.service.impl;

import com.example.demo.dto.request.AsignacionTrabajadorProyectoRequestDTO;
import com.example.demo.dto.response.AsignacionProyectoContratistaResponseDTO;
import com.example.demo.dto.response.AsignacionTrabajadorProyectoResponseDTO;
import com.example.demo.model.AsignacionProyectoContratista;
import com.example.demo.model.AsignacionTrabajadorProyecto;
import com.example.demo.model.AsignacionTrabajadorProyecto.EstatusAsignacion;
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
    private final TrabajadorServiceImpl trabajadorService;
    private final ProyectoServiceImpl proyectoService;
    private final AsignacionProyectoContratistaServiceImpl asignacionPcService;

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

        if (asignacionRepository.existsByTrabajador_IdTrabajadorAndProyecto_IdProyecto(
                dto.getIdTrabajador(), dto.getIdProyecto())) {
            throw new IllegalArgumentException("El trabajador ya está asignado a este proyecto.");
        }

        return toResponse(asignacionTpRepository.save(asignacion));
    }

    @Override
    @Transactional
    public AsignacionTrabajadorProyectoResponseDTO update(Integer id,
                                                          AsignacionTrabajadorProyectoRequestDTO dto) {
        AsignacionTrabajadorProyecto asignacion = getAsignacionOrThrow(id);

        Trabajador trabajador = trabajadorRepository.findById(request.getIdTrabajador())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Trabajador no encontrado con id: " + request.getIdTrabajador()));

        Proyecto proyecto = proyectoRepository.findById(request.getIdProyecto())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Proyecto no encontrado con id: " + request.getIdProyecto()));

        AsignacionProyectoContratista asignacionPc = asignacionPcRepository.findById(request.getIdAsignacionPc())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Contrato (asignacion_pc) no encontrado con id: " + dto.getIdAsignacionPc()));

        if (!asignacionPc.getProyecto().getIdProyecto().equals(dto.getIdProyecto())) {
            throw new IllegalArgumentException(
                    "El contrato indicado no pertenece al proyecto especificado.");
        }

        boolean cambioRelacion = !asignacion.getTrabajador().getIdTrabajador().equals(dto.getIdTrabajador())
                || !asignacion.getProyecto().getIdProyecto().equals(dto.getIdProyecto());

        if (cambioRelacion && asignacionRepository.existsByTrabajador_IdTrabajadorAndProyecto_IdProyecto(
                dto.getIdTrabajador(), dto.getIdProyecto())) {
            throw new IllegalArgumentException("El trabajador ya está asignado a este proyecto.");
        }

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

    private void mapDtoToEntity(AsignacionTrabajadorProyectoRequestDTO dto,
                                AsignacionTrabajadorProyecto asignacion) {
        asignacion.setPuestoEnProyecto(dto.getPuestoEnProyecto());
        asignacion.setFechaInicio(dto.getFechaInicio());
        asignacion.setFechaFinEstimada(dto.getFechaFinEstimada());
    }

    // ─── MAPPER ───────────────────────────────────────────────────────────────
    private AsignacionTrabajadorProyectoResponseDTO toResponse(AsignacionTrabajadorProyecto a) {
        AsignacionProyectoContratistaResponseDTO apcDTO =
                asignacionPcService.toResponse(a.getAsignacionProyectoContratista());

        return AsignacionTrabajadorProyectoResponseDTO.builder()
                .idAsignacionTp(a.getIdAsignacionTp())
                .trabajador(trabajadorService.toResponse(a.getTrabajador()))
                .proyecto(proyectoService.toResponse(a.getProyecto()))
                .asignacionProyectoContratista(apcDTO)
                .puestoEnProyecto(a.getPuestoEnProyecto())
                .fechaInicio(a.getFechaInicio())
                .fechaFinEstimada(a.getFechaFinEstimada())
                .estatusAsignacion(a.getEstatusAsignacion())
                .build();
    }
}
