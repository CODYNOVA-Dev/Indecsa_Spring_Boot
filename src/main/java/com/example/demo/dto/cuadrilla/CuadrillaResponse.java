package com.example.demo.dto.cuadrilla;

import com.example.demo.model.Cuadrilla;

public class CuadrillaResponse {

    private Integer idCuadrilla;
    private Integer idProyecto;
    private String nombreProyecto;
    private String nombreCuadrilla;
    private String frenteTrabajo;
    private String estatusCuadrilla;
    private String observaciones;

    public static CuadrillaResponse from(Cuadrilla c) {
        CuadrillaResponse r = new CuadrillaResponse();
        r.idCuadrilla     = c.getIdCuadrilla();
        r.idProyecto      = c.getProyecto().getIdProyecto();
        r.nombreProyecto  = c.getProyecto().getNombreProyecto();
        r.nombreCuadrilla = c.getNombreCuadrilla();
        r.frenteTrabajo   = c.getFrenteTrabajo();
        r.estatusCuadrilla = c.getEstatusCuadrilla() != null ? c.getEstatusCuadrilla().name() : null;
        r.observaciones   = c.getObservaciones();
        return r;
    }

    public Integer getIdCuadrilla()         { return idCuadrilla; }
    public Integer getIdProyecto()          { return idProyecto; }
    public String getNombreProyecto()       { return nombreProyecto; }
    public String getNombreCuadrilla()      { return nombreCuadrilla; }
    public String getFrenteTrabajo()        { return frenteTrabajo; }
    public String getEstatusCuadrilla()     { return estatusCuadrilla; }
    public String getObservaciones()        { return observaciones; }
}
