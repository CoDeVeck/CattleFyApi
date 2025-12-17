package com.Cibertec.CattleFyApi.dto;

import lombok.Data;

@Data
public class AnimalTrasReq {
	private Integer idAnimal;
	private Integer idLoteDestino;
	private String motivo;
}
