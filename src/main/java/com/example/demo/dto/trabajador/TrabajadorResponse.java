package com.example.demo.dto.trabajador;

import com.example.demo.model.Trabajador;
import java.time.LocalDate;

public class TrabajadorResponse {

    private Integer idTrabajador;
    private String nombreTrabajador;
    private String nssTrabajador;
    private String experiencia;
    private String telefonoTrabajador;
    private String correoTrabajador;
    private String especialidadTrabajador;
    private String estadoTrabajador;
    private String descripcionTrabajador;
    private Integer calificacionTrabajador;
    private LocalDate fechaIngreso;
    private String ubicacionTrabajador;

    public static TrabajadorResponse from(Trabajador t) {
        TrabajadorResponse r = new TrabajadorResponse();
        r.idTrabajador = t.getIdTrabajador();
        r.nombreTrabajador = t.getNombreTrabajador();
        r.nssTrabajador = t.getNssTrabajador();
        r.experiencia = t.getExperiencia();
        r.telefonoTrabajador = t.getTelefonoTrabajador();
        r.correoTrabajador = t.getCorreoTrabajador();
        r.especialidadTrabajador = t.getEspecialidadTrabajador();
        r.estadoTrabajador = t.getEstadoTrabajador() != null ? t.getEstadoTrabajador().name() : null;
        r.descripcionTrabajador = t.getDescripcionTrabajador();
        r.calificacionTrabajador = t.getEvaluacionTrabajador() != null ? t.getEvaluacionTrabajador().intValue() : null;
        r.fechaIngreso = t.getFechaIngreso();
        r.ubicacionTrabajador = t.getCalidadVida() != null ? t.getCalidadVida().name() : null;
        return r;
    }

    public Integer getIdTrabajador()          { return idTrabajador; }
    public String getNombreTrabajador()       { return nombreTrabajador; }
    public String getNssTrabajador()          { return nssTrabajador; }
    public String getExperiencia()            { return experiencia; }
    public String getTelefonoTrabajador()     { return telefonoTrabajador; }
    public String getCorreoTrabajador()       { return correoTrabajador; }
    public String getEspecialidadTrabajador() { return especialidadTrabajador; }
    public String getEstadoTrabajador()       { return estadoTrabajador; }
    public String getDescripcionTrabajador()  { return descripcionTrabajador; }
    public Integer getCalificacionTrabajador(){ return calificacionTrabajador; }
    public LocalDate getFechaIngreso()        { return fechaIngreso; }
    public String getUbicacionTrabajador()    { return ubicacionTrabajador; }
}
