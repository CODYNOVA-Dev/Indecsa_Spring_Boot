package com.example.demo.dto.response;

import lombok.Builder;
import lombok.Data;

// RolResponseDTO is in the same package — no import needed

@Data
@Builder
public class EmpleadoResponseDTO {

    private Integer idEmpleado;
    private String nombreEmpleado;
    private String curp;
    private String correoEmpleado;
    // La contraseña nunca se expone en el response
    private RolResponseDTO rol;
}
