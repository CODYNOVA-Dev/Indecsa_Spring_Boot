package com.example.demo.dto.registro;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class RegistroHorasRequest {

    @NotNull
    private Integer idAsignacionTp;

    private Integer idCuadrilla;

    @NotNull
    private LocalDate fechaRegistro;

    @NotNull
    @DecimalMin("0.01")
    @DecimalMax("24.0")
    private BigDecimal horasTrabajadas;

    @NotBlank
    private String tipoPeriodo;

    @Size(max = 500)
    private String observaciones;

    public Integer getIdAsignacionTp()          { return idAsignacionTp; }
    public void setIdAsignacionTp(Integer v)    { this.idAsignacionTp = v; }

    public Integer getIdCuadrilla()             { return idCuadrilla; }
    public void setIdCuadrilla(Integer v)       { this.idCuadrilla = v; }

    public LocalDate getFechaRegistro()         { return fechaRegistro; }
    public void setFechaRegistro(LocalDate v)   { this.fechaRegistro = v; }

    public BigDecimal getHorasTrabajadas()      { return horasTrabajadas; }
    public void setHorasTrabajadas(BigDecimal v){ this.horasTrabajadas = v; }

    public String getTipoPeriodo()              { return tipoPeriodo; }
    public void setTipoPeriodo(String v)        { this.tipoPeriodo = v; }

    public String getObservaciones()            { return observaciones; }
    public void setObservaciones(String v)      { this.observaciones = v; }
}
