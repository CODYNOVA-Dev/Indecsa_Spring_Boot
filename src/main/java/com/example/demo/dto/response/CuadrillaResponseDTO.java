package com.example.demo.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CuadrillaResponseDTO {

    private Integer idCuadrilla;
    private Integer idProyecto;
    private String nombreProyecto;
    private String nombreCuadrilla;
    private String frenteTrabajo;
    private String estatusCuadrilla;
}
