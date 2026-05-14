package com.example.demo.dto.cuadrilla;

import jakarta.validation.constraints.*;

public class CuadrillaRequest {

    @NotNull
    private Integer idProyecto;

    @NotBlank
    @Size(max = 100)
    private String nombreCuadrilla;

    @Size(max = 200)
    private String frenteTrabajo;

    @Size(max = 500)
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
