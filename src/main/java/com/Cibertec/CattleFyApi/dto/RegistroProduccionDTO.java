package com.Cibertec.CattleFyApi.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class RegistroProduccionDTO {

    private Integer produccionId;
    private LocalDateTime fechaRegistro;
    private String tipoProduccion;
    private BigDecimal cantidad;

    private Integer loteId;
    private String nombreLote;

}