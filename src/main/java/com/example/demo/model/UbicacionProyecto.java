package com.indecsa.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Ubicacion_Proyecto")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class UbicacionProyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ubicacion")
    private Integer idUbicacion;

    @Column(name = "calle", nullable = false, length = 100)
    private String calle;

    @Column(name = "num_ext", nullable = false, length = 10)
    private String numExt;

    @Column(name = "num_int", length = 10)
    private String numInt;

    @Column(name = "colonia", nullable = false, length = 100)
    private String colonia;

    @Column(name = "cod_post", nullable = false)
    private Integer codPost;

    @Column(name = "mun_alc", nullable = false, length = 100)
    private String munAlc;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EntidadFederativa estado;

    public enum EntidadFederativa {
        CDMX, Hidalgo, Puebla
    }
}
