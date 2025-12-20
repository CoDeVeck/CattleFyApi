package com.Cibertec.CattleFyApi.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoteSimpleDTO {

    private Integer loteId;
    private String nombre;
    private Integer cantidadAnimales;
}
