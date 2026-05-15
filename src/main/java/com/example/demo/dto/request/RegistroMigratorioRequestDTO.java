package com.example.demo.dto.request;

import com.example.demo.model.RegistroMigratorio.CategoriaVisitante;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RegistroMigratorioRequestDTO {

    @NotBlank(message = "El folio del documento es obligatorio")
    @Size(max = 50, message = "El folio no puede exceder 50 caracteres")
    private String folioDocumento;

    @NotNull(message = "La categoría es obligatoria")
    private CategoriaVisitante categoria;

    @NotNull(message = "La fecha de emisión es obligatoria")
    private LocalDate fechaEmision;

    private Integer diasVigencia;

    @NotNull(message = "La fecha de vencimiento es obligatoria")
    private LocalDate fechaVencimiento;

    @NotNull(message = "El permiso de trabajo es obligatorio")
    private Boolean permisoTrabajo;
}
