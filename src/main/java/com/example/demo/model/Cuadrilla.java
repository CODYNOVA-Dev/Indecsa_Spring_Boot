package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "Cuadrilla",
    uniqueConstraints = @UniqueConstraint(
        name = "uq_cuadrilla_proyecto",
        columnNames = {"nombre_cuadrilla", "id_proyecto"}
    )
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Cuadrilla {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cuadrilla")
    private Integer idCuadrilla;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proyecto", nullable = false)
    private Proyecto proyecto;

    @Column(name = "nombre_cuadrilla", nullable = false, length = 100)
    private String nombreCuadrilla;

    @Column(name = "frente_trabajo", length = 200)
    private String frenteTrabajo;

    @Enumerated(EnumType.STRING)
    @Column(name = "estatus_cuadrilla", nullable = false)
    private EstatusCuadrilla estatusCuadrilla;

    public enum EstatusCuadrilla {
        ACTIVO, INACTIVO
    }
}
