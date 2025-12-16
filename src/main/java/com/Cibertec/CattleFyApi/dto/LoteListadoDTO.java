package com.Cibertec.CattleFyApi.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class LoteListadoDTO {
    private Integer loteId;
    private String nombreLote;
    private String nombreGranja;

    private String nombreEspecie;
    private String nombreCategoria;

    private LocalDateTime fechaCreacion;
    private String estado;
    private Integer capacidadMax;
}
