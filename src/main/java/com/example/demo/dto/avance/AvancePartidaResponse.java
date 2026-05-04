package com.example.demo.dto.avance;

import com.example.demo.model.AvancePartida;
import java.math.BigDecimal;
import java.time.LocalDate;

public class AvancePartidaResponse {

    private Integer idAvance;
    private Integer idProyecto;
    private String nombreProyecto;
    private Integer idCuadrilla;
    private String nombreCuadrilla;
    private Integer idEstandar;
    private String nombreActividad;
    private BigDecimal rendimientoEsperado;
    private String nombrePartida;
    private LocalDate fechaRegistro;
    private BigDecimal cantidadEjecutada;
    private String unidadMedida;
    private BigDecimal cantidadProgramada;
    private String observaciones;

    public static AvancePartidaResponse from(AvancePartida a) {
        AvancePartidaResponse r = new AvancePartidaResponse();
        r.idAvance         = a.getIdAvance();
        r.idProyecto       = a.getProyecto().getIdProyecto();
        r.nombreProyecto   = a.getProyecto().getNombreProyecto();
        if (a.getCuadrilla() != null) {
            r.idCuadrilla    = a.getCuadrilla().getIdCuadrilla();
            r.nombreCuadrilla = a.getCuadrilla().getNombreCuadrilla();
        }
        if (a.getEstandar() != null) {
            r.idEstandar          = a.getEstandar().getIdEstandar();
            r.nombreActividad     = a.getEstandar().getNombreActividad();
            r.rendimientoEsperado = a.getEstandar().getRendimientoEsperado();
        }
        r.nombrePartida      = a.getNombrePartida();
        r.fechaRegistro      = a.getFechaRegistro();
        r.cantidadEjecutada  = a.getCantidadEjecutada();
        r.unidadMedida       = a.getUnidadMedida() != null ? a.getUnidadMedida().name() : null;
        r.cantidadProgramada = a.getCantidadProgramada();
        r.observaciones      = a.getObservaciones();
        return r;
    }

    public Integer getIdAvance()                { return idAvance; }
    public Integer getIdProyecto()              { return idProyecto; }
    public String getNombreProyecto()           { return nombreProyecto; }
    public Integer getIdCuadrilla()             { return idCuadrilla; }
    public String getNombreCuadrilla()          { return nombreCuadrilla; }
    public Integer getIdEstandar()              { return idEstandar; }
    public String getNombreActividad()          { return nombreActividad; }
    public BigDecimal getRendimientoEsperado()  { return rendimientoEsperado; }
    public String getNombrePartida()            { return nombrePartida; }
    public LocalDate getFechaRegistro()         { return fechaRegistro; }
    public BigDecimal getCantidadEjecutada()    { return cantidadEjecutada; }
    public String getUnidadMedida()             { return unidadMedida; }
    public BigDecimal getCantidadProgramada()   { return cantidadProgramada; }
    public String getObservaciones()            { return observaciones; }
}
