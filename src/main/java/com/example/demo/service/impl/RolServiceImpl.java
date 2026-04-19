package com.indecsa.service.impl;

import com.indecsa.dto.rol.RolRequest;
import com.indecsa.dto.rol.RolResponse;
import com.indecsa.model.Rol;
import com.indecsa.repository.RolRepository;
import com.indecsa.service.RolService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;

    @Override
    public List<RolResponse> findAll() {
        return rolRepository.findAll()
                .stream()
                .map(RolResponse::from)
                .toList();
    }

    @Override
    public RolResponse findById(Integer id) {
        return RolResponse.from(getRolOrThrow(id));
    }

    @Override
    @Transactional
    public RolResponse create(RolRequest request) {
        Rol rol = Rol.builder()
                .nombreRol(request.getNombreRol())
                .descripcionRol(request.getDescripcionRol())
                .build();
        return RolResponse.from(rolRepository.save(rol));
    }

    @Override
    @Transactional
    public RolResponse update(Integer id, RolRequest request) {
        Rol rol = getRolOrThrow(id);
        rol.setNombreRol(request.getNombreRol());
        rol.setDescripcionRol(request.getDescripcionRol());
        return RolResponse.from(rolRepository.save(rol));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        getRolOrThrow(id);
        rolRepository.deleteById(id);
    }

    private Rol getRolOrThrow(Integer id) {
        return rolRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado con id: " + id));
    }
}
