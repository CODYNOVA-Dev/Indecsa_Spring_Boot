package com.example.demo.dto.asignacion;

import com.example.demo.model.AsignacionTrabajadorProyecto.EstatusAsignacion;
import java.time.LocalDate;

public class AsignacionTrabajadorProyectoRequest {
    private Integer idTrabajador;
    private Integer idProyecto;
    private Integer idAsignacionPc;
    private String puestoEnProyecto;
    private LocalDate fechaInicio;
    private LocalDate fechaFinEstimada;
    private EstatusAsignacion estatusAsignacion;
    private String observaciones;

    public Integer getIdTrabajador()               { return idTrabajador; }
    public void setIdTrabajador(Integer v)         { this.idTrabajador = v; }
    public Integer getIdProyecto()                 { return idProyecto; }
    public void setIdProyecto(Integer v)           { this.idProyecto = v; }
    public Integer getIdAsignacionPc()             { return idAsignacionPc; }
    public void setIdAsignacionPc(Integer v)       { this.idAsignacionPc = v; }
    public String getPuestoEnProyecto()            { return puestoEnProyecto; }
    public void setPuestoEnProyecto(String v)      { this.puestoEnProyecto = v; }
    public LocalDate getFechaInicio()              { return fechaInicio; }
    public void setFechaInicio(LocalDate v)        { this.fechaInicio = v; }
    public LocalDate getFechaFinEstimada()         { return fechaFinEstimada; }
    public void setFechaFinEstimada(LocalDate v)   { this.fechaFinEstimada = v; }
    public EstatusAsignacion getEstatusAsignacion()            { return estatusAsignacion; }
    public void setEstatusAsignacion(EstatusAsignacion v)      { this.estatusAsignacion = v; }
    public String getObservaciones()               { return observaciones; }
    public void setObservaciones(String v)         { this.observaciones = v; }
}
