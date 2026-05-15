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
    @Transactional
    public EstandarRendimientoResponseDTO crear(EstandarRendimientoRequestDTO request) {
        EstandarRendimiento estandar = EstandarRendimiento.builder()
                .nombreActividad(request.getNombreActividad())
                .unidadMedida(EstandarRendimiento.UnidadMedida.valueOf(request.getUnidadMedida()))
                .rendimientoEsperado(request.getRendimientoEsperado())
                .build();
        return toResponse(estandarRepository.save(estandar));
    }

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
    public EstandarRendimientoResponseDTO actualizar(Integer id, EstandarRendimientoRequestDTO request) {
        EstandarRendimiento estandar = getOrThrow(id);
        if (request.getNombreActividad() != null)     estandar.setNombreActividad(request.getNombreActividad());
        if (request.getUnidadMedida() != null)
            estandar.setUnidadMedida(EstandarRendimiento.UnidadMedida.valueOf(request.getUnidadMedida()));
        if (request.getRendimientoEsperado() != null) estandar.setRendimientoEsperado(request.getRendimientoEsperado());
        return toResponse(estandarRepository.save(estandar));
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        getOrThrow(id);
        estandarRepository.deleteById(id);
    }

    private EstandarRendimiento getOrThrow(Integer id) {
        return estandarRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estándar de rendimiento no encontrado con id: " + id));
    }

    EstandarRendimientoResponseDTO toResponse(EstandarRendimiento e) {
        return EstandarRendimientoResponseDTO.builder()
                .idEstandar(e.getIdEstandar())
                .nombreActividad(e.getNombreActividad())
                .unidadMedida(e.getUnidadMedida())
                .rendimientoEsperado(e.getRendimientoEsperado())
                .build();
    }
}
