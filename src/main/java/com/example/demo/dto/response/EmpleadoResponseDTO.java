package com.example.demo.dto.response;

import com.example.demo.model.Rol;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmpleadoResponseDTO {
    private Integer idEmpleado;
    private String nombreEmpleado;
    private String curp;
    private String correoEmpleado;
    private Integer idRol;
    private Rol.NombreRol nombreRol;
}
