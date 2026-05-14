package com.example.demo.dto.contratista;

import com.example.demo.model.Contratista;

public class ContratistaResponse {

    private Integer idContratista;
    private String nombreContratista;
    private String rfcContratista;
    private String telefonoContratista;
    private String correoContratista;
    private String descripcionContratista;
    private String experiencia;
    private Integer calificacionContratista;
    private String estadoContratista;
    private String ubicacionContratista;

    public static ContratistaResponse from(Contratista c) {
        ContratistaResponse r = new ContratistaResponse();
        r.idContratista = c.getIdContratista();
        r.nombreContratista = c.getNombreContratista();
        r.rfcContratista = c.getRfcContratista();
        r.telefonoContratista = c.getTelefonoContratista();
        r.correoContratista = c.getCorreoContratista();
        r.descripcionContratista = c.getDescripcionContratista();
        r.experiencia = c.getExperiencia();
        r.calificacionContratista = c.getCalificacionContratista() != null ? c.getCalificacionContratista().intValue() : null;
        r.estadoContratista = c.getEstadoContratista() != null ? c.getEstadoContratista().name() : null;
        r.ubicacionContratista = c.getUbicacionContratista() != null ? c.getUbicacionContratista().name() : null;
        return r;
    }

    public Integer getIdContratista()          { return idContratista; }
    public String getNombreContratista()       { return nombreContratista; }
    public String getRfcContratista()          { return rfcContratista; }
    public String getTelefonoContratista()     { return telefonoContratista; }
    public String getCorreoContratista()       { return correoContratista; }
    public String getDescripcionContratista()  { return descripcionContratista; }
    public String getExperiencia()             { return experiencia; }
    public Integer getCalificacionContratista(){ return calificacionContratista; }
    public String getEstadoContratista()       { return estadoContratista; }
    public String getUbicacionContratista()    { return ubicacionContratista; }
}
