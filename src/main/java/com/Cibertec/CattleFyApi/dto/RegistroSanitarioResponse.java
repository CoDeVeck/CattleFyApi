package com.Cibertec.CattleFyApi.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RegistroSanitarioResponse {

    private Integer sanitarioId;
    
    private Integer idLote;
    private String nombreLote;
    
    private Integer idAnimal;
    private String codigoQrAnimal;
    
    private String tipoAplicacion; 
    private String protocoloTipo; 
    private String nombreProducto;
    private BigDecimal costoTotal;
    private BigDecimal costoPorDosis;
    private BigDecimal cantidadDosis; 
    private Integer animalesTratados; 
    
    private LocalDateTime fechaAplicacion;
    private String imagenUrl;
}
