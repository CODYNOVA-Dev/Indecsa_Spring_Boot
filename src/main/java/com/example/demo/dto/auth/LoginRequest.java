package com.example.demo.dto.auth;

public class LoginRequest {
    private String correoEmpleado;
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
