package com.Cibertec.CattleFyApi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReporteGrafico2 {

    private LocalDate fecha;
    private String tipo_produccion;
    private BigDecimal cantidad_total;
}
