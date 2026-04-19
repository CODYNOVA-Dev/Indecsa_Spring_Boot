package com.indecsa.dto;

import com.indecsa.model.Proyecto.EntidadFederativa;
import com.indecsa.model.Proyecto.EstatusProyecto;
import com.indecsa.model.Proyecto.TipoProyecto;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class ProyectoDTO {

    public record Request(
        @NotBlank(message = "El nombre del proyecto es obligatorio")
        @Size(max = 150)
        String nombreProyecto,

        TipoProyecto tipoProyecto,

        @Size(max = 200)
        String ofertaTrabajo,

        @NotBlank(message = "El cliente es obligatorio")
        @Size(max = 200)
        String cliente,

        @NotNull(message = "El id de ubicación es obligatorio")
        Integer idUbicacion,

        @Size(max = 100)
        String municipioProyecto,

        @NotNull(message = "El estado geográfico es obligatorio")
        EntidadFederativa estadoProyectoGeo,

        LocalDate fechaEstimadaInicio,
        LocalDate fechaEstimadaFin,

        @Min(1) @Max(5)
        Byte calificacionProyecto,

        EstatusProyecto estatusProyecto,

        @Size(max = 500)
        String descripcionProyecto
    ) {}

    public record Response(
        Integer idProyecto,
        String nombreProyecto,
        TipoProyecto tipoProyecto,
        String ofertaTrabajo,
        String cliente,
        UbicacionProyectoDTO.Response ubicacion,
        String municipioProyecto,
        EntidadFederativa estadoProyectoGeo,
        LocalDate fechaEstimadaInicio,
        LocalDate fechaEstimadaFin,
        Byte calificacionProyecto,
        EstatusProyecto estatusProyecto,
        String descripcionProyecto
    ) {}
}
