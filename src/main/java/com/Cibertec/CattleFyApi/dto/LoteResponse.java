package com.Cibertec.CattleFyApi.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LoteResponse {

    private Integer idLote;
    private String codigoQr; // Usar un metodo generador de codigo en Util
    private String nombre;
    private int idEspecie;
    private String especie;
    private int idCategoria;
    private String categoria;
    private String tipoLote;
    private LocalDateTime fechaCreacion;
    private int diasDesdeCreacion;
    private String estado; // Tener cuidado, usar el metodo actualizarEstadoLote
    private int capacidadMax;
    private int animalesVivos; // Utilizar metodo del repo contarAnimalesPorLote
    private Double CVT;
    private Double pesoPromedio;

}
