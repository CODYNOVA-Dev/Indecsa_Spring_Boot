package com.example.demo.dto.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class EstandarRendimientoResponseDTO {

    private Integer idEstandar;
    private String nombreActividad;
    private String unidadMedida;
    private BigDecimal rendimientoEsperado;
}
