package com.Cibertec.CattleFyApi.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class VentaListadoResponse {
    
    private Integer ventaId;
    private Integer loteId;
    private String loteNombre;
    private String especieNombre;
    private String categoriaManejoNombre;
    
    private BigDecimal roiReal;
        private BigDecimal roiObjetivo;
    
    // Indicador de cumplimiento
    private Boolean cumplioObjetivo; // true si roiReal >= roiObjetivo
    
    private Integer cantidadAnimalesVivos;
    private BigDecimal pesoPromedioLote;
    private BigDecimal sumaTotalPesos;
    private BigDecimal costoTotalVenta;
    private BigDecimal precioSugeridoPorKg;
    private LocalDateTime fechaVenta;
    private String tipoVenta;
    private String tipoAlcanceVenta;
}