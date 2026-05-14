package com.example.demo.dto.migratorio;

import com.example.demo.model.RegistroMigratorio;
import java.time.LocalDate;

public class RegistroMigratorioResponse {
    private Integer idMigratorio;
    private String folioDocumento;
    private String categoria;
    private LocalDate fechaEmision;
    private Integer diasVigencia;
    private LocalDate fechaVencimiento;
    private Boolean permisoTrabajo;
    private Boolean activo;

    public static RegistroMigratorioResponse from(RegistroMigratorio r) {
        RegistroMigratorioResponse res = new RegistroMigratorioResponse();
        res.idMigratorio = r.getIdMigratorio();
        res.folioDocumento = r.getFolioDocumento();
        res.categoria = r.getCategoria() != null ? r.getCategoria().name() : null;
        res.fechaEmision = r.getFechaEmision();
        res.diasVigencia = r.getDiasVigencia();
        res.fechaVencimiento = r.getFechaVencimiento();
        res.permisoTrabajo = r.getPermisoTrabajo();
        res.activo = r.getActivo();
        return res;
    }

    public Integer getIdMigratorio()    { return idMigratorio; }
    public String getFolioDocumento()   { return folioDocumento; }
    public String getCategoria()        { return categoria; }
    public LocalDate getFechaEmision()  { return fechaEmision; }
    public Integer getDiasVigencia()    { return diasVigencia; }
    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public Boolean getPermisoTrabajo()  { return permisoTrabajo; }
    public Boolean getActivo()          { return activo; }
}
