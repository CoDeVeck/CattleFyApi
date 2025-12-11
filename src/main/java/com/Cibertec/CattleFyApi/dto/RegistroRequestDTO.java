package com.Cibertec.CattleFyApi.dto;
import lombok.Data;

@Data
public class RegistroRequestDTO {
    private String nombres;
    private String apePat;
    private String apeMat;
    private String documento;
    private String email;
    private String contra;
    private String telefono;
    private String imagenUrl;
    private Integer rolId;
}