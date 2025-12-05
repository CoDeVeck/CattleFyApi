package com.Cibertec.CattleFyApi.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_registro_movilidad")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistroMovilidad {

		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "movilidad_id")
	    private Integer movilidadId;
	    
	    @ManyToOne
	    @JoinColumn(name = "animal_id")
	    private Animal animal;
	    
	    @ManyToOne
	    @JoinColumn(name = "lote_origen_id")
	    private Lote loteOrigen;
	    
	    @ManyToOne
	    @JoinColumn(name = "lote_destino_id")
	    private Lote loteDestino;
	    
	    @Column(name = "fecha_movimiento")
	    private LocalDateTime fechaMovimiento;
	    
	    @Column(name = "motivo")
	    private String motivo;
}
