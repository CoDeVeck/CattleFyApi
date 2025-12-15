package com.Cibertec.CattleFyApi.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "tb_animales")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Animal {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "animal_id")
	    private Integer animalId;
	    
	    @Column(name = "animal_qr")
	    private String codigoQr;
	    
	    @ManyToOne
	    @JoinColumn(name = "especie_id")
	    private Especie especie;
	    
	    @ManyToOne
	    @JoinColumn(name = "lote_id")
	    private Lote lote;
	    
	    @ManyToOne
	    @JoinColumn(name = "madre_id")
	    private Animal madre;
	    
	    @Column(name = "origen")
	    private String origen;

        @Column(name = "sexo")
        private String sexo;

        @Column(name = "fecha_ingreso")
	    private LocalDateTime fechaIngreso;
	    
	    @Column(name = "fecha_nacimiento")
	    private LocalDateTime fechaNacimiento;
	    
	    @Column(name = "peso")
	    private BigDecimal peso;
	    
	    @Column(name = "precio_compra")
	    private BigDecimal precioCompra;
	    
	    @Column(name = "estado")
	    private String estado;
	    
	    @Column(name = "foto_url")
	    private String fotoUrl;
	    
	    
	    @JsonIgnore
	    @Transient
	    private MultipartFile imagenMultipart; // para la subida de imagens
}
