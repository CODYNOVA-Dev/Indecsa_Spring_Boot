package com.example.demo.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AvancePartidaRequestDTO {

    @NotNull
    private Integer idProyecto;

    private Integer idCuadrilla;

    private Integer idEstandar;

    @NotBlank
    @Size(max = 200)
    private String nombrePartida;

    @NotNull
    private LocalDate fechaRegistro;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal cantidadEjecutada;
}
