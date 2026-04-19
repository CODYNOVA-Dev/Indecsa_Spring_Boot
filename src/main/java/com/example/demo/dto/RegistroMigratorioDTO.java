package com.indecsa.dto;

import com.indecsa.model.RegistroMigratorio.CategoriaVisitante;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class RegistroMigratorioDTO {

    public record Request(
        @NotBlank(message = "El folio del documento es obligatorio")
        @Size(max = 50)
        String folioDocumento,

        @NotNull(message = "La categoría es obligatoria")
        CategoriaVisitante categoria,

        @NotNull(message = "La fecha de emisión es obligatoria")
        LocalDate fechaEmision,

        @Max(value = 1460, message = "Los días de vigencia no pueden superar 1460")
        Integer diasVigencia,

        @NotNull(message = "La fecha de vencimiento es obligatoria")
        LocalDate fechaVencimiento,

        @NotNull(message = "El permiso de trabajo es obligatorio")
        Boolean permisoTrabajo,

        @NotNull(message = "El campo activo es obligatorio")
        Boolean activo
    ) {}

    public record Response(
        Integer idMigratorio,
        String folioDocumento,
        CategoriaVisitante categoria,
        LocalDate fechaEmision,
        Integer diasVigencia,
        LocalDate fechaVencimiento,
        Boolean permisoTrabajo,
        Boolean activo
    ) {}
}
