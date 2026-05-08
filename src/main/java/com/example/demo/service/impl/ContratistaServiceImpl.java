package com.example.demo.service.impl;

import com.example.demo.dto.contratista.ContratistaRequest;
import com.example.demo.dto.contratista.ContratistaResponse;
import com.example.demo.model.Contratista;
import com.example.demo.repository.ContratistaRepository;
import com.example.demo.service.ContratistaService;
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
public class ContratistaServiceImpl implements ContratistaService {

    private final ContratistaRepository contratistaRepository;

    @Override
    public Page<ContratistaResponse> findAll(Pageable pageable) {
        return contratistaRepository.findAll(pageable).map(ContratistaResponse::from);
    }

    @Override
    public List<ContratistaResponse> findByEstado(Contratista.EstadoContratista estado) {
        return contratistaRepository.findByEstadoContratista(estado)
                .stream().map(ContratistaResponse::from).collect(Collectors.toList());
    }

    @Override
    public ContratistaResponse findById(Integer id) {
        return ContratistaResponse.from(getOrThrow(id));
    }

    @Override
    @Transactional
    public ContratistaResponse create(ContratistaRequest request) {
        if (request.getRfcContratista() != null
                && contratistaRepository.existsByRfcContratista(request.getRfcContratista())) {
            throw new IllegalArgumentException("Ya existe un contratista con el RFC: " + request.getRfcContratista());
        }
        if (request.getCorreoContratista() != null
                && contratistaRepository.existsByCorreoContratista(request.getCorreoContratista())) {
            throw new IllegalArgumentException("Ya existe un contratista con el correo: " + request.getCorreoContratista());
        }

        Contratista contratista = Contratista.builder()
                .nombreContratista(request.getNombreContratista())
                .curp(request.getCurp() != null ? request.getCurp() : "")
                .rfcContratista(request.getRfcContratista() != null ? request.getRfcContratista() : "")
                .telefonoContratista(request.getTelefonoContratista() != null ? request.getTelefonoContratista() : "")
                .correoContratista(request.getCorreoContratista() != null ? request.getCorreoContratista() : "")
                .descripcionContratista(request.getDescripcionContratista() != null ? request.getDescripcionContratista() : "")
                .experiencia(request.getExperiencia())
                .calificacionContratista(request.getCalificacionContratista())
                .estadoContratista(request.getEstadoContratista() != null
                        ? request.getEstadoContratista()
                        : Contratista.EstadoContratista.ACTIVO)
                .ubicacionContratista(request.getUbicacionContratista())
                .build();

        return ContratistaResponse.from(contratistaRepository.save(contratista));
    }

    @Override
    @Transactional
    public ContratistaResponse update(Integer id, ContratistaRequest request) {
        Contratista contratista = getOrThrow(id);

        if (request.getRfcContratista() != null
                && !request.getRfcContratista().equals(contratista.getRfcContratista())
                && contratistaRepository.existsByRfcContratista(request.getRfcContratista())) {
            throw new IllegalArgumentException("Ya existe un contratista con el RFC: " + request.getRfcContratista());
        }
        if (request.getCorreoContratista() != null
                && !request.getCorreoContratista().equals(contratista.getCorreoContratista())
                && contratistaRepository.existsByCorreoContratista(request.getCorreoContratista())) {
            throw new IllegalArgumentException("Ya existe un contratista con el correo: " + request.getCorreoContratista());
        }

        if (request.getNombreContratista() != null)     contratista.setNombreContratista(request.getNombreContratista());
        if (request.getCurp() != null)                  contratista.setCurp(request.getCurp());
        if (request.getRfcContratista() != null)        contratista.setRfcContratista(request.getRfcContratista());
        if (request.getTelefonoContratista() != null)   contratista.setTelefonoContratista(request.getTelefonoContratista());
        if (request.getCorreoContratista() != null)     contratista.setCorreoContratista(request.getCorreoContratista());
        if (request.getDescripcionContratista() != null)contratista.setDescripcionContratista(request.getDescripcionContratista());
        if (request.getExperiencia() != null)           contratista.setExperiencia(request.getExperiencia());
        if (request.getCalificacionContratista() != null) contratista.setCalificacionContratista(request.getCalificacionContratista());
        if (request.getEstadoContratista() != null)     contratista.setEstadoContratista(request.getEstadoContratista());
        if (request.getUbicacionContratista() != null)  contratista.setUbicacionContratista(request.getUbicacionContratista());

        return ContratistaResponse.from(contratistaRepository.save(contratista));
    }

    @Override
    @Transactional
    public ContratistaResponse cambiarEstado(Integer id, Contratista.EstadoContratista estado) {
        Contratista contratista = getOrThrow(id);
        contratista.setEstadoContratista(estado);
        return ContratistaResponse.from(contratistaRepository.save(contratista));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        getOrThrow(id);
        contratistaRepository.deleteById(id);
    }

    private Contratista getOrThrow(Integer id) {
        return contratistaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contratista no encontrado con id: " + id));
    }
}
