package com.example.demo.dto.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class AvancePartidaResponseDTO {
    private Integer idAvance;
    private Integer idProyecto;
    private String nombreProyecto;
    private Integer idCuadrilla;
    private String nombreCuadrilla;
    private Integer idEstandar;
    private String nombreActividad;
    private BigDecimal rendimientoEsperado;
    private String nombrePartida;
    private LocalDate fechaRegistro;
    private BigDecimal cantidadEjecutada;
}
