package com.indecsa.dto;

import com.indecsa.model.AsignacionProyectoContratista.EstatusContrato;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class AsignacionProyectoContratistaDTO {

    public record Request(
        @NotNull(message = "El id del proyecto es obligatorio")
        Integer idProyecto,

        @NotNull(message = "El id del contratista es obligatorio")
        Integer idContratista,

        @Size(max = 50)
        String numeroContrato,

        LocalDate fechaInicio,
        LocalDate fechaFinEstimada,

        @NotNull(message = "El personal asignado es obligatorio")
        @Min(value = 1, message = "Debe haber al menos 1 persona asignada")
        Integer personalAsignado,

        @Size(max = 500)
        String puestosRequeridos,

        EstatusContrato estatusContrato,

        @Size(max = 500)
        String observaciones
    ) {}

    public record Response(
        Integer idAsignacionPc,
        Integer idProyecto,
        String nombreProyecto,
        Integer idContratista,
        String nombreContratista,
        String numeroContrato,
        LocalDate fechaInicio,
        LocalDate fechaFinEstimada,
        Integer personalAsignado,
        String puestosRequeridos,
        EstatusContrato estatusContrato,
        String observaciones
    ) {}
}
