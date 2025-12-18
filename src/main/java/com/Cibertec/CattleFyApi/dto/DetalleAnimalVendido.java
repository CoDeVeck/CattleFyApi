package com.Cibertec.CattleFyApi.dto;

import java.math.BigDecimal;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleAnimalVendido {
	private Integer idAnimal;
	private String codigoQr;
	private BigDecimal peso;
	private BigDecimal costoUnitario;
}
