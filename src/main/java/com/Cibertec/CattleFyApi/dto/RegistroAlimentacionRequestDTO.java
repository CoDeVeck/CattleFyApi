package com.Cibertec.CattleFyApi.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistroAlimentacionRequestDTO {

    private Integer loteId;
    private BigDecimal cantidadKg;
    private BigDecimal costoPorKg;
    private String dietaTipo;
}