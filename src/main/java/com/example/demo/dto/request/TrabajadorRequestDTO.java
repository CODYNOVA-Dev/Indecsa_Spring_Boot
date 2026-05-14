package com.example.demo.dto.request;

import com.example.demo.model.enums.Sexo;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TrabajadorRequestDTO {

    @NotBlank(message = "El nombre del trabajador es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombreTrabajador;

    @NotBlank(message = "La CURP es obligatoria")
    @Size(min = 18, max = 18, message = "La CURP debe tener exactamente 18 caracteres")
    private String curp;

    @NotBlank(message = "El RFC es obligatorio")
    @Size(min = 13, max = 13, message = "El RFC debe tener exactamente 13 caracteres")
    private String rfc;

    @Size(max = 11, message = "El NSS no puede exceder 11 caracteres")
    private String nssTrabajador;

    @NotBlank(message = "La nacionalidad es obligatoria")
    @Size(max = 100, message = "La nacionalidad no puede exceder 100 caracteres")
    private String nacionalidad;

    private Integer idMigratorio;

    @NotNull(message = "El domicilio es obligatorio")
    private Integer idDomicilio;

    @NotBlank(message = "El puesto es obligatorio")
    @Size(max = 100, message = "El puesto no puede exceder 100 caracteres")
    private String puesto;

    @NotBlank(message = "La descripción del puesto es obligatoria")
    @Size(max = 500, message = "La descripción del puesto no puede exceder 500 caracteres")
    private String descPuesto;

    @NotBlank(message = "La especialidad es obligatoria")
    @Size(max = 100, message = "La especialidad no puede exceder 100 caracteres")
    private String especialidadTrabajador;

    @NotBlank(message = "La escolaridad es obligatoria")
    @Size(max = 100, message = "La escolaridad no puede exceder 100 caracteres")
    private String escolaridad;

    @Size(max = 200, message = "La experiencia no puede exceder 200 caracteres")
    private String experiencia;

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(max = 15, message = "El teléfono no puede exceder 15 caracteres")
    private String telefonoTrabajador;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo no tiene un formato válido")
    @Size(max = 100, message = "El correo no puede exceder 100 caracteres")
    private String correoTrabajador;

    @NotBlank(message = "La modalidad de contratación es obligatoria")
    @Size(max = 200, message = "La contratación no puede exceder 200 caracteres")
    private String contratacion;

    @NotBlank(message = "La jornada es obligatoria")
    @Size(max = 200, message = "La jornada no puede exceder 200 caracteres")
    private String jornada;

    @Min(value = 1, message = "La evaluación mínima es 1")
    @Max(value = 5, message = "La evaluación máxima es 5")
    private Byte evaluacionTrabajador;

    @NotNull(message = "La fecha de ingreso es obligatoria")
    private LocalDate fechaIngreso;

    @NotNull(message = "El estado de calidad de vida es obligatorio")
    private Integer idEstadoCalidadVida;

    @NotNull(message = "El sexo es obligatorio")
    private Sexo sexo;

    @Size(max = 500, message = "Los antecedentes penales no pueden exceder 500 caracteres")
    private String antPenal;

    @Size(max = 500, message = "El campo deudor alimentario no puede exceder 500 caracteres")
    private String deudorAlim;

    @Size(max = 20, message = "El folio de licencia de conducir no puede exceder 20 caracteres")
    private String folioLicCond;

    @Size(max = 50, message = "El estado civil no puede exceder 50 caracteres")
    private String estadoCivil;

    @Size(max = 200, message = "Los idiomas no pueden exceder 200 caracteres")
    private String idiomas;

    @Size(max = 100, message = "La lengua indígena no puede exceder 100 caracteres")
    private String lenguaIndigena;
}
