package com.example.demo.dto.migratorio;

import com.example.demo.model.RegistroMigratorio.CategoriaVisitante;
import java.time.LocalDate;

public class RegistroMigratorioRequest {
    private String folioDocumento;
    private CategoriaVisitante categoria;
    private LocalDate fechaEmision;
    private Integer diasVigencia;
    private LocalDate fechaVencimiento;
    private Boolean permisoTrabajo;
    private Boolean activo;

    public String getFolioDocumento()          { return folioDocumento; }
    public void setFolioDocumento(String v)    { this.folioDocumento = v; }
    public CategoriaVisitante getCategoria()   { return categoria; }
    public void setCategoria(CategoriaVisitante v) { this.categoria = v; }
    public LocalDate getFechaEmision()         { return fechaEmision; }
    public void setFechaEmision(LocalDate v)   { this.fechaEmision = v; }
    public Integer getDiasVigencia()           { return diasVigencia; }
    public void setDiasVigencia(Integer v)     { this.diasVigencia = v; }
    public LocalDate getFechaVencimiento()     { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDate v){ this.fechaVencimiento = v; }
    public Boolean getPermisoTrabajo()         { return permisoTrabajo; }
    public void setPermisoTrabajo(Boolean v)   { this.permisoTrabajo = v; }
    public Boolean getActivo()                 { return activo; }
    public void setActivo(Boolean v)           { this.activo = v; }
}
