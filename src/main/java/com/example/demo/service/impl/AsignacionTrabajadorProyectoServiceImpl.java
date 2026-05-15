package com.example.demo.service.impl;

import com.example.demo.dto.request.AsignacionTrabajadorProyectoRequestDTO;
import com.example.demo.dto.response.AsignacionProyectoContratistaResponseDTO;
import com.example.demo.dto.response.AsignacionTrabajadorProyectoResponseDTO;
import com.example.demo.dto.response.ContratistaResponseDTO;
import com.example.demo.dto.response.ProyectoResponseDTO;
import com.example.demo.dto.response.TrabajadorResponseDTO;
import com.example.demo.model.*;
import com.example.demo.model.AsignacionTrabajadorProyecto.EstatusAsignacion;
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
public class AsignacionTrabajadorProyectoServiceImpl implements AsignacionTrabajadorProyectoService {

    private final AsignacionTrabajadorProyectoRepository asignacionRepository;
    private final TrabajadorRepository trabajadorRepository;
    private final ProyectoRepository proyectoRepository;
    private final AsignacionProyectoContratistaRepository asignacionPcRepository;

    // ─── FIND ALL ─────────────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<AsignacionTrabajadorProyectoResponseDTO> findAll() {
        return asignacionRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─── FIND BY ID ───────────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public AsignacionTrabajadorProyectoResponseDTO findById(Integer id) {
        return toResponse(getAsignacionOrThrow(id));
    }

    // ─── FIND BY PROYECTO ─────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<AsignacionTrabajadorProyectoResponseDTO> findByProyecto(Integer idProyecto) {
        return asignacionRepository.findByProyecto_IdProyecto(idProyecto)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─── FIND BY TRABAJADOR ───────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<AsignacionTrabajadorProyectoResponseDTO> findByTrabajador(Integer idTrabajador) {
        return asignacionRepository.findByTrabajador_IdTrabajador(idTrabajador)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─── FIND BY ASIGNACION PC ────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<AsignacionTrabajadorProyectoResponseDTO> findByAsignacionPc(Integer idAsignacionPc) {
        return asignacionRepository.findByAsignacionProyectoContratista_IdAsignacionPc(idAsignacionPc)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─── FIND BY ESTATUS ──────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<AsignacionTrabajadorProyectoResponseDTO> findByEstatus(EstatusAsignacion estatus) {
        return asignacionRepository.findByEstatusAsignacion(estatus)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─── CREATE ───────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public AsignacionTrabajadorProyectoResponseDTO create(AsignacionTrabajadorProyectoRequestDTO dto) {
        Trabajador trabajador = trabajadorRepository.findById(dto.getIdTrabajador())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Trabajador no encontrado con id: " + dto.getIdTrabajador()));

        Proyecto proyecto = proyectoRepository.findById(dto.getIdProyecto())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Proyecto no encontrado con id: " + dto.getIdProyecto()));

        AsignacionProyectoContratista asignacionPc = asignacionPcRepository
                .findById(dto.getIdAsignacionPc())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Contrato (asignacion_pc) no encontrado con id: " + dto.getIdAsignacionPc()));

        if (!asignacionPc.getProyecto().getIdProyecto().equals(dto.getIdProyecto())) {
            throw new IllegalArgumentException(
                    "El contrato indicado no pertenece al proyecto especificado.");
        }

        if (asignacionRepository.existsByTrabajador_IdTrabajadorAndProyecto_IdProyecto(
                dto.getIdTrabajador(), dto.getIdProyecto())) {
            throw new IllegalArgumentException(
                    "El trabajador ya está asignado a este proyecto.");
        }

        AsignacionTrabajadorProyecto asignacion = new AsignacionTrabajadorProyecto();
        asignacion.setTrabajador(trabajador);
        asignacion.setProyecto(proyecto);
        asignacion.setAsignacionProyectoContratista(asignacionPc);
        mapDtoToEntity(dto, asignacion);
        asignacion.setEstatusAsignacion(EstatusAsignacion.ACTIVO);
        return toResponse(asignacionRepository.save(asignacion));
    }

    // ─── UPDATE ───────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public AsignacionTrabajadorProyectoResponseDTO update(Integer id, AsignacionTrabajadorProyectoRequestDTO dto) {
        AsignacionTrabajadorProyecto asignacion = getAsignacionOrThrow(id);

        Trabajador trabajador = trabajadorRepository.findById(dto.getIdTrabajador())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Trabajador no encontrado con id: " + dto.getIdTrabajador()));

        Proyecto proyecto = proyectoRepository.findById(dto.getIdProyecto())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Proyecto no encontrado con id: " + dto.getIdProyecto()));

        AsignacionProyectoContratista asignacionPc = asignacionPcRepository
                .findById(dto.getIdAsignacionPc())
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
            throw new IllegalArgumentException(
                    "El trabajador ya está asignado a este proyecto.");
        }

        asignacion.setTrabajador(trabajador);
        asignacion.setProyecto(proyecto);
        asignacion.setAsignacionProyectoContratista(asignacionPc);
        mapDtoToEntity(dto, asignacion);
        return toResponse(asignacionRepository.save(asignacion));
    }

    // ─── CAMBIAR ESTATUS ──────────────────────────────────────────────────────
    @Override
    @Transactional
    public AsignacionTrabajadorProyectoResponseDTO cambiarEstatus(Integer id, EstatusAsignacion estatus) {
        AsignacionTrabajadorProyecto asignacion = getAsignacionOrThrow(id);
        asignacion.setEstatusAsignacion(estatus);
        return toResponse(asignacionRepository.save(asignacion));
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public void delete(Integer id) {
        if (!asignacionRepository.existsById(id)) {
            throw new EntityNotFoundException("Asignación no encontrada con id: " + id);
        }
        asignacionRepository.deleteById(id);
    }

    // ─── HELPERS ──────────────────────────────────────────────────────────────
    private AsignacionTrabajadorProyecto getAsignacionOrThrow(Integer id) {
        return asignacionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Asignación trabajador-proyecto no encontrada con id: " + id));
    }

    private void mapDtoToEntity(AsignacionTrabajadorProyectoRequestDTO dto,
                                AsignacionTrabajadorProyecto asignacion) {
        asignacion.setPuestoEnProyecto(dto.getPuestoEnProyecto());
        asignacion.setFechaInicio(dto.getFechaInicio());
        asignacion.setFechaFinEstimada(dto.getFechaFinEstimada());
    }

    // ─── NESTED MAPPERS ───────────────────────────────────────────────────────
    private TrabajadorResponseDTO toTrabajadorResponse(Trabajador t) {
        Domicilio d = t.getDomicilio();
        Estado eca = t.getEstadoCalidadVida();
        return TrabajadorResponseDTO.builder()
                .idTrabajador(t.getIdTrabajador())
                .nombreTrabajador(t.getNombreTrabajador())
                .curp(t.getCurp())
                .rfc(t.getRfc())
                .nssTrabajador(t.getNssTrabajador())
                .nacionalidad(t.getNacionalidad())
                .idMigratorio(t.getRegistroMigratorio() != null
                        ? t.getRegistroMigratorio().getIdMigratorio() : null)
                .idDomicilio(d.getIdDomicilio())
                .calleDomicilio(d.getCalle() + " " + d.getNumExt())
                .munAlcDomicilio(d.getMunAlc())
                .nombreEstadoDomicilio(d.getEstado().getNombreEst())
                .fotoPerfilUrl(t.getFotoPerfilUrl())
                .puesto(t.getPuesto())
                .descPuesto(t.getDescPuesto())
                .especialidadTrabajador(t.getEspecialidadTrabajador())
                .escolaridad(t.getEscolaridad())
                .experiencia(t.getExperiencia())
                .telefonoTrabajador(t.getTelefonoTrabajador())
                .correoTrabajador(t.getCorreoTrabajador())
                .contratacion(t.getContratacion())
                .jornada(t.getJornada())
                .estadoTrabajador(t.getEstadoTrabajador())
                .evaluacionTrabajador(t.getEvaluacionTrabajador())
                .fechaIngreso(t.getFechaIngreso())
                .idEstadoCalidadVida(eca.getIdEstado())
                .nombreEstadoCalidadVida(eca.getNombreEst())
                .sexo(t.getSexo())
                .antPenal(t.getAntPenal())
                .deudorAlim(t.getDeudorAlim())
                .folioLicCond(t.getFolioLicCond())
                .estadoCivil(t.getEstadoCivil())
                .idiomas(t.getIdiomas())
                .lenguaIndigena(t.getLenguaIndigena())
                .build();
    }

    private ProyectoResponseDTO toProyectoResponse(Proyecto p) {
        Domicilio d = p.getDomicilio();
        return ProyectoResponseDTO.builder()
                .idProyecto(p.getIdProyecto())
                .nombreProyecto(p.getNombreProyecto())
                .tipoProyecto(p.getTipoProyecto())
                .ofertaTrabajo(p.getOfertaTrabajo())
                .cliente(p.getCliente())
                .idDomicilio(d.getIdDomicilio())
                .calleDomicilio(d.getCalle() + " " + d.getNumExt())
                .munAlcDomicilio(d.getMunAlc())
                .nombreEstadoDomicilio(d.getEstado().getNombreEst())
                .fechaEstimadaInicio(p.getFechaEstimadaInicio())
                .fechaEstimadaFin(p.getFechaEstimadaFin())
                .calificacionProyecto(p.getCalificacionProyecto())
                .estatusProyecto(p.getEstatusProyecto())
                .descripcionProyecto(p.getDescripcionProyecto())
                .imagenProyectoUrl(p.getImagenProyectoUrl())
                .build();
    }

    private ContratistaResponseDTO toContratistaResponse(Contratista c) {
        return ContratistaResponseDTO.builder()
                .idContratista(c.getIdContratista())
                .nombreContratista(c.getNombreContratista())
                .curp(c.getCurp())
                .rfcContratista(c.getRfcContratista())
                .telefonoContratista(c.getTelefonoContratista())
                .correoContratista(c.getCorreoContratista())
                .descripcionContratista(c.getDescripcionContratista())
                .fotoPerfilUrl(c.getFotoPerfilUrl())
                .experiencia(c.getExperiencia())
                .calificacionContratista(c.getCalificacionContratista())
                .estadoContratista(c.getEstadoContratista())
                .idEstadoOperacion(c.getEstadoOperacion().getIdEstado())
                .nombreEstadoOperacion(c.getEstadoOperacion().getNombreEst())
                .build();
    }

    private AsignacionProyectoContratistaResponseDTO toApcResponse(AsignacionProyectoContratista apc) {
        return AsignacionProyectoContratistaResponseDTO.builder()
                .idAsignacionPc(apc.getIdAsignacionPc())
                .proyecto(toProyectoResponse(apc.getProyecto()))
                .contratista(toContratistaResponse(apc.getContratista()))
                .numeroContrato(apc.getNumeroContrato())
                .fechaInicio(apc.getFechaInicio())
                .fechaFinEstimada(apc.getFechaFinEstimada())
                .personalAsignado(apc.getPersonalAsignado())
                .estatusContrato(apc.getEstatusContrato())
                .observaciones(apc.getObservaciones())
                .build();
    }

    // ─── MAPPER ───────────────────────────────────────────────────────────────
    private AsignacionTrabajadorProyectoResponseDTO toResponse(AsignacionTrabajadorProyecto a) {
        return AsignacionTrabajadorProyectoResponseDTO.builder()
                .idAsignacionTp(a.getIdAsignacionTp())
                .trabajador(toTrabajadorResponse(a.getTrabajador()))
                .proyecto(toProyectoResponse(a.getProyecto()))
                .asignacionProyectoContratista(toApcResponse(a.getAsignacionProyectoContratista()))
                .puestoEnProyecto(a.getPuestoEnProyecto())
                .fechaInicio(a.getFechaInicio())
                .fechaFinEstimada(a.getFechaFinEstimada())
                .estatusAsignacion(a.getEstatusAsignacion())
                .build();
    }
}
