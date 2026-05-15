package com.example.demo.dto.response;

import com.example.demo.model.Cuadrilla.EstatusCuadrilla;
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
    private EstatusCuadrilla estatusCuadrilla;
}
