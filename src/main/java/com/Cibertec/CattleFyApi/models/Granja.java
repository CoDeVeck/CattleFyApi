package com.Cibertec.CattleFyApi.models;
import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_granjas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Granja {

	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "granja_id")
	    private Integer granjaId;
	    
	    @ManyToOne
	    @JoinColumn(name = "usuario_id")
	    private Usuario usuario;
	    
	    @Column(name = "nombre")
	    private String nombre;
	    
	    @Column(name = "direccion")
	    private String direccion;
	    
	    @Column(name = "latitud")
	    private BigDecimal latitud;
	    
	    @Column(name = "longitud")
	    private BigDecimal longitud;
	    
	    @Column(name = "imagen_url")
	    private String imagenUrl;
	    
	    @JsonIgnore
	    @Transient
	    private MultipartFile imagenMultipart; // para la subida de imagens
}
