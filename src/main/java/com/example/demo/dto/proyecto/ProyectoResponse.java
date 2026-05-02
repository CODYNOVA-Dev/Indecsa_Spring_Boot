package com.example.demo.dto.proyecto;

import com.example.demo.model.Proyecto;

public class ProyectoResponse {

    private Integer idProyecto;
    private String nombreProyecto;
    private String tipoProyecto;
    private String lugarProyecto;
    private String estadoProyectoGeo;

    public static ProyectoResponse from(Proyecto p) {
        ProyectoResponse r = new ProyectoResponse();
        r.idProyecto = p.getIdProyecto();
        r.nombreProyecto = p.getNombreProyecto();
        r.tipoProyecto = p.getTipoProyecto() != null ? p.getTipoProyecto().name() : null;
        r.lugarProyecto = p.getMunicipioProyecto();
        r.estadoProyectoGeo = p.getEstadoProyectoGeo() != null ? p.getEstadoProyectoGeo().name() : null;
        return r;
    }

    public Integer getIdProyecto()       { return idProyecto; }
    public String getNombreProyecto()    { return nombreProyecto; }
    public String getTipoProyecto()      { return tipoProyecto; }
    public String getLugarProyecto()     { return lugarProyecto; }
    public String getEstadoProyectoGeo() { return estadoProyectoGeo; }
}
