package com.example.demo.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AvancePartidaRequestDTO {

    @NotNull(message = "El id del proyecto es obligatorio")
    private Integer idProyecto;

    private Integer idCuadrilla;   // nullable

    private Integer idEstandar;    // nullable

    @NotBlank(message = "El nombre de la partida es obligatorio")
    @Size(max = 200, message = "El nombre de la partida no puede exceder 200 caracteres")
    private String nombrePartida;

    @NotNull(message = "La fecha de registro es obligatoria")
    private LocalDate fechaRegistro;

    @NotNull(message = "La cantidad ejecutada es obligatoria")
    @DecimalMin(value = "0.0001", message = "La cantidad ejecutada debe ser mayor a 0")
    private BigDecimal cantidadEjecutada;

    @NotNull(message = "El id del empleado que registra es obligatorio")
    private Integer idEmpleadoRegistro;
}
