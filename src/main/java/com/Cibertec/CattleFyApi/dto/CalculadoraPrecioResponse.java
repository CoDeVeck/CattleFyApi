package com.Cibertec.CattleFyApi.dto;

import java.math.BigDecimal;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CalculadoraPrecioResponse {
    private Integer loteId;
    private BigDecimal costoTotalInvertido;
    private BigDecimal pesoTotalDisponible;
    private BigDecimal roiObjetivoPorcentaje;
    
    private BigDecimal precioMinimoBreakEven; // Para no perder (ROI = 0%)
    private BigDecimal precioPorKgSugerido; // Para lograr el ROI objetivo
    private BigDecimal ingresoTotalEsperado;
    private BigDecimal gananciaNeta;
}