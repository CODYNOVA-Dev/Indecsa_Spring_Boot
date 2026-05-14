package com.example.demo.dto.rendimiento;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RendimientoIndicadorResponse {

    private Integer idTrabajador;
    private String nombreTrabajador;
    private Integer idProyecto;
    private String nombreProyecto;
    private Integer idCuadrilla;
    private String nombreCuadrilla;
    private LocalDate periodoInicio;
    private LocalDate periodoFin;
    private BigDecimal totalHorasTrabajadas;
    private BigDecimal totalAvanceEjecutado;
    private String unidadMedida;
    private BigDecimal rendimientoReal;
    private BigDecimal rendimientoEsperado;
    private BigDecimal porcentajeDesviacion;
    private String indicadorSemaforo;

    public Integer getIdTrabajador()                { return idTrabajador; }
    public void setIdTrabajador(Integer v)          { this.idTrabajador = v; }

    public String getNombreTrabajador()             { return nombreTrabajador; }
    public void setNombreTrabajador(String v)       { this.nombreTrabajador = v; }

    public Integer getIdProyecto()                  { return idProyecto; }
    public void setIdProyecto(Integer v)            { this.idProyecto = v; }

    public String getNombreProyecto()               { return nombreProyecto; }
    public void setNombreProyecto(String v)         { this.nombreProyecto = v; }

    public Integer getIdCuadrilla()                 { return idCuadrilla; }
    public void setIdCuadrilla(Integer v)           { this.idCuadrilla = v; }

    public String getNombreCuadrilla()              { return nombreCuadrilla; }
    public void setNombreCuadrilla(String v)        { this.nombreCuadrilla = v; }

    public LocalDate getPeriodoInicio()             { return periodoInicio; }
    public void setPeriodoInicio(LocalDate v)       { this.periodoInicio = v; }

    public LocalDate getPeriodoFin()                { return periodoFin; }
    public void setPeriodoFin(LocalDate v)          { this.periodoFin = v; }

    public BigDecimal getTotalHorasTrabajadas()     { return totalHorasTrabajadas; }
    public void setTotalHorasTrabajadas(BigDecimal v){ this.totalHorasTrabajadas = v; }

    public BigDecimal getTotalAvanceEjecutado()     { return totalAvanceEjecutado; }
    public void setTotalAvanceEjecutado(BigDecimal v){ this.totalAvanceEjecutado = v; }

    public String getUnidadMedida()                 { return unidadMedida; }
    public void setUnidadMedida(String v)           { this.unidadMedida = v; }

    public BigDecimal getRendimientoReal()          { return rendimientoReal; }
    public void setRendimientoReal(BigDecimal v)    { this.rendimientoReal = v; }

    public BigDecimal getRendimientoEsperado()      { return rendimientoEsperado; }
    public void setRendimientoEsperado(BigDecimal v){ this.rendimientoEsperado = v; }

    public BigDecimal getPorcentajeDesviacion()     { return porcentajeDesviacion; }
    public void setPorcentajeDesviacion(BigDecimal v){ this.porcentajeDesviacion = v; }

    public String getIndicadorSemaforo()            { return indicadorSemaforo; }
    public void setIndicadorSemaforo(String v)      { this.indicadorSemaforo = v; }
}
