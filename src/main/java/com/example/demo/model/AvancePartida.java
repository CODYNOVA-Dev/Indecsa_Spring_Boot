package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Avance_Partida")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class AvancePartida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_avance")
    private Integer idAvance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proyecto", nullable = false)
    private Proyecto proyecto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cuadrilla")
    private Cuadrilla cuadrilla;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estandar")
    private EstandarRendimiento estandar;

    @Column(name = "nombre_partida", nullable = false, length = 200)
    private String nombrePartida;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;

    @Column(name = "cantidad_ejecutada", nullable = false, precision = 12, scale = 4)
    private BigDecimal cantidadEjecutada;

    @Enumerated(EnumType.STRING)
    @Column(name = "unidad_medida", nullable = false)
    private EstandarRendimiento.UnidadMedida unidadMedida;

    @Column(name = "cantidad_programada", precision = 12, scale = 4)
    private BigDecimal cantidadProgramada;

    @Column(name = "observaciones", length = 500)
    private String observaciones;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empleado_registro", nullable = false)
    private Empleado empleadoRegistro;
}
