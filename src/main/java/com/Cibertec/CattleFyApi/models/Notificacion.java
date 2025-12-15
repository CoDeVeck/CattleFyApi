package com.Cibertec.CattleFyApi.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_notificaciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notificacion {

	  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notificacion_id")
    private Integer notificacionId;
    
    @ManyToOne
    @JoinColumn(name = "granja_id")
    private Granja granja;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name = "tipo_notificacion_id")
    private TipoNotificacion tipoNotificacion;
    
    @Column(name = "titulo")
    private String titulo;
    
    @Column(name = "mensaje")
    private String mensaje;
    
    @ManyToOne
    @JoinColumn(name = "lote_id")
    private Lote lote;
    
    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;
    
    @ManyToOne
    @JoinColumn(name = "venta_id")
    private RegistroVenta venta;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    @Column(name = "leida")
    private Boolean leida = false;
    
    @Column(name = "descartada")
    private Boolean descartada = false;
}
