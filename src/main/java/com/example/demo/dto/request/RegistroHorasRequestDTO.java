package com.example.demo.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class RegistroHorasRequestDTO {

    @NotNull
    private Integer idAsignacionTp;

    private Integer idCuadrilla;

    @NotNull
    private LocalDate fechaRegistro;

    @NotNull @DecimalMin("0.01") @DecimalMax("24.0")
    private BigDecimal horasTrabajadas;
}
