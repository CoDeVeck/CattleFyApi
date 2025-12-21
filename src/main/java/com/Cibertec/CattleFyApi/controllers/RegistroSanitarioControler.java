package com.Cibertec.CattleFyApi.controllers;

import com.Cibertec.CattleFyApi.dto.RegistroSanitarioListResponse;
import com.Cibertec.CattleFyApi.dto.RegistroSanitarioRequest;
import com.Cibertec.CattleFyApi.dto.RegistroSanitarioResponse;
import com.Cibertec.CattleFyApi.dto.ResultadoResponse;
import com.Cibertec.CattleFyApi.service.RegistroSanitarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registroSanitario")
@RequiredArgsConstructor
public class RegistroSanitarioControler {
    private final RegistroSanitarioService registroSanitarioService;

    @PostMapping("/crear-unificado")
    public ResponseEntity<ResultadoResponse<RegistroSanitarioResponse>> crearRegistroUnificado(
            @ModelAttribute RegistroSanitarioRequest request) {
        ResultadoResponse<RegistroSanitarioResponse> response =
                registroSanitarioService.crearRegistroUnificado(request);

        if (response.isValor()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/listarHistorial/{loteId}")
    public ResponseEntity<ResultadoResponse<RegistroSanitarioListResponse>> listarTodos(@PathVariable Integer loteId) {
        ResultadoResponse<RegistroSanitarioListResponse> response =
                registroSanitarioService.listarTodos(loteId);

        if (response.isValor()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
}
