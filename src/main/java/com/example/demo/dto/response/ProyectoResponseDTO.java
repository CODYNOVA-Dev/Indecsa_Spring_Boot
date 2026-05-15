package com.example.demo.dto.response;

import com.example.demo.model.Proyecto.EstatusProyecto;
import com.example.demo.model.Proyecto.TipoProyecto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ProyectoResponseDTO {

    private Integer idProyecto;
    private String nombreProyecto;
    private TipoProyecto tipoProyecto;
    private String ofertaTrabajo;
    private String cliente;
    // Domicilio denormalizado
    private Integer idDomicilio;
    private String calleDomicilio;
    private String munAlcDomicilio;
    private String nombreEstadoDomicilio;
    private LocalDate fechaEstimadaInicio;
    private LocalDate fechaEstimadaFin;
    private Byte calificacionProyecto;
    private EstatusProyecto estatusProyecto;
    private String descripcionProyecto;
    private String imagenProyectoUrl;
}
