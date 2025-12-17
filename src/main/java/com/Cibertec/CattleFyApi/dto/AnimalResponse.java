package com.Cibertec.CattleFyApi.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AnimalResponse {

    private Integer idAnimal;
    private String codigoQr;
    private int idEspecie;
    private String especie;
    private int idLote;
    private String lote;
    private int idMadre;
    private String codigoQrMadre;
    private String origen; // Compra o nacimiento
    private LocalDateTime fechaIngreso;
    private LocalDate fechaNacimiento; // es obligatorio
    private int edadEnDias;
    private String sexo;
    private Double peso;
    private String precioCompra; // si nacio en la granja es 0
    private String estado;
    private String foto_url;

}
