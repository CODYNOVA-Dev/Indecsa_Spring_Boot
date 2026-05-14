package com.example.demo.dto.estandar;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class EstandarRendimientoRequest {

    @NotBlank
    @Size(max = 200)
    private String nombreActividad;

    @NotBlank
    private String unidadMedida;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal rendimientoEsperado;

    @Size(max = 500)
    private String descripcion;

    @NotNull
    @DecimalMin("0.01")
    @DecimalMax("24.0")
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
