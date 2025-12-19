package com.Cibertec.CattleFyApi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReporteProduccionReproduccion {
    private BigDecimal total_de_lecha;
    private BigDecimal total_de_huevos;
    private BigDecimal promedio_leches_por_dia;
    private BigDecimal promedio_huevos_por_dia;
    private Long total_nacimientos;
}
