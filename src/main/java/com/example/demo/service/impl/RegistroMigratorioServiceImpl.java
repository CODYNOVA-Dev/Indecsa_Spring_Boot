package com.indecsa.service.impl;

import com.indecsa.dto.migratorio.RegistroMigratorioRequest;
import com.indecsa.dto.migratorio.RegistroMigratorioResponse;
import com.indecsa.model.RegistroMigratorio;
import com.indecsa.repository.RegistroMigratorioRepository;
import com.indecsa.service.RegistroMigratorioService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RegistroMigratorioServiceImpl implements RegistroMigratorioService {

    private final RegistroMigratorioRepository migratorioRepository;

    @Override
    public List<RegistroMigratorioResponse> findAll() {
        return migratorioRepository.findAll()
                .stream()
                .map(RegistroMigratorioResponse::from)
                .toList();
    }

    @Override
    public RegistroMigratorioResponse findById(Integer id) {
        return RegistroMigratorioResponse.from(getOrThrow(id));
    }

    @Override
    @Transactional
    public RegistroMigratorioResponse create(RegistroMigratorioRequest request) {
        if (migratorioRepository.existsByFolioDocumento(request.getFolioDocumento())) {
            throw new IllegalArgumentException("Ya existe un registro con el folio: " + request.getFolioDocumento());
        }
        RegistroMigratorio registro = RegistroMigratorio.builder()
                .folioDocumento(request.getFolioDocumento())
                .categoria(request.getCategoria())
                .fechaEmision(request.getFechaEmision())
                .diasVigencia(request.getDiasVigencia())
                .fechaVencimiento(request.getFechaVencimiento())
                .permisoTrabajo(request.getPermisoTrabajo())
                .activo(request.getActivo())
                .build();
        return RegistroMigratorioResponse.from(migratorioRepository.save(registro));
    }

    @Override
    @Transactional
    public RegistroMigratorioResponse update(Integer id, RegistroMigratorioRequest request) {
        RegistroMigratorio registro = getOrThrow(id);

        if (!registro.getFolioDocumento().equals(request.getFolioDocumento())
                && migratorioRepository.existsByFolioDocumento(request.getFolioDocumento())) {
            throw new IllegalArgumentException("Ya existe un registro con el folio: " + request.getFolioDocumento());
        }

        registro.setFolioDocumento(request.getFolioDocumento());
        registro.setCategoria(request.getCategoria());
        registro.setFechaEmision(request.getFechaEmision());
        registro.setDiasVigencia(request.getDiasVigencia());
        registro.setFechaVencimiento(request.getFechaVencimiento());
        registro.setPermisoTrabajo(request.getPermisoTrabajo());
        registro.setActivo(request.getActivo());

        return RegistroMigratorioResponse.from(migratorioRepository.save(registro));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        getOrThrow(id);
        migratorioRepository.deleteById(id);
    }

    private RegistroMigratorio getOrThrow(Integer id) {
        return migratorioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Registro migratorio no encontrado con id: " + id));
    }
}
