package com.indecsa.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "Trabajador")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Trabajador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_trabajador")
    private Integer idTrabajador;

    // --- Datos personales ---
    @Column(name = "nombre_trabajador", nullable = false, length = 100)
    private String nombreTrabajador;

    @Column(name = "curp", nullable = false, unique = true, length = 18)
    private String curp;

    @Column(name = "rfc", nullable = false, unique = true, length = 13)
    private String rfc;

    @Column(name = "nss_trabajador", length = 11)
    private String nssTrabajador;

    @Column(name = "nacionalidad", nullable = false, length = 100)
    private String nacionalidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_migratorio")
    private RegistroMigratorio registroMigratorio;

    // --- Domicilio ---
    @Column(name = "calle", nullable = false, length = 100)
    private String calle;

    @Column(name = "num_ext", nullable = false, length = 10)
    private String numExt;

    @Column(name = "num_int", length = 10)
    private String numInt;

    @Column(name = "colonia", nullable = false, length = 100)
    private String colonia;

    @Column(name = "cod_post", nullable = false)
    private Integer codPost;

    @Column(name = "mun_alc", nullable = false, length = 100)
    private String munAlc;

    @Column(name = "estado", nullable = false, length = 100)
    private String estado;

    // --- Información laboral ---
    @Column(name = "puesto", nullable = false, length = 100)
    private String puesto;

    @Column(name = "desc_puesto", nullable = false, length = 500)
    private String descPuesto;

    @Column(name = "especialidad_trabajador", nullable = false, length = 100)
    private String especialidadTrabajador;

    @Column(name = "escolaridad", nullable = false, length = 100)
    private String escolaridad;

    @Column(name = "experiencia", length = 200)
    private String experiencia;

    @Column(name = "telefono_trabajador", nullable = false, length = 15)
    private String telefonoTrabajador;

    @Column(name = "correo_trabajador", nullable = false, unique = true, length = 100)
    private String correoTrabajador;

    @Column(name = "contratacion", nullable = false, length = 200)
    private String contratacion;

    @Column(name = "jornada", nullable = false, length = 200)
    private String jornada;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_trabajador", nullable = false)
    private EstadoTrabajador estadoTrabajador;

    // --- Otros datos ---
    @Column(name = "descripcion_trabajador", columnDefinition = "TEXT")
    private String descripcionTrabajador;

    @Column(name = "evaluacion_trabajador")
    private Byte evaluacionTrabajador;

    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDate fechaIngreso;

    @Enumerated(EnumType.STRING)
    @Column(name = "calidad_vida", nullable = false)
    private EntidadFederativa calidadVida;

    @Column(name = "ant_penal", length = 500)
    private String antPenal;

    @Column(name = "deudor_alim", length = 500)
    private String deudorAlim;

    @Column(name = "folio_lic_cond", length = 20)
    private String folioLicCond;

    @Column(name = "estado_civil", length = 50)
    private String estadoCivil;

    @Column(name = "idiomas", length = 200)
    private String idiomas;

    @Column(name = "lengua_indigena", length = 100)
    private String lenguaIndigena;

    @Column(name = "sexo", length = 50)
    private String sexo;

    public enum EstadoTrabajador {
        DESCANSO, VACACIONES, INCAPACIDAD, ACTIVO, INACTIVO, BAJA, BOLETINADO
    }

    public enum EntidadFederativa {
        CDMX, Hidalgo, Puebla
    }
}
