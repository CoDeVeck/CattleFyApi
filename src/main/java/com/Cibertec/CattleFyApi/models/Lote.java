package com.Cibertec.CattleFyApi.models;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_lotes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lote {
	 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lote_id")
    private Integer loteId;
    
    @Column(name = "lote_qr")
    private String codigoQr;
    
    @ManyToOne
    @JoinColumn(name = "granja_id")
    private Granja granja;
    
    @Column(name = "nombre")
    private String nombre;
    
    @ManyToOne
    @JoinColumn(name = "especie_id")
    private Especie especie;
    
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoriaManejo categoria;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    @Column(name = "estado")
    private String estado;
    
    @Column(name = "capacidad_max")
    private Integer capacidadMax;
}
