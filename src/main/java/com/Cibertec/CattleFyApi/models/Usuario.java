package com.Cibertec.CattleFyApi.models;
import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

        @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "usuario_id")
	    private Integer usuarioId;
	    
	    @Column(name = "nombres")
	    private String nombres;
	    
	    @Column(name = "ape_pat")
	    private String apePat;
	    
	    @Column(name = "ape_mat")
	    private String apeMat;
	    
	    @Column(name = "documento")
	    private String documento;
	    
	    @Column(name = "email")
	    private String email;
	    
	    @Column(name = "contra")
	    private String contra;
	    
	    @Column(name = "telefono")
	    private String telefono;
	    
	    @Column(name = "imagen_url")
	    private String imagenUrl;
	    
	    @ManyToOne
	    @JoinColumn(name = "rol_id")
	    private Rol rol;
	    
	    @Column(name = "fecha_registro")
	    private LocalDateTime fechaRegistro;
	    
	    @Column(name = "activo")
	    private Boolean activo = true;
	    
	    @Column(name = "fcm_token", length = 500)
	    private String fcmToken;
	    
	    @Column(name = "fcm_token_fecha")
	    private LocalDateTime fcmTokenFecha;
	    
	    @JsonIgnore
	    @Transient
	    private MultipartFile imagenMultipart; // para la subida de imagens

		// Firebase UID para vincular con Firebase Auth
		@Column(name = "firebase_uid", unique = true, length = 128)
		private String firebaseUid;
}
