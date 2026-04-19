package com.indecsa.service.impl;

import com.indecsa.dto.trabajador.TrabajadorRequest;
import com.indecsa.dto.trabajador.TrabajadorResponse;
import com.indecsa.model.RegistroMigratorio;
import com.indecsa.model.Trabajador;
import com.indecsa.repository.RegistroMigratorioRepository;
import com.indecsa.repository.TrabajadorRepository;
import com.indecsa.service.TrabajadorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TrabajadorServiceImpl implements TrabajadorService {

    private final TrabajadorRepository trabajadorRepository;
    private final RegistroMigratorioRepository migratorioRepository;

    @Override
    public Page<TrabajadorResponse> findByFiltros(
            String nombre,
            Trabajador.EstadoTrabajador estado,
            String especialidad,
            String puesto,
            Trabajador.EntidadFederativa calidadVida,
            Pageable pageable
    ) {
        return trabajadorRepository
                .findByFiltros(nombre, estado, especialidad, puesto, calidadVida, pageable)
                .map(TrabajadorResponse::from);
    }

    @Override
    public TrabajadorResponse findById(Integer id) {
        return TrabajadorResponse.from(getOrThrow(id));
    }

    @Override
    @Transactional
    public TrabajadorResponse create(TrabajadorRequest request) {
        if (trabajadorRepository.existsByCurp(request.getCurp())) {
            throw new IllegalArgumentException("Ya existe un trabajador con la CURP: " + request.getCurp());
        }
        if (trabajadorRepository.existsByRfc(request.getRfc())) {
            throw new IllegalArgumentException("Ya existe un trabajador con el RFC: " + request.getRfc());
        }
        if (trabajadorRepository.existsByCorreoTrabajador(request.getCorreoTrabajador())) {
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

        if (!trabajador.getCurp().equals(request.getCurp())
                && trabajadorRepository.existsByCurp(request.getCurp())) {
            throw new IllegalArgumentException("Ya existe un trabajador con la CURP: " + request.getCurp());
        }
        if (!trabajador.getRfc().equals(request.getRfc())
                && trabajadorRepository.existsByRfc(request.getRfc())) {
            throw new IllegalArgumentException("Ya existe un trabajador con el RFC: " + request.getRfc());
        }
        if (!trabajador.getCorreoTrabajador().equals(request.getCorreoTrabajador())
                && trabajadorRepository.existsByCorreoTrabajador(request.getCorreoTrabajador())) {
            throw new IllegalArgumentException("Ya existe un trabajador con el correo: " + request.getCorreoTrabajador());
        }

        RegistroMigratorio migratorio = null;
        if (request.getIdMigratorio() != null) {
            migratorio = migratorioRepository.findById(request.getIdMigratorio())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Registro migratorio no encontrado con id: " + request.getIdMigratorio()));
        }

        // Datos personales
        trabajador.setNombreTrabajador(request.getNombreTrabajador());
        trabajador.setCurp(request.getCurp());
        trabajador.setRfc(request.getRfc());
        trabajador.setNssTrabajador(request.getNssTrabajador());
        trabajador.setNacionalidad(request.getNacionalidad());
        trabajador.setRegistroMigratorio(migratorio);
        // Domicilio
        trabajador.setCalle(request.getCalle());
        trabajador.setNumExt(request.getNumExt());
        trabajador.setNumInt(request.getNumInt());
        trabajador.setColonia(request.getColonia());
        trabajador.setCodPost(request.getCodPost());
        trabajador.setMunAlc(request.getMunAlc());
        trabajador.setEstado(request.getEstado());
        // Laboral
        trabajador.setPuesto(request.getPuesto());
        trabajador.setDescPuesto(request.getDescPuesto());
        trabajador.setEspecialidadTrabajador(request.getEspecialidadTrabajador());
        trabajador.setEscolaridad(request.getEscolaridad());
        trabajador.setExperiencia(request.getExperiencia());
        trabajador.setTelefonoTrabajador(request.getTelefonoTrabajador());
        trabajador.setCorreoTrabajador(request.getCorreoTrabajador());
        trabajador.setContratacion(request.getContratacion());
        trabajador.setJornada(request.getJornada());
        if (request.getEstadoTrabajador() != null) {
            trabajador.setEstadoTrabajador(request.getEstadoTrabajador());
        }
        // Otros
        trabajador.setDescripcionTrabajador(request.getDescripcionTrabajador());
        trabajador.setEvaluacionTrabajador(request.getEvaluacionTrabajador());
        trabajador.setFechaIngreso(request.getFechaIngreso());
        trabajador.setCalidadVida(request.getCalidadVida());
        trabajador.setAntPenal(request.getAntPenal());
        trabajador.setDeudorAlim(request.getDeudorAlim());
        trabajador.setFolioLicCond(request.getFolioLicCond());
        trabajador.setEstadoCivil(request.getEstadoCivil());
        trabajador.setIdiomas(request.getIdiomas());
        trabajador.setLenguaIndigena(request.getLenguaIndigena());
        trabajador.setSexo(request.getSexo());

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
                .nacionalidad(r.getNacionalidad())
                .registroMigratorio(migratorio)
                .calle(r.getCalle())
                .numExt(r.getNumExt())
                .numInt(r.getNumInt())
                .colonia(r.getColonia())
                .codPost(r.getCodPost())
                .munAlc(r.getMunAlc())
                .estado(r.getEstado())
                .puesto(r.getPuesto())
                .descPuesto(r.getDescPuesto())
                .especialidadTrabajador(r.getEspecialidadTrabajador())
                .escolaridad(r.getEscolaridad())
                .experiencia(r.getExperiencia())
                .telefonoTrabajador(r.getTelefonoTrabajador())
                .correoTrabajador(r.getCorreoTrabajador())
                .contratacion(r.getContratacion())
                .jornada(r.getJornada())
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
