package com.Cibertec.CattleFyApi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SanidadEstadisticasDTO {

    private BigDecimal cantidad_total_dosis;
    private BigDecimal costo_total;
    private Long animales_tratados;
    private String medicamente_mas_usado;

}
