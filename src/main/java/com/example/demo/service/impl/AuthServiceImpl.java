package com.example.demo.service.impl;

import com.example.demo.dto.auth.LoginRequest;
import com.example.demo.dto.auth.LoginResponse;
import com.example.demo.model.Empleado;
import com.example.demo.repository.EmpleadoRepository;
import com.example.demo.security.JwtService;
import com.example.demo.service.AuthService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final EmpleadoRepository empleadoRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public LoginResponse login(LoginRequest request) {
        Empleado empleado = empleadoRepository
                .findByCorreoEmpleado(request.getCorreoEmpleado().toLowerCase().trim())
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado."));

        if (!passwordEncoder.matches(request.getContrasena(), empleado.getContrasena())) {
            throw new BadCredentialsException("Credenciales inválidas.");
        }

        String token = jwtService.generateToken(empleado);

        return new LoginResponse(
                empleado.getIdEmpleado(),
                empleado.getNombreEmpleado(),
                empleado.getCorreoEmpleado(),
                empleado.getRol().getNombreRol().name(),
                token
        );
    }
}
