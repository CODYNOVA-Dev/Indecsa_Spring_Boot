package com.example.demo.service;

import com.example.demo.dto.request.EmpleadoRequestDTO;
import com.example.demo.dto.response.EmpleadoResponseDTO;
import com.example.demo.dto.response.LoginResponseDTO;

import java.util.List;

public interface EmpleadoService {

    List<EmpleadoResponseDTO> findAll();

    EmpleadoResponseDTO findById(Integer id);

    List<EmpleadoResponseDTO> findByRol(Integer idRol);

    EmpleadoResponseDTO create(EmpleadoRequestDTO dto);

    EmpleadoResponseDTO update(Integer id, EmpleadoRequestDTO dto);

    void delete(Integer id);

    // ─── LOGIN ────────────────────────────────────────────────────────────────
    // Devuelve LoginResponseDTO con el rol como String, o lanza
    // IllegalArgumentException si las credenciales son incorrectas.
    LoginResponseDTO login(String correo, String contrasena);
}