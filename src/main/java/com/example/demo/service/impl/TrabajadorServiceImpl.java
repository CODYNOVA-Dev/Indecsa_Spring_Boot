package com.example.demo.service.impl;

import com.example.demo.dto.request.TrabajadorRequestDTO;
import com.example.demo.dto.response.DomicilioResponseDTO;
import com.example.demo.dto.response.TrabajadorResponseDTO;
import com.example.demo.model.Domicilio;
import com.example.demo.model.Estado;
import com.example.demo.model.Trabajador;
import com.example.demo.model.Trabajador.EstadoTrabajador;
import com.example.demo.repository.DomicilioRepository;
import com.example.demo.repository.TrabajadorRepository;
import com.example.demo.service.TrabajadorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TrabajadorServiceImpl implements TrabajadorService {

    private final TrabajadorRepository trabajadorRepository;
    private final DomicilioRepository domicilioRepository;
    private final EstadoServiceImpl estadoService;

    // ─── FIND ALL ─────────────────────────────────────────────────────────────
    @Override
    public Page<TrabajadorResponseDTO> findAll(Pageable pageable) {
        return trabajadorRepository.findAll(pageable).map(this::toResponse);
    }

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
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<TrabajadorResponseDTO> findByEspecialidad(String especialidad) {
        return trabajadorRepository.findByEspecialidadTrabajadorContainingIgnoreCase(especialidad)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TrabajadorResponseDTO create(TrabajadorRequestDTO dto) {
        if (trabajadorRepository.existsByCorreoTrabajador(dto.getCorreoTrabajador())) {
            throw new IllegalArgumentException(
                    "Ya existe un trabajador con el correo: " + dto.getCorreoTrabajador());
        }
        if (dto.getNssTrabajador() != null
                && trabajadorRepository.existsByNssTrabajador(dto.getNssTrabajador())) {
            throw new IllegalArgumentException(
                    "Ya existe un trabajador con el NSS: " + dto.getNssTrabajador());
        }
        Domicilio domicilio = getDomicilioOrThrow(dto.getIdDomicilio());
        Estado estadoCalidadVida = estadoService.getEstadoOrThrow(dto.getIdEstadoCalidadVida());

        Trabajador trabajador = new Trabajador();
        mapDtoToEntity(dto, trabajador, domicilio, estadoCalidadVida);
        trabajador.setEstadoTrabajador(EstadoTrabajador.ACTIVO);
        return toResponse(trabajadorRepository.save(trabajador));
    }

    @Override
    @Transactional
    public TrabajadorResponseDTO create(TrabajadorRequestDTO request) {
        if (request.getCurp() != null && trabajadorRepository.existsByCurp(request.getCurp())) {
            throw new IllegalArgumentException("Ya existe un trabajador con la CURP: " + request.getCurp());
        }
        if (request.getRfc() != null && trabajadorRepository.existsByRfc(request.getRfc())) {
            throw new IllegalArgumentException("Ya existe un trabajador con el RFC: " + request.getRfc());
        }
        if (request.getCorreoTrabajador() != null
                && trabajadorRepository.existsByCorreoTrabajador(request.getCorreoTrabajador())) {
            throw new IllegalArgumentException("Ya existe un trabajador con el correo: " + request.getCorreoTrabajador());
        }

        RegistroMigratorio migratorio = null;
        if (request.getIdMigratorio() != null) {
            migratorio = migratorioRepository.findById(request.getIdMigratorio())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Registro migratorio no encontrado con id: " + request.getIdMigratorio()));
        }
        if (dto.getNssTrabajador() != null
                && !dto.getNssTrabajador().equals(trabajador.getNssTrabajador())
                && trabajadorRepository.existsByNssTrabajador(dto.getNssTrabajador())) {
            throw new IllegalArgumentException(
                    "Ya existe un trabajador con el NSS: " + dto.getNssTrabajador());
        }
        Domicilio domicilio = getDomicilioOrThrow(dto.getIdDomicilio());
        Estado estadoCalidadVida = estadoService.getEstadoOrThrow(dto.getIdEstadoCalidadVida());

        mapDtoToEntity(dto, trabajador, domicilio, estadoCalidadVida);
        return toResponse(trabajadorRepository.save(trabajador));
    }

    @Override
    @Transactional
    public TrabajadorResponseDTO update(Integer id, TrabajadorRequestDTO request) {
        Trabajador trabajador = getOrThrow(id);

        if (request.getCurp() != null && !request.getCurp().equals(trabajador.getCurp())
                && trabajadorRepository.existsByCurp(request.getCurp())) {
            throw new IllegalArgumentException("Ya existe un trabajador con la CURP: " + request.getCurp());
        }
        if (request.getRfc() != null && !request.getRfc().equals(trabajador.getRfc())
                && trabajadorRepository.existsByRfc(request.getRfc())) {
            throw new IllegalArgumentException("Ya existe un trabajador con el RFC: " + request.getRfc());
        }
        if (request.getCorreoTrabajador() != null
                && !request.getCorreoTrabajador().equals(trabajador.getCorreoTrabajador())
                && trabajadorRepository.existsByCorreoTrabajador(request.getCorreoTrabajador())) {
            throw new IllegalArgumentException("Ya existe un trabajador con el correo: " + request.getCorreoTrabajador());
        }

        applyUpdates(trabajador, request);
        return toResponse(trabajadorRepository.save(trabajador));
    }

    @Override
    @Transactional
    public TrabajadorResponseDTO cambiarEstado(Integer id, Trabajador.EstadoTrabajador estado) {
        Trabajador trabajador = getOrThrow(id);
        trabajador.setEstadoTrabajador(estado);
        return toResponse(trabajadorRepository.save(trabajador));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        getOrThrow(id);
        trabajadorRepository.deleteById(id);
    }

    // ─── HELPERS ──────────────────────────────────────────────────────────────
    private Trabajador getTrabajadorOrThrow(Integer id) {
        return trabajadorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Trabajador no encontrado con id: " + id));
    }

    private Domicilio getDomicilioOrThrow(Integer id) {
        return domicilioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Domicilio no encontrado con id: " + id));
    }

    private void mapDtoToEntity(TrabajadorRequestDTO dto, Trabajador t,
                                Domicilio domicilio, Estado estadoCalidadVida) {
        t.setNombreTrabajador(dto.getNombreTrabajador());
        t.setCurp(dto.getCurp());
        t.setRfc(dto.getRfc());
        t.setNssTrabajador(dto.getNssTrabajador());
        t.setNacionalidad(dto.getNacionalidad());
        t.setDomicilio(domicilio);
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
        t.setEstadoCalidadVida(estadoCalidadVida);
        t.setSexo(dto.getSexo());
        t.setAntPenal(dto.getAntPenal());
        t.setDeudorAlim(dto.getDeudorAlim());
        t.setFolioLicCond(dto.getFolioLicCond());
        t.setEstadoCivil(dto.getEstadoCivil());
        t.setIdiomas(dto.getIdiomas());
        t.setLenguaIndigena(dto.getLenguaIndigena());
    }

    // ─── MAPPER ───────────────────────────────────────────────────────────────
    public TrabajadorResponseDTO toResponse(Trabajador t) {
        DomicilioResponseDTO domicilioDTO = DomicilioResponseDTO.builder()
                .idDomicilio(t.getDomicilio().getIdDomicilio())
                .calle(t.getDomicilio().getCalle())
                .numExt(t.getDomicilio().getNumExt())
                .numInt(t.getDomicilio().getNumInt())
                .colonia(t.getDomicilio().getColonia())
                .codPost(t.getDomicilio().getCodPost())
                .munAlc(t.getDomicilio().getMunAlc())
                .idEstado(t.getDomicilio().getEstado().getIdEstado())
                .nombreEstado(t.getDomicilio().getEstado().getNombreEst())
                .build();

        return TrabajadorResponseDTO.builder()
                .idTrabajador(t.getIdTrabajador())
                .nombreTrabajador(t.getNombreTrabajador())
                .curp(t.getCurp())
                .rfc(t.getRfc())
                .nssTrabajador(t.getNssTrabajador())
                .nacionalidad(t.getNacionalidad())
                .idMigratorio(t.getRegistroMigratorio() != null
                        ? t.getRegistroMigratorio().getIdMigratorio() : null)
                .domicilio(domicilioDTO)
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
                .idEstadoCalidadVida(t.getEstadoCalidadVida().getIdEstado())
                .nombreEstadoCalidadVida(t.getEstadoCalidadVida().getNombreEst())
                .sexo(t.getSexo())
                .antPenal(t.getAntPenal())
                .deudorAlim(t.getDeudorAlim())
                .folioLicCond(t.getFolioLicCond())
                .estadoCivil(t.getEstadoCivil())
                .idiomas(t.getIdiomas())
                .lenguaIndigena(t.getLenguaIndigena())
                .build();
    }

    private Trabajador getOrThrow(Integer id) {
        return trabajadorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Trabajador no encontrado con id: " + id));
    }

    public TrabajadorResponseDTO toResponse(Trabajador t) {
        return TrabajadorResponseDTO.builder()
                .idTrabajador(t.getIdTrabajador())
                .nombreTrabajador(t.getNombreTrabajador())
                .curp(t.getCurp())
                .rfc(t.getRfc())
                .nssTrabajador(t.getNssTrabajador())
                .nacionalidad(t.getNacionalidad())
                .idMigratorio(t.getRegistroMigratorio() != null
                        ? t.getRegistroMigratorio().getIdMigratorio() : null)
                .calle(t.getCalle())
                .numExt(t.getNumExt())
                .numInt(t.getNumInt())
                .colonia(t.getColonia())
                .codPost(t.getCodPost())
                .munAlc(t.getMunAlc())
                .estado(t.getEstado())
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
                .descripcionTrabajador(t.getDescripcionTrabajador())
                .evaluacionTrabajador(t.getEvaluacionTrabajador())
                .fechaIngreso(t.getFechaIngreso())
                .calidadVida(t.getCalidadVida())
                .antPenal(t.getAntPenal())
                .deudorAlim(t.getDeudorAlim())
                .folioLicCond(t.getFolioLicCond())
                .estadoCivil(t.getEstadoCivil())
                .idiomas(t.getIdiomas())
                .lenguaIndigena(t.getLenguaIndigena())
                .sexo(t.getSexo())
                .build();
    }
}
