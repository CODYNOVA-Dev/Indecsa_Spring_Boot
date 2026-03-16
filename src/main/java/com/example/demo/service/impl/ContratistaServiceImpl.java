package com.example.demo.service.impl;

import com.example.demo.dto.request.ContratistaRequestDTO;
import com.example.demo.dto.response.ContratistaResponseDTO;
import com.example.demo.model.Contratista;
import com.example.demo.model.Contratista.EstadoContratista;
import com.example.demo.repository.ContratistaRepository;
import com.example.demo.service.ContratistaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContratistaServiceImpl implements ContratistaService {

    private final ContratistaRepository contratistaRepository;

    // Se eliminó PasswordEncoder: contrasenia_contratista no existe en la BD

    // ─── FIND ALL ─────────────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<ContratistaResponseDTO> findAll() {
        return contratistaRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─── FIND BY ID ───────────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public ContratistaResponseDTO findById(Integer id) {
        return toResponse(getContratistaOrThrow(id));
    }

    // ─── FIND BY ESTADO ───────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<ContratistaResponseDTO> findByEstado(EstadoContratista estado) {
        return contratistaRepository.findByEstadoContratista(estado)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─── CREATE ───────────────────────────────────────────────────────────────
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
        Contratista contratista = new Contratista();
        mapDtoToEntity(dto, contratista);
        contratista.setEstadoContratista(EstadoContratista.ACTIVO);
        return toResponse(contratistaRepository.save(contratista));
    }

    // ─── UPDATE ───────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public ContratistaResponseDTO update(Integer id, ContratistaRequestDTO dto) {
        Contratista contratista = getContratistaOrThrow(id);

        if (!contratista.getCorreoContratista().equals(dto.getCorreoContratista())
                && contratistaRepository.existsByCorreoContratista(dto.getCorreoContratista())) {
            throw new IllegalArgumentException(
                    "Ya existe un contratista con el correo: " + dto.getCorreoContratista());
        }
        if (!contratista.getRfcContratista().equals(dto.getRfcContratista())
                && contratistaRepository.existsByRfcContratista(dto.getRfcContratista())) {
            throw new IllegalArgumentException(
                    "Ya existe un contratista con el RFC: " + dto.getRfcContratista());
        }
        mapDtoToEntity(dto, contratista);
        return toResponse(contratistaRepository.save(contratista));
    }

    // ─── CAMBIAR ESTADO ───────────────────────────────────────────────────────
    @Override
    @Transactional
    public ContratistaResponseDTO cambiarEstado(Integer id, EstadoContratista estado) {
        Contratista contratista = getContratistaOrThrow(id);
        contratista.setEstadoContratista(estado);
        return toResponse(contratistaRepository.save(contratista));
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public void delete(Integer id) {
        if (!contratistaRepository.existsById(id)) {
            throw new EntityNotFoundException("Contratista no encontrado con id: " + id);
        }
        contratistaRepository.deleteById(id);
    }

    // ─── HELPERS ──────────────────────────────────────────────────────────────
    private Contratista getContratistaOrThrow(Integer id) {
        return contratistaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Contratista no encontrado con id: " + id));
    }

    private void mapDtoToEntity(ContratistaRequestDTO dto, Contratista contratista) {
        // Agregado: nombreContratista existe en la BD
        contratista.setNombreContratista(dto.getNombreContratista());
        contratista.setRfcContratista(dto.getRfcContratista());
        contratista.setTelefonoContratista(dto.getTelefonoContratista());
        contratista.setCorreoContratista(dto.getCorreoContratista().toLowerCase().trim());
        contratista.setDescripcionContratista(dto.getDescripcionContratista());
        contratista.setExperiencia(dto.getExperiencia());
        contratista.setCalificacionContratista(dto.getCalificacionContratista());
        // Agregado: ubicacionContratista existe en la BD
        contratista.setUbicacionContratista(dto.getUbicacionContratista());
    }

    // ─── MAPPER ───────────────────────────────────────────────────────────────
    private ContratistaResponseDTO toResponse(Contratista c) {
        return ContratistaResponseDTO.builder()
                .idContratista(c.getIdContratista())
                // Agregado: nombreContratista existe en la BD
                .nombreContratista(c.getNombreContratista())
                .rfcContratista(c.getRfcContratista())
                .telefonoContratista(c.getTelefonoContratista())
                .correoContratista(c.getCorreoContratista())
                .descripcionContratista(c.getDescripcionContratista())
                .experiencia(c.getExperiencia())
                .calificacionContratista(c.getCalificacionContratista())
                .estadoContratista(c.getEstadoContratista())
                // Agregado: ubicacionContratista existe en la BD
                .ubicacionContratista(c.getUbicacionContratista())
                .build();
    }
}
