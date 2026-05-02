package com.example.demo.dto.empleado;

import com.example.demo.model.Empleado;

public class EmpleadoResponse {

    private Integer idEmpleado;
    private String nombreEmpleado;
    private String correoEmpleado;
    private String nombreRol;

    public static EmpleadoResponse from(Empleado e) {
        EmpleadoResponse r = new EmpleadoResponse();
        r.idEmpleado = e.getIdEmpleado();
        r.nombreEmpleado = e.getNombreEmpleado();
        r.correoEmpleado = e.getCorreoEmpleado();
        r.nombreRol = e.getRol() != null ? e.getRol().getNombreRol().name() : null;
        return r;
    }

    public Integer getIdEmpleado()    { return idEmpleado; }
    public String getNombreEmpleado() { return nombreEmpleado; }
    public String getCorreoEmpleado() { return correoEmpleado; }
    public String getNombreRol()      { return nombreRol; }

    public void setIdEmpleado(Integer v)    { this.idEmpleado = v; }
    public void setNombreEmpleado(String v) { this.nombreEmpleado = v; }
    public void setCorreoEmpleado(String v) { this.correoEmpleado = v; }
    public void setNombreRol(String v)      { this.nombreRol = v; }
}
