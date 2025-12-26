package com.Cibertec.CattleFyApi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class AplicacionesRecientesDTO {

    private String nombre_producto;
    private String protocolo_tipo;
    private Integer lote_id;
    private Integer animal_id;
    private BigDecimal costo_por_dosis;
    private LocalDate fecha_aplicacion;
}
