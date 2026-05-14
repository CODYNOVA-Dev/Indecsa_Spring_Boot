package com.example.demo.service.impl;

import com.example.demo.dto.request.EmpleadoRequestDTO;
import com.example.demo.dto.response.EmpleadoResponseDTO;
import com.example.demo.dto.response.RolResponseDTO;
import com.example.demo.model.Empleado;
import com.example.demo.model.Rol;
import com.example.demo.repository.EmpleadoRepository;
import com.example.demo.repository.RolRepository;
import com.example.demo.service.EmpleadoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<EmpleadoResponseDTO> findAll() {
        return empleadoRepository.findAll()
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<EmpleadoResponseDTO> findByRol(Integer idRol) {
        return empleadoRepository.findByRolIdRol(idRol)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public EmpleadoResponseDTO findById(Integer id) {
        return toResponse(getEmpleadoOrThrow(id));
    }

    @Override
    @Transactional
    public EmpleadoResponseDTO create(EmpleadoRequestDTO dto) {
        if (empleadoRepository.existsByCorreoEmpleado(dto.getCorreoEmpleado())) {
            throw new IllegalArgumentException("Ya existe un empleado con el correo: " + dto.getCorreoEmpleado());
        }
        if (empleadoRepository.existsByCurp(dto.getCurp())) {
            throw new IllegalArgumentException("Ya existe un empleado con la CURP: " + dto.getCurp());
        }
        Rol rol = getRolOrThrow(dto.getIdRol());

        Empleado empleado = new Empleado();
        empleado.setNombreEmpleado(dto.getNombreEmpleado());
        empleado.setCurp(dto.getCurp());
        empleado.setCorreoEmpleado(dto.getCorreoEmpleado().toLowerCase().trim());
        empleado.setContrasena(passwordEncoder.encode(dto.getContrasena()));
        empleado.setRol(rol);
        return toResponse(empleadoRepository.save(empleado));
    }

    @Override
    @Transactional
    public EmpleadoResponseDTO update(Integer id, EmpleadoRequestDTO dto) {
        Empleado empleado = getEmpleadoOrThrow(id);

        if (dto.getCorreoEmpleado() != null
                && !dto.getCorreoEmpleado().equals(empleado.getCorreoEmpleado())
                && empleadoRepository.existsByCorreoEmpleado(dto.getCorreoEmpleado())) {
            throw new IllegalArgumentException("Ya existe un empleado con el correo: " + dto.getCorreoEmpleado());
        }
        if (dto.getCurp() != null && !dto.getCurp().equals(empleado.getCurp())
                && empleadoRepository.existsByCurp(dto.getCurp())) {
            throw new IllegalArgumentException("Ya existe un empleado con la CURP: " + dto.getCurp());
        }

        Rol rol = getRolOrThrow(dto.getIdRol());
        empleado.setNombreEmpleado(dto.getNombreEmpleado());
        empleado.setCurp(dto.getCurp());
        empleado.setCorreoEmpleado(dto.getCorreoEmpleado().toLowerCase().trim());
        if (dto.getContrasena() != null && !dto.getContrasena().isBlank()) {
            empleado.setContrasena(passwordEncoder.encode(dto.getContrasena()));
        }
        empleado.setRol(rol);
        return toResponse(empleadoRepository.save(empleado));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        getEmpleadoOrThrow(id);
        empleadoRepository.deleteById(id);
    }

    public EmpleadoResponseDTO toResponse(Empleado empleado) {
        RolResponseDTO rolDTO = RolResponseDTO.builder()
                .idRol(empleado.getRol().getIdRol())
                .nombreRol(empleado.getRol().getNombreRol())
                .descripcionRol(empleado.getRol().getDescripcionRol())
                .build();

        return EmpleadoResponseDTO.builder()
                .idEmpleado(empleado.getIdEmpleado())
                .nombreEmpleado(empleado.getNombreEmpleado())
                .curp(empleado.getCurp())
                .correoEmpleado(empleado.getCorreoEmpleado())
                .rol(rolDTO)
                .build();
    }

    private Empleado getEmpleadoOrThrow(Integer id) {
        return empleadoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado con id: " + id));
    }

    private Rol getRolOrThrow(Integer id) {
        return rolRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado con id: " + id));
    }
}
