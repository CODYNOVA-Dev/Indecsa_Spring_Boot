package com.indecsa.service.impl;

import com.indecsa.dto.auth.LoginRequest;
import com.indecsa.dto.auth.LoginResponse;
import com.indecsa.model.Empleado;
import com.indecsa.repository.EmpleadoRepository;
import com.indecsa.security.JwtService;
import com.indecsa.service.AuthService;
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
                .findByCorreoEmpleado(request.getCorreo().toLowerCase().trim())
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado."));

        if (!passwordEncoder.matches(request.getContrasena(), empleado.getContrasena())) {
            throw new BadCredentialsException("Credenciales inválidas.");
        }

        String token = jwtService.generateToken(empleado);

        return new LoginResponse(
                token,
                empleado.getCorreoEmpleado(),
                empleado.getRol().getNombreRol()
        );
    }
}
