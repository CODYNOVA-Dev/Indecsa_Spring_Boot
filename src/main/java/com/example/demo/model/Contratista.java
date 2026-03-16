package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Contratista")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contratista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contratista")
    private Integer idContratista;

    // Campo agregado: existe en la BD pero faltaba en el modelo
    @Column(name = "nombre_contratista", nullable = false, length = 100)
    private String nombreContratista;

    @Column(name = "rfc_contratista", nullable = false, unique = true, length = 15)
    private String rfcContratista;

    @Column(name = "telefono_contratista", nullable = false, length = 15)
    private String telefonoContratista;

    @Column(name = "correo_contratista", nullable = false, unique = true, length = 100)
    private String correoContratista;

    // Campo eliminado: contrasenia_contratista no existe en la BD
    // private String contraseniaContratista;

    @Column(name = "descripcion_contratista", nullable = false, length = 255)
    private String descripcionContratista;

    @Column(name = "experiencia", length = 200)
    private String experiencia;

    @Column(name = "calificacion_contratista")
    private Byte calificacionContratista;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_contratista", nullable = false, columnDefinition = "ENUM('ACTIVO','INACTIVO','SUSPENDIDO') DEFAULT 'ACTIVO'")
    private EstadoContratista estadoContratista = EstadoContratista.ACTIVO;

    // Campo agregado: existe en la BD pero faltaba en el modelo
    @Enumerated(EnumType.STRING)
    @Column(name = "ubicacion_contratista", nullable = false)
    private Ubicacion ubicacionContratista;

    public enum EstadoContratista {
        ACTIVO,
        INACTIVO,
        SUSPENDIDO
    }

    public enum Ubicacion {
        CDMX,
        Hidalgo,
        Puebla
    }
}
