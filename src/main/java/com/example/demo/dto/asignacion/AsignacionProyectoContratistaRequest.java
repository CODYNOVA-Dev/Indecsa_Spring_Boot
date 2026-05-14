package com.example.demo.dto.asignacion;

import com.example.demo.model.AsignacionProyectoContratista.EstatusContrato;
import java.time.LocalDate;

public class AsignacionProyectoContratistaRequest {
    private Integer idProyecto;
    private Integer idContratista;
    private String numeroContrato;
    private LocalDate fechaInicio;
    private LocalDate fechaFinEstimada;
    private Integer personalAsignado;
    private String puestosRequeridos;
    private EstatusContrato estatusContrato;
    private String observaciones;

    public Integer getIdProyecto()              { return idProyecto; }
    public void setIdProyecto(Integer v)        { this.idProyecto = v; }
    public Integer getIdContratista()           { return idContratista; }
    public void setIdContratista(Integer v)     { this.idContratista = v; }
    public String getNumeroContrato()           { return numeroContrato; }
    public void setNumeroContrato(String v)     { this.numeroContrato = v; }
    public LocalDate getFechaInicio()           { return fechaInicio; }
    public void setFechaInicio(LocalDate v)     { this.fechaInicio = v; }
    public LocalDate getFechaFinEstimada()      { return fechaFinEstimada; }
    public void setFechaFinEstimada(LocalDate v){ this.fechaFinEstimada = v; }
    public Integer getPersonalAsignado()        { return personalAsignado; }
    public void setPersonalAsignado(Integer v)  { this.personalAsignado = v; }
    public String getPuestosRequeridos()        { return puestosRequeridos; }
    public void setPuestosRequeridos(String v)  { this.puestosRequeridos = v; }
    public EstatusContrato getEstatusContrato()           { return estatusContrato; }
    public void setEstatusContrato(EstatusContrato v)     { this.estatusContrato = v; }
    public String getObservaciones()            { return observaciones; }
    public void setObservaciones(String v)      { this.observaciones = v; }
}
