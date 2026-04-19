package com.indecsa.dto;

import com.indecsa.model.AsignacionTrabajadorProyecto.EstatusAsignacion;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class AsignacionTrabajadorProyectoDTO {

    public record Request(
        @NotNull(message = "El id del trabajador es obligatorio")
        Integer idTrabajador,

        @NotNull(message = "El id del proyecto es obligatorio")
        Integer idProyecto,

        @NotNull(message = "El id de asignación proyecto-contratista es obligatorio")
        Integer idAsignacionPc,

        @Size(max = 100)
        String puestoEnProyecto,

        LocalDate fechaInicio,
        LocalDate fechaFinEstimada,

        EstatusAsignacion estatusAsignacion,

        @Size(max = 500)
        String observaciones
    ) {}

    public record Response(
        Integer idAsignacionTp,
        Integer idTrabajador,
        String nombreTrabajador,
        Integer idProyecto,
        String nombreProyecto,
        Integer idAsignacionPc,
        String puestoEnProyecto,
        LocalDate fechaInicio,
        LocalDate fechaFinEstimada,
        EstatusAsignacion estatusAsignacion,
        String observaciones
    ) {}
}
