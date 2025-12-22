package com.Cibertec.CattleFyApi.controllers;

import com.Cibertec.CattleFyApi.dto.RegistrarAlimentacionHistorialDTO;
import com.Cibertec.CattleFyApi.dto.RegistroAlimentacionRequestDTO;
import com.Cibertec.CattleFyApi.dto.RegistroAlimentacionResponseDTO;
import com.Cibertec.CattleFyApi.dto.ResultadoResponse;
import com.Cibertec.CattleFyApi.models.RegistroAlimentacion;
import com.Cibertec.CattleFyApi.service.RegistroAlimentacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/registrarAlimentacion")
@RestController
@RequiredArgsConstructor
public class RegistrarAlimentacionController {
    private final RegistroAlimentacionService alimentacionService;

    @GetMapping("/listHistorial/{loteId}")
    public ResponseEntity<List<RegistrarAlimentacionHistorialDTO>> listarHistorial(@PathVariable Integer loteId){
        List<RegistrarAlimentacionHistorialDTO> historial = alimentacionService.listarHistorialAlimentacion(loteId);
        if(!historial.isEmpty()){
            return new ResponseEntity<>(historial, HttpStatus.OK);
        }
        return new ResponseEntity<>(historial, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<ResultadoResponse<RegistroAlimentacionResponseDTO>> create(@RequestBody RegistroAlimentacionRequestDTO ra){
        ResultadoResponse<RegistroAlimentacionResponseDTO> respuesta = alimentacionService.crearAlimentacion(ra);
        if (respuesta.isValor()) {
            return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
        }
    }
}
