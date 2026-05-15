package com.example.demo.service.impl;

import com.example.demo.dto.request.TrabajadorRequestDTO;
import com.example.demo.dto.response.TrabajadorResponseDTO;
import com.example.demo.model.RegistroMigratorio;
import com.example.demo.model.Trabajador;
import com.example.demo.repository.RegistroMigratorioRepository;
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
    private final RegistroMigratorioRepository migratorioRepository;

    @Override
    public Page<TrabajadorResponseDTO> findAll(Pageable pageable) {
        return trabajadorRepository.findAll(pageable).map(this::toResponse);
    }

    @Override
    public List<TrabajadorResponseDTO> findByEstado(Trabajador.EstadoTrabajador estado) {
        return trabajadorRepository.findByEstadoTrabajador(estado)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<TrabajadorResponseDTO> findByEspecialidad(String especialidad) {
        return trabajadorRepository.findByEspecialidadTrabajadorContainingIgnoreCase(especialidad)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public TrabajadorResponseDTO findById(Integer id) {
        return toResponse(getOrThrow(id));
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

        Trabajador trabajador = buildFromRequest(new Trabajador(), request);
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

    private Trabajador buildFromRequest(Trabajador t, TrabajadorRequestDTO r) {
        RegistroMigratorio migratorio = null;
        if (r.getIdMigratorio() != null) {
            migratorio = migratorioRepository.findById(r.getIdMigratorio())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Registro migratorio no encontrado con id: " + r.getIdMigratorio()));
        }
        t.setNombreTrabajador(r.getNombreTrabajador());
        t.setCurp(r.getCurp());
        t.setRfc(r.getRfc());
        t.setNssTrabajador(r.getNssTrabajador());
        t.setNacionalidad(r.getNacionalidad() != null ? r.getNacionalidad() : "Mexicana");
        t.setRegistroMigratorio(migratorio);
        t.setCalle(r.getCalle() != null ? r.getCalle() : "");
        t.setNumExt(r.getNumExt() != null ? r.getNumExt() : "S/N");
        t.setNumInt(r.getNumInt());
        t.setColonia(r.getColonia() != null ? r.getColonia() : "");
        t.setCodPost(r.getCodPost() != null ? r.getCodPost() : 0);
        t.setMunAlc(r.getMunAlc() != null ? r.getMunAlc() : "");
        t.setEstado(r.getEstado() != null ? r.getEstado() : "");
        t.setPuesto(r.getPuesto() != null ? r.getPuesto() : "");
        t.setDescPuesto(r.getDescPuesto() != null ? r.getDescPuesto() : "");
        t.setEspecialidadTrabajador(r.getEspecialidadTrabajador() != null ? r.getEspecialidadTrabajador() : "");
        t.setEscolaridad(r.getEscolaridad() != null ? r.getEscolaridad() : "");
        t.setExperiencia(r.getExperiencia());
        t.setTelefonoTrabajador(r.getTelefonoTrabajador() != null ? r.getTelefonoTrabajador() : "");
        t.setCorreoTrabajador(r.getCorreoTrabajador() != null ? r.getCorreoTrabajador() : "");
        t.setContratacion(r.getContratacion() != null ? r.getContratacion() : "");
        t.setJornada(r.getJornada() != null ? r.getJornada() : "");
        t.setEstadoTrabajador(r.getEstadoTrabajador() != null
                ? r.getEstadoTrabajador() : Trabajador.EstadoTrabajador.ACTIVO);
        t.setDescripcionTrabajador(r.getDescripcionTrabajador());
        t.setEvaluacionTrabajador(r.getEvaluacionTrabajador());
        t.setFechaIngreso(r.getFechaIngreso());
        t.setCalidadVida(r.getCalidadVida());
        t.setAntPenal(r.getAntPenal());
        t.setDeudorAlim(r.getDeudorAlim());
        t.setFolioLicCond(r.getFolioLicCond());
        t.setEstadoCivil(r.getEstadoCivil());
        t.setIdiomas(r.getIdiomas());
        t.setLenguaIndigena(r.getLenguaIndigena());
        t.setSexo(r.getSexo());
        return t;
    }

    private void applyUpdates(Trabajador t, TrabajadorRequestDTO r) {
        if (r.getIdMigratorio() != null) {
            t.setRegistroMigratorio(migratorioRepository.findById(r.getIdMigratorio())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Registro migratorio no encontrado con id: " + r.getIdMigratorio())));
        } else {
            t.setRegistroMigratorio(null);
        }
        if (r.getNombreTrabajador() != null)        t.setNombreTrabajador(r.getNombreTrabajador());
        if (r.getCurp() != null)                    t.setCurp(r.getCurp());
        if (r.getRfc() != null)                     t.setRfc(r.getRfc());
        if (r.getNssTrabajador() != null)           t.setNssTrabajador(r.getNssTrabajador());
        if (r.getNacionalidad() != null)            t.setNacionalidad(r.getNacionalidad());
        if (r.getCalle() != null)                   t.setCalle(r.getCalle());
        if (r.getNumExt() != null)                  t.setNumExt(r.getNumExt());
        if (r.getNumInt() != null)                  t.setNumInt(r.getNumInt());
        if (r.getColonia() != null)                 t.setColonia(r.getColonia());
        if (r.getCodPost() != null)                 t.setCodPost(r.getCodPost());
        if (r.getMunAlc() != null)                  t.setMunAlc(r.getMunAlc());
        if (r.getEstado() != null)                  t.setEstado(r.getEstado());
        if (r.getPuesto() != null)                  t.setPuesto(r.getPuesto());
        if (r.getDescPuesto() != null)              t.setDescPuesto(r.getDescPuesto());
        if (r.getEspecialidadTrabajador() != null)  t.setEspecialidadTrabajador(r.getEspecialidadTrabajador());
        if (r.getEscolaridad() != null)             t.setEscolaridad(r.getEscolaridad());
        if (r.getExperiencia() != null)             t.setExperiencia(r.getExperiencia());
        if (r.getTelefonoTrabajador() != null)      t.setTelefonoTrabajador(r.getTelefonoTrabajador());
        if (r.getCorreoTrabajador() != null)        t.setCorreoTrabajador(r.getCorreoTrabajador());
        if (r.getContratacion() != null)            t.setContratacion(r.getContratacion());
        if (r.getJornada() != null)                 t.setJornada(r.getJornada());
        if (r.getEstadoTrabajador() != null)        t.setEstadoTrabajador(r.getEstadoTrabajador());
        if (r.getDescripcionTrabajador() != null)   t.setDescripcionTrabajador(r.getDescripcionTrabajador());
        if (r.getEvaluacionTrabajador() != null)    t.setEvaluacionTrabajador(r.getEvaluacionTrabajador());
        if (r.getFechaIngreso() != null)            t.setFechaIngreso(r.getFechaIngreso());
        if (r.getCalidadVida() != null)             t.setCalidadVida(r.getCalidadVida());
        if (r.getAntPenal() != null)                t.setAntPenal(r.getAntPenal());
        if (r.getDeudorAlim() != null)              t.setDeudorAlim(r.getDeudorAlim());
        if (r.getFolioLicCond() != null)            t.setFolioLicCond(r.getFolioLicCond());
        if (r.getEstadoCivil() != null)             t.setEstadoCivil(r.getEstadoCivil());
        if (r.getIdiomas() != null)                 t.setIdiomas(r.getIdiomas());
        if (r.getLenguaIndigena() != null)          t.setLenguaIndigena(r.getLenguaIndigena());
        if (r.getSexo() != null)                    t.setSexo(r.getSexo());
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
