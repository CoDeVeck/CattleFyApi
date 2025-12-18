package com.Cibertec.CattleFyApi.controllers;

import com.Cibertec.CattleFyApi.dto.RegistroProduccionRequest;
import com.Cibertec.CattleFyApi.dto.RegistroProduccionResponse;
import com.Cibertec.CattleFyApi.dto.ResultadoResponse;
import com.Cibertec.CattleFyApi.service.RegistroProduccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/registrarProduccion")
public class RegistrarProduccionController {

    private final RegistroProduccionService registroProduccionService;

    @PostMapping("/registrar")
    public ResponseEntity<ResultadoResponse<RegistroProduccionResponse>> crear(
            @RequestBody RegistroProduccionRequest request) {

        ResultadoResponse<RegistroProduccionResponse> response =
                registroProduccionService.registrarProduccion(request);

        if (response.isValor()) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

}
