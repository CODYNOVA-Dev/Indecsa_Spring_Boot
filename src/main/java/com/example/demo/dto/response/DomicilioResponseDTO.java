package com.example.demo.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DomicilioResponseDTO {

    private Integer idDomicilio;
    private String calle;
    private String numExt;
    private String numInt;
    private String colonia;
    private Integer codPost;
    private String munAlc;
    private Integer idEstado;
    private String nombreEstado;
}
