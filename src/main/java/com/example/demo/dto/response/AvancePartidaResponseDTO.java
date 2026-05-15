package com.example.demo.dto.response;

import com.example.demo.model.EstandarRendimiento.UnidadMedida;
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
    private Integer idCuadrilla;       // nullable
    private String nombreCuadrilla;    // nullable
    private Integer idEstandar;        // nullable
    private String nombreActividad;    // nullable
    private UnidadMedida unidadMedida; // nullable
    private String nombrePartida;
    private LocalDate fechaRegistro;
    private BigDecimal cantidadEjecutada;
    private Integer idEmpleadoRegistro;
    private String nombreEmpleadoRegistro;
}
