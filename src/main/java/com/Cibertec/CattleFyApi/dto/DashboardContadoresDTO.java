package com.Cibertec.CattleFyApi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardContadoresDTO {
    private Long animalesActivos;
    private Long lotesActivos;
    private Long alertasCriticas;
    private BigDecimal ventasDelMes;
}