package com.example.demo.dto.rol;

import com.example.demo.model.Rol;

public class RolResponse {
    private Integer idRol;
    private String nombreRol;
    private String descripcionRol;

    public static RolResponse from(Rol r) {
        RolResponse res = new RolResponse();
        res.idRol = r.getIdRol();
        res.nombreRol = r.getNombreRol() != null ? r.getNombreRol().name() : null;
        res.descripcionRol = r.getDescripcionRol();
        return res;
    }

    public Integer getIdRol()          { return idRol; }
    public String getNombreRol()       { return nombreRol; }
    public String getDescripcionRol()  { return descripcionRol; }
}
