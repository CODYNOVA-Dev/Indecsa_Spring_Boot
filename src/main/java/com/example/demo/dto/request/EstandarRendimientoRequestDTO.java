package com.example.demo.dto.request;

import com.example.demo.model.EstandarRendimiento.UnidadMedida;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EstandarRendimientoRequestDTO {

    @NotBlank(message = "El nombre de la actividad es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombreActividad;

    @NotNull(message = "La unidad de medida es obligatoria")
    private UnidadMedida unidadMedida;

    @NotNull(message = "El rendimiento esperado es obligatorio")
    @DecimalMin(value = "0.0001", message = "El rendimiento esperado debe ser mayor a 0")
    private BigDecimal rendimientoEsperado;
}
