package com.example.demo.dto.empleado;

import jakarta.validation.constraints.*;

public class EmpleadoRequest {

    @NotBlank
    @Size(max = 100)
    private String nombreEmpleado;

    @NotBlank
    @Size(min = 18, max = 18)
    private String curp;

    @NotBlank
    @Email
    @Size(max = 100)
    private String correoEmpleado;

    @NotBlank
    @Size(min = 8, max = 255)
    private String contrasena;

    @NotNull
    private Integer idRol;

    public String getNombreEmpleado()        { return nombreEmpleado; }
    public void setNombreEmpleado(String v)  { this.nombreEmpleado = v; }

    public String getCurp()                  { return curp; }
    public void setCurp(String v)            { this.curp = v; }

    public String getCorreoEmpleado()        { return correoEmpleado; }
    public void setCorreoEmpleado(String v)  { this.correoEmpleado = v; }

    public String getContrasena()            { return contrasena; }
    public void setContrasena(String v)      { this.contrasena = v; }

    public Integer getIdRol()                { return idRol; }
    public void setIdRol(Integer v)          { this.idRol = v; }
}
