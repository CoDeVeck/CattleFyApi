package com.Cibertec.CattleFyApi.models;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "tb_tipos_notificacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TipoNotificacion {

	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "tipo_notificacion_id")
	    private Integer tipoNotificacionId;
	    
	    @Column(name = "codigo")
	    private String codigo;
	    
	    @Column(name = "descripcion")
	    private String descripcion;
}
