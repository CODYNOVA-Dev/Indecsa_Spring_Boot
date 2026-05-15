package com.example.demo.dto.request;

import com.example.demo.model.Contratista;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ContratistaRequestDTO {

    @NotBlank @Size(max = 100)
    private String nombreContratista;

    @NotBlank @Size(min = 18, max = 18)
    private String curp;

    @NotBlank @Size(max = 15)
    private String rfcContratista;

    @NotBlank @Size(max = 15)
    private String telefonoContratista;

    @NotBlank @Email @Size(max = 100)
    private String correoContratista;

    @NotBlank @Size(max = 255)
    private String descripcionContratista;

    @Size(max = 200)
    private String experiencia;

    @Min(1) @Max(10)
    private Byte calificacionContratista;

    private Contratista.EstadoContratista estadoContratista;

    @NotNull
    private Contratista.EntidadFederativa ubicacionContratista;
}
