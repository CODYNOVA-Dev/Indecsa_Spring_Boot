package com.example.demo.dto.response;

import com.example.demo.model.Trabajador.EstadoTrabajador;
import com.example.demo.model.Trabajador.Sexo;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class TrabajadorResponseDTO {

    private Integer idTrabajador;
    private String nombreTrabajador;
    private String curp;
    private String rfc;
    private String nssTrabajador;
    private String nacionalidad;
    private Integer idMigratorio;
    // Domicilio denormalizado
    private Integer idDomicilio;
    private String calleDomicilio;
    private String munAlcDomicilio;
    private String nombreEstadoDomicilio;
    private String fotoPerfilUrl;
    // Laboral
    private String puesto;
    private String descPuesto;
    private String especialidadTrabajador;
    private String escolaridad;
    private String experiencia;
    private String telefonoTrabajador;
    private String correoTrabajador;
    private String contratacion;
    private String jornada;
    private EstadoTrabajador estadoTrabajador;
    private Byte evaluacionTrabajador;
    private LocalDate fechaIngreso;
    // Calidad de vida
    private Integer idEstadoCalidadVida;
    private String nombreEstadoCalidadVida;
    // Otros
    private Sexo sexo;
    private String antPenal;
    private String deudorAlim;
    private String folioLicCond;
    private String estadoCivil;
    private String idiomas;
    private String lenguaIndigena;
}
