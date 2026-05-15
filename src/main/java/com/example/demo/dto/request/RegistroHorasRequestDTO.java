package com.example.demo.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class RegistroHorasRequestDTO {

    @NotNull(message = "El id de la asignación trabajador-proyecto es obligatorio")
    private Integer idAsignacionTp;

    private Integer idCuadrilla;  // nullable

    @NotNull(message = "La fecha de registro es obligatoria")
    private LocalDate fechaRegistro;

    @NotNull(message = "Las horas trabajadas son obligatorias")
    @DecimalMin(value = "0.01", message = "Las horas trabajadas deben ser mayores a 0")
    @DecimalMax(value = "24.00", message = "Las horas trabajadas no pueden superar 24")
    private BigDecimal horasTrabajadas;

    @NotNull(message = "El id del empleado que registra es obligatorio")
    private Integer idEmpleadoRegistro;
}
