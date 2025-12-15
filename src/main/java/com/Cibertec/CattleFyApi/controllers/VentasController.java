package com.Cibertec.CattleFyApi.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Cibertec.CattleFyApi.service.RegistroVentaService;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/ventas")
public class VentasController {

	@Autowired
	RegistroVentaService ventasService;
	
    @GetMapping("/total/{anio}/{mes}")
    public ResponseEntity<BigDecimal> obtenerTotalVentasPorMes(@PathVariable int anio, @PathVariable int mes) {
        BigDecimal totalVentas = ventasService.totalVentasPorMes(anio, mes);
        return ResponseEntity.ok(totalVentas);
    }
	
}
