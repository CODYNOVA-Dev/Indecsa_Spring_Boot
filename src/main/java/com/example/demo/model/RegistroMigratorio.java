package com.indecsa.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "registros_migratorios")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class RegistroMigratorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_migratorio")
    private Integer idMigratorio;

    @Column(name = "folio_documento", nullable = false, unique = true, length = 50)
    private String folioDocumento;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false)
    private CategoriaVisitante categoria;

    @Column(name = "fecha_emision", nullable = false)
    private LocalDate fechaEmision;

    @Column(name = "dias_vigencia")
    private Integer diasVigencia;

    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;

    @Column(name = "permiso_trabajo", nullable = false)
    private Boolean permisoTrabajo;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    public enum CategoriaVisitante {
        turismo, negocios, razones_humanitarias, transito, actividades_remuneradas
    }
}
