package com.Cibertec.CattleFyApi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReporteFinancieroDTO {

    private BigDecimal ingresos;
    private BigDecimal costo_compras;
    private BigDecimal costo_alimentacion;
    private BigDecimal costo_sanitario;

}
