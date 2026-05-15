package com.example.demo.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class EstandarRendimientoRequestDTO {

    @NotBlank @Size(max = 100)
    private String nombreActividad;

    @NotBlank
    private String unidadMedida;

    @NotNull @DecimalMin("0.01")
    private BigDecimal rendimientoEsperado;
}
