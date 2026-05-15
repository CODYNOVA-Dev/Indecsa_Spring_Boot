package com.example.demo.dto.response;

import com.example.demo.model.EstandarRendimiento.UnidadMedida;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class EstandarRendimientoResponseDTO {

    private Integer idEstandar;
    private String nombreActividad;
    private UnidadMedida unidadMedida;
    private BigDecimal rendimientoEsperado;
}
