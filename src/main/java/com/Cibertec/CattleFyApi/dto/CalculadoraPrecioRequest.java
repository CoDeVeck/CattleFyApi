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

public class CalculadoraPrecioRequest {
    private Integer loteId;
    private BigDecimal roiObjetivoPorcentaje; // El ROI que el productor quiere lograr
}