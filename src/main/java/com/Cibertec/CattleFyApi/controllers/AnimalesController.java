package com.Cibertec.CattleFyApi.controllers;

import com.Cibertec.CattleFyApi.dto.*;
import com.Cibertec.CattleFyApi.models.RegistroMovilidad;
import com.Cibertec.CattleFyApi.models.RegistroMuerte;
import com.Cibertec.CattleFyApi.models.RegistroPeso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Cibertec.CattleFyApi.service.AnimalService;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/animales")
public class AnimalesController {

	@Autowired
	AnimalService animalService;
	
    @GetMapping("/total-vivos/{granjaId}")
    public ResponseEntity<Long> obtenerTotalAnimalesVivos (@PathVariable Integer granjaId) {
        Long animales = animalService.totalAnimalesVivos(granjaId);
        return ResponseEntity.ok(animales);
    }

    @PostMapping(value = "/registrar", consumes = {"multipart/form-data"})
    public ResponseEntity<?> crearAnimal(@ModelAttribute AnimalRequest animal) {
        try {
            AnimalResponse rpta = animalService.registrarAnimal(animal);
            return ResponseEntity.ok(rpta);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar animal: " + e.getMessage());
        }
    }

    @GetMapping("/animal/{qrAnimal}")
    public ResponseEntity<?> obtenerAnimalPorQr (@PathVariable String qrAnimal) {
        try {
            AnimalResponse animal = animalService.obtenerAnimalPorQr(qrAnimal);
            return ResponseEntity.ok(animal);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al buscar animal por qr: " + e.getMessage());
        }
    }

    @GetMapping("/lote/{idLote}")
    public ResponseEntity<?> listarAnimalesPorLote(@PathVariable Integer idLote) {
        try {
            List<AnimalResponse> lstAnimales = animalService.listarAnimalesPorLote(idLote);
            return ResponseEntity.ok(lstAnimales);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al buscar animales por id lote: " + e.getMessage());
        }

    }
    
    @PostMapping("/registrar-peso")
    public ResponseEntity<?> registrarPeso(@RequestBody AnimalPesoReq req) {
        try {
            AnimalResponse response = animalService.registrarPeso(req);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Animal no encontrado: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Error de validación: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar peso del animal: " + e.getMessage());
        }
    }

    @PostMapping("/registrar-muerte")
    public ResponseEntity<?> registrarMuerte(@RequestBody AnimalMuerteReq req) {
        try {
            AnimalResponse response = animalService.marcarBaja(req);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Animal no encontrado: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Error de validación: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar muerte del animal: " + e.getMessage());
        }
    }

    @PostMapping("/registrar-traslado")
    public ResponseEntity<?> registrarTraslado(@RequestBody AnimalTrasReq req) {
        try {
            AnimalResponse response = animalService.trasladarAnimal(req);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Error de validación: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar traslado del animal: " + e.getMessage());
        }
    }
    
    @GetMapping("/{idAnimal}/historial/pesaje")
    public ResponseEntity<List<AnimalHistPesaje>> obtenerHistorialPesaje(
            @PathVariable Integer idAnimal) {
        List<AnimalHistPesaje> historial = animalService.listarHistorialPesaje(idAnimal);
        return ResponseEntity.ok(historial);
    }

    @GetMapping("/{idAnimal}/historial/sanitario")
    public ResponseEntity<List<AnimalHistSanitario>> obtenerHistorialSanitario(
            @PathVariable Integer idAnimal) {
        List<AnimalHistSanitario> historial = animalService.listarHistorialSanitario(idAnimal);
        return ResponseEntity.ok(historial);
    }

    @GetMapping("/{idAnimal}/historial/traslados")
    public ResponseEntity<List<AnimalHistTraslado>> obtenerHistorialTraslados(
            @PathVariable Integer idAnimal) {
        List<AnimalHistTraslado> historial = animalService.listarHistorialTraslado(idAnimal);
        return ResponseEntity.ok(historial);
    }
}
