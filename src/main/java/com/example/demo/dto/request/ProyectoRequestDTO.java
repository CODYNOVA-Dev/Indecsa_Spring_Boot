package com.example.demo.dto.request;

import com.example.demo.model.Proyecto;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ProyectoRequestDTO {

    @NotBlank @Size(max = 150)
    private String nombreProyecto;

    private Proyecto.TipoProyecto tipoProyecto;

    @Size(max = 200)
    private String ofertaTrabajo;

    @NotBlank @Size(max = 200)
    private String cliente;

    @Size(max = 100)
    private String municipioProyecto;

    private Proyecto.EntidadFederativa estadoProyectoGeo;

    private LocalDate fechaEstimadaInicio;

    private LocalDate fechaEstimadaFin;

    @Min(1) @Max(10)
    private Byte calificacionProyecto;

    private Proyecto.EstatusProyecto estatusProyecto;

    @Size(max = 500)
    private String descripcionProyecto;
}
