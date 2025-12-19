package com.Cibertec.CattleFyApi.dto;

import lombok.*;

import java.math.BigDecimal;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReporteProduccionEngordeDTO {

    private BigDecimal peso_promedio;
    private BigDecimal ganancia_kg;
}
