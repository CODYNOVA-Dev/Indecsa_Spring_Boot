package com.example.demo.service.impl;

import com.example.demo.dto.request.EmpleadoRequestDTO;
import com.example.demo.dto.response.EmpleadoResponseDTO;
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
    public EmpleadoResponseDTO create(EmpleadoRequestDTO request) {
        if (empleadoRepository.existsByCorreoEmpleado(request.getCorreoEmpleado())) {
            throw new IllegalArgumentException("Ya existe un empleado con el correo: " + request.getCorreoEmpleado());
        }
        if (request.getCurp() != null && empleadoRepository.existsByCurp(request.getCurp())) {
            throw new IllegalArgumentException("Ya existe un empleado con la CURP: " + request.getCurp());
        }
        Rol rol = getRolOrThrow(request.getIdRol());

        Empleado empleado = Empleado.builder()
                .nombreEmpleado(request.getNombreEmpleado())
                .curp(request.getCurp() != null ? request.getCurp() : "")
                .correoEmpleado(request.getCorreoEmpleado().toLowerCase().trim())
                .contrasena(passwordEncoder.encode(request.getContrasena()))
                .rol(rol)
                .build();
        return toResponse(empleadoRepository.save(empleado));
    }

    @Override
    @Transactional
    public EmpleadoResponseDTO update(Integer id, EmpleadoRequestDTO request) {
        Empleado empleado = getEmpleadoOrThrow(id);

        if (request.getCorreoEmpleado() != null
                && !request.getCorreoEmpleado().equals(empleado.getCorreoEmpleado())
                && empleadoRepository.existsByCorreoEmpleado(request.getCorreoEmpleado())) {
            throw new IllegalArgumentException("Ya existe un empleado con el correo: " + request.getCorreoEmpleado());
        }

        if (request.getNombreEmpleado() != null) empleado.setNombreEmpleado(request.getNombreEmpleado());
        if (request.getCurp() != null)            empleado.setCurp(request.getCurp());
        if (request.getCorreoEmpleado() != null)  empleado.setCorreoEmpleado(request.getCorreoEmpleado().toLowerCase().trim());
        if (request.getContrasena() != null)      empleado.setContrasena(passwordEncoder.encode(request.getContrasena()));
        if (request.getIdRol() != null)           empleado.setRol(getRolOrThrow(request.getIdRol()));

        return toResponse(empleadoRepository.save(empleado));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        getEmpleadoOrThrow(id);
        empleadoRepository.deleteById(id);
    }

    private Empleado getEmpleadoOrThrow(Integer id) {
        return empleadoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado con id: " + id));
    }

    private Rol getRolOrThrow(Integer idRol) {
        return rolRepository.findById(idRol)
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado con id: " + idRol));
    }

    EmpleadoResponseDTO toResponse(Empleado e) {
        return EmpleadoResponseDTO.builder()
                .idEmpleado(e.getIdEmpleado())
                .nombreEmpleado(e.getNombreEmpleado())
                .curp(e.getCurp())
                .correoEmpleado(e.getCorreoEmpleado())
                .idRol(e.getRol().getIdRol())
                .nombreRol(e.getRol().getNombreRol())
                .build();
    }
}
