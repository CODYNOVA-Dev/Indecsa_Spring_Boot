package com.example.demo.dto.ubicacion;

import com.example.demo.model.UbicacionProyecto;

public class UbicacionProyectoResponse {
    private Integer idUbicacion;
    private String calle;
    private String numExt;
    private String numInt;
    private String colonia;
    private Integer codPost;
    private String munAlc;
    private String estado;

    public static UbicacionProyectoResponse from(UbicacionProyecto u) {
        UbicacionProyectoResponse r = new UbicacionProyectoResponse();
        r.idUbicacion = u.getIdUbicacion();
        r.calle = u.getCalle();
        r.numExt = u.getNumExt();
        r.numInt = u.getNumInt();
        r.colonia = u.getColonia();
        r.codPost = u.getCodPost();
        r.munAlc = u.getMunAlc();
        r.estado = u.getEstado() != null ? u.getEstado().name() : null;
        return r;
    }

    public Integer getIdUbicacion() { return idUbicacion; }
    public String getCalle()        { return calle; }
    public String getNumExt()       { return numExt; }
    public String getNumInt()       { return numInt; }
    public String getColonia()      { return colonia; }
    public Integer getCodPost()     { return codPost; }
    public String getMunAlc()       { return munAlc; }
    public String getEstado()       { return estado; }
}
