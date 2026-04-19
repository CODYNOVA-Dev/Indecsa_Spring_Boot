package com.indecsa.dto;

import com.indecsa.model.UbicacionProyecto.EntidadFederativa;
import jakarta.validation.constraints.*;

public class UbicacionProyectoDTO {

    public record Request(
        @NotBlank(message = "La calle es obligatoria")
        @Size(max = 100)
        String calle,

        @NotBlank(message = "El número exterior es obligatorio")
        @Size(max = 10)
        String numExt,

        @Size(max = 10)
        String numInt,

        @NotBlank(message = "La colonia es obligatoria")
        @Size(max = 100)
        String colonia,

        @NotNull(message = "El código postal es obligatorio")
        Integer codPost,

        @NotBlank(message = "El municipio/alcaldía es obligatorio")
        @Size(max = 100)
        String munAlc,

        @NotNull(message = "El estado es obligatorio")
        EntidadFederativa estado
    ) {}

    public record Response(
        Integer idUbicacion,
        String calle,
        String numExt,
        String numInt,
        String colonia,
        Integer codPost,
        String munAlc,
        EntidadFederativa estado
    ) {}
}
