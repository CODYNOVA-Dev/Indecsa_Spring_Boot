package com.example.demo.dto.auth;

import jakarta.validation.constraints.*;

public class LoginRequest {

    @NotBlank
    @Email
    private String correoEmpleado;

    @NotBlank
    private String contrasena;

    public LoginRequest() {}

    public LoginRequest(String correoEmpleado, String contrasena) {
        this.correoEmpleado = correoEmpleado;
        this.contrasena = contrasena;
    }

    public String getCorreoEmpleado() { return correoEmpleado; }
    public void setCorreoEmpleado(String v) { this.correoEmpleado = v; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String v) { this.contrasena = v; }
}
