package com.example.demo.dto.response;

import com.example.demo.model.AsignacionTrabajadorProyecto;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class AsignacionTrabajadorProyectoResponseDTO {
    private Integer idAsignacionTp;
    private Integer idTrabajador;
    private String nombreTrabajador;
    private Integer idProyecto;
    private String nombreProyecto;
    private Integer idAsignacionPc;
    private String puestoEnProyecto;
    private LocalDate fechaInicio;
    private LocalDate fechaFinEstimada;
    private AsignacionTrabajadorProyecto.EstatusAsignacion estatusAsignacion;
    private String observaciones;
}
