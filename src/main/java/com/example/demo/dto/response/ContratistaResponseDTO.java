package com.example.demo.dto.response;

import com.example.demo.model.Contratista.EstadoContratista;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContratistaResponseDTO {

    private Integer idContratista;
    private String nombreContratista;
    private String curp;
    private String rfcContratista;
    private String telefonoContratista;
    private String correoContratista;
    private String descripcionContratista;
    private String experiencia;
    private Byte calificacionContratista;
    private EstadoContratista estadoContratista;
    private Integer idEstadoOperacion;
    private String nombreEstadoOperacion;
}
