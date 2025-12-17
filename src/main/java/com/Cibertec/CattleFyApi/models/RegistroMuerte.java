package com.Cibertec.CattleFyApi.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_registro_muerte")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistroMuerte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "muerte_id")
    private Integer muerteId;
    
    @OneToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;
    
    @ManyToOne
    @JoinColumn(name = "lote_id")
    private Lote lote;
    
    @Column(name = "fecha_muerte")
    private LocalDateTime fechaMuerte;
    
    @Column(name = "causa_muerte")
    private String causaMuerte;
    
}
