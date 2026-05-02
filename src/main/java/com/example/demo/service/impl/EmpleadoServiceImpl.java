package com.example.demo.service.impl;

import com.example.demo.dto.empleado.EmpleadoRequest;
import com.example.demo.dto.empleado.EmpleadoResponse;
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
    public List<EmpleadoResponse> findAll() {
        return empleadoRepository.findAll()
                .stream().map(EmpleadoResponse::from).collect(Collectors.toList());
    }

    @Override
    public List<EmpleadoResponse> findByRol(Integer idRol) {
        return empleadoRepository.findByRolIdRol(idRol)
                .stream().map(EmpleadoResponse::from).collect(Collectors.toList());
    }

    @Override
    public EmpleadoResponse findById(Integer id) {
        return EmpleadoResponse.from(getEmpleadoOrThrow(id));
    }

    @Override
    @Transactional
    public EmpleadoResponse create(EmpleadoRequest request) {
        if (request.getCorreoEmpleado() != null
                && empleadoRepository.existsByCorreoEmpleado(request.getCorreoEmpleado())) {
            throw new IllegalArgumentException("Ya existe un empleado con el correo: " + request.getCorreoEmpleado());
        }
        if (request.getCurp() != null
                && empleadoRepository.existsByCurp(request.getCurp())) {
            throw new IllegalArgumentException("Ya existe un empleado con la CURP: " + request.getCurp());
        }
        Rol rol = rolRepository.findById(request.getIdRol())
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado con id: " + request.getIdRol()));

        Empleado empleado = Empleado.builder()
                .nombreEmpleado(request.getNombreEmpleado())
                .curp(request.getCurp() != null ? request.getCurp() : "")
                .correoEmpleado(request.getCorreoEmpleado().toLowerCase().trim())
                .contrasena(passwordEncoder.encode(request.getContrasena()))
                .rol(rol)
                .build();
        return EmpleadoResponse.from(empleadoRepository.save(empleado));
    }

    @Override
    @Transactional
    public EmpleadoResponse update(Integer id, EmpleadoRequest request) {
        Empleado empleado = getEmpleadoOrThrow(id);

        if (request.getCorreoEmpleado() != null
                && !request.getCorreoEmpleado().equals(empleado.getCorreoEmpleado())
                && empleadoRepository.existsByCorreoEmpleado(request.getCorreoEmpleado())) {
            throw new IllegalArgumentException("Ya existe un empleado con el correo: " + request.getCorreoEmpleado());
        }

        Rol rol = rolRepository.findById(request.getIdRol())
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado con id: " + request.getIdRol()));

        if (request.getNombreEmpleado() != null)  empleado.setNombreEmpleado(request.getNombreEmpleado());
        if (request.getCurp() != null)             empleado.setCurp(request.getCurp());
        if (request.getCorreoEmpleado() != null)   empleado.setCorreoEmpleado(request.getCorreoEmpleado().toLowerCase().trim());
        if (request.getContrasena() != null)       empleado.setContrasena(passwordEncoder.encode(request.getContrasena()));
        empleado.setRol(rol);

        return EmpleadoResponse.from(empleadoRepository.save(empleado));
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
}
