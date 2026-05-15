package com.example.demo.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class EmpleadoRequestDTO {

    @NotBlank @Size(max = 100)
    private String nombreEmpleado;

    @NotBlank @Size(min = 18, max = 18)
    private String curp;

    @NotBlank @Email @Size(max = 100)
    private String correoEmpleado;

    @NotBlank @Size(min = 8)
    private String contrasena;

    @NotNull
    private Integer idRol;
}
