package com.example.demo.dto;

import com.example.demo.model.Rol.NombreRol;
import jakarta.validation.constraints.NotNull;

public class RolDTO {

    public record Request(
        @NotNull(message = "El nombre del rol es obligatorio")
        NombreRol nombreRol,

        String descripcionRol
    ) {}

    public record Response(
        Integer idRol,
        NombreRol nombreRol,
        String descripcionRol
    ) {}
}
