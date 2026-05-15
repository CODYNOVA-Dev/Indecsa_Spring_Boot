package com.example.demo.dto.response;

import com.example.demo.model.AsignacionProyectoContratista;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class AsignacionProyectoContratistaResponseDTO {
    private Integer idAsignacionPc;
    private Integer idProyecto;
    private String nombreProyecto;
    private Integer idContratista;
    private String nombreContratista;
    private String numeroContrato;
    private LocalDate fechaInicio;
    private LocalDate fechaFinEstimada;
    private Integer personalAsignado;
    private String puestosRequeridos;
    private AsignacionProyectoContratista.EstatusContrato estatusContrato;
    private String observaciones;
}
