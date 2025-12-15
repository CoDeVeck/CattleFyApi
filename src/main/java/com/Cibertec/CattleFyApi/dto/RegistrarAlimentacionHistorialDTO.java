package com.Cibertec.CattleFyApi.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class RegistrarAlimentacionHistorialDTO {
    private Integer alimentacionId;
    private LocalDateTime fecha;
    private String dieta;
    private BigDecimal cantidadKg;
    private BigDecimal costoTotal;
    private Integer loteId;
    private String nombreLote;
}
