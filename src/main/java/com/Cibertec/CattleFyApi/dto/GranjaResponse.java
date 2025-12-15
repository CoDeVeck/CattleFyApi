package com.Cibertec.CattleFyApi.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data

public class GranjaResponse {

    private Integer granjaId;
    private Integer usuarioId;
    private String nombre;
    private String direccion;
    private BigDecimal latitud;
    private BigDecimal longitud;
    private String imagenUrl;
}
