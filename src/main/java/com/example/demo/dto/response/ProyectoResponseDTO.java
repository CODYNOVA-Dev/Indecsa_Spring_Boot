package com.example.demo.dto.response;

import com.example.demo.model.Proyecto.EstatusProyecto;
import com.example.demo.model.Proyecto.UbicacionGeo;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ProyectoResponseDTO {

    private Integer idProyecto;
    private String nombreProyecto;
    private String tipoProyecto;
    private String lugarProyecto;
    private String municipioProyecto;
    // Corregido: era String, pero en la BD es ENUM('CDMX','Hidalgo','Puebla')
    private UbicacionGeo estadoProyectoGeo;
    private LocalDate fechaEstimadaInicio;
    private LocalDate fechaEstimadaFin;
    private Byte calificacionProyecto;
    private EstatusProyecto estatusProyecto;
    private String descripcionProyecto;
}
