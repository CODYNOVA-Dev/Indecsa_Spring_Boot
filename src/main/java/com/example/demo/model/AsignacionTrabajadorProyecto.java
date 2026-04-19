package com.indecsa.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(
    name = "Asignacion_Trabajador_Proyecto",
    uniqueConstraints = @UniqueConstraint(
        name = "uq_trabajador_proyecto",
        columnNames = {"id_trabajador", "id_proyecto"}
    )
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class AsignacionTrabajadorProyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asignacion_tp")
    private Integer idAsignacionTp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_trabajador", nullable = false)
    private Trabajador trabajador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proyecto", nullable = false)
    private Proyecto proyecto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_asignacion_pc", nullable = false)
    private AsignacionProyectoContratista asignacionProyectoContratista;

    @Column(name = "puesto_en_proyecto", length = 100)
    private String puestoEnProyecto;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin_estimada")
    private LocalDate fechaFinEstimada;

    @Enumerated(EnumType.STRING)
    @Column(name = "estatus_asignacion", nullable = false)
    private EstatusAsignacion estatusAsignacion;

    @Column(name = "observaciones", length = 500)
    private String observaciones;

    public enum EstatusAsignacion {
        ACTIVO, SUSPENDIDO, INCAPACIDAD, CANCELADO, VACACIONES, FINALIZADO
    }
}
