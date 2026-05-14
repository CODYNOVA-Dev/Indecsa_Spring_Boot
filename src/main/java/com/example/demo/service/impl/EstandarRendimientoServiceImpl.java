package com.example.demo.service.impl;

import com.example.demo.dto.estandar.EstandarRendimientoRequest;
import com.example.demo.dto.estandar.EstandarRendimientoResponse;
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
    public EstandarRendimientoResponse crear(EstandarRendimientoRequest req) {
        EstandarRendimiento estandar = EstandarRendimiento.builder()
                .nombreActividad(req.getNombreActividad())
                .unidadMedida(EstandarRendimiento.UnidadMedida.valueOf(req.getUnidadMedida()))
                .rendimientoEsperado(req.getRendimientoEsperado())
                .descripcion(req.getDescripcion())
                .build();
        return EstandarRendimientoResponse.from(estandarRepository.save(estandar));
    }

    @Override
    public List<EstandarRendimientoResponse> listarTodos() {
        return estandarRepository.findAll()
                .stream().map(EstandarRendimientoResponse::from).collect(Collectors.toList());
    }

    @Override
    public EstandarRendimientoResponse findById(Integer id) {
        return EstandarRendimientoResponse.from(getOrThrow(id));
    }

    @Override
    @Transactional
    public EstandarRendimientoResponse actualizar(Integer id, EstandarRendimientoRequest req) {
        EstandarRendimiento estandar = getOrThrow(id);
        if (req.getNombreActividad() != null)    estandar.setNombreActividad(req.getNombreActividad());
        if (req.getUnidadMedida() != null)
            estandar.setUnidadMedida(EstandarRendimiento.UnidadMedida.valueOf(req.getUnidadMedida()));
        if (req.getRendimientoEsperado() != null) estandar.setRendimientoEsperado(req.getRendimientoEsperado());
        if (req.getDescripcion() != null)         estandar.setDescripcion(req.getDescripcion());
        return EstandarRendimientoResponse.from(estandarRepository.save(estandar));
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
}
