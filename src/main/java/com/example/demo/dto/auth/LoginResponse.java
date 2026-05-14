package com.example.demo.dto.auth;

public class LoginResponse {
    private Integer idEmpleado;
    private String nombreEmpleado;
    private String correoEmpleado;
    private String nombreRol;
    private String token;

    public LoginResponse() {}

    public LoginResponse(Integer idEmpleado, String nombreEmpleado,
                         String correoEmpleado, String nombreRol, String token) {
        this.idEmpleado = idEmpleado;
        this.nombreEmpleado = nombreEmpleado;
        this.correoEmpleado = correoEmpleado;
        this.nombreRol = nombreRol;
        this.token = token;
    }

    public Integer getIdEmpleado()    { return idEmpleado; }
    public String getNombreEmpleado() { return nombreEmpleado; }
    public String getCorreoEmpleado() { return correoEmpleado; }
    public String getNombreRol()      { return nombreRol; }
    public String getToken()          { return token; }
}
