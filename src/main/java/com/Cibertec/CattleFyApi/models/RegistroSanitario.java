package com.Cibertec.CattleFyApi.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "tb_registro_sanitario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistroSanitario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sanitario_id")
    private Integer sanitarioId;
    
    @ManyToOne
    @JoinColumn(name = "lote_id")
    private Lote lote;
    
    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;
    
    @Column(name = "tipo_aplicacion", length = 20, nullable = false)
    private String tipoAplicacion; 
    
    @Column(name = "protocolo_tipo", length = 50, nullable = false)
    private String protocoloTipo; 
    
    @Column(name = "nombre_producto", length = 100, nullable = false)
    private String nombreProducto;
    
    @Column(name = "costo_por_dosis", precision = 10, scale = 4, nullable = false)
    private BigDecimal costoPorDosis;
    
    @Column(name = "cantidad_dosis", precision = 10, scale = 2)
    private BigDecimal cantidadDosis = BigDecimal.ONE;
    
    @Column(name = "animales_tratados")
    private Integer animalesTratados;
    
    @Column(name = "fecha_aplicacion", nullable = false)
    private LocalDateTime fechaAplicacion;
}
