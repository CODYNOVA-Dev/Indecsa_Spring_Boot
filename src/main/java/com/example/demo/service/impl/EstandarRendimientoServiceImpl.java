package com.example.demo.service.impl;

import com.example.demo.dto.request.EstandarRendimientoRequestDTO;
import com.example.demo.dto.response.EstandarRendimientoResponseDTO;
import com.example.demo.model.EstandarRendimiento;
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
@Transactional(readOnly = true)
public class EstandarRendimientoServiceImpl implements EstandarRendimientoService {

    private final EstandarRendimientoRepository estandarRepository;

    @Override
    public List<EstandarRendimientoResponseDTO> listarTodos() {
        return estandarRepository.findAll()
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public EstandarRendimientoResponseDTO findById(Integer id) {
        return toResponse(getOrThrow(id));
    }

    @Override
    @Transactional
    public EstandarRendimientoResponseDTO crear(EstandarRendimientoRequestDTO dto) {
        EstandarRendimiento estandar = EstandarRendimiento.builder()
                .nombreActividad(dto.getNombreActividad())
                .unidadMedida(EstandarRendimiento.UnidadMedida.valueOf(dto.getUnidadMedida()))
                .rendimientoEsperado(dto.getRendimientoEsperado())
                .build();
        return toResponse(estandarRepository.save(estandar));
    }

    @Override
    @Transactional
    public EstandarRendimientoResponseDTO actualizar(Integer id, EstandarRendimientoRequestDTO dto) {
        EstandarRendimiento estandar = getOrThrow(id);
        if (dto.getNombreActividad() != null)    estandar.setNombreActividad(dto.getNombreActividad());
        if (dto.getUnidadMedida() != null)
            estandar.setUnidadMedida(EstandarRendimiento.UnidadMedida.valueOf(dto.getUnidadMedida()));
        if (dto.getRendimientoEsperado() != null) estandar.setRendimientoEsperado(dto.getRendimientoEsperado());
        return toResponse(estandarRepository.save(estandar));
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        getOrThrow(id);
        estandarRepository.deleteById(id);
    }

    public EstandarRendimientoResponseDTO toResponse(EstandarRendimiento e) {
        return EstandarRendimientoResponseDTO.builder()
                .idEstandar(e.getIdEstandar())
                .nombreActividad(e.getNombreActividad())
                .unidadMedida(e.getUnidadMedida() != null ? e.getUnidadMedida().name() : null)
                .rendimientoEsperado(e.getRendimientoEsperado())
                .build();
    }

    private EstandarRendimiento getOrThrow(Integer id) {
        return estandarRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Estándar de rendimiento no encontrado con id: " + id));
    }
}
