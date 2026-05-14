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
    public AsignacionTrabajadorProyectoResponseDTO create(AsignacionTrabajadorProyectoRequestDTO dto) {
        Trabajador trabajador = getTrabajadorOrThrow(dto.getIdTrabajador());
        Proyecto proyecto = getProyectoOrThrow(dto.getIdProyecto());
        AsignacionProyectoContratista asignacionPc = getAsignacionPcOrThrow(dto.getIdAsignacionPc());

        if (asignacionTpRepository.existsByTrabajador_IdTrabajadorAndProyecto_IdProyecto(
                dto.getIdTrabajador(), dto.getIdProyecto())) {
            throw new IllegalArgumentException("El trabajador ya está asignado a este proyecto.");
        }

        AsignacionTrabajadorProyecto asignacion = new AsignacionTrabajadorProyecto();
        asignacion.setTrabajador(trabajador);
        asignacion.setProyecto(proyecto);
        asignacion.setAsignacionProyectoContratista(asignacionPc);
        mapDtoToEntity(dto, asignacion);
        asignacion.setEstatusAsignacion(EstatusAsignacion.ACTIVO);
        return toResponse(asignacionTpRepository.save(asignacion));
    }

    @Override
    @Transactional
    public AsignacionTrabajadorProyectoResponseDTO update(Integer id,
                                                          AsignacionTrabajadorProyectoRequestDTO dto) {
        AsignacionTrabajadorProyecto asignacion = getOrThrow(id);

        Trabajador trabajador = getTrabajadorOrThrow(dto.getIdTrabajador());
        Proyecto proyecto = getProyectoOrThrow(dto.getIdProyecto());
        AsignacionProyectoContratista asignacionPc = getAsignacionPcOrThrow(dto.getIdAsignacionPc());

        if (!asignacionPc.getProyecto().getIdProyecto().equals(dto.getIdProyecto())) {
            throw new IllegalArgumentException(
                    "El contrato indicado no pertenece al proyecto especificado.");
        }

        boolean cambioRelacion = !asignacion.getTrabajador().getIdTrabajador().equals(dto.getIdTrabajador())
                || !asignacion.getProyecto().getIdProyecto().equals(dto.getIdProyecto());

        if (cambioRelacion && asignacionTpRepository.existsByTrabajador_IdTrabajadorAndProyecto_IdProyecto(
                dto.getIdTrabajador(), dto.getIdProyecto())) {
            throw new IllegalArgumentException("El trabajador ya está asignado a este proyecto.");
        }

        asignacion.setTrabajador(trabajador);
        asignacion.setProyecto(proyecto);
        asignacion.setAsignacionProyectoContratista(asignacionPc);
        mapDtoToEntity(dto, asignacion);
        if (dto.getEstatusAsignacion() != null) {
            asignacion.setEstatusAsignacion(dto.getEstatusAsignacion());
        }
        return toResponse(asignacionTpRepository.save(asignacion));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        getOrThrow(id);
        asignacionTpRepository.deleteById(id);
    }

    public AsignacionTrabajadorProyectoResponseDTO toResponse(AsignacionTrabajadorProyecto a) {
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

    private void mapDtoToEntity(AsignacionTrabajadorProyectoRequestDTO dto,
                                AsignacionTrabajadorProyecto asignacion) {
        asignacion.setPuestoEnProyecto(dto.getPuestoEnProyecto());
        asignacion.setFechaInicio(dto.getFechaInicio());
        asignacion.setFechaFinEstimada(dto.getFechaFinEstimada());
    }

    private AsignacionTrabajadorProyecto getOrThrow(Integer id) {
        return asignacionTpRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Asignación trabajador-proyecto no encontrada con id: " + id));
    }

    private Trabajador getTrabajadorOrThrow(Integer id) {
        return trabajadorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Trabajador no encontrado con id: " + id));
    }

    private Proyecto getProyectoOrThrow(Integer id) {
        return proyectoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proyecto no encontrado con id: " + id));
    }

    private AsignacionProyectoContratista getAsignacionPcOrThrow(Integer id) {
        return asignacionPcRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Asignación proyecto-contratista no encontrada con id: " + id));
    }
}
