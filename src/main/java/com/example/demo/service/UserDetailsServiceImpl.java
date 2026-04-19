package com.indecsa.security;

import com.indecsa.model.Empleado;
import com.indecsa.repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final EmpleadoRepository empleadoRepository;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Empleado empleado = empleadoRepository.findByCorreoEmpleado(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Empleado no encontrado: " + correo));

        return new User(
                empleado.getCorreoEmpleado(),
                empleado.getContrasena(),
                List.of(new SimpleGrantedAuthority("ROLE_" + empleado.getRol().getNombreRol().name()))
        );
    }
}
