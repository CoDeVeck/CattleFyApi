package com.Cibertec.CattleFyApi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {
    private Integer usuarioId;
    private String firebaseUid;
    private String email;
    private String nombres;
    private String apellidos;
    private String token;
    private String rol;
}