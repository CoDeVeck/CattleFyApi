package com.Cibertec.CattleFyApi.controllers;

import com.Cibertec.CattleFyApi.dto.LoteListadoDTO;
import com.Cibertec.CattleFyApi.dto.ResultadoResponse;
import com.Cibertec.CattleFyApi.models.Lote;
import com.Cibertec.CattleFyApi.service.LoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/lote")
@RequiredArgsConstructor
@RestController
public class LoteController {
    private final LoteService loteService;

    @GetMapping("/list")
    public ResponseEntity<List<LoteListadoDTO>> list(){
        List<LoteListadoDTO> listado = loteService.list();
        if(!listado.isEmpty()){
            return new ResponseEntity<>(listado, HttpStatus.OK);
        }
        return new ResponseEntity<>(listado, HttpStatus.NOT_FOUND);
    }

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
