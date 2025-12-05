package com.Cibertec.CattleFyApi.models;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_registro_alimentacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistroAlimentacion {

	   	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "alimentacion_id")
	    private Integer alimentacionId;
	    
	    @ManyToOne
	    @JoinColumn(name = "lote_id", nullable = false)
	    private Lote lote;
	    
	    @Column(name = "fecha_registro")
	    private LocalDateTime fechaRegistro;
	    
	    @Column(name = "cantidad_kg", precision = 10, scale = 2, nullable = false)
	    private BigDecimal cantidadKg;
	    
	    @Column(name = "costo_por_kg", precision = 10, scale = 4, nullable = false)
	    private BigDecimal costoPorKg;
	    
	    @Column(name = "dieta_tipo", length = 100)
	    private String dietaTipo;
}
