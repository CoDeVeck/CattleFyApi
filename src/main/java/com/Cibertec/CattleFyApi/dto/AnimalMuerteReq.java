package com.Cibertec.CattleFyApi.dto;

import lombok.Data;

@Data
public class AnimalMuerteReq {
	private Integer idAnimal;
	private String causaMuerte;
}
