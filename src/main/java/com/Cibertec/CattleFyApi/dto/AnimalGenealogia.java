package com.Cibertec.CattleFyApi.dto;

import lombok.Data;

@Data
public class AnimalGenealogia {

    private Integer idAnimal;
    private String qrAnimalMadre;
    private String nombreLote;
    private Double peso;
}
