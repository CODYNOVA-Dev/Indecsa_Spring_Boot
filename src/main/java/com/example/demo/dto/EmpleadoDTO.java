package com.indecsa.dto;

import com.indecsa.model.Rol.NombreRol;
import jakarta.validation.constraints.*;

public class EmpleadoDTO {

    public record Request(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100)
        String nombreEmpleado,

        @NotBlank(message = "El CURP es obligatorio")
        @Size(min = 18, max = 18, message = "El CURP debe tener exactamente 18 caracteres")
        String curp,

        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "Formato de correo inválido")
        @Size(max = 100)
        String correoEmpleado,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
        String contrasena,

        @NotNull(message = "El id del rol es obligatorio")
        Integer idRol
    ) {}

    public record Response(
        Integer idEmpleado,
        String nombreEmpleado,
        String curp,
        String correoEmpleado,
        NombreRol rol
        // contrasena excluida intencionalmente
    ) {}
}
