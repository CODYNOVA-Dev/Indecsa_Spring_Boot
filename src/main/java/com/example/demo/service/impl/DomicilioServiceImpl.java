package com.example.demo.service.impl;

import com.example.demo.dto.request.DomicilioRequestDTO;
import com.example.demo.dto.response.DomicilioResponseDTO;
import com.example.demo.model.Domicilio;
import com.example.demo.model.Estado;
import com.example.demo.repository.DomicilioRepository;
import com.example.demo.repository.EstadoRepository;
import com.example.demo.service.DomicilioService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DomicilioServiceImpl implements DomicilioService {

    private final DomicilioRepository domicilioRepository;
    private final EstadoRepository estadoRepository;

    // ─── FIND ALL ─────────────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<DomicilioResponseDTO> findAll() {
        return domicilioRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─── FIND BY ID ───────────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public DomicilioResponseDTO findById(Integer id) {
        return toResponse(getOrThrow(id));
    }

    // ─── FIND BY ESTADO ───────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<DomicilioResponseDTO> findByEstado(Integer idEstado) {
        return domicilioRepository.findByEstado_IdEstado(idEstado)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─── CREATE ───────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public DomicilioResponseDTO create(DomicilioRequestDTO dto) {
        Domicilio domicilio = new Domicilio();
        mapDtoToEntity(dto, domicilio);
        return toResponse(domicilioRepository.save(domicilio));
    }

    // ─── UPDATE ───────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public DomicilioResponseDTO update(Integer id, DomicilioRequestDTO dto) {
        Domicilio domicilio = getOrThrow(id);
        mapDtoToEntity(dto, domicilio);
        return toResponse(domicilioRepository.save(domicilio));
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public void delete(Integer id) {
        if (!domicilioRepository.existsById(id)) {
            throw new EntityNotFoundException("Domicilio no encontrado con id: " + id);
        }
        domicilioRepository.deleteById(id);
    }

    // ─── HELPERS ──────────────────────────────────────────────────────────────
    private Domicilio getOrThrow(Integer id) {
        return domicilioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Domicilio no encontrado con id: " + id));
    }

    private Estado getEstadoOrThrow(Integer idEstado) {
        return estadoRepository.findById(idEstado)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Estado no encontrado con id: " + idEstado));
    }

    private void mapDtoToEntity(DomicilioRequestDTO dto, Domicilio domicilio) {
        domicilio.setCalle(dto.getCalle());
        domicilio.setNumExt(dto.getNumExt());
        domicilio.setNumInt(dto.getNumInt());
        domicilio.setColonia(dto.getColonia());
        domicilio.setCodPost(dto.getCodPost());
        domicilio.setMunAlc(dto.getMunAlc());
        domicilio.setEstado(getEstadoOrThrow(dto.getIdEstado()));
    }

    private DomicilioResponseDTO toResponse(Domicilio d) {
        return DomicilioResponseDTO.builder()
                .idDomicilio(d.getIdDomicilio())
                .calle(d.getCalle())
                .numExt(d.getNumExt())
                .numInt(d.getNumInt())
                .colonia(d.getColonia())
                .codPost(d.getCodPost())
                .munAlc(d.getMunAlc())
                .idEstado(d.getEstado().getIdEstado())
                .nombreEstado(d.getEstado().getNombreEst())
                .build();
    }
}
