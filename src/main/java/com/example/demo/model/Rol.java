package com.indecsa.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Rol")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Integer idRol;

    @Enumerated(EnumType.STRING)
    @Column(name = "nombre_rol", nullable = false, unique = true)
    private NombreRol nombreRol;

    @Column(name = "descripcion_rol", length = 255)
    private String descripcionRol;

    public enum NombreRol {
        ADMIN, CAPITAL_HUMANO
    }
}
