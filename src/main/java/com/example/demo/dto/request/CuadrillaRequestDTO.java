package com.example.demo.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CuadrillaRequestDTO {

    @NotNull
    private Integer idProyecto;

    @NotBlank @Size(max = 100)
    private String nombreCuadrilla;

    @Size(max = 200)
    private String frenteTrabajo;
}
