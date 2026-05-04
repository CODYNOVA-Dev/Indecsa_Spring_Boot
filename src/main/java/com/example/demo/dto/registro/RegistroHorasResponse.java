package com.example.demo.dto.registro;

import com.example.demo.model.RegistroHoras;
import java.math.BigDecimal;
import java.time.LocalDate;

public class RegistroHorasResponse {

    private Integer idRegistro;
    private Integer idAsignacionTp;
    private Integer idTrabajador;
    private String nombreTrabajador;
    private Integer idProyecto;
    private String nombreProyecto;
    private Integer idCuadrilla;
    private String nombreCuadrilla;
    private LocalDate fechaRegistro;
    private BigDecimal horasTrabajadas;
    private String tipoPeriodo;
    private String observaciones;

    public static RegistroHorasResponse from(RegistroHoras rh) {
        RegistroHorasResponse r = new RegistroHorasResponse();
        r.idRegistro      = rh.getIdRegistro();
        r.idAsignacionTp  = rh.getAsignacionTrabajadorProyecto().getIdAsignacionTp();
        r.idTrabajador    = rh.getAsignacionTrabajadorProyecto().getTrabajador().getIdTrabajador();
        r.nombreTrabajador = rh.getAsignacionTrabajadorProyecto().getTrabajador().getNombreTrabajador();
        r.idProyecto      = rh.getAsignacionTrabajadorProyecto().getProyecto().getIdProyecto();
        r.nombreProyecto  = rh.getAsignacionTrabajadorProyecto().getProyecto().getNombreProyecto();
        if (rh.getCuadrilla() != null) {
            r.idCuadrilla    = rh.getCuadrilla().getIdCuadrilla();
            r.nombreCuadrilla = rh.getCuadrilla().getNombreCuadrilla();
        }
        r.fechaRegistro   = rh.getFechaRegistro();
        r.horasTrabajadas = rh.getHorasTrabajadas();
        r.tipoPeriodo     = rh.getTipoPeriodo() != null ? rh.getTipoPeriodo().name() : null;
        r.observaciones   = rh.getObservaciones();
        return r;
    }

    public Integer getIdRegistro()          { return idRegistro; }
    public Integer getIdAsignacionTp()      { return idAsignacionTp; }
    public Integer getIdTrabajador()        { return idTrabajador; }
    public String getNombreTrabajador()     { return nombreTrabajador; }
    public Integer getIdProyecto()          { return idProyecto; }
    public String getNombreProyecto()       { return nombreProyecto; }
    public Integer getIdCuadrilla()         { return idCuadrilla; }
    public String getNombreCuadrilla()      { return nombreCuadrilla; }
    public LocalDate getFechaRegistro()     { return fechaRegistro; }
    public BigDecimal getHorasTrabajadas()  { return horasTrabajadas; }
    public String getTipoPeriodo()          { return tipoPeriodo; }
    public String getObservaciones()        { return observaciones; }
}
