package com.indecsa.service.impl;

import com.indecsa.dto.contratista.ContratistaRequest;
import com.indecsa.dto.contratista.ContratistaResponse;
import com.indecsa.model.Contratista;
import com.indecsa.repository.ContratistaRepository;
import com.indecsa.service.ContratistaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContratistaServiceImpl implements ContratistaService {

    private final ContratistaRepository contratistaRepository;

    @Override
    public Page<ContratistaResponse> findByFiltros(
            String nombre,
            Contratista.EstadoContratista estado,
            Contratista.EntidadFederativa ubicacion,
            Byte calificacionMin,
            Pageable pageable
    ) {
        return contratistaRepository
                .findByFiltros(nombre, estado, ubicacion, calificacionMin, pageable)
                .map(ContratistaResponse::from);
    }

    @Override
    public ContratistaResponse findById(Integer id) {
        return ContratistaResponse.from(getOrThrow(id));
    }

    @Override
    @Transactional
    public ContratistaResponse create(ContratistaRequest request) {
        if (contratistaRepository.existsByRfcContratista(request.getRfcContratista())) {
            throw new IllegalArgumentException("Ya existe un contratista con el RFC: " + request.getRfcContratista());
        }
        if (contratistaRepository.existsByCorreoContratista(request.getCorreoContratista())) {
            throw new IllegalArgumentException("Ya existe un contratista con el correo: " + request.getCorreoContratista());
        }

        Contratista contratista = Contratista.builder()
                .nombreContratista(request.getNombreContratista())
                .curp(request.getCurp())
                .rfcContratista(request.getRfcContratista())
                .telefonoContratista(request.getTelefonoContratista())
                .correoContratista(request.getCorreoContratista())
                .descripcionContratista(request.getDescripcionContratista())
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

        if (!contratista.getRfcContratista().equals(request.getRfcContratista())
                && contratistaRepository.existsByRfcContratista(request.getRfcContratista())) {
            throw new IllegalArgumentException("Ya existe un contratista con el RFC: " + request.getRfcContratista());
        }
        if (!contratista.getCorreoContratista().equals(request.getCorreoContratista())
                && contratistaRepository.existsByCorreoContratista(request.getCorreoContratista())) {
            throw new IllegalArgumentException("Ya existe un contratista con el correo: " + request.getCorreoContratista());
        }

        contratista.setNombreContratista(request.getNombreContratista());
        contratista.setCurp(request.getCurp());
        contratista.setRfcContratista(request.getRfcContratista());
        contratista.setTelefonoContratista(request.getTelefonoContratista());
        contratista.setCorreoContratista(request.getCorreoContratista());
        contratista.setDescripcionContratista(request.getDescripcionContratista());
        contratista.setExperiencia(request.getExperiencia());
        contratista.setCalificacionContratista(request.getCalificacionContratista());
        if (request.getEstadoContratista() != null) {
            contratista.setEstadoContratista(request.getEstadoContratista());
        }
        contratista.setUbicacionContratista(request.getUbicacionContratista());

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
