package com.example.demo.service.impl;

import com.example.demo.dto.trabajador.TrabajadorRequest;
import com.example.demo.dto.trabajador.TrabajadorResponse;
import com.example.demo.model.RegistroMigratorio;
import com.example.demo.model.Trabajador;
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
@Transactional(readOnly = true)
public class TrabajadorServiceImpl implements TrabajadorService {

    private final TrabajadorRepository trabajadorRepository;
    private final RegistroMigratorioRepository migratorioRepository;

    @Override
    public List<TrabajadorResponse> findAll() {
        return trabajadorRepository.findAll()
                .stream().map(TrabajadorResponse::from).collect(Collectors.toList());
    }

    @Override
    public List<TrabajadorResponse> findByEstado(Trabajador.EstadoTrabajador estado) {
        return trabajadorRepository.findByEstadoTrabajador(estado)
                .stream().map(TrabajadorResponse::from).collect(Collectors.toList());
    }

    @Override
    public List<TrabajadorResponse> findByEspecialidad(String especialidad) {
        return trabajadorRepository.findByEspecialidadTrabajadorContainingIgnoreCase(especialidad)
                .stream().map(TrabajadorResponse::from).collect(Collectors.toList());
    }

    @Override
    public TrabajadorResponse findById(Integer id) {
        return TrabajadorResponse.from(getOrThrow(id));
    }

    @Override
    @Transactional
    public TrabajadorResponse create(TrabajadorRequest request) {
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

        Trabajador trabajador = buildTrabajador(request, migratorio);
        return TrabajadorResponse.from(trabajadorRepository.save(trabajador));
    }

    @Override
    @Transactional
    public TrabajadorResponse update(Integer id, TrabajadorRequest request) {
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

        RegistroMigratorio migratorio = null;
        if (request.getIdMigratorio() != null) {
            migratorio = migratorioRepository.findById(request.getIdMigratorio())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Registro migratorio no encontrado con id: " + request.getIdMigratorio()));
        }

        if (request.getNombreTrabajador() != null) trabajador.setNombreTrabajador(request.getNombreTrabajador());
        if (request.getCurp() != null)               trabajador.setCurp(request.getCurp());
        if (request.getRfc() != null)                trabajador.setRfc(request.getRfc());
        if (request.getNssTrabajador() != null)      trabajador.setNssTrabajador(request.getNssTrabajador());
        if (request.getNacionalidad() != null)       trabajador.setNacionalidad(request.getNacionalidad());
        trabajador.setRegistroMigratorio(migratorio);
        if (request.getCalle() != null)              trabajador.setCalle(request.getCalle());
        if (request.getNumExt() != null)             trabajador.setNumExt(request.getNumExt());
        if (request.getNumInt() != null)             trabajador.setNumInt(request.getNumInt());
        if (request.getColonia() != null)            trabajador.setColonia(request.getColonia());
        if (request.getCodPost() != null)            trabajador.setCodPost(request.getCodPost());
        if (request.getMunAlc() != null)             trabajador.setMunAlc(request.getMunAlc());
        if (request.getEstado() != null)             trabajador.setEstado(request.getEstado());
        if (request.getPuesto() != null)             trabajador.setPuesto(request.getPuesto());
        if (request.getDescPuesto() != null)         trabajador.setDescPuesto(request.getDescPuesto());
        if (request.getEspecialidadTrabajador() != null) trabajador.setEspecialidadTrabajador(request.getEspecialidadTrabajador());
        if (request.getEscolaridad() != null)        trabajador.setEscolaridad(request.getEscolaridad());
        if (request.getExperiencia() != null)        trabajador.setExperiencia(request.getExperiencia());
        if (request.getTelefonoTrabajador() != null) trabajador.setTelefonoTrabajador(request.getTelefonoTrabajador());
        if (request.getCorreoTrabajador() != null)   trabajador.setCorreoTrabajador(request.getCorreoTrabajador());
        if (request.getContratacion() != null)       trabajador.setContratacion(request.getContratacion());
        if (request.getJornada() != null)            trabajador.setJornada(request.getJornada());
        if (request.getEstadoTrabajador() != null)   trabajador.setEstadoTrabajador(request.getEstadoTrabajador());
        if (request.getDescripcionTrabajador() != null) trabajador.setDescripcionTrabajador(request.getDescripcionTrabajador());
        if (request.getEvaluacionTrabajador() != null)  trabajador.setEvaluacionTrabajador(request.getEvaluacionTrabajador());
        if (request.getFechaIngreso() != null)       trabajador.setFechaIngreso(request.getFechaIngreso());
        if (request.getCalidadVida() != null)        trabajador.setCalidadVida(request.getCalidadVida());
        if (request.getAntPenal() != null)           trabajador.setAntPenal(request.getAntPenal());
        if (request.getDeudorAlim() != null)         trabajador.setDeudorAlim(request.getDeudorAlim());
        if (request.getFolioLicCond() != null)       trabajador.setFolioLicCond(request.getFolioLicCond());
        if (request.getEstadoCivil() != null)        trabajador.setEstadoCivil(request.getEstadoCivil());
        if (request.getIdiomas() != null)            trabajador.setIdiomas(request.getIdiomas());
        if (request.getLenguaIndigena() != null)     trabajador.setLenguaIndigena(request.getLenguaIndigena());
        if (request.getSexo() != null)               trabajador.setSexo(request.getSexo());

        return TrabajadorResponse.from(trabajadorRepository.save(trabajador));
    }

    @Override
    @Transactional
    public TrabajadorResponse cambiarEstado(Integer id, Trabajador.EstadoTrabajador estado) {
        Trabajador trabajador = getOrThrow(id);
        trabajador.setEstadoTrabajador(estado);
        return TrabajadorResponse.from(trabajadorRepository.save(trabajador));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        getOrThrow(id);
        trabajadorRepository.deleteById(id);
    }

    private Trabajador buildTrabajador(TrabajadorRequest r, RegistroMigratorio migratorio) {
        return Trabajador.builder()
                .nombreTrabajador(r.getNombreTrabajador())
                .curp(r.getCurp())
                .rfc(r.getRfc())
                .nssTrabajador(r.getNssTrabajador())
                .nacionalidad(r.getNacionalidad() != null ? r.getNacionalidad() : "Mexicana")
                .registroMigratorio(migratorio)
                .calle(r.getCalle() != null ? r.getCalle() : "")
                .numExt(r.getNumExt() != null ? r.getNumExt() : "S/N")
                .numInt(r.getNumInt())
                .colonia(r.getColonia() != null ? r.getColonia() : "")
                .codPost(r.getCodPost() != null ? r.getCodPost() : 0)
                .munAlc(r.getMunAlc() != null ? r.getMunAlc() : "")
                .estado(r.getEstado() != null ? r.getEstado() : "")
                .puesto(r.getPuesto() != null ? r.getPuesto() : "")
                .descPuesto(r.getDescPuesto() != null ? r.getDescPuesto() : "")
                .especialidadTrabajador(r.getEspecialidadTrabajador() != null ? r.getEspecialidadTrabajador() : "")
                .escolaridad(r.getEscolaridad() != null ? r.getEscolaridad() : "")
                .experiencia(r.getExperiencia())
                .telefonoTrabajador(r.getTelefonoTrabajador() != null ? r.getTelefonoTrabajador() : "")
                .correoTrabajador(r.getCorreoTrabajador() != null ? r.getCorreoTrabajador() : "")
                .contratacion(r.getContratacion() != null ? r.getContratacion() : "")
                .jornada(r.getJornada() != null ? r.getJornada() : "")
                .estadoTrabajador(r.getEstadoTrabajador() != null
                        ? r.getEstadoTrabajador()
                        : Trabajador.EstadoTrabajador.ACTIVO)
                .descripcionTrabajador(r.getDescripcionTrabajador())
                .evaluacionTrabajador(r.getEvaluacionTrabajador())
                .fechaIngreso(r.getFechaIngreso())
                .calidadVida(r.getCalidadVida())
                .antPenal(r.getAntPenal())
                .deudorAlim(r.getDeudorAlim())
                .folioLicCond(r.getFolioLicCond())
                .estadoCivil(r.getEstadoCivil())
                .idiomas(r.getIdiomas())
                .lenguaIndigena(r.getLenguaIndigena())
                .sexo(r.getSexo())
                .build();
    }

    private Trabajador getOrThrow(Integer id) {
        return trabajadorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Trabajador no encontrado con id: " + id));
    }
}
