package com.Cibertec.CattleFyApi.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class LoteDetalleResponse {
    private Integer loteId;
    private String nombre;
    private String codigo;
    private String numeroLote;
    private String estado;
    private String tipoLote;

    // Información de relaciones
    private String granjaNombre;
    private Integer granjaId;
    private String especieNombre;
    private Integer especieId;

    // Cantidades y fechas
    private Integer cantidadInicial;
    private Integer cantidadActual;
    private LocalDateTime fechaInicio;

    // Métricas clave
    private Double pesoPromedio;
    private Double pesoTotal;
    private Double precioEstimado;

    // Contadores de historiales
    private Integer cantidadRegistrosBajas;
}
