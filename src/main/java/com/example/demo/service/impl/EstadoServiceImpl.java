package com.example.demo.service.impl;

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

    @Override
    @Transactional(readOnly = true)
    public List<EstadoResponseDTO> findAll() {
        return estadoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EstadoResponseDTO findById(Integer id) {
        return toResponse(getEstadoOrThrow(id));
    }

    // ─── HELPERS ──────────────────────────────────────────────────────────────
    public Estado getEstadoOrThrow(Integer id) {
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
