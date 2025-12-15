package com.Cibertec.CattleFyApi.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistroSanitarioListResponse {
    private List<RegistroSanitarioResponse> registros;
}