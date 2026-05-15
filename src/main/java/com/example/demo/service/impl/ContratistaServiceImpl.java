package com.example.demo.service.impl;

import com.example.demo.dto.request.ContratistaRequestDTO;
import com.example.demo.dto.response.ContratistaResponseDTO;
import com.example.demo.model.Contratista;
import com.example.demo.model.Contratista.EstadoContratista;
import com.example.demo.model.Estado;
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
    private final EstadoServiceImpl estadoService;

    // ─── FIND ALL ─────────────────────────────────────────────────────────────
    @Override
    public Page<ContratistaResponseDTO> findAll(Pageable pageable) {
        return contratistaRepository.findAll(pageable).map(this::toResponse);
    }

    @Override
    public List<ContratistaResponseDTO> findByEstado(Contratista.EstadoContratista estado) {
        return contratistaRepository.findByEstadoContratista(estado)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ContratistaResponseDTO create(ContratistaRequestDTO dto) {
        if (contratistaRepository.existsByCorreoContratista(dto.getCorreoContratista())) {
            throw new IllegalArgumentException(
                    "Ya existe un contratista con el correo: " + dto.getCorreoContratista());
        }
        if (contratistaRepository.existsByRfcContratista(dto.getRfcContratista())) {
            throw new IllegalArgumentException(
                    "Ya existe un contratista con el RFC: " + dto.getRfcContratista());
        }
        Estado estadoOperacion = estadoService.getEstadoOrThrow(dto.getIdEstadoOperacion());
        Contratista contratista = new Contratista();
        mapDtoToEntity(dto, contratista, estadoOperacion);
        contratista.setEstadoContratista(EstadoContratista.ACTIVO);
        return toResponse(contratistaRepository.save(contratista));
    }

    @Override
    @Transactional
    public ContratistaResponseDTO update(Integer id, ContratistaRequestDTO request) {
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

        if (request.getNombreContratista() != null)      contratista.setNombreContratista(request.getNombreContratista());
        if (request.getCurp() != null)                   contratista.setCurp(request.getCurp());
        if (request.getRfcContratista() != null)         contratista.setRfcContratista(request.getRfcContratista());
        if (request.getTelefonoContratista() != null)    contratista.setTelefonoContratista(request.getTelefonoContratista());
        if (request.getCorreoContratista() != null)      contratista.setCorreoContratista(request.getCorreoContratista());
        if (request.getDescripcionContratista() != null) contratista.setDescripcionContratista(request.getDescripcionContratista());
        if (request.getExperiencia() != null)            contratista.setExperiencia(request.getExperiencia());
        if (request.getCalificacionContratista() != null) contratista.setCalificacionContratista(request.getCalificacionContratista());

        return toResponse(contratistaRepository.save(contratista));
    }

    @Override
    @Transactional
    public ContratistaResponseDTO cambiarEstado(Integer id, Contratista.EstadoContratista estado) {
        Contratista contratista = getOrThrow(id);
        contratista.setEstadoContratista(estado);
        return toResponse(contratistaRepository.save(contratista));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        getOrThrow(id);
        contratistaRepository.deleteById(id);
    }

    private Contratista getOrThrow(Integer id) {
        return contratistaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Contratista no encontrado con id: " + id));
    }

    private void mapDtoToEntity(ContratistaRequestDTO dto, Contratista c, Estado estadoOperacion) {
        c.setNombreContratista(dto.getNombreContratista());
        c.setCurp(dto.getCurp());
        c.setRfcContratista(dto.getRfcContratista());
        c.setTelefonoContratista(dto.getTelefonoContratista());
        c.setCorreoContratista(dto.getCorreoContratista().toLowerCase().trim());
        c.setDescripcionContratista(dto.getDescripcionContratista());
        c.setExperiencia(dto.getExperiencia());
        c.setCalificacionContratista(dto.getCalificacionContratista());
        c.setEstadoOperacion(estadoOperacion);
    }

    // ─── MAPPER ───────────────────────────────────────────────────────────────
    public ContratistaResponseDTO toResponse(Contratista c) {
        return ContratistaResponseDTO.builder()
                .idContratista(c.getIdContratista())
                .nombreContratista(c.getNombreContratista())
                .curp(c.getCurp())
                .rfcContratista(c.getRfcContratista())
                .telefonoContratista(c.getTelefonoContratista())
                .correoContratista(c.getCorreoContratista())
                .descripcionContratista(c.getDescripcionContratista())
                .experiencia(c.getExperiencia())
                .calificacionContratista(c.getCalificacionContratista())
                .estadoContratista(c.getEstadoContratista())
                .idEstadoOperacion(c.getEstadoOperacion().getIdEstado())
                .nombreEstadoOperacion(c.getEstadoOperacion().getNombreEst())
                .build();
    }

}

