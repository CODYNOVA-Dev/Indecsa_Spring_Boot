package com.indecsa.service.impl;

import com.indecsa.dto.empleado.EmpleadoRequest;
import com.indecsa.dto.empleado.EmpleadoResponse;
import com.indecsa.model.Empleado;
import com.indecsa.model.Rol;
import com.indecsa.repository.EmpleadoRepository;
import com.indecsa.repository.RolRepository;
import com.indecsa.service.EmpleadoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
                .stream()
                .map(EmpleadoResponse::from)
                .toList();
    }

    @Override
    public EmpleadoResponse findById(Integer id) {
        return EmpleadoResponse.from(getEmpleadoOrThrow(id));
    }

    @Override
    @Transactional
    public EmpleadoResponse create(EmpleadoRequest request) {
        if (empleadoRepository.existsByCorreoEmpleado(request.getCorreoEmpleado())) {
            throw new IllegalArgumentException("Ya existe un empleado con el correo: " + request.getCorreoEmpleado());
        }
        if (empleadoRepository.existsByCurp(request.getCurp())) {
            throw new IllegalArgumentException("Ya existe un empleado con la CURP: " + request.getCurp());
        }
        Rol rol = rolRepository.findById(request.getIdRol())
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado con id: " + request.getIdRol()));

        Empleado empleado = Empleado.builder()
                .nombreEmpleado(request.getNombreEmpleado())
                .curp(request.getCurp())
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

        if (!empleado.getCorreoEmpleado().equals(request.getCorreoEmpleado())
                && empleadoRepository.existsByCorreoEmpleado(request.getCorreoEmpleado())) {
            throw new IllegalArgumentException("Ya existe un empleado con el correo: " + request.getCorreoEmpleado());
        }

        Rol rol = rolRepository.findById(request.getIdRol())
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado con id: " + request.getIdRol()));

        empleado.setNombreEmpleado(request.getNombreEmpleado());
        empleado.setCurp(request.getCurp());
        empleado.setCorreoEmpleado(request.getCorreoEmpleado().toLowerCase().trim());
        empleado.setContrasena(passwordEncoder.encode(request.getContrasena()));
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
