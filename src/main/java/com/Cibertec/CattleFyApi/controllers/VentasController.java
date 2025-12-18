package com.Cibertec.CattleFyApi.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import com.Cibertec.CattleFyApi.dto.CalculadoraPrecioRequest;
import com.Cibertec.CattleFyApi.dto.CalculadoraPrecioResponse;
import com.Cibertec.CattleFyApi.dto.ErrorResponse;
import com.Cibertec.CattleFyApi.dto.LoteDisponibleVentaResponse;
import com.Cibertec.CattleFyApi.dto.RegistroVentaRequest;
import com.Cibertec.CattleFyApi.dto.VentaDetalleResponse;
import com.Cibertec.CattleFyApi.dto.VentaListadoResponse;
import com.Cibertec.CattleFyApi.service.RegistroVentaService;

import jakarta.persistence.EntityNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/ventas")
public class VentasController {

	@Autowired
	RegistroVentaService ventaService;
	
    @GetMapping("/total/{anio}/{mes}")
    public ResponseEntity<BigDecimal> obtenerTotalVentasPorMes(@PathVariable int anio, @PathVariable int mes) {
        BigDecimal totalVentas = ventaService.totalVentasPorMes(anio, mes);
        return ResponseEntity.ok(totalVentas);
    }
    
    @GetMapping("/total-animales-vendidos/{idGranja}")
    public ResponseEntity<Long> obtenerTotalAnimalesVendidos(@PathVariable Integer idGranja) {
    	Long cantidad = ventaService.totalAnimalesVendidos(idGranja);
        return ResponseEntity.ok(cantidad);
    }
    
    @GetMapping("/ingresos-mensuales/{idGranja}")
    public ResponseEntity<BigDecimal> ingresosMensuales(@PathVariable Integer idGranja,
            @RequestParam int anio,
            @RequestParam int mes) {
        return ResponseEntity.ok(ventaService.obtenerIngresosTotalesMes(idGranja, anio, mes)
        );
    }
    
    @GetMapping("/roi-promedio-mensual/{idGranja}")
    public ResponseEntity<BigDecimal> roiPromedioMensual(@PathVariable Integer idGranja,
            @RequestParam int anio,
            @RequestParam int mes) {

        return ResponseEntity.ok(ventaService.obtenerRoiPromedioMes(idGranja, anio, mes)
        );
    }
    
    @PostMapping("/venta")
    public ResponseEntity<VentaDetalleResponse> registrarVenta(@RequestBody RegistroVentaRequest request) {
        
        try {
            VentaDetalleResponse response = ventaService.registrarVenta(request);
            
            return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("X-Message", "Venta registrada exitosamente")
                .header("X-Resource-Id", response.getVentaId().toString())
                .header("X-Timestamp", LocalDateTime.now().toString())
                .body(response);
                
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
                    
        } catch (IllegalStateException | IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }


    @GetMapping("/{ventaId}")
    public ResponseEntity<VentaDetalleResponse> obtenerDetalleVenta(
            @PathVariable Integer ventaId) {
        
        try {
            VentaDetalleResponse response = ventaService.obtenerDetalleVenta(ventaId);
            
            return ResponseEntity.ok()
                .header("X-Message", "Detalle de venta obtenido exitosamente")
                .header("X-Resource-Type", "VentaDetalle")
                .header("X-Timestamp", LocalDateTime.now().toString())
                .body(response);
            
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    // no es necesario poner los params, pero si hacemos con fechas lo ponemos
    @GetMapping("/granja/{granjaId}")
    public ResponseEntity<List<VentaListadoResponse>> listarVentas(
            @PathVariable Integer granjaId,
            @RequestParam(required = false) String tipoVenta,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaDesde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaHasta) {
        
        List<VentaListadoResponse> ventas = ventaService.listarVentas(
            granjaId, tipoVenta, fechaDesde, fechaHasta
        );
        
        List<String> filtrosAplicados = new ArrayList<>();
        if (tipoVenta != null) filtrosAplicados.add("tipoVenta=" + tipoVenta);
        if (fechaDesde != null) filtrosAplicados.add("fechaDesde=" + fechaDesde);
        if (fechaHasta != null) filtrosAplicados.add("fechaHasta=" + fechaHasta);
        
        String filtros = filtrosAplicados.isEmpty() 
            ? "ninguno" 
            : String.join(", ", filtrosAplicados);
        
        return ResponseEntity.ok()
            .header("X-Total-Count", String.valueOf(ventas.size()))
            .header("X-Filter-Applied", filtros)
            .header("X-Message", "Listado de ventas obtenido exitosamente")
            .header("X-Timestamp", LocalDateTime.now().toString())
            .body(ventas);
    }


    @GetMapping("/lotes-disponibles/{granjaId}")
    public ResponseEntity<List<LoteDisponibleVentaResponse>> obtenerLotesDisponibles(
            @PathVariable Integer granjaId,
            @RequestParam(required = false) String tipoLote) {
        
        List<LoteDisponibleVentaResponse> lotes = ventaService
            .obtenerLotesDisponiblesParaVenta(granjaId, tipoLote);
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(lotes.size()));
        headers.add("X-Message", "Lotes disponibles obtenidos exitosamente");
        
        if (tipoLote != null) {
            headers.add("X-Filter-Type", tipoLote);
        }
        
        return ResponseEntity.ok()
            .headers(headers)
            .body(lotes);
    }

    //Calcula el precio necesario para alcanzar un ROI objetivo

    @PostMapping("/calcular-precio")
    public ResponseEntity<CalculadoraPrecioResponse> calcularPrecioParaRoi(@RequestBody 
    		CalculadoraPrecioRequest request) {
        
        try {
            CalculadoraPrecioResponse response = ventaService
                .calcularPrecioParaRoiObjetivo(request);
            
            return ResponseEntity.ok()
                .header("X-Message", "Precio calculado exitosamente")
                .header("X-Calculation-Type", "ROI-Based-Price")
                .header("X-Timestamp", LocalDateTime.now().toString())
                .body(response);
            
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
                    
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
    

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(
            ResponseStatusException ex, 
            WebRequest request) {
        
        ErrorResponse error = ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(ex.getStatusCode().value())
            .error(ex.getReason())
            .message(ex.getMessage())
            .path(request.getDescription(false).replace("uri=", ""))
            .build();
        
        return ResponseEntity
            .status(ex.getStatusCode())
            .header("X-Error", "true")
            .header("X-Error-Type", ex.getStatusCode().toString())
            .body(error);
    }
}
