package com.example.demo.dto.response;

import com.example.demo.model.RegistroMigratorio.CategoriaVisitante;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class RegistroMigratorioResponseDTO {

    private Integer idMigratorio;
    private String folioDocumento;
    private CategoriaVisitante categoria;
    private LocalDate fechaEmision;
    private Integer diasVigencia;
    private LocalDate fechaVencimiento;
    private Boolean permisoTrabajo;
    private Boolean activo;
}
