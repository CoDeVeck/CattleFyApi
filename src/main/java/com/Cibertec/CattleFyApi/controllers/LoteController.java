package com.Cibertec.CattleFyApi.controllers;

import com.Cibertec.CattleFyApi.dto.ResultadoResponse;
import com.Cibertec.CattleFyApi.models.Lote;
import com.Cibertec.CattleFyApi.service.LoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/lote")
@RequiredArgsConstructor
@RestController
public class LoteController {
    private final LoteService loteService;

    @PostMapping("/create")
    public ResponseEntity<ResultadoResponse<Lote>> crearLote(@RequestBody Lote lote) {
        ResultadoResponse<Lote> respuesta = loteService.crearLote(lote);
        if (respuesta.isValor()) {
            return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
        }
    }
}
