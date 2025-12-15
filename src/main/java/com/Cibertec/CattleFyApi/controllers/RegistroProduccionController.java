package com.Cibertec.CattleFyApi.controllers;

import com.Cibertec.CattleFyApi.models.RegistroProduccion;
import com.Cibertec.CattleFyApi.service.RegistroProduccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/registroProduccion")
@RequiredArgsConstructor
public class RegistroProduccionController {
    private final RegistroProduccionService registroProduccionService;

    @GetMapping("/listHistorial")
    public ResponseEntity<List<RegistroProduccion>> listHistorial(){
        List<RegistroProduccion> resultado = registroProduccionService.getAll();
        if(!resultado.isEmpty()){
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        }
        return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
    }
}
