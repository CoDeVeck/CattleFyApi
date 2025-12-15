package com.Cibertec.CattleFyApi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Cibertec.CattleFyApi.service.LoteService;

@RestController
@RequestMapping("/lotes")
public class LotesController {

	@Autowired
	LoteService lotesService;
	
    @GetMapping("/total-activos")
    public ResponseEntity<Long> obtenerTotalLotesActivos(@PathVariable Integer granjaId) {
        Long lotes = lotesService.totalLotesActivos(granjaId);
        return ResponseEntity.ok(lotes);
    }
    
    
	
}
