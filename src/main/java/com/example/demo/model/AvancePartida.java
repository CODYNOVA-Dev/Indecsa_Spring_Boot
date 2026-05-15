package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Avance_Partida")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private Cuadrilla cuadrilla;  // nullable

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estandar")
    private EstandarRendimiento estandarRendimiento;  // nullable

    @Column(name = "nombre_partida", nullable = false, length = 200)
    private String nombrePartida;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;

    @Column(name = "cantidad_ejecutada", nullable = false, precision = 12, scale = 4)
    private BigDecimal cantidadEjecutada;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empleado_registro", nullable = false)
    private Empleado empleadoRegistro;
}
