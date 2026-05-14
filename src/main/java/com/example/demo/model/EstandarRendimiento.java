package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Estandar_Rendimiento")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class EstandarRendimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estandar")
    private Integer idEstandar;

    @Column(name = "nombre_actividad", nullable = false, length = 100)
    private String nombreActividad;

    @Enumerated(EnumType.STRING)
    @Column(name = "unidad_medida", nullable = false)
    private UnidadMedida unidadMedida;

    @Column(name = "rendimiento_esperado", nullable = false, precision = 10, scale = 4)
    private BigDecimal rendimientoEsperado;

    public enum UnidadMedida {
        m2, m3, ml, piezas, porcentaje
    }
}
