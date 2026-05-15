package com.example.demo.dto.response;

import com.example.demo.model.Proyecto;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class ProyectoResponseDTO {
    private Integer idProyecto;
    private String nombreProyecto;
    private Proyecto.TipoProyecto tipoProyecto;
    private String ofertaTrabajo;
    private String cliente;
    private String municipioProyecto;
    private Proyecto.EntidadFederativa estadoProyectoGeo;
    private LocalDate fechaEstimadaInicio;
    private LocalDate fechaEstimadaFin;
    private Byte calificacionProyecto;
    private Proyecto.EstatusProyecto estatusProyecto;
    private String descripcionProyecto;
}
