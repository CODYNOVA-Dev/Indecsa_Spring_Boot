package com.example.demo.dto.asignacion;

import com.example.demo.model.AsignacionTrabajadorProyecto;
import java.time.LocalDate;

public class AsignacionTrabajadorProyectoResponse {
    private Integer idAsignacionTp;
    private Integer idTrabajador;
    private Integer idProyecto;
    private Integer idAsignacionPc;
    private String puestoEnProyecto;
    private LocalDate fechaInicio;
    private LocalDate fechaFinEstimada;
    private String estatusAsignacion;
    private String observaciones;

    public static AsignacionTrabajadorProyectoResponse from(AsignacionTrabajadorProyecto a) {
        AsignacionTrabajadorProyectoResponse r = new AsignacionTrabajadorProyectoResponse();
        r.idAsignacionTp = a.getIdAsignacionTp();
        r.idTrabajador = a.getTrabajador() != null ? a.getTrabajador().getIdTrabajador() : null;
        r.idProyecto = a.getProyecto() != null ? a.getProyecto().getIdProyecto() : null;
        r.idAsignacionPc = a.getAsignacionProyectoContratista() != null ? a.getAsignacionProyectoContratista().getIdAsignacionPc() : null;
        r.puestoEnProyecto = a.getPuestoEnProyecto();
        r.fechaInicio = a.getFechaInicio();
        r.fechaFinEstimada = a.getFechaFinEstimada();
        r.estatusAsignacion = a.getEstatusAsignacion() != null ? a.getEstatusAsignacion().name() : null;
        r.observaciones = a.getObservaciones();
        return r;
    }

    public Integer getIdAsignacionTp()     { return idAsignacionTp; }
    public Integer getIdTrabajador()       { return idTrabajador; }
    public Integer getIdProyecto()         { return idProyecto; }
    public Integer getIdAsignacionPc()     { return idAsignacionPc; }
    public String getPuestoEnProyecto()    { return puestoEnProyecto; }
    public LocalDate getFechaInicio()      { return fechaInicio; }
    public LocalDate getFechaFinEstimada() { return fechaFinEstimada; }
    public String getEstatusAsignacion()   { return estatusAsignacion; }
    public String getObservaciones()       { return observaciones; }
}
