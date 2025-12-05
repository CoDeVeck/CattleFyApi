package com.Cibertec.CattleFyApi.models;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_registro_produccion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistroProduccion {

	  	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "produccion_id")
	    private Integer produccionId;
	    
	    @ManyToOne
	    @JoinColumn(name = "lote_id")
	    private Lote lote;
	    
	    @Column(name = "fecha_registro")
	    private LocalDateTime fechaRegistro;
	    
	    @Column(name = "tipo_produccion")
	    private String tipoProduccion; 
	    
	    @Column(name = "cantidad", precision = 10, scale = 2, nullable = false)
	    private BigDecimal cantidad;
}
