package com.example.demo.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class RegistroHorasResponseDTO {

    private Integer idRegistro;
    private Integer idAsignacionTp;
    private String nombreTrabajador;
    private Integer idCuadrilla;       // nullable
    private String nombreCuadrilla;    // nullable
    private LocalDate fechaRegistro;
    private BigDecimal horasTrabajadas;
    private Integer idEmpleadoRegistro;
    private String nombreEmpleadoRegistro;
}
