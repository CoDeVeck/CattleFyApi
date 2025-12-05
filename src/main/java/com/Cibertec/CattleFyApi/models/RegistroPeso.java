package com.Cibertec.CattleFyApi.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_registro_peso")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistroPeso {

	   	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "peso_id")
	    private Integer pesoId;
	    
	    @ManyToOne
	    @JoinColumn(name = "animal_id")
	    private Animal animal;
	    
	    @Column(name = "fecha_pesaje")
	    private LocalDateTime fechaPesaje;
	    
	    @Column(name = "peso_kg")
	    private BigDecimal pesoKg;
	    
	    @Column(name = "ganancia_kg")
	    private BigDecimal gananciaKg;
}
