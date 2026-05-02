package com.example.demo.dto.rol;

import com.example.demo.model.Rol.NombreRol;

public class RolRequest {
    private NombreRol nombreRol;
    private String descripcionRol;

    public NombreRol getNombreRol()         { return nombreRol; }
    public void setNombreRol(NombreRol v)   { this.nombreRol = v; }
    public String getDescripcionRol()       { return descripcionRol; }
    public void setDescripcionRol(String v) { this.descripcionRol = v; }
}
