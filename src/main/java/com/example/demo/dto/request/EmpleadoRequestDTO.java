package com.example.demo.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class EmpleadoRequestDTO {

    @NotBlank(message = "El nombre del empleado es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombreEmpleado;

    @NotBlank(message = "La CURP es obligatoria")
    @Size(min = 18, max = 18, message = "La CURP debe tener exactamente 18 caracteres")
    private String curp;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo no tiene un formato válido")
    @Size(max = 100, message = "El correo no puede exceder 100 caracteres")
    private String correoEmpleado;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 255, message = "La contraseña debe tener entre 8 y 255 caracteres")
    private String contrasena;

    @NotNull(message = "El id del rol es obligatorio")
    private Integer idRol;
}
