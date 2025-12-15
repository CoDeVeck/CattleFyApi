package com.Cibertec.CattleFyApi.controllers;

import com.Cibertec.CattleFyApi.dto.ResultadoResponse;
import com.Cibertec.CattleFyApi.models.RegistroSanitario;
import com.Cibertec.CattleFyApi.service.RegistroSanitarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registroSanitario")
@RequiredArgsConstructor
public class RegistroSanitarioControler {
    private final RegistroSanitarioService registroSanitarioService;

    @PostMapping("/createMasivo")
    public ResponseEntity<ResultadoResponse<RegistroSanitario>> createMasivo(
            @ModelAttribute RegistroSanitario rs) {
        ResultadoResponse<RegistroSanitario> respuesta = registroSanitarioService.crearRegistroMasivo(rs);

        if (respuesta.isValor()) {
            return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
        }
    }
}
