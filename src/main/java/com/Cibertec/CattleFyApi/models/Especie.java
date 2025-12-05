package com.Cibertec.CattleFyApi.models;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "tb_especies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Especie {

	
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "especie_id")
	    private Integer especieId;
	    
	    @Column(name = "nombre")
	    private String nombre;
}
