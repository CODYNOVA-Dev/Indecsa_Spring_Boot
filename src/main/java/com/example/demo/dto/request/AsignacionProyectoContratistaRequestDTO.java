package com.example.demo.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AsignacionProyectoContratistaRequestDTO {

    @NotNull(message = "El id del proyecto es obligatorio")
    private Integer idProyecto;

    @NotNull(message = "El id del contratista es obligatorio")
    private Integer idContratista;

    @Size(max = 50, message = "El número de contrato no puede exceder 50 caracteres")
    private String numeroContrato;

    private LocalDate fechaInicio;

    private LocalDate fechaFinEstimada;

    @NotNull(message = "El personal asignado es obligatorio")
    @Min(value = 1, message = "El personal asignado debe ser al menos 1")
    private Integer personalAsignado;

    @Size(max = 500, message = "Las observaciones no pueden exceder 500 caracteres")
    private String observaciones;
}
