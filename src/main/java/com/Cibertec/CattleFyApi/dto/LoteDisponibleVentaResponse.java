package com.Cibertec.CattleFyApi.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class LoteDisponibleVentaResponse {
    
    private Integer loteId;
    private String loteNombre;
    private String especieNombre;
    private String categoriaManejoNombre;
    private String tipoLote; // Engorde, Reproduccion, Descarte
    private Integer cantidadAnimalesVivos;
    private BigDecimal pesoPromedioLote;
    private BigDecimal sumaTotalPesos;
    private BigDecimal costoTotalAcumulado; // alimentación + sanidad + compras
    
    private BigDecimal precioSugeridoPorKgBase;
    private BigDecimal roiBasePorcentaje; // ROI x defecto: 30% por defecto
    
    // CALCULADORA: Si el productor quiere X% de ROI, cuánto debe cobrar
    private Map<String, BigDecimal> preciosPorRoiObjetivo; // {"20": 7.50, "30": 8.20, "40": 9.00}
    
    private LocalDateTime fechaCreacionLote;
    private Integer diasEnProduccion;
}