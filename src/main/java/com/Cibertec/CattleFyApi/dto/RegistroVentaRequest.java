package com.Cibertec.CattleFyApi.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroVentaRequest {
    
    private Integer loteId;
    private String tipoAlcanceVenta; //Total o Parcial
    private String tipoVenta; // Engorde, Reproduccion o Descarte
    private BigDecimal pesoTotalKg;
    private BigDecimal precioPorKg;
    private BigDecimal precioTotal;
    
    // ROI OBJETIVO: El productor puede definir su margen deseado (opcional)
    // Si no se envía, el sistema calculará el ROI real basado en el precio de venta
    private BigDecimal roiMeta;
    private String clienteNombre;    
    private List<Integer> animalesVendidosIds;
}
