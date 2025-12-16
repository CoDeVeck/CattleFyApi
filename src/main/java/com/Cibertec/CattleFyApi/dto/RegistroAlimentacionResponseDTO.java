package com.Cibertec.CattleFyApi.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroAlimentacionResponseDTO {

    private Integer alimentacionId;
    private Integer loteId;
    private LocalDateTime fechaRegistro;
    private BigDecimal cantidadKg;
    private BigDecimal costoPorKg;
    private String dietaTipo;
    private String costoTotal;
}
