package com.Cibertec.CattleFyApi.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AnimalHistPesaje {

    private Integer idAnimal;
    private BigDecimal peso;
    private LocalDateTime fecha;
    private String dieta;
    private BigDecimal gananciaPeso;

}
