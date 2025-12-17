package com.Cibertec.CattleFyApi.dto;

import lombok.Data;

@Data
public class AnimalHistTraslado {

    private Integer idAnimal;
    private String especie;
    private String loteOrigen;
    private String loteTraslado;
    private String motivo;

}
