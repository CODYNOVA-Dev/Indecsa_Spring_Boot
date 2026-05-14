package com.example.demo.dto.trabajador;

import com.example.demo.model.Trabajador.EntidadFederativa;
import com.example.demo.model.Trabajador.EstadoTrabajador;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class TrabajadorRequest {

    @NotBlank
    @Size(max = 100)
    private String nombreTrabajador;

    @NotBlank
    @Size(min = 18, max = 18)
    private String curp;

    @NotBlank
    @Size(min = 12, max = 13)
    private String rfc;

    @Size(max = 11)
    private String nssTrabajador;

    @NotBlank
    @Size(max = 100)
    private String nacionalidad;

    private Integer idMigratorio;

    @NotBlank
    @Size(max = 100)
    private String calle;

    @NotBlank
    @Size(max = 10)
    private String numExt;

    @Size(max = 10)
    private String numInt;

    @NotBlank
    @Size(max = 100)
    private String colonia;

    @NotNull
    private Integer codPost;

    @NotBlank
    @Size(max = 100)
    private String munAlc;

    @NotBlank
    @Size(max = 100)
    private String estado;

    @NotBlank
    @Size(max = 100)
    private String puesto;

    @NotBlank
    @Size(max = 500)
    private String descPuesto;

    @NotBlank
    @Size(max = 100)
    private String especialidadTrabajador;

    @NotBlank
    @Size(max = 100)
    private String escolaridad;

    @Size(max = 200)
    private String experiencia;

    @NotBlank
    @Size(max = 15)
    private String telefonoTrabajador;

    @NotBlank
    @Email
    @Size(max = 100)
    private String correoTrabajador;

    @NotBlank
    @Size(max = 200)
    private String contratacion;

    @NotBlank
    @Size(max = 200)
    private String jornada;

    @NotNull
    private EstadoTrabajador estadoTrabajador;

    private String descripcionTrabajador;

    @Min(1) @Max(5)
    private Byte evaluacionTrabajador;

    @NotNull
    private LocalDate fechaIngreso;

    @NotNull
    private EntidadFederativa calidadVida;

    @Size(max = 500)
    private String antPenal;

    @Size(max = 500)
    private String deudorAlim;

    @Size(max = 20)
    private String folioLicCond;

    @Size(max = 50)
    private String estadoCivil;

    @Size(max = 200)
    private String idiomas;

    @Size(max = 100)
    private String lenguaIndigena;

    @Size(max = 50)
    private String sexo;

    public String getNombreTrabajador()        { return nombreTrabajador; }
    public void setNombreTrabajador(String v)  { this.nombreTrabajador = v; }

    public String getCurp()                    { return curp; }
    public void setCurp(String v)              { this.curp = v; }

    public String getRfc()                     { return rfc; }
    public void setRfc(String v)               { this.rfc = v; }

    public String getNssTrabajador()           { return nssTrabajador; }
    public void setNssTrabajador(String v)     { this.nssTrabajador = v; }

    public String getNacionalidad()            { return nacionalidad; }
    public void setNacionalidad(String v)      { this.nacionalidad = v; }

    public Integer getIdMigratorio()           { return idMigratorio; }
    public void setIdMigratorio(Integer v)     { this.idMigratorio = v; }

    public String getCalle()                   { return calle; }
    public void setCalle(String v)             { this.calle = v; }

    public String getNumExt()                  { return numExt; }
    public void setNumExt(String v)            { this.numExt = v; }

    public String getNumInt()                  { return numInt; }
    public void setNumInt(String v)            { this.numInt = v; }

    public String getColonia()                 { return colonia; }
    public void setColonia(String v)           { this.colonia = v; }

    public Integer getCodPost()                { return codPost; }
    public void setCodPost(Integer v)          { this.codPost = v; }

    public String getMunAlc()                  { return munAlc; }
    public void setMunAlc(String v)            { this.munAlc = v; }

    public String getEstado()                  { return estado; }
    public void setEstado(String v)            { this.estado = v; }

    public String getPuesto()                  { return puesto; }
    public void setPuesto(String v)            { this.puesto = v; }

    public String getDescPuesto()              { return descPuesto; }
    public void setDescPuesto(String v)        { this.descPuesto = v; }

    public String getEspecialidadTrabajador()  { return especialidadTrabajador; }
    public void setEspecialidadTrabajador(String v) { this.especialidadTrabajador = v; }

    public String getEscolaridad()             { return escolaridad; }
    public void setEscolaridad(String v)       { this.escolaridad = v; }

    public String getExperiencia()             { return experiencia; }
    public void setExperiencia(String v)       { this.experiencia = v; }

    public String getTelefonoTrabajador()      { return telefonoTrabajador; }
    public void setTelefonoTrabajador(String v){ this.telefonoTrabajador = v; }

    public String getCorreoTrabajador()        { return correoTrabajador; }
    public void setCorreoTrabajador(String v)  { this.correoTrabajador = v; }

    public String getContratacion()            { return contratacion; }
    public void setContratacion(String v)      { this.contratacion = v; }

    public String getJornada()                 { return jornada; }
    public void setJornada(String v)           { this.jornada = v; }

    public EstadoTrabajador getEstadoTrabajador()         { return estadoTrabajador; }
    public void setEstadoTrabajador(EstadoTrabajador v)   { this.estadoTrabajador = v; }

    public String getDescripcionTrabajador()   { return descripcionTrabajador; }
    public void setDescripcionTrabajador(String v) { this.descripcionTrabajador = v; }

    public Byte getEvaluacionTrabajador()      { return evaluacionTrabajador; }
    public void setEvaluacionTrabajador(Byte v){ this.evaluacionTrabajador = v; }

    public LocalDate getFechaIngreso()         { return fechaIngreso; }
    public void setFechaIngreso(LocalDate v)   { this.fechaIngreso = v; }

    public EntidadFederativa getCalidadVida()  { return calidadVida; }
    public void setCalidadVida(EntidadFederativa v) { this.calidadVida = v; }

    public String getAntPenal()                { return antPenal; }
    public void setAntPenal(String v)          { this.antPenal = v; }

    public String getDeudorAlim()              { return deudorAlim; }
    public void setDeudorAlim(String v)        { this.deudorAlim = v; }

    public String getFolioLicCond()            { return folioLicCond; }
    public void setFolioLicCond(String v)      { this.folioLicCond = v; }

    public String getEstadoCivil()             { return estadoCivil; }
    public void setEstadoCivil(String v)       { this.estadoCivil = v; }

    public String getIdiomas()                 { return idiomas; }
    public void setIdiomas(String v)           { this.idiomas = v; }

    public String getLenguaIndigena()          { return lenguaIndigena; }
    public void setLenguaIndigena(String v)    { this.lenguaIndigena = v; }

    public String getSexo()                    { return sexo; }
    public void setSexo(String v)              { this.sexo = v; }
}
