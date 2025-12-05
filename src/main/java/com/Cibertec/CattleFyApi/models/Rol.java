package com.Cibertec.CattleFyApi.models;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "tb_rol")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rol {

	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "rol_id")
	    private Integer rolId;
	    
	    @Column(name = "descripcion", length = 20)
	    private String descripcion;
}
