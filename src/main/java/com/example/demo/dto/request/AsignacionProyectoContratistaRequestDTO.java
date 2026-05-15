package com.example.demo.dto.request;

import com.example.demo.model.AsignacionProyectoContratista;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class AsignacionProyectoContratistaRequestDTO {

    @NotNull
    private Integer idProyecto;

    @NotNull
    private Integer idContratista;

    @Size(max = 50)
    private String numeroContrato;

    private LocalDate fechaInicio;

    private LocalDate fechaFinEstimada;

    @NotNull @Min(1)
    private Integer personalAsignado;

    @Size(max = 500)
    private String puestosRequeridos;

    private AsignacionProyectoContratista.EstatusContrato estatusContrato;

    @Size(max = 500)
    private String observaciones;
}
