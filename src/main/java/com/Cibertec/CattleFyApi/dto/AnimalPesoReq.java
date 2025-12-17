package com.Cibertec.CattleFyApi.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class AnimalPesoReq {

	private Integer idAnimal;
	private BigDecimal peso;
	
}
