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
public class ReporteGrafico1 {
    private LocalDate fecha;
    private BigDecimal peso_kg;
}
