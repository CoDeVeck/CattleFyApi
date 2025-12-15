package com.Cibertec.CattleFyApi.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
public class GranjaRequest {

    private Integer usuarioId;
    private String nombre;
    private String direccion;
    private BigDecimal latitud;
    private BigDecimal longitud;
    private MultipartFile imagen;

}