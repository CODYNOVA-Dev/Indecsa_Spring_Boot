package com.example.demo.dto.request;

import com.example.demo.model.Proyecto.TipoProyecto;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProyectoRequestDTO {

    @NotBlank(message = "El nombre del proyecto es obligatorio")
    @Size(max = 150, message = "El nombre no puede exceder 150 caracteres")
    private String nombreProyecto;

    private TipoProyecto tipoProyecto;

    @Size(max = 200, message = "La oferta de trabajo no puede exceder 200 caracteres")
    private String ofertaTrabajo;

    @NotBlank(message = "El cliente es obligatorio")
    @Size(max = 200, message = "El cliente no puede exceder 200 caracteres")
    private String cliente;

    @NotNull(message = "El domicilio del proyecto es obligatorio")
    private Integer idDomicilio;

    private LocalDate fechaEstimadaInicio;

    private LocalDate fechaEstimadaFin;

    @Min(value = 1, message = "La calificación mínima es 1")
    @Max(value = 5, message = "La calificación máxima es 5")
    private Byte calificacionProyecto;

    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String descripcionProyecto;
}
