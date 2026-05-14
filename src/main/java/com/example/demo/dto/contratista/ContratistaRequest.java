package com.example.demo.dto.contratista;

import com.example.demo.model.Contratista.EntidadFederativa;
import com.example.demo.model.Contratista.EstadoContratista;
import jakarta.validation.constraints.*;

public class ContratistaRequest {

    @NotBlank
    @Size(max = 100)
    private String nombreContratista;

    @NotBlank
    @Size(min = 18, max = 18)
    private String curp;

    @NotBlank
    @Size(min = 12, max = 13)
    private String rfcContratista;

    @NotBlank
    @Size(max = 15)
    private String telefonoContratista;

    @NotBlank
    @Email
    @Size(max = 100)
    private String correoContratista;

    @Size(max = 500)
    private String descripcionContratista;

    @Size(max = 200)
    private String experiencia;

    @Min(1) @Max(5)
    private Byte calificacionContratista;

    @NotNull
    private EstadoContratista estadoContratista;

    @NotNull
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
