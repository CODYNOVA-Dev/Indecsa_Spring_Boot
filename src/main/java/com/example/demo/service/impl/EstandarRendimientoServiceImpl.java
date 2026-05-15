package com.example.demo.service.impl;

import com.example.demo.dto.request.EstandarRendimientoRequestDTO;
import com.example.demo.dto.response.EstandarRendimientoResponseDTO;
import com.example.demo.model.EstandarRendimiento;
import com.example.demo.model.EstandarRendimiento.UnidadMedida;
import com.example.demo.repository.EstandarRendimientoRepository;
import com.example.demo.service.EstandarRendimientoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstandarRendimientoServiceImpl implements EstandarRendimientoService {

    private final EstandarRendimientoRepository estandarRepository;

    // ─── FIND ALL ─────────────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<EstandarRendimientoResponseDTO> findAll() {
        return estandarRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─── FIND BY ID ───────────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public EstandarRendimientoResponseDTO findById(Integer id) {
        return toResponse(getOrThrow(id));
    }

    // ─── FIND BY NOMBRE ACTIVIDAD ─────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<EstandarRendimientoResponseDTO> findByNombreActividad(String nombre) {
        return estandarRepository.findByNombreActividadContainingIgnoreCase(nombre)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─── FIND BY UNIDAD MEDIDA ────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<EstandarRendimientoResponseDTO> findByUnidadMedida(UnidadMedida unidadMedida) {
        return estandarRepository.findByUnidadMedida(unidadMedida)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─── CREATE ───────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public EstandarRendimientoResponseDTO create(EstandarRendimientoRequestDTO dto) {
        EstandarRendimiento estandar = new EstandarRendimiento();
        mapDtoToEntity(dto, estandar);
        return toResponse(estandarRepository.save(estandar));
    }

    // ─── UPDATE ───────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public EstandarRendimientoResponseDTO update(Integer id, EstandarRendimientoRequestDTO dto) {
        EstandarRendimiento estandar = getOrThrow(id);
        mapDtoToEntity(dto, estandar);
        return toResponse(estandarRepository.save(estandar));
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public void delete(Integer id) {
        if (!estandarRepository.existsById(id)) {
            throw new EntityNotFoundException("Estándar de rendimiento no encontrado con id: " + id);
        }
        estandarRepository.deleteById(id);
    }

    // ─── HELPERS ──────────────────────────────────────────────────────────────
    private EstandarRendimiento getOrThrow(Integer id) {
        return estandarRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Estándar de rendimiento no encontrado con id: " + id));
    }

    private void mapDtoToEntity(EstandarRendimientoRequestDTO dto, EstandarRendimiento estandar) {
        estandar.setNombreActividad(dto.getNombreActividad());
        estandar.setUnidadMedida(dto.getUnidadMedida());
        estandar.setRendimientoEsperado(dto.getRendimientoEsperado());
    }

    // ─── MAPPER ───────────────────────────────────────────────────────────────
    private EstandarRendimientoResponseDTO toResponse(EstandarRendimiento e) {
        return EstandarRendimientoResponseDTO.builder()
                .idEstandar(e.getIdEstandar())
                .nombreActividad(e.getNombreActividad())
                .unidadMedida(e.getUnidadMedida())
                .rendimientoEsperado(e.getRendimientoEsperado())
                .build();
    }
}
