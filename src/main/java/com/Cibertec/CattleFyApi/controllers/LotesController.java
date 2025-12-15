package com.Cibertec.CattleFyApi.controllers;

import com.Cibertec.CattleFyApi.dto.LoteListadoDTO;
import com.Cibertec.CattleFyApi.dto.LoteRequest;
import com.Cibertec.CattleFyApi.dto.LoteResponse;
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

    @GetMapping("/listFiltro")
    public ResponseEntity<ResultadoResponse<List<LoteResponse>>> listarLotes(
            @RequestParam(required = false) Integer granjaId,
            @RequestParam(required = false) Integer especieId,
            @RequestParam(required = false) String tipoLote) {

        try {
            List<LoteResponse> lotes = lotesService.listarLotes(granjaId, especieId, tipoLote);

            if (lotes.isEmpty()) {
                return ResponseEntity.ok(
                        ResultadoResponse.success("No se encontraron lotes con los filtros especificados.", lotes));
            }

            return ResponseEntity.ok(
                    ResultadoResponse.success("Lotes listados exitosamente.", lotes));

        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body(ResultadoResponse.error("Error interno al listar los lotes."));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ResultadoResponse<LoteResponse>> crearLote(@RequestBody LoteRequest lote) {
        ResultadoResponse<LoteResponse> respuesta = lotesService.registrarLote(lote);
        if (respuesta.isValor()) {
            return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
        }
    }
}
