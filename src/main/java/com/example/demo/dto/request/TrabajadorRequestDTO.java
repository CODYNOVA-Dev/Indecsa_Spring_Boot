package com.example.demo.dto.request;

import com.example.demo.model.Trabajador;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class TrabajadorRequestDTO {

    @NotBlank @Size(max = 100)
    private String nombreTrabajador;

    @NotBlank @Size(min = 18, max = 18)
    private String curp;

    @NotBlank @Size(min = 13, max = 13)
    private String rfc;

    @Size(max = 11)
    private String nssTrabajador;

    @Size(max = 100)
    private String nacionalidad;

    private Integer idMigratorio;

    @Size(max = 100)
    private String calle;

    @Size(max = 10)
    private String numExt;

    @Size(max = 10)
    private String numInt;

    @Size(max = 100)
    private String colonia;

    private Integer codPost;

    @Size(max = 100)
    private String munAlc;

    @Size(max = 100)
    private String estado;

    @NotBlank @Size(max = 100)
    private String puesto;

    @Size(max = 500)
    private String descPuesto;

    @Size(max = 100)
    private String especialidadTrabajador;

    @Size(max = 100)
    private String escolaridad;

    @Size(max = 200)
    private String experiencia;

    @NotBlank @Size(max = 15)
    private String telefonoTrabajador;

    @NotBlank @Email @Size(max = 100)
    private String correoTrabajador;

    @Size(max = 200)
    private String contratacion;

    @Size(max = 200)
    private String jornada;

    private Trabajador.EstadoTrabajador estadoTrabajador;

    private String descripcionTrabajador;

    @Min(1) @Max(10)
    private Byte evaluacionTrabajador;

    @NotNull
    private LocalDate fechaIngreso;

    private Trabajador.EntidadFederativa calidadVida;

    @Size(max = 500)
    private String antPenal;

    @Size(max = 500)
    private String deudorAlim;

    @Size(max = 20)
    private String folioLicCond;

    @Size(max = 50)
    private String estadoCivil;

    @Size(max = 200)
    private String idiomas;

    @Size(max = 100)
    private String lenguaIndigena;

    @Size(max = 50)
    private String sexo;
}
