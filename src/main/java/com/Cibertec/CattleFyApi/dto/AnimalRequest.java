package com.Cibertec.CattleFyApi.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class AnimalRequest {

    private String origen;
    private MultipartFile imagen;
    private Integer idLote;
    private String codigoQrMadre;
    private Integer idEspecie;
    private Integer idCategoria;
    private LocalDateTime fechaNacimiento;
    private String sexo;
    private Double peso;
    private Double precioCompra;


}
