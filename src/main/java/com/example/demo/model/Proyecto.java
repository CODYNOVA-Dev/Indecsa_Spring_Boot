package com.indecsa.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "Proyecto")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proyecto")
    private Integer idProyecto;

    @Column(name = "nombre_proyecto", nullable = false, length = 150)
    private String nombreProyecto;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_proyecto")
    private TipoProyecto tipoProyecto;

    @Column(name = "oferta_trabajo", length = 200)
    private String ofertaTrabajo;

    @Column(name = "cliente", nullable = false, length = 200)
    private String cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ubicacion", nullable = false)
    private UbicacionProyecto ubicacion;

    @Column(name = "municipio_proyecto", length = 100)
    private String municipioProyecto;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_proyecto_geo", nullable = false)
    private EntidadFederativa estadoProyectoGeo;

    @Column(name = "fecha_estimada_inicio")
    private LocalDate fechaEstimadaInicio;

    @Column(name = "fecha_estimada_fin")
    private LocalDate fechaEstimadaFin;

    @Column(name = "calificacion_proyecto")
    private Byte calificacionProyecto;

    @Enumerated(EnumType.STRING)
    @Column(name = "estatus_proyecto", nullable = false)
    private EstatusProyecto estatusProyecto;

    @Column(name = "descripcion_proyecto", length = 500)
    private String descripcionProyecto;

    public enum TipoProyecto {
        Construccion, Remodelacion,
        Venta_mobiliaria, Instalacion_de_mobiliario
    }

    public enum EstatusProyecto {
        PLANEACION, EN_CURSO, PENDIENTE, FINALIZADO, CANCELADO
    }

    public enum EntidadFederativa {
        CDMX, Hidalgo, Puebla
    }
}
