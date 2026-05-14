package com.example.demo.dto.asignacion;

import com.example.demo.model.AsignacionProyectoContratista;
import java.time.LocalDate;

public class AsignacionProyectoContratistaResponse {
    private Integer idAsignacionPc;
    private Integer idProyecto;
    private Integer idContratista;
    private String numeroContrato;
    private LocalDate fechaInicio;
    private LocalDate fechaFinEstimada;
    private Integer personalAsignado;
    private String puestosRequeridos;
    private String estatusContrato;
    private String observaciones;

    public static AsignacionProyectoContratistaResponse from(AsignacionProyectoContratista a) {
        AsignacionProyectoContratistaResponse r = new AsignacionProyectoContratistaResponse();
        r.idAsignacionPc = a.getIdAsignacionPc();
        r.idProyecto = a.getProyecto() != null ? a.getProyecto().getIdProyecto() : null;
        r.idContratista = a.getContratista() != null ? a.getContratista().getIdContratista() : null;
        r.numeroContrato = a.getNumeroContrato();
        r.fechaInicio = a.getFechaInicio();
        r.fechaFinEstimada = a.getFechaFinEstimada();
        r.personalAsignado = a.getPersonalAsignado();
        r.puestosRequeridos = a.getPuestosRequeridos();
        r.estatusContrato = a.getEstatusContrato() != null ? a.getEstatusContrato().name() : null;
        r.observaciones = a.getObservaciones();
        return r;
    }

    public Integer getIdAsignacionPc()     { return idAsignacionPc; }
    public Integer getIdProyecto()         { return idProyecto; }
    public Integer getIdContratista()      { return idContratista; }
    public String getNumeroContrato()      { return numeroContrato; }
    public LocalDate getFechaInicio()      { return fechaInicio; }
    public LocalDate getFechaFinEstimada() { return fechaFinEstimada; }
    public Integer getPersonalAsignado()   { return personalAsignado; }
    public String getPuestosRequeridos()   { return puestosRequeridos; }
    public String getEstatusContrato()     { return estatusContrato; }
    public String getObservaciones()       { return observaciones; }
}
