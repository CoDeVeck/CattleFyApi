package com.Cibertec.CattleFyApi.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class RegistroProduccionRequest {
    private Integer loteId;
    private String tipoProduccion;
    private BigDecimal cantidad;
}
