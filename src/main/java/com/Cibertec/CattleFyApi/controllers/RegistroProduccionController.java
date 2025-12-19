package com.Cibertec.CattleFyApi.controllers;

import com.Cibertec.CattleFyApi.dto.LoteSimpleDTO;
import com.Cibertec.CattleFyApi.dto.ReporteGrafico1;
import com.Cibertec.CattleFyApi.dto.ReporteProduccionEngordeDTO;
import com.Cibertec.CattleFyApi.dto.ReporteTotalAnimalesLecheDto;
import com.Cibertec.CattleFyApi.models.RegistroProduccion;
import com.Cibertec.CattleFyApi.service.LoteService;
import com.Cibertec.CattleFyApi.service.RegistroProduccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/registroProduccion")
@RequiredArgsConstructor
public class RegistroProduccionController {

    private final RegistroProduccionService registroProduccionService;
    private final LoteService loteService;

    @GetMapping("/listHistorial")
    public ResponseEntity<List<RegistroProduccion>> listHistorial(){
        List<RegistroProduccion> resultado = registroProduccionService.getAll();
        if(!resultado.isEmpty()){
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        }
        return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
    }


    @GetMapping("/reporte/{granja_id}/produccion")
    public ResponseEntity<List<ReporteProduccionEngordeDTO>>reporteProduccion(
            @PathVariable("granja_id")Integer granja_id,
            @RequestParam(required = false) Integer lote_id,
            @RequestParam(required = false) Integer categoria_id,
            @RequestParam(required = false) String fecha_inicio,
            @RequestParam(required = false) String fecha_fin
    ){

        List<ReporteProduccionEngordeDTO> lista =
                registroProduccionService.ListaReporteProduccion(granja_id,lote_id,categoria_id,fecha_inicio,fecha_fin);

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/grafico1/{granja_id}/produccion")
    public ResponseEntity<List<ReporteGrafico1>>graficoProduccion(
            @PathVariable("granja_id")Integer granja_id,
            @RequestParam(required = false) Integer lote_id,
            @RequestParam(required = false) Integer categoria_id,
            @RequestParam(required = false) String fecha_inicio,
            @RequestParam(required = false) String fecha_fin
    ){
        List<ReporteGrafico1>reporteGrafico =
                registroProduccionService.GraficoReporte(granja_id,lote_id,categoria_id,fecha_inicio,fecha_fin);

        return ResponseEntity.ok(reporteGrafico);
    }

    @GetMapping("/loteGranja/{granjaId}")
    public ResponseEntity<List<LoteSimpleDTO>>loteSimple(
            @PathVariable("granjaId") Integer granjaId
    ){
        List<LoteSimpleDTO> lote =
                loteService.obtenerLorePorGranja(granjaId);

        return ResponseEntity.ok(lote);
    }

    @GetMapping("/cantidad/{granjaId}")
    public ResponseEntity<List<ReporteTotalAnimalesLecheDto>>cantidad(
            @PathVariable("granjaId") Integer granjaId
    ){
        List<ReporteTotalAnimalesLecheDto> canmtidad =
                registroProduccionService.ListaAnimalLeche(granjaId);
        return  ResponseEntity.ok(canmtidad);
    }

}
