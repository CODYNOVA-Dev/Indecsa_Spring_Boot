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
    private Integer idTrabajador;
    private String nombreTrabajador;
    private Integer idProyecto;
    private String nombreProyecto;
    private Integer idCuadrilla;
    private String nombreCuadrilla;
    private LocalDate fechaRegistro;
    private BigDecimal horasTrabajadas;
}
