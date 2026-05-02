package com.example.demo.dto.contratista;

import com.example.demo.model.Contratista.EntidadFederativa;
import com.example.demo.model.Contratista.EstadoContratista;

public class ContratistaRequest {

    private String nombreContratista;
    private String curp;
    private String rfcContratista;
    private String telefonoContratista;
    private String correoContratista;
    private String descripcionContratista;
    private String experiencia;
    private Byte calificacionContratista;
    private EstadoContratista estadoContratista;
    private EntidadFederativa ubicacionContratista;

    public String getNombreContratista()            { return nombreContratista; }
    public void setNombreContratista(String v)      { this.nombreContratista = v; }

    public String getCurp()                         { return curp; }
    public void setCurp(String v)                   { this.curp = v; }

    public String getRfcContratista()               { return rfcContratista; }
    public void setRfcContratista(String v)         { this.rfcContratista = v; }

    public String getTelefonoContratista()          { return telefonoContratista; }
    public void setTelefonoContratista(String v)    { this.telefonoContratista = v; }

    public String getCorreoContratista()            { return correoContratista; }
    public void setCorreoContratista(String v)      { this.correoContratista = v; }

    public String getDescripcionContratista()       { return descripcionContratista; }
    public void setDescripcionContratista(String v) { this.descripcionContratista = v; }

    public String getExperiencia()                  { return experiencia; }
    public void setExperiencia(String v)            { this.experiencia = v; }

    public Byte getCalificacionContratista()        { return calificacionContratista; }
    public void setCalificacionContratista(Byte v)  { this.calificacionContratista = v; }

    public EstadoContratista getEstadoContratista()       { return estadoContratista; }
    public void setEstadoContratista(EstadoContratista v) { this.estadoContratista = v; }

    public EntidadFederativa getUbicacionContratista()         { return ubicacionContratista; }
    public void setUbicacionContratista(EntidadFederativa v)   { this.ubicacionContratista = v; }
}
