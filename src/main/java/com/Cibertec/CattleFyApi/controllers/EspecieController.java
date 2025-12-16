package com.Cibertec.CattleFyApi.controllers;

import com.Cibertec.CattleFyApi.models.Especie;
import com.Cibertec.CattleFyApi.service.EspecieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/especies")
@RequiredArgsConstructor
public class EspecieController {
    private final EspecieService especieService;

    @GetMapping("/list")
    public ResponseEntity<List<Especie>> list(){
        List<Especie> listado = especieService.list();

        if(!listado.isEmpty()){
            return new ResponseEntity<>(listado, HttpStatus.OK);
        }
        return new ResponseEntity<>(listado, HttpStatus.NOT_FOUND);
    }
}
