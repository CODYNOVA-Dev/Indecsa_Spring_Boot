package com.example.demo.dto.request;

import com.example.demo.model.AsignacionTrabajadorProyecto;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class AsignacionTrabajadorProyectoRequestDTO {

    @NotNull
    private Integer idTrabajador;

    @NotNull
    private Integer idProyecto;

    @NotNull
    private Integer idAsignacionPc;

    @Size(max = 100)
    private String puestoEnProyecto;

    private LocalDate fechaInicio;

    private LocalDate fechaFinEstimada;

    private AsignacionTrabajadorProyecto.EstatusAsignacion estatusAsignacion;

    @Size(max = 500)
    private String observaciones;
}
