package com.example.demo.dto.response;

import com.example.demo.model.Trabajador;
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
    private String calle;
    private String numExt;
    private String numInt;
    private String colonia;
    private Integer codPost;
    private String munAlc;
    private String estado;
    private String puesto;
    private String descPuesto;
    private String especialidadTrabajador;
    private String escolaridad;
    private String experiencia;
    private String telefonoTrabajador;
    private String correoTrabajador;
    private String contratacion;
    private String jornada;
    private Trabajador.EstadoTrabajador estadoTrabajador;
    private String descripcionTrabajador;
    private Byte evaluacionTrabajador;
    private LocalDate fechaIngreso;
    private Trabajador.EntidadFederativa calidadVida;
    private String antPenal;
    private String deudorAlim;
    private String folioLicCond;
    private String estadoCivil;
    private String idiomas;
    private String lenguaIndigena;
    private String sexo;
}
