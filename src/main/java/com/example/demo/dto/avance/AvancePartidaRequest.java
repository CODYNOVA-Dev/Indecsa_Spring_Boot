package com.example.demo.dto.avance;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class AvancePartidaRequest {

    @NotNull
    private Integer idProyecto;

    private Integer idCuadrilla;
    private Integer idEstandar;

    @NotBlank
    @Size(max = 200)
    private String nombrePartida;

    @NotNull
    private LocalDate fechaRegistro;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal cantidadEjecutada;

    @NotBlank
    private String unidadMedida;

    @DecimalMin("0.0")
    private BigDecimal cantidadProgramada;

    @Size(max = 500)
    private String observaciones;

    public Integer getIdProyecto()                  { return idProyecto; }
    public void setIdProyecto(Integer v)            { this.idProyecto = v; }

    public Integer getIdCuadrilla()                 { return idCuadrilla; }
    public void setIdCuadrilla(Integer v)           { this.idCuadrilla = v; }

    public Integer getIdEstandar()                  { return idEstandar; }
    public void setIdEstandar(Integer v)            { this.idEstandar = v; }

    public String getNombrePartida()                { return nombrePartida; }
    public void setNombrePartida(String v)          { this.nombrePartida = v; }

    public LocalDate getFechaRegistro()             { return fechaRegistro; }
    public void setFechaRegistro(LocalDate v)       { this.fechaRegistro = v; }

    public BigDecimal getCantidadEjecutada()        { return cantidadEjecutada; }
    public void setCantidadEjecutada(BigDecimal v)  { this.cantidadEjecutada = v; }

    public String getUnidadMedida()                 { return unidadMedida; }
    public void setUnidadMedida(String v)           { this.unidadMedida = v; }

    public BigDecimal getCantidadProgramada()       { return cantidadProgramada; }
    public void setCantidadProgramada(BigDecimal v) { this.cantidadProgramada = v; }

    public String getObservaciones()                { return observaciones; }
    public void setObservaciones(String v)          { this.observaciones = v; }
}
