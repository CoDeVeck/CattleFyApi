package com.Cibertec.CattleFyApi.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_registro_venta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistroVenta {

	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "venta_id")
	    private Integer ventaId;
	    
	    @ManyToOne
	    @JoinColumn(name = "lote_id")
	    private Lote lote;
	    
	    @Column(name = "tipo_alcance_venta")
	    private String alcanceVenta;
	    
	    @Column(name = "tipo_venta")
	    private String tipoVenta;
	    
	    @Column(name = "peso_total_kg")
	    private BigDecimal pesoTotalKg;
	    
	    @Column(name = "precio_por_kg")
	    private BigDecimal precioPorKg;
	    
	    @Column(name = "precio_total")
	    private BigDecimal precioTotal;
	    
	    @Column(name = "roi_estimado")
	    private BigDecimal roiEstimado;
	    
	    @Column(name = "roi_meta")
	    private BigDecimal roiMeta;
	    
	    @Column(name = "cliente_nombre")
	    private String clienteNombre;
	    
	    @Column(name = "fecha_venta")
	    private LocalDateTime fechaVenta;
	    
	    @Column(name = "animales_vendidos_ids")
	    private Integer[] animalesVendidosIds;
}
