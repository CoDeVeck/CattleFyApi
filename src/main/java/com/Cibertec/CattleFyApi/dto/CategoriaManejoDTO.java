package com.Cibertec.CattleFyApi.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoriaManejoDTO {

    private Integer categoriaId;
    private String nombre;
    private String tipoLote;
}
