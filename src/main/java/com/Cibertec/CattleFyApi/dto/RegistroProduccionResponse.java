package com.Cibertec.CattleFyApi.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RegistroProduccionResponse {
    private Integer produccionId;
    private Integer loteId;
    private LocalDateTime fechaRegistro;
    private String tipoProduccion;
    private BigDecimal cantidad;
}