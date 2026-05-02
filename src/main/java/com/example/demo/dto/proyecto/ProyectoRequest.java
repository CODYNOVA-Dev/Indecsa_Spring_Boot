package com.example.demo.dto.proyecto;

import com.example.demo.model.Proyecto.EntidadFederativa;
import com.example.demo.model.Proyecto.EstatusProyecto;
import com.example.demo.model.Proyecto.TipoProyecto;
import java.time.LocalDate;

public class ProyectoRequest {

    private String nombreProyecto;
    private TipoProyecto tipoProyecto;
    private String ofertaTrabajo;
    private String cliente;
    private Integer idUbicacion;
    private String lugarProyecto;
    private EntidadFederativa estadoProyectoGeo;
    private LocalDate fechaEstimadaInicio;
    private LocalDate fechaEstimadaFin;
    private Byte calificacionProyecto;
    private EstatusProyecto estatusProyecto;
    private String descripcionProyecto;

    public String getNombreProyecto()              { return nombreProyecto; }
    public void setNombreProyecto(String v)        { this.nombreProyecto = v; }

    public TipoProyecto getTipoProyecto()          { return tipoProyecto; }
    public void setTipoProyecto(TipoProyecto v)    { this.tipoProyecto = v; }

    public String getOfertaTrabajo()               { return ofertaTrabajo; }
    public void setOfertaTrabajo(String v)         { this.ofertaTrabajo = v; }

    public String getCliente()                     { return cliente; }
    public void setCliente(String v)               { this.cliente = v; }

    public Integer getIdUbicacion()                { return idUbicacion; }
    public void setIdUbicacion(Integer v)          { this.idUbicacion = v; }

    public String getLugarProyecto()               { return lugarProyecto; }
    public void setLugarProyecto(String v)         { this.lugarProyecto = v; }

    public EntidadFederativa getEstadoProyectoGeo()          { return estadoProyectoGeo; }
    public void setEstadoProyectoGeo(EntidadFederativa v)    { this.estadoProyectoGeo = v; }

    public LocalDate getFechaEstimadaInicio()      { return fechaEstimadaInicio; }
    public void setFechaEstimadaInicio(LocalDate v){ this.fechaEstimadaInicio = v; }

    public LocalDate getFechaEstimadaFin()         { return fechaEstimadaFin; }
    public void setFechaEstimadaFin(LocalDate v)   { this.fechaEstimadaFin = v; }

    public Byte getCalificacionProyecto()          { return calificacionProyecto; }
    public void setCalificacionProyecto(Byte v)    { this.calificacionProyecto = v; }

    public EstatusProyecto getEstatusProyecto()         { return estatusProyecto; }
    public void setEstatusProyecto(EstatusProyecto v)   { this.estatusProyecto = v; }

    public String getDescripcionProyecto()         { return descripcionProyecto; }
    public void setDescripcionProyecto(String v)   { this.descripcionProyecto = v; }
}
