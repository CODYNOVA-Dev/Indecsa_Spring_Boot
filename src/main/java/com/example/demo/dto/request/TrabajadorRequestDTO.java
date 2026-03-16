package com.example.demo.dto.request;

import com.example.demo.model.Trabajador.Ubicacion;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TrabajadorRequestDTO {

    @NotBlank(message = "El nombre del trabajador es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombreTrabajador;

    @Size(max = 15, message = "El NSS no puede exceder 15 caracteres")
    private String nssTrabajador;

    @Size(max = 200, message = "La experiencia no puede exceder 200 caracteres")
    private String experiencia;

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(max = 15, message = "El teléfono no puede exceder 15 caracteres")
    private String telefonoTrabajador;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo no tiene un formato válido")
    @Size(max = 100, message = "El correo no puede exceder 100 caracteres")
    private String correoTrabajador;

    // Campo eliminado: contrasenia_trabajador no existe en la BD
    // private String contraseniaTrabajador;

    @NotBlank(message = "La especialidad es obligatoria")
    @Size(max = 100, message = "La especialidad no puede exceder 100 caracteres")
    private String especialidadTrabajador;

    @Size(max = 800, message = "La descripción no puede exceder 800 caracteres")
    private String descripcionTrabajador;

    @Min(value = 1, message = "La calificación mínima es 1")
    @Max(value = 5, message = "La calificación máxima es 5")
    private Byte calificacionTrabajador;

    @NotNull(message = "La fecha de ingreso es obligatoria")
    private LocalDate fechaIngreso;

    // Campo agregado: ubicacion_trabajador es NOT NULL en la BD
    @NotNull(message = "La ubicación del trabajador es obligatoria")
    private Ubicacion ubicacionTrabajador;
}
