package com.example.demo.dto.response;

import com.example.demo.model.AsignacionTrabajadorProyecto.EstatusAsignacion;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class AsignacionTrabajadorProyectoResponseDTO {

    private Integer idAsignacionTp;
    private TrabajadorResponseDTO trabajador;
    private ProyectoResponseDTO proyecto;
    private AsignacionProyectoContratistaResponseDTO asignacionProyectoContratista;
    private String puestoEnProyecto;
    private LocalDate fechaInicio;
    private LocalDate fechaFinEstimada;
    private EstatusAsignacion estatusAsignacion;
}
