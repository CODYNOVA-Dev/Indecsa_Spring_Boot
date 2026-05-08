package com.example.demo.dto.estandar;

import com.example.demo.model.EstandarRendimiento;
import java.math.BigDecimal;

public class EstandarRendimientoResponse {

    private Integer idEstandar;
    private String nombreActividad;
    private String unidadMedida;
    private BigDecimal rendimientoEsperado;
    private String descripcion;
    private BigDecimal jornadaBaseHoras;

    public static EstandarRendimientoResponse from(EstandarRendimiento e) {
        EstandarRendimientoResponse r = new EstandarRendimientoResponse();
        r.idEstandar          = e.getIdEstandar();
        r.nombreActividad     = e.getNombreActividad();
        r.unidadMedida        = e.getUnidadMedida() != null ? e.getUnidadMedida().name() : null;
        r.rendimientoEsperado = e.getRendimientoEsperado();
        r.descripcion         = e.getDescripcion();
        r.jornadaBaseHoras    = e.getJornadaBaseHoras();
        return r;
    }

    public Integer getIdEstandar()              { return idEstandar; }
    public String getNombreActividad()          { return nombreActividad; }
    public String getUnidadMedida()             { return unidadMedida; }
    public BigDecimal getRendimientoEsperado()  { return rendimientoEsperado; }
    public String getDescripcion()              { return descripcion; }
    public BigDecimal getJornadaBaseHoras()     { return jornadaBaseHoras; }
}
