package com.Cibertec.CattleFyApi.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AnimalHistSanitario {

    private Integer idAnimal;
    private String nombreTrat;
    private String tipoProtocolo;
    private LocalDateTime fecha;
    private BigDecimal dosis;
    private  BigDecimal precio;

}
