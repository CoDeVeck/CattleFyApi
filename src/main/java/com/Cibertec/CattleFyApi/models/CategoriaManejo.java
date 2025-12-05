package com.Cibertec.CattleFyApi.models;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "tb_categorias_manejo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaManejo {

	  	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "categoria_id")
	    private Integer categoriaId;
	    
	    @ManyToOne
	    @JoinColumn(name = "especie_id")
	    private Especie especie;
	    
	    @Column(name = "nombre")
	    private String nombre;
	    
	    @Column(name = "tipo_lote")
	    private String tipoLote; 
	    
	    @Column(name = "dieta_recomendada", length = 200)
	    private String dietaRecomendada;
}
