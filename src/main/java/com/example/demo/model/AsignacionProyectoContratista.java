package com.indecsa.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(
    name = "Asignacion_Proyecto_Contratista",
    uniqueConstraints = @UniqueConstraint(
        name = "uq_proyecto_contratista",
        columnNames = {"id_proyecto", "id_contratista"}
    )
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class AsignacionProyectoContratista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asignacion_pc")
    private Integer idAsignacionPc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proyecto", nullable = false)
    private Proyecto proyecto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_contratista", nullable = false)
    private Contratista contratista;

    @Column(name = "numero_contrato", length = 50)
    private String numeroContrato;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin_estimada")
    private LocalDate fechaFinEstimada;

    @Column(name = "personal_asignado", nullable = false)
    private Integer personalAsignado;

    @Column(name = "puestos_requeridos", length = 500)
    private String puestosRequeridos;

    @Enumerated(EnumType.STRING)
    @Column(name = "estatus_contrato", nullable = false)
    private EstatusContrato estatusContrato;

    @Column(name = "observaciones", length = 500)
    private String observaciones;

    public enum EstatusContrato {
        ACTIVO, VIGENTE, SUSPENDIDO, FINALIZADO, CANCELADO
    }
}
