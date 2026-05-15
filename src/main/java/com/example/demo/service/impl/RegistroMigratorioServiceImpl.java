package com.example.demo.service.impl;

import com.example.demo.dto.request.RegistroMigratorioRequestDTO;
import com.example.demo.dto.response.RegistroMigratorioResponseDTO;
import com.example.demo.model.RegistroMigratorio;
import com.example.demo.repository.RegistroMigratorioRepository;
import com.example.demo.service.RegistroMigratorioService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegistroMigratorioServiceImpl implements RegistroMigratorioService {

    private final RegistroMigratorioRepository registroMigratorioRepository;

    // ─── FIND ALL ─────────────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<RegistroMigratorioResponseDTO> findAll() {
        return registroMigratorioRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─── FIND BY ID ───────────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public RegistroMigratorioResponseDTO findById(Integer id) {
        return toResponse(getOrThrow(id));
    }

    // ─── CREATE ───────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public RegistroMigratorioResponseDTO create(RegistroMigratorioRequestDTO dto) {
        if (registroMigratorioRepository.existsByFolioDocumento(dto.getFolioDocumento())) {
            throw new IllegalArgumentException(
                    "Ya existe un registro con el folio: " + dto.getFolioDocumento());
        }
        RegistroMigratorio registro = new RegistroMigratorio();
        mapDtoToEntity(dto, registro);
        registro.setActivo(true);
        return toResponse(registroMigratorioRepository.save(registro));
    }

    // ─── UPDATE ───────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public RegistroMigratorioResponseDTO update(Integer id, RegistroMigratorioRequestDTO dto) {
        RegistroMigratorio registro = getOrThrow(id);
        if (!registro.getFolioDocumento().equals(dto.getFolioDocumento())
                && registroMigratorioRepository.existsByFolioDocumento(dto.getFolioDocumento())) {
            throw new IllegalArgumentException(
                    "Ya existe un registro con el folio: " + dto.getFolioDocumento());
        }
        mapDtoToEntity(dto, registro);
        return toResponse(registroMigratorioRepository.save(registro));
    }

    // ─── CAMBIAR ACTIVO ───────────────────────────────────────────────────────
    @Override
    @Transactional
    public RegistroMigratorioResponseDTO cambiarActivo(Integer id, Boolean activo) {
        RegistroMigratorio registro = getOrThrow(id);
        registro.setActivo(activo);
        return toResponse(registroMigratorioRepository.save(registro));
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public void delete(Integer id) {
        if (!registroMigratorioRepository.existsById(id)) {
            throw new EntityNotFoundException("Registro migratorio no encontrado con id: " + id);
        }
        registroMigratorioRepository.deleteById(id);
    }

    // ─── HELPERS ──────────────────────────────────────────────────────────────
    private RegistroMigratorio getOrThrow(Integer id) {
        return registroMigratorioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Registro migratorio no encontrado con id: " + id));
    }

    private void mapDtoToEntity(RegistroMigratorioRequestDTO dto, RegistroMigratorio registro) {
        registro.setFolioDocumento(dto.getFolioDocumento());
        registro.setCategoria(dto.getCategoria());
        registro.setFechaEmision(dto.getFechaEmision());
        registro.setDiasVigencia(dto.getDiasVigencia());
        registro.setFechaVencimiento(dto.getFechaVencimiento());
        registro.setPermisoTrabajo(dto.getPermisoTrabajo());
    }

    private RegistroMigratorioResponseDTO toResponse(RegistroMigratorio r) {
        return RegistroMigratorioResponseDTO.builder()
                .idMigratorio(r.getIdMigratorio())
                .folioDocumento(r.getFolioDocumento())
                .categoria(r.getCategoria())
                .fechaEmision(r.getFechaEmision())
                .diasVigencia(r.getDiasVigencia())
                .fechaVencimiento(r.getFechaVencimiento())
                .permisoTrabajo(r.getPermisoTrabajo())
                .activo(r.getActivo())
                .build();
    }
}
