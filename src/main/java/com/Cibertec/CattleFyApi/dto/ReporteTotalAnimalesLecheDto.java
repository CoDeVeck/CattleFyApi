package com.Cibertec.CattleFyApi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReporteTotalAnimalesLecheDto {
    private long total_animales;
    private BigDecimal total_de_leche;
}
