package com.Cibertec.CattleFyApi.controllers;

import com.Cibertec.CattleFyApi.dto.*;
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

    @GetMapping("/listHistorial/{loteId}")
    public ResponseEntity<List<RegistroProduccionDTO>> listHistorial(@PathVariable Integer loteId){
        List<RegistroProduccionDTO> resultado = registroProduccionService.listarProduccionPorLote(loteId);
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

    @GetMapping("/reporte/reproduccion/{granja_id}")
    public ResponseEntity<List<ReporteProduccionReproduccion>>lista(
            @PathVariable("granja_id")Integer granja_id,
            @RequestParam(required = false) Integer lote_id,
            @RequestParam(required = false) Integer categoria_id,
            @RequestParam(required = false) String fecha_inicio,
            @RequestParam(required = false) String fecha_fin
    ){
        List<ReporteProduccionReproduccion> lsita =
                registroProduccionService.ListaProduccionReproduccion(granja_id,lote_id,categoria_id,fecha_inicio,fecha_fin);

        return ResponseEntity.ok(lsita);
    }

    @GetMapping("/grafico2/{granja_id}/reproduccion")
    public  ResponseEntity<List<ReporteGrafico2>>lista3 (
            @PathVariable("granja_id")Integer granja_id,
            @RequestParam(required = false) Integer lote_id,
            @RequestParam(required = false) String fecha_inicio,
            @RequestParam(required = false) String fecha_fin
    ){
        List<ReporteGrafico2> lista =
                registroProduccionService.GraficoReporte2(granja_id,lote_id,fecha_inicio,fecha_fin);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/reporte/{granja_id}/financiero")
    public  ResponseEntity<List<ReporteFinancieroDTO>>lista4(
            @PathVariable("granja_id")Integer granja_id,
            @RequestParam(required = false) Integer lote_id,
            @RequestParam(required = false) String fecha_inicio,
            @RequestParam(required = false) String fecha_fin
    ){
        List<ReporteFinancieroDTO>listafinaciero =
                registroProduccionService.reporteFinanciero(granja_id,lote_id,fecha_inicio,fecha_fin);
        return ResponseEntity.ok(listafinaciero);
    }

    @GetMapping("/grafico3/{granja_id}/distribucion")
    public ResponseEntity<List<Grafico3>>Grafico3(
            @PathVariable("granja_id")Integer granja_id,
            @RequestParam(required = false) Integer lote_id,
            @RequestParam(required = false) String fecha_inicio,
            @RequestParam(required = false) String fecha_fin
    ){
      List<Grafico3> grafico3 = registroProduccionService.distribucionDeGastos(granja_id,lote_id,fecha_inicio,fecha_fin);

      return ResponseEntity.ok(grafico3);
    }

    @GetMapping("/reporte/{granja_id}/sanidad")
    public ResponseEntity<SanidadEstadisticasDTO> reportesSanitdad(
            @PathVariable("granja_id")Integer granja_id,
            @RequestParam(required = false) Integer lote_id,
            @RequestParam(required = false) String fecha_inicio,
            @RequestParam(required = false) String fecha_fin
    ){
        SanidadEstadisticasDTO sanidad = registroProduccionService.sanidadEstadisticasDTO(granja_id,lote_id,fecha_inicio, fecha_fin);

        return ResponseEntity.ok(sanidad);
    }

    @GetMapping("/reporte/{granja_id}/detalleAplicaciones")
    public ResponseEntity <List<AplicacionesRecientesDTO>> reportesSanidad(
            @PathVariable("granja_id")Integer granja_id,
            @RequestParam(required = false) Integer lote_id,
            @RequestParam(required = false) String protocolo_tipo,
            @RequestParam(required = false) String fecha_inicio,
            @RequestParam(required = false) String fecha_fin
    ){
        List<AplicacionesRecientesDTO> lista = registroProduccionService.listaAplicacionRecientes(granja_id,lote_id,protocolo_tipo,fecha_inicio,fecha_fin);

        return ResponseEntity.ok(lista);
    }

}
