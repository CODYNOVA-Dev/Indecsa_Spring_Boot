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
    private String fotoPerfilUrl;
    private String experiencia;
    private Byte calificacionContratista;
    private EstadoContratista estadoContratista;
    // Estado de operación (reemplaza ENUM ubicacion_contratista)
    private Integer idEstadoOperacion;
    private String nombreEstadoOperacion;
}
