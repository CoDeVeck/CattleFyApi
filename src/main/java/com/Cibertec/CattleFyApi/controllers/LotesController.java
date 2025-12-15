package com.Cibertec.CattleFyApi.controllers;

import com.Cibertec.CattleFyApi.dto.LoteListadoDTO;
import com.Cibertec.CattleFyApi.dto.ResultadoResponse;
import com.Cibertec.CattleFyApi.models.Lote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Cibertec.CattleFyApi.service.LoteService;

import java.util.List;

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

    @GetMapping("/list")
    public ResponseEntity<List<LoteListadoDTO>> list(){
        List<LoteListadoDTO> listado = lotesService.list();
        if(!listado.isEmpty()){
            return new ResponseEntity<>(listado, HttpStatus.OK);
        }
        return new ResponseEntity<>(listado, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<ResultadoResponse<Lote>> crearLote(@RequestBody Lote lote) {
        ResultadoResponse<Lote> respuesta = lotesService.crearLote(lote);
        if (respuesta.isValor()) {
            return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
        }
    }
}
