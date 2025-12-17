package com.Cibertec.CattleFyApi.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class VentaDetalleResponse {
    
    private Integer ventaId;
    private Integer loteId;
    private String loteNombre;
    private String especieNombre;
    private String categoriaManejoNombre;
    private String tipoAlcanceVenta;
    private String tipoVenta;
    private BigDecimal pesoTotalKg;
    private BigDecimal precioPorKg;
    private BigDecimal precioTotal;
    
    // ROI REAL: Calculado automáticamente basado en costos vs ingreso real
    private BigDecimal roiReal;
   
    // ROI OBJETIVO: El margen que el productor quería alcanzar (puede ser null)
    private BigDecimal roiObjetivo;
    
    // DIFERENCIA: Indica si cumplió, superó o no alcanzó su objetivo
    private BigDecimal diferenciaRoi; // positivo = superó, negativo = no alcanzó
    
    private String clienteNombre;
    private LocalDateTime fechaVenta;
    private Integer cantidadAnimalesVendidos;
    private List<Integer> animalesVendidosIds;
    
    // Información adicional del lote
    private String granjaId;
    private String granjaNombre;
    private String estadoLote;
    
    // Información financiera
    private BigDecimal costoTotalInvertido;
    private BigDecimal gananciaNeta;
    
    // Advertencias o recomendaciones del sistema
    private String advertencia;
    private String recomendacion;
}