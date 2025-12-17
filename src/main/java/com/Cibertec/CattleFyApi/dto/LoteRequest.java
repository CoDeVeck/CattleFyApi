package com.Cibertec.CattleFyApi.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LoteRequest {
    private Integer idGranja;
    private String nombre;
    private int idEspecie;
    private int idCategoria;
    private int capacidadMax;
}
