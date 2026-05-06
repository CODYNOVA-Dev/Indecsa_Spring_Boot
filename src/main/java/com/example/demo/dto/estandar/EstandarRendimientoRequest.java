package com.example.demo.dto.estandar;

import java.math.BigDecimal;

public class EstandarRendimientoRequest {

    private String nombreActividad;
    private String unidadMedida;
    private BigDecimal rendimientoEsperado;
    private String descripcion;
    private BigDecimal jornadaBaseHoras;

    public String getNombreActividad()              { return nombreActividad; }
    public void setNombreActividad(String v)        { this.nombreActividad = v; }

    public String getUnidadMedida()                 { return unidadMedida; }
    public void setUnidadMedida(String v)           { this.unidadMedida = v; }

    public BigDecimal getRendimientoEsperado()      { return rendimientoEsperado; }
    public void setRendimientoEsperado(BigDecimal v){ this.rendimientoEsperado = v; }

    public String getDescripcion()                  { return descripcion; }
    public void setDescripcion(String v)            { this.descripcion = v; }

    public BigDecimal getJornadaBaseHoras()         { return jornadaBaseHoras; }
    public void setJornadaBaseHoras(BigDecimal v)   { this.jornadaBaseHoras = v; }
}
