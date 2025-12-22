package com.Cibertec.CattleFyApi.controllers;

import com.Cibertec.CattleFyApi.dto.*;
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
	
    @GetMapping("/total-activos/{granjaId}")
    public ResponseEntity<Long> obtenerTotalLotesActivos(@PathVariable Integer granjaId) {
        Long lotes = lotesService.totalLotesActivos(granjaId);
        return ResponseEntity.ok(lotes);
    }

    @GetMapping("/lote/{qrLote}")
    public ResponseEntity<?> obtenerLotePorQr(@PathVariable String qrLote) {
        try {
            LoteResponse lote = lotesService.obtenerLotePorQr(qrLote);
            return ResponseEntity.ok(lote);
        }
        catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al buscar lote por qr: " + e.getMessage());
        }
    }

    @GetMapping("/list-simple")
    public ResponseEntity<ResultadoResponse<List<LoteSimpleDTO>>> listarLotesSimple() {

        List<LoteSimpleDTO> lotes = lotesService.listarLotesSimples();

        return ResponseEntity.ok(
                ResultadoResponse.success("Lotes listados", lotes)
        );
    }

    @GetMapping("obtenerDetalle/{id}")
    public ResponseEntity<ResultadoResponse<LoteDetalleResponse>> obtenerLoteDetalle(@PathVariable Integer id) {
        try {
            LoteDetalleResponse lote = lotesService.obtenerLoteDetalle(id);

            if (lote == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(ResultadoResponse.error("Lote no encontrado."));
            }

            return ResponseEntity.ok(
                    ResultadoResponse.success("Lote obtenido exitosamente.", lote));

        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body(ResultadoResponse.error("Error interno al obtener el lote: " + e.getMessage()));
        }
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
