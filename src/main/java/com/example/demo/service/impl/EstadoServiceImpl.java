package com.example.demo.service.impl;

import com.example.demo.dto.request.EstadoRequestDTO;
import com.example.demo.dto.response.EstadoResponseDTO;
import com.example.demo.model.Estado;
import com.example.demo.repository.EstadoRepository;
import com.example.demo.service.EstadoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstadoServiceImpl implements EstadoService {

    private final EstadoRepository estadoRepository;

    // ─── FIND ALL ─────────────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<EstadoResponseDTO> findAll() {
        return estadoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─── FIND BY ID ───────────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public EstadoResponseDTO findById(Integer id) {
        return toResponse(getOrThrow(id));
    }

    // ─── CREATE ───────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public EstadoResponseDTO create(EstadoRequestDTO dto) {
        if (estadoRepository.existsByNombreEst(dto.getNombreEst())) {
            throw new IllegalArgumentException(
                    "Ya existe un estado con el nombre: " + dto.getNombreEst());
        }
        Estado estado = new Estado();
        estado.setNombreEst(dto.getNombreEst());
        return toResponse(estadoRepository.save(estado));
    }

    // ─── UPDATE ───────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public EstadoResponseDTO update(Integer id, EstadoRequestDTO dto) {
        Estado estado = getOrThrow(id);
        if (!estado.getNombreEst().equals(dto.getNombreEst())
                && estadoRepository.existsByNombreEst(dto.getNombreEst())) {
            throw new IllegalArgumentException(
                    "Ya existe un estado con el nombre: " + dto.getNombreEst());
        }
        estado.setNombreEst(dto.getNombreEst());
        return toResponse(estadoRepository.save(estado));
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public void delete(Integer id) {
        if (!estadoRepository.existsById(id)) {
            throw new EntityNotFoundException("Estado no encontrado con id: " + id);
        }
        estadoRepository.deleteById(id);
    }

    // ─── HELPERS ──────────────────────────────────────────────────────────────
    private Estado getOrThrow(Integer id) {
        return estadoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Estado no encontrado con id: " + id));
    }

    private EstadoResponseDTO toResponse(Estado e) {
        return EstadoResponseDTO.builder()
                .idEstado(e.getIdEstado())
                .nombreEst(e.getNombreEst())
                .build();
    }
}
