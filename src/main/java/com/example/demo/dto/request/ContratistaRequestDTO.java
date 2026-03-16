package com.example.demo.dto.request;

import com.example.demo.model.Contratista.Ubicacion;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ContratistaRequestDTO {

    // Campo agregado: nombre_contratista es NOT NULL en la BD
    @NotBlank(message = "El nombre del contratista es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombreContratista;

    @NotBlank(message = "El RFC es obligatorio")
    @Size(max = 15, message = "El RFC no puede exceder 15 caracteres")
    private String rfcContratista;

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(max = 15, message = "El teléfono no puede exceder 15 caracteres")
    private String telefonoContratista;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo no tiene un formato válido")
    @Size(max = 100, message = "El correo no puede exceder 100 caracteres")
    private String correoContratista;

    // Campo eliminado: contrasenia_contratista no existe en la BD
    // private String contraseniaContratista;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 255, message = "La descripción no puede exceder 255 caracteres")
    private String descripcionContratista;

    @Size(max = 200, message = "La experiencia no puede exceder 200 caracteres")
    private String experiencia;

    @Min(value = 1, message = "La calificación mínima es 1")
    @Max(value = 5, message = "La calificación máxima es 5")
    private Byte calificacionContratista;

    // Campo agregado: ubicacion_contratista es NOT NULL en la BD
    @NotNull(message = "La ubicación del contratista es obligatoria")
    private Ubicacion ubicacionContratista;
}
