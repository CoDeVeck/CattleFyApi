package com.Cibertec.CattleFyApi.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data

public class RegistroSanitarioRequest {
    
	// El qr es el identificador, de ahi llamar a cada repo para buscar animal x qr
    private String qrLote;
    private String qrAnimal; 
    
    private String tipoAplicacion;
    private String protocoloTipo; 
    private String nombreProducto;
    private BigDecimal costoPorDosis;
    private BigDecimal cantidadDosis;
    private Integer animalesTratados; 
    
}
