package com.Cibertec.CattleFyApi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Cibertec.CattleFyApi.service.AnimalService;

@RestController
@RequestMapping("/animales")
public class AnimalesController {

	@Autowired
	AnimalService animaleService;
	
    @GetMapping("/total-vivos")
    public ResponseEntity<Long> obtenerTotalAnimalesVivos (@PathVariable Integer granjaId) {
        Long animales = animaleService.totalAnimalesVivos(granjaId);
        return ResponseEntity.ok(animales);
    }
	
}
