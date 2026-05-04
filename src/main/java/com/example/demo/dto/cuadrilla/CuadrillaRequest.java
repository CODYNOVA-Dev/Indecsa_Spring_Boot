package com.example.demo.dto.cuadrilla;

public class CuadrillaRequest {

    private Integer idProyecto;
    private String nombreCuadrilla;
    private String frenteTrabajo;
    private String observaciones;

    public Integer getIdProyecto()              { return idProyecto; }
    public void setIdProyecto(Integer v)        { this.idProyecto = v; }

    public String getNombreCuadrilla()          { return nombreCuadrilla; }
    public void setNombreCuadrilla(String v)    { this.nombreCuadrilla = v; }

    public String getFrenteTrabajo()            { return frenteTrabajo; }
    public void setFrenteTrabajo(String v)      { this.frenteTrabajo = v; }

    public String getObservaciones()            { return observaciones; }
    public void setObservaciones(String v)      { this.observaciones = v; }
}
