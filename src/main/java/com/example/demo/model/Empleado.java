package com.indecsa.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Empleado")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado")
    private Integer idEmpleado;

    @Column(name = "nombre_empleado", nullable = false, length = 100)
    private String nombreEmpleado;

    @Column(name = "curp", nullable = false, length = 18)
    private String curp;

    @Column(name = "correo_empleado", nullable = false, unique = true, length = 100)
    private String correoEmpleado;

    @Column(name = "contrasena", nullable = false, length = 255)
    private String contrasena;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol rol;
}
