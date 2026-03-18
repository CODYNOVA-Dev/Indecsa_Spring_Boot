package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO para login.
 *
 * CORRECCIÓN: Se eliminó @Email del campo correoEmpleado.
 * El data seeding usa correos cortos como "admin" y "cap" (sin dominio),
 * por lo que @Email rechazaba esas credenciales con 400 antes de llegar
 * al servicio, haciendo el login imposible para esos usuarios.
 *
 * Si en el futuro todos los correos tendrán formato real de email,
 * se puede restaurar @Email aquí y actualizar el data seeding.
 */
@Data
public class LoginRequestDTO {

    @NotBlank(message = "El correo es obligatorio")
    private String correoEmpleado;

    @NotBlank(message = "La contraseña es obligatoria")
    private String contrasena;
}