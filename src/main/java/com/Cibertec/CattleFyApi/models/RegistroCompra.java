package com.Cibertec.CattleFyApi.models;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_registro_compra")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class RegistroCompra {

	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "compra_id")
    private Integer compraId;
    
    @ManyToOne
    @JoinColumn(name = "lote_id")
    private Lote lote;
    
    @Column(name = "proveedor_nombre")
    private String proveedorNombre;
    
    @Column(name = "fecha_compra")
    private LocalDateTime fechaCompra;
    
    @Column(name = "cantidad_animales")
    private Integer cantidadAnimales;
    
    @Column(name = "costo_total")
    private BigDecimal costoTotal;
    
    @Column(name = "observaciones")
    private String observaciones;
}
