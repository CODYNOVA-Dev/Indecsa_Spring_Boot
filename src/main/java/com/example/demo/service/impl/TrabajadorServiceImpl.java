package com.example.demo.service.impl;

import com.example.demo.dto.request.TrabajadorRequestDTO;
import com.example.demo.dto.response.TrabajadorResponseDTO;
import com.example.demo.model.Domicilio;
import com.example.demo.model.Estado;
import com.example.demo.model.RegistroMigratorio;
import com.example.demo.model.Trabajador;
import com.example.demo.model.Trabajador.EstadoTrabajador;
import com.example.demo.repository.DomicilioRepository;
import com.example.demo.repository.EstadoRepository;
import com.example.demo.repository.RegistroMigratorioRepository;
import com.example.demo.repository.TrabajadorRepository;
import com.example.demo.service.TrabajadorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrabajadorServiceImpl implements TrabajadorService {

    private final TrabajadorRepository trabajadorRepository;
    private final DomicilioRepository domicilioRepository;
    private final EstadoRepository estadoRepository;
    private final RegistroMigratorioRepository registroMigratorioRepository;

    // ─── FIND ALL ─────────────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<TrabajadorResponseDTO> findAll() {
        return trabajadorRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─── FIND BY ID ───────────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public TrabajadorResponseDTO findById(Integer id) {
        return toResponse(getTrabajadorOrThrow(id));
    }

    // ─── FIND BY ESTADO ───────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<TrabajadorResponseDTO> findByEstado(EstadoTrabajador estado) {
        return trabajadorRepository.findByEstadoTrabajador(estado)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─── FIND BY ESPECIALIDAD ─────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<TrabajadorResponseDTO> findByEspecialidad(String especialidad) {
        return trabajadorRepository.findByEspecialidadTrabajadorContainingIgnoreCase(especialidad)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─── CREATE ───────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public TrabajadorResponseDTO create(TrabajadorRequestDTO dto) {
        validarUnicos(dto, null);
        Trabajador trabajador = new Trabajador();
        mapDtoToEntity(dto, trabajador);
        trabajador.setEstadoTrabajador(EstadoTrabajador.ACTIVO);
        return toResponse(trabajadorRepository.save(trabajador));
    }

    // ─── UPDATE ───────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public TrabajadorResponseDTO update(Integer id, TrabajadorRequestDTO dto) {
        Trabajador trabajador = getTrabajadorOrThrow(id);
        validarUnicos(dto, trabajador);
        mapDtoToEntity(dto, trabajador);
        return toResponse(trabajadorRepository.save(trabajador));
    }

    // ─── CAMBIAR ESTADO ───────────────────────────────────────────────────────
    @Override
    @Transactional
    public TrabajadorResponseDTO cambiarEstado(Integer id, EstadoTrabajador estado) {
        Trabajador trabajador = getTrabajadorOrThrow(id);
        trabajador.setEstadoTrabajador(estado);
        return toResponse(trabajadorRepository.save(trabajador));
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public void delete(Integer id) {
        if (!trabajadorRepository.existsById(id)) {
            throw new EntityNotFoundException("Trabajador no encontrado con id: " + id);
        }
        trabajadorRepository.deleteById(id);
    }

    // ─── HELPERS ──────────────────────────────────────────────────────────────
    private Trabajador getTrabajadorOrThrow(Integer id) {
        return trabajadorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Trabajador no encontrado con id: " + id));
    }

    private void validarUnicos(TrabajadorRequestDTO dto, Trabajador existente) {
        boolean esNuevo = existente == null;

        if (esNuevo || !existente.getCorreoTrabajador().equals(dto.getCorreoTrabajador())) {
            if (trabajadorRepository.existsByCorreoTrabajador(dto.getCorreoTrabajador()))
                throw new IllegalArgumentException("Ya existe un trabajador con el correo: " + dto.getCorreoTrabajador());
        }
        if (esNuevo || !existente.getCurp().equals(dto.getCurp())) {
            if (trabajadorRepository.existsByCurp(dto.getCurp()))
                throw new IllegalArgumentException("Ya existe un trabajador con el CURP: " + dto.getCurp());
        }
        if (esNuevo || !existente.getRfc().equals(dto.getRfc())) {
            if (trabajadorRepository.existsByRfc(dto.getRfc()))
                throw new IllegalArgumentException("Ya existe un trabajador con el RFC: " + dto.getRfc());
        }
        if (dto.getNssTrabajador() != null) {
            boolean nssDistinto = esNuevo || !dto.getNssTrabajador().equals(existente.getNssTrabajador());
            if (nssDistinto && trabajadorRepository.existsByNssTrabajador(dto.getNssTrabajador()))
                throw new IllegalArgumentException("Ya existe un trabajador con el NSS: " + dto.getNssTrabajador());
        }
    }

    private void mapDtoToEntity(TrabajadorRequestDTO dto, Trabajador t) {
        t.setNombreTrabajador(dto.getNombreTrabajador());
        t.setCurp(dto.getCurp().toUpperCase().trim());
        t.setRfc(dto.getRfc().toUpperCase().trim());
        t.setNssTrabajador(dto.getNssTrabajador());
        t.setNacionalidad(dto.getNacionalidad());
        t.setDomicilio(getDomicilioOrThrow(dto.getIdDomicilio()));
        t.setFotoPerfilUrl(dto.getFotoPerfilUrl());
        t.setPuesto(dto.getPuesto());
        t.setDescPuesto(dto.getDescPuesto());
        t.setEspecialidadTrabajador(dto.getEspecialidadTrabajador());
        t.setEscolaridad(dto.getEscolaridad());
        t.setExperiencia(dto.getExperiencia());
        t.setTelefonoTrabajador(dto.getTelefonoTrabajador());
        t.setCorreoTrabajador(dto.getCorreoTrabajador().toLowerCase().trim());
        t.setContratacion(dto.getContratacion());
        t.setJornada(dto.getJornada());
        t.setEvaluacionTrabajador(dto.getEvaluacionTrabajador());
        t.setFechaIngreso(dto.getFechaIngreso());
        t.setEstadoCalidadVida(getEstadoOrThrow(dto.getIdEstadoCalidadVida()));
        t.setSexo(dto.getSexo());
        t.setAntPenal(dto.getAntPenal());
        t.setDeudorAlim(dto.getDeudorAlim());
        t.setFolioLicCond(dto.getFolioLicCond());
        t.setEstadoCivil(dto.getEstadoCivil());
        t.setIdiomas(dto.getIdiomas());
        t.setLenguaIndigena(dto.getLenguaIndigena());

        if (dto.getIdMigratorio() != null) {
            t.setRegistroMigratorio(registroMigratorioRepository.findById(dto.getIdMigratorio())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Registro migratorio no encontrado con id: " + dto.getIdMigratorio())));
        } else {
            t.setRegistroMigratorio(null);
        }
    }

    private Domicilio getDomicilioOrThrow(Integer idDomicilio) {
        return domicilioRepository.findById(idDomicilio)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Domicilio no encontrado con id: " + idDomicilio));
    }

    private Estado getEstadoOrThrow(Integer idEstado) {
        return estadoRepository.findById(idEstado)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Estado no encontrado con id: " + idEstado));
    }

    // ─── MAPPER ───────────────────────────────────────────────────────────────
    private TrabajadorResponseDTO toResponse(Trabajador t) {
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
}
