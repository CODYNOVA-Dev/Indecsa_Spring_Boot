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
        if (empleadoRepository.existsByCurp(dto.getCurp())) {
            throw new IllegalArgumentException(
                    "Ya existe un empleado con la CURP: " + dto.getCurp());
        }
        Rol rol = rolRepository.findById(dto.getIdRol())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Rol no encontrado con id: " + dto.getIdRol()));

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
    public EmpleadoResponse update(Integer id, EmpleadoRequest request) {
        Empleado empleado = getEmpleadoOrThrow(id);

        if (request.getCorreoEmpleado() != null
                && !request.getCorreoEmpleado().equals(empleado.getCorreoEmpleado())
                && empleadoRepository.existsByCorreoEmpleado(request.getCorreoEmpleado())) {
            throw new IllegalArgumentException("Ya existe un empleado con el correo: " + request.getCorreoEmpleado());
        }
        if (!empleado.getCurp().equals(dto.getCurp())
                && empleadoRepository.existsByCurp(dto.getCurp())) {
            throw new IllegalArgumentException(
                    "Ya existe un empleado con la CURP: " + dto.getCurp());
        }
        Rol rol = rolRepository.findById(dto.getIdRol())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Rol no encontrado con id: " + dto.getIdRol()));

        empleado.setNombreEmpleado(dto.getNombreEmpleado());
        empleado.setCurp(dto.getCurp());
        empleado.setCorreoEmpleado(dto.getCorreoEmpleado().toLowerCase().trim());
        empleado.setContrasena(passwordEncoder.encode(dto.getContrasena()));
        empleado.setRol(rol);

        return EmpleadoResponse.from(empleadoRepository.save(empleado));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        getEmpleadoOrThrow(id);
        empleadoRepository.deleteById(id);
    }

    // ─── LOGIN ────────────────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public LoginResponseDTO login(String correo, String contrasena) {
        Empleado empleado = empleadoRepository
                .findByCorreoEmpleado(correo.toLowerCase().trim())
                .orElseThrow(() -> new IllegalArgumentException("Credenciales incorrectas"));

        if (!passwordEncoder.matches(contrasena, empleado.getContrasena())) {
            throw new IllegalArgumentException("Credenciales incorrectas");
        }

        return LoginResponseDTO.builder()
                .idEmpleado(empleado.getIdEmpleado())
                .nombreEmpleado(empleado.getNombreEmpleado())
                .correoEmpleado(empleado.getCorreoEmpleado())
                .nombreRol(empleado.getRol().getNombreRol().name())
                .build();
    }

    // ─── HELPERS ──────────────────────────────────────────────────────────────
    private Empleado getEmpleadoOrThrow(Integer id) {
        return empleadoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado con id: " + id));
    }

    // ─── MAPPER ───────────────────────────────────────────────────────────────
    private EmpleadoResponseDTO toResponse(Empleado empleado) {
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
}
