package com.indecsa.dto;

import com.indecsa.model.Trabajador.EntidadFederativa;
import com.indecsa.model.Trabajador.EstadoTrabajador;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class TrabajadorDTO {

    public record Request(
        // --- Datos personales ---
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100)
        String nombreTrabajador,

        @NotBlank(message = "El CURP es obligatorio")
        @Size(min = 18, max = 18, message = "El CURP debe tener exactamente 18 caracteres")
        String curp,

        @NotBlank(message = "El RFC es obligatorio")
        @Size(max = 13)
        String rfc,

        @Size(max = 11)
        String nssTrabajador,

        @NotBlank(message = "La nacionalidad es obligatoria")
        @Size(max = 100)
        String nacionalidad,

        Integer idMigratorio,   // null si es mexicano

        // --- Domicilio ---
        @NotBlank @Size(max = 100) String calle,
        @NotBlank @Size(max = 10)  String numExt,
        @Size(max = 10)            String numInt,
        @NotBlank @Size(max = 100) String colonia,
        @NotNull                   Integer codPost,
        @NotBlank @Size(max = 100) String munAlc,
        @NotBlank @Size(max = 100) String estado,

        // --- Información laboral ---
        @NotBlank @Size(max = 100) String puesto,
        @NotBlank @Size(max = 500) String descPuesto,
        @NotBlank @Size(max = 100) String especialidadTrabajador,
        @NotBlank @Size(max = 100) String escolaridad,
        @Size(max = 200)           String experiencia,

        @NotBlank(message = "El teléfono es obligatorio")
        @Size(max = 15)
        String telefonoTrabajador,

        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "Formato de correo inválido")
        @Size(max = 100)
        String correoTrabajador,

        @NotBlank @Size(max = 200) String contratacion,
        @NotBlank @Size(max = 200) String jornada,

        EstadoTrabajador estadoTrabajador,

        // --- Otros datos ---
        String descripcionTrabajador,

        @Min(1) @Max(5)
        Byte evaluacionTrabajador,

        @NotNull(message = "La fecha de ingreso es obligatoria")
        LocalDate fechaIngreso,

        @NotNull(message = "La calidad de vida (entidad) es obligatoria")
        EntidadFederativa calidadVida,

        @Size(max = 500) String antPenal,
        @Size(max = 500) String deudorAlim,
        @Size(max = 20)  String folioLicCond,
        @Size(max = 50)  String estadoCivil,
        @Size(max = 200) String idiomas,
        @Size(max = 100) String lenguaIndigena,
        @Size(max = 50)  String sexo
    ) {}

    public record Response(
        Integer idTrabajador,
        String nombreTrabajador,
        String curp,
        String rfc,
        String nssTrabajador,
        String nacionalidad,
        Integer idMigratorio,
        // Domicilio
        String calle,
        String numExt,
        String numInt,
        String colonia,
        Integer codPost,
        String munAlc,
        String estado,
        // Laboral
        String puesto,
        String descPuesto,
        String especialidadTrabajador,
        String escolaridad,
        String experiencia,
        String telefonoTrabajador,
        String correoTrabajador,
        String contratacion,
        String jornada,
        EstadoTrabajador estadoTrabajador,
        // Otros
        String descripcionTrabajador,
        Byte evaluacionTrabajador,
        LocalDate fechaIngreso,
        EntidadFederativa calidadVida,
        String antPenal,
        String deudorAlim,
        String folioLicCond,
        String estadoCivil,
        String idiomas,
        String lenguaIndigena,
        String sexo
    ) {}
}
