package com.example.demo.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AsignacionTrabajadorProyectoRequestDTO {

    @NotNull(message = "El id del trabajador es obligatorio")
    private Integer idTrabajador;

    @NotNull(message = "El id del proyecto es obligatorio")
    private Integer idProyecto;

    @NotNull(message = "El id de la asignación proyecto-contratista es obligatorio")
    private Integer idAsignacionPc;

    @Size(max = 100, message = "El puesto en el proyecto no puede exceder 100 caracteres")
    private String puestoEnProyecto;

    private LocalDate fechaInicio;

    private LocalDate fechaFinEstimada;
}
