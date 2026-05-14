package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DomicilioRequestDTO {

    @NotBlank(message = "La calle es obligatoria")
    @Size(max = 100, message = "La calle no puede exceder 100 caracteres")
    private String calle;

    @NotBlank(message = "El número exterior es obligatorio")
    @Size(max = 10, message = "El número exterior no puede exceder 10 caracteres")
    private String numExt;

    @Size(max = 10, message = "El número interior no puede exceder 10 caracteres")
    private String numInt;

    @NotBlank(message = "La colonia es obligatoria")
    @Size(max = 100, message = "La colonia no puede exceder 100 caracteres")
    private String colonia;

    @NotNull(message = "El código postal es obligatorio")
    private Integer codPost;

    @NotBlank(message = "El municipio/alcaldía es obligatorio")
    @Size(max = 100, message = "El municipio/alcaldía no puede exceder 100 caracteres")
    private String munAlc;

    @NotNull(message = "El estado es obligatorio")
    private Integer idEstado;
}
