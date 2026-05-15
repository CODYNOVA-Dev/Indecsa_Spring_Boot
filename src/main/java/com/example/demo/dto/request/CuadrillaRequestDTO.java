package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CuadrillaRequestDTO {

    @NotNull(message = "El id del proyecto es obligatorio")
    private Integer idProyecto;

    @NotBlank(message = "El nombre de la cuadrilla es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombreCuadrilla;

    @Size(max = 200, message = "El frente de trabajo no puede exceder 200 caracteres")
    private String frenteTrabajo;
}
