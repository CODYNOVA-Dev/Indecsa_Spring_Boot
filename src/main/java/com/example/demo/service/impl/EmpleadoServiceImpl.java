package com.example.demo.service.impl;

import com.example.demo.dto.auth.LoginResponse;
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
    public EmpleadoResponseDTO create(EmpleadoRequestDTO request) {
        if (empleadoRepository.existsByCorreoEmpleado(request.getCorreoEmpleado())) {
            throw new IllegalArgumentException("Ya existe un empleado con el correo: " + request.getCorreoEmpleado());
        }
        if (empleadoRepository.existsByCurp(request.getCurp())) {
            throw new IllegalArgumentException(
                    "Ya existe un empleado con la CURP: " + request.getCurp());
        }
        Rol rol = rolRepository.findById(request.getIdRol())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Rol no encontrado con id: " + request.getIdRol()));

        Empleado empleado = new Empleado();
        empleado.setNombreEmpleado(request.getNombreEmpleado());
        empleado.setCurp(request.getCurp());
        empleado.setCorreoEmpleado(request.getCorreoEmpleado().toLowerCase().trim());
        empleado.setContrasena(passwordEncoder.encode(request.getContrasena()));
        empleado.setRol(rol);
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
        if (!empleado.getCurp().equals(request.getCurp())
                && empleadoRepository.existsByCurp(request.getCurp())) {
            throw new IllegalArgumentException(
                    "Ya existe un empleado con la CURP: " + request.getCurp());
        }
        Rol rol = rolRepository.findById(request.getIdRol())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Rol no encontrado con id: " + request.getIdRol()));

        empleado.setNombreEmpleado(request.getNombreEmpleado());
        empleado.setCurp(request.getCurp());
        empleado.setCorreoEmpleado(request.getCorreoEmpleado().toLowerCase().trim());
        empleado.setContrasena(passwordEncoder.encode(request.getContrasena()));
        empleado.setRol(rol);

        return toResponse(empleadoRepository.save(empleado));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        getEmpleadoOrThrow(id);
        empleadoRepository.deleteById(id);
    }

    // ─── LOGIN ────────────────────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public LoginResponse login(String correo, String contrasena) {
        Empleado empleado = empleadoRepository
                .findByCorreoEmpleado(correo.toLowerCase().trim())
                .orElseThrow(() -> new IllegalArgumentException("Credenciales incorrectas"));

        if (!passwordEncoder.matches(contrasena, empleado.getContrasena())) {
            throw new IllegalArgumentException("Credenciales incorrectas");
        }

        return new LoginResponse(
                empleado.getIdEmpleado(),
                empleado.getNombreEmpleado(),
                empleado.getCorreoEmpleado(),
                empleado.getRol().getNombreRol().name(),
                null
        );
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
