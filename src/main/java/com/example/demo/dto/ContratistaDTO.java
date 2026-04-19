package com.indecsa.dto;

import com.indecsa.model.Contratista.EntidadFederativa;
import com.indecsa.model.Contratista.EstadoContratista;
import jakarta.validation.constraints.*;

public class ContratistaDTO {

    public record Request(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100)
        String nombreContratista,

        @NotBlank(message = "El CURP es obligatorio")
        @Size(min = 18, max = 18, message = "El CURP debe tener exactamente 18 caracteres")
        String curp,

        @NotBlank(message = "El RFC es obligatorio")
        @Size(max = 15)
        String rfcContratista,

        @NotBlank(message = "El teléfono es obligatorio")
        @Size(max = 15)
        String telefonoContratista,

        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "Formato de correo inválido")
        @Size(max = 100)
        String correoContratista,

        @NotBlank(message = "La descripción es obligatoria")
        @Size(max = 255)
        String descripcionContratista,

        @Size(max = 200)
        String experiencia,

        @Min(value = 1, message = "La calificación mínima es 1")
        @Max(value = 5, message = "La calificación máxima es 5")
        Byte calificacionContratista,

        EstadoContratista estadoContratista,

        @NotNull(message = "La ubicación es obligatoria")
        EntidadFederativa ubicacionContratista
    ) {}

    public record Response(
        Integer idContratista,
        String nombreContratista,
        String curp,
        String rfcContratista,
        String telefonoContratista,
        String correoContratista,
        String descripcionContratista,
        String experiencia,
        Byte calificacionContratista,
        EstadoContratista estadoContratista,
        EntidadFederativa ubicacionContratista
    ) {}
}
