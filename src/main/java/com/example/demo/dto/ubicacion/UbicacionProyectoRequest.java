package com.example.demo.dto.ubicacion;

import com.example.demo.model.UbicacionProyecto.EntidadFederativa;

public class UbicacionProyectoRequest {
    private String calle;
    private String numExt;
    private String numInt;
    private String colonia;
    private Integer codPost;
    private String munAlc;
    private EntidadFederativa estado;

    public String getCalle()               { return calle; }
    public void setCalle(String v)         { this.calle = v; }
    public String getNumExt()              { return numExt; }
    public void setNumExt(String v)        { this.numExt = v; }
    public String getNumInt()              { return numInt; }
    public void setNumInt(String v)        { this.numInt = v; }
    public String getColonia()             { return colonia; }
    public void setColonia(String v)       { this.colonia = v; }
    public Integer getCodPost()            { return codPost; }
    public void setCodPost(Integer v)      { this.codPost = v; }
    public String getMunAlc()              { return munAlc; }
    public void setMunAlc(String v)        { this.munAlc = v; }
    public EntidadFederativa getEstado()   { return estado; }
    public void setEstado(EntidadFederativa v) { this.estado = v; }
}
