package com.example.demo.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {

    private Integer idEmpleado;
    private String nombreEmpleado;
    private String correoEmpleado;
    private String nombreRol; // "ADMIN" o "CAPITAL_HUMANO"
}