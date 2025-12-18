package com.Cibertec.CattleFyApi.service;

import com.Cibertec.CattleFyApi.dto.CalculadoraPrecioRequest;
import com.Cibertec.CattleFyApi.dto.CalculadoraPrecioResponse;
import com.Cibertec.CattleFyApi.dto.DetalleAnimalVendido;
import com.Cibertec.CattleFyApi.dto.LoteDisponibleVentaResponse;
import com.Cibertec.CattleFyApi.dto.RegistroVentaRequest;
import com.Cibertec.CattleFyApi.dto.VentaDetalleResponse;
import com.Cibertec.CattleFyApi.dto.VentaListadoResponse;
import com.Cibertec.CattleFyApi.models.Animal;
import com.Cibertec.CattleFyApi.models.Lote;
import com.Cibertec.CattleFyApi.models.RegistroVenta;
import com.Cibertec.CattleFyApi.repository.IAnimalRepository;
import com.Cibertec.CattleFyApi.repository.ILoteRepository;
import com.Cibertec.CattleFyApi.repository.IRegistroAlimentacionRepository;
import com.Cibertec.CattleFyApi.repository.IRegistroCompraRepository;
import com.Cibertec.CattleFyApi.repository.IRegistroSanitarioRepository;
import com.Cibertec.CattleFyApi.repository.IRegistroVentaRepository;

import jakarta.persistence.EntityNotFoundException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistroVentaService {

    @Autowired
    IRegistroVentaRepository ventaRepository;
    
    @Autowired
    private ILoteRepository loteRepository;
    
    @Autowired
    private IAnimalRepository animalRepository;
    
    @Autowired
    private IRegistroCompraRepository compraRepository;
    
    @Autowired
    private IRegistroAlimentacionRepository alimentacionRepository;
    
    @Autowired
    private IRegistroSanitarioRepository sanitarioRepository;
    
    public BigDecimal totalVentasPorMes(int anio, int mes) {

        YearMonth yearMonth = YearMonth.of(anio, mes);

        LocalDateTime inicio = yearMonth
                .atDay(1)
                .atStartOfDay();

        LocalDateTime fin = yearMonth
                .plusMonths(1)
                .atDay(1)
                .atStartOfDay();

        return ventaRepository.sumarVentasPorRango(inicio, fin);
    }

    public VentaDetalleResponse registrarVenta(RegistroVentaRequest request) {

        Lote lote = validarLote(request.getLoteId());

        List<Animal> animalesVivos = animalRepository.findByLoteAndEstado(lote, "Vivo");
        if (animalesVivos.isEmpty()) {
            throw new IllegalStateException("No hay animales vivos en el lote");
        }

        validarTipoVenta(request, lote.getCategoria().getTipoLote(), animalesVivos.size());
        List<Animal> animalesAVender = obtenerAnimalesAVender(request, animalesVivos, lote);

        BigDecimal pesoTotalCalculado = calcularPesoTotalAnimales(animalesAVender);
        request.setPesoTotalKg(pesoTotalCalculado);

        // ROI Real
        BigDecimal costoTotal = calcularCostoTotalLote(lote);
        BigDecimal roiReal = calcularROI(costoTotal, request.getPrecioTotal());

        validarPerdidas(roiReal, request.getRoiMeta(), costoTotal, request.getPesoTotalKg());

        RegistroVenta venta = crearYGuardarVenta(request, lote, roiReal, animalesAVender);
        actualizarEstados(animalesAVender, lote, "Total".equals(request.getTipoAlcanceVenta()));

        return construirVentaDetalle(venta, lote, animalesAVender, costoTotal, request.getRoiMeta());
    }
    
    @Transactional(readOnly = true)
    public VentaDetalleResponse obtenerDetalleVenta(Integer ventaId) {
        RegistroVenta venta = ventaRepository.findById(ventaId)
            .orElseThrow(() -> new EntityNotFoundException("Venta no encontrada"));
        
        Lote lote = venta.getLote();
        List<Animal> animales = animalRepository.findAllById(Arrays.asList(venta.getAnimalesVendidosIds()));
        BigDecimal costoTotal = calcularCostoTotalLote(lote);
        
        return construirVentaDetalle(venta, lote, animales, costoTotal, venta.getRoiMeta());
    }

    @Transactional(readOnly = true)
    public List<VentaListadoResponse> listarVentas(Integer granjaId, String tipoVenta,
                                                    LocalDateTime fechaDesde, LocalDateTime fechaHasta) {
        return ventaRepository.findByGranjaWithFilters(granjaId, tipoVenta, fechaDesde, fechaHasta)
            .stream()
            .map(this::construirVentaListado)
            .collect(Collectors.toList());
    }
    

    @Transactional(readOnly = true)
    public List<LoteDisponibleVentaResponse> obtenerLotesDisponiblesParaVenta(Integer granjaId, String tipoLote) {
        return loteRepository.findByGranjaIdAndEstadoAndTipoLote(granjaId, "Activo", tipoLote)
            .stream()
            .map(this::construirLoteDisponible)
            .filter(dto -> dto.getCantidadAnimalesVivos() > 0)
            .collect(Collectors.toList());
    }
    

    @Transactional(readOnly = true)
    public CalculadoraPrecioResponse calcularPrecioParaRoiObjetivo(CalculadoraPrecioRequest request) {
        Lote lote = loteRepository.findById(request.getLoteId())
            .orElseThrow(() -> new EntityNotFoundException("Lote no encontrado"));
        
        BigDecimal pesoTotal = animalRepository.sumPesoByLoteAndEstado(lote.getLoteId(), "Vivo")
            .orElse(BigDecimal.ZERO);
        
        if (pesoTotal.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalStateException("No hay animales vivos con peso en el lote");
        }
        
        BigDecimal costoTotal = calcularCostoTotalLote(lote);
        BigDecimal precioBreakEven = costoTotal.divide(pesoTotal, 2, RoundingMode.HALF_UP);
        
        BigDecimal multiplicador = BigDecimal.ONE.add(
            request.getRoiObjetivoPorcentaje().divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP)
        );
        BigDecimal ingresoNecesario = costoTotal.multiply(multiplicador);
        BigDecimal precioPorKg = ingresoNecesario.divide(pesoTotal, 2, RoundingMode.HALF_UP);
        
        return CalculadoraPrecioResponse.builder()
            .loteId(lote.getLoteId())
            .costoTotalInvertido(costoTotal)
            .pesoTotalDisponible(pesoTotal)
            .roiObjetivoPorcentaje(request.getRoiObjetivoPorcentaje())
            .precioMinimoBreakEven(precioBreakEven)
            .precioPorKgSugerido(precioPorKg)
            .ingresoTotalEsperado(ingresoNecesario)
            .gananciaNeta(ingresoNecesario.subtract(costoTotal))
            .build();
    }
    
    //Validaciones
    
    private Lote validarLote(Integer loteId) {
        Lote lote = loteRepository.findById(loteId)
            .orElseThrow(() -> new EntityNotFoundException("Lote no encontrado"));
        
        if (!"Activo".equals(lote.getEstado())) {
            throw new IllegalStateException("El lote no está activo");
        }
        return lote;
    }
    
    private void validarTipoVenta(RegistroVentaRequest request, String tipoLote, int cantidadAnimalesVivos) {
        if ("Engorde".equals(tipoLote) && !"Total".equals(request.getTipoAlcanceVenta())) {
            throw new IllegalStateException("Las ventas de lotes de Engorde deben ser de tipo Total");
        }
        
        if (!request.getTipoVenta().equals(tipoLote)) {
            throw new IllegalStateException("El tipo de venta debe coincidir con el tipo de lote");
        }
        
        if ("Total".equals(request.getTipoAlcanceVenta()) && 
            request.getAnimalesVendidosIds() != null && 
            !request.getAnimalesVendidosIds().isEmpty() &&
            request.getAnimalesVendidosIds().size() != cantidadAnimalesVivos) {
            throw new IllegalStateException("En venta total deben venderse todos los animales del lote");
        }
    }
    
    private List<Animal> obtenerAnimalesAVender(RegistroVentaRequest request, List<Animal> animalesVivos, Lote lote) {
        if ("Total".equals(request.getTipoAlcanceVenta())) {
            return animalesVivos;
        }
        
        if (request.getAnimalesVendidosIds() == null || request.getAnimalesVendidosIds().isEmpty()) {
            throw new IllegalArgumentException("Para venta parcial debe especificar los animales a vender");
        }
        
        List<Animal> animales = animalRepository.findAllById(request.getAnimalesVendidosIds());
        
        if (animales.size() != request.getAnimalesVendidosIds().size()) {
            throw new EntityNotFoundException("Algunos animales especificados no existen");
        }
        
        for (Animal animal : animales) {
            if (!animal.getLote().getLoteId().equals(lote.getLoteId()) || !"Vivo".equals(animal.getEstado())) {
                throw new IllegalStateException("Animal " + animal.getAnimalId() + " no válido para venta");
            }
        }
        
        return animales;
    }
    
    private BigDecimal calcularROI(BigDecimal costoTotal, BigDecimal ingresoVenta) {
        if (costoTotal.compareTo(BigDecimal.ZERO) == 0) return BigDecimal.ZERO;
        
        return ingresoVenta.subtract(costoTotal)
            .divide(costoTotal, 4, RoundingMode.HALF_UP)
            .multiply(new BigDecimal("100"))
            .setScale(2, RoundingMode.HALF_UP);
    }
    
    private void validarPerdidas(BigDecimal roiReal, BigDecimal roiObjetivo, BigDecimal costoTotal, BigDecimal pesoTotal) {
        if (roiObjetivo != null && roiObjetivo.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El ROI objetivo no puede ser negativo");
        }
    }
    
    private RegistroVenta crearYGuardarVenta(RegistroVentaRequest request, Lote lote, 
                                             BigDecimal roiReal, List<Animal> animalesAVender) {
        RegistroVenta venta = new RegistroVenta();
        venta.setLote(lote);
        venta.setAlcanceVenta(request.getTipoAlcanceVenta());
        venta.setTipoVenta(request.getTipoVenta());
        venta.setPesoTotalKg(request.getPesoTotalKg());
        venta.setPrecioPorKg(request.getPrecioPorKg());
        venta.setPrecioTotal(request.getPrecioTotal());
        venta.setRoiEstimado(roiReal);
        venta.setRoiMeta(request.getRoiMeta()); // ✅ GUARDAR EL ROI META
        venta.setClienteNombre(request.getClienteNombre());
        venta.setFechaVenta(LocalDateTime.now());
        venta.setAnimalesVendidosIds(animalesAVender.stream()
            .map(Animal::getAnimalId)
            .toArray(Integer[]::new));
        
        return ventaRepository.save(venta);
    }
    
    private BigDecimal calcularPesoTotalAnimales(List<Animal> animales) {
        BigDecimal pesoTotal = BigDecimal.ZERO;
        
        for (Animal animal : animales) {
            if (animal.getPeso() != null) {
                pesoTotal = pesoTotal.add(animal.getPeso());
            }
        }
        
        if (pesoTotal.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalStateException("Los animales seleccionados no tienen peso registrado");
        }
        
        return pesoTotal;
    }
    
    private void actualizarEstados(List<Animal> animales, Lote lote, boolean esVentaTotal) {
        animales.forEach(animal -> animal.setEstado("Vendido"));
        animalRepository.saveAll(animales);
        
        if (esVentaTotal) {
            lote.setEstado("Cerrado");
            loteRepository.save(lote);
        }
    }
    
    private VentaDetalleResponse construirVentaDetalle(RegistroVenta venta, Lote lote, 
                                                       List<Animal> animales, BigDecimal costoTotal, 
                                                       BigDecimal roiObjetivo) {
        BigDecimal roiReal = venta.getRoiEstimado();
        BigDecimal diferenciaRoi = roiObjetivo != null ? roiReal.subtract(roiObjetivo) : null;
        BigDecimal gananciaNeta = venta.getPrecioTotal().subtract(costoTotal);
        
        String advertencia = null;
        String recomendacion = null;
        
        if (roiReal.compareTo(BigDecimal.ZERO) < 0) {
            advertencia = String.format("Esta venta generó pérdidas del %.2f%%", roiReal.abs());
        } else if (diferenciaRoi != null) {
            if (diferenciaRoi.compareTo(new BigDecimal("-5.00")) < 0) {
                advertencia = String.format("No alcanzaste tu objetivo. Faltaron %.2f puntos", diferenciaRoi.abs());
            } else if (diferenciaRoi.compareTo(new BigDecimal("10.00")) > 0) {
                recomendacion = String.format("¡Excelente! Superaste tu objetivo por %.2f puntos", diferenciaRoi);
            }
        }
        
        List<DetalleAnimalVendido> animalesVendidosDetalle = construirDetalleAnimalesVendidos(animales, venta.getPrecioTotal());        
        
        return VentaDetalleResponse.builder()
            .ventaId(venta.getVentaId())
            .loteId(lote.getLoteId())
            .loteNombre(lote.getNombre())
            .especieNombre(lote.getEspecie().getNombre())
            .categoriaManejoNombre(lote.getCategoria().getNombre())
            .tipoAlcanceVenta(venta.getAlcanceVenta())
            .tipoVenta(venta.getTipoVenta())
            .pesoTotalKg(venta.getPesoTotalKg())
            .precioPorKg(venta.getPrecioPorKg())
            .precioTotal(venta.getPrecioTotal())
            .roiReal(roiReal)
            .roiObjetivo(roiObjetivo)
            .diferenciaRoi(diferenciaRoi)
            .clienteNombre(venta.getClienteNombre())
            .fechaVenta(venta.getFechaVenta())
            .cantidadAnimalesVendidos(animales.size())
            .animalesVendidos(animalesVendidosDetalle)
            .costoTotalInvertido(costoTotal)
            .gananciaNeta(gananciaNeta)
            .advertencia(advertencia)
            .recomendacion(recomendacion)
            .build();
    }
    
    private List<DetalleAnimalVendido> construirDetalleAnimalesVendidos(List<Animal> animales, BigDecimal precioTotalVenta) {
        BigDecimal pesoTotalVendido = animales.stream()
            .map(animal -> animal.getPeso() != null ? animal.getPeso() : BigDecimal.ZERO)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        if (pesoTotalVendido.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalStateException("El peso total de los animales vendidos es cero");
        }
        
        return animales.stream()
            .map(animal -> {
                BigDecimal pesoAnimal = animal.getPeso() != null ? animal.getPeso() : BigDecimal.ZERO;
                BigDecimal costoUnitario = pesoAnimal
                    .divide(pesoTotalVendido, 4, RoundingMode.HALF_UP)
                    .multiply(precioTotalVenta)
                    .setScale(2, RoundingMode.HALF_UP);
                
                return DetalleAnimalVendido.builder()
                    .idAnimal(animal.getAnimalId())
                    .codigoQr(animal.getCodigoQr())
                    .peso(pesoAnimal)
                    .costoUnitario(costoUnitario)
                    .build();
            })
            .collect(Collectors.toList());
    }
    
    private VentaListadoResponse construirVentaListado(RegistroVenta venta) {
        int animalesVivos = venta.getAnimalesVendidosIds().length;
        BigDecimal pesoPromedio = venta.getPesoTotalKg() != null && animalesVivos > 0
            ? venta.getPesoTotalKg().divide(new BigDecimal(animalesVivos), 2, RoundingMode.HALF_UP)
            : BigDecimal.ZERO;
        
        return VentaListadoResponse.builder()
            .ventaId(venta.getVentaId())
            .loteId(venta.getLote().getLoteId())
            .loteNombre(venta.getLote().getNombre())
            .especieNombre(venta.getLote().getEspecie().getNombre())
            .categoriaManejoNombre(venta.getLote().getCategoria().getNombre())
            .roiReal(venta.getRoiEstimado())
            .roiObjetivo(venta.getRoiMeta())
            .cumplioObjetivo(venta.getRoiMeta() != null ? venta.getRoiEstimado().compareTo(venta.getRoiMeta()) >= 0 : null)
            .cantidadAnimalesVivos(animalesVivos)
            .pesoPromedioLote(pesoPromedio)
            .sumaTotalPesos(venta.getPesoTotalKg())
            .costoTotalVenta(venta.getPrecioTotal())
            .precioSugeridoPorKg(venta.getPrecioPorKg())
            .fechaVenta(venta.getFechaVenta())
            .tipoVenta(venta.getTipoVenta())
            .tipoAlcanceVenta(venta.getAlcanceVenta())
            .build();
    }
    
    private LoteDisponibleVentaResponse construirLoteDisponible(Lote lote) {
        int animalesVivos = animalRepository.countByLoteAndEstado(lote, "Vivo");
        BigDecimal sumaPesos = animalRepository.sumPesoByLoteAndEstado(lote.getLoteId(), "Vivo")
            .orElse(BigDecimal.ZERO);
        
        BigDecimal pesoPromedio = animalesVivos > 0
            ? sumaPesos.divide(new BigDecimal(animalesVivos), 2, RoundingMode.HALF_UP)
            : BigDecimal.ZERO;
        
        BigDecimal costoTotal = calcularCostoTotalLote(lote);
        BigDecimal precioSugerido = sumaPesos.compareTo(BigDecimal.ZERO) > 0
            ? costoTotal.multiply(new BigDecimal("1.30")).divide(sumaPesos, 2, RoundingMode.HALF_UP)
            : BigDecimal.ZERO;
        
        Map<String, BigDecimal> preciosPorRoi = calcularPreciosPorRoi(costoTotal, sumaPesos);
        
        long diasProduccion = ChronoUnit.DAYS.between(lote.getFechaCreacion(), LocalDateTime.now());
        
        return LoteDisponibleVentaResponse.builder()
            .loteId(lote.getLoteId())
            .loteNombre(lote.getNombre())
            .especieNombre(lote.getEspecie().getNombre())
            .categoriaManejoNombre(lote.getCategoria().getNombre())
            .tipoLote(lote.getCategoria().getTipoLote())
            .cantidadAnimalesVivos(animalesVivos)
            .pesoPromedioLote(pesoPromedio)
            .sumaTotalPesos(sumaPesos)
            .costoTotalAcumulado(costoTotal)
            .precioSugeridoPorKgBase(precioSugerido)
            .roiEstimadoPorcentaje(new BigDecimal("30.00"))
            .preciosPorRoiObjetivo(preciosPorRoi)
            .fechaCreacionLote(lote.getFechaCreacion())
            .diasEnProduccion((int) diasProduccion)
            .build();
    }
    
    private Map<String, BigDecimal> calcularPreciosPorRoi(BigDecimal costoTotal, BigDecimal pesoTotal) {
        Map<String, BigDecimal> precios = new HashMap<>();
        if (pesoTotal.compareTo(BigDecimal.ZERO) == 0) return precios;
        
        int[] rois = {0, 15, 20, 25, 30, 40, 50};
        for (int roi : rois) {
            BigDecimal multiplicador = BigDecimal.ONE.add(
                new BigDecimal(roi).divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP)
            );
            BigDecimal precio = costoTotal.multiply(multiplicador).divide(pesoTotal, 2, RoundingMode.HALF_UP);
            precios.put(String.valueOf(roi), precio);
        }
        return precios;
    }
    
    private BigDecimal calcularCostoTotalLote(Lote lote) {
        BigDecimal costoCompras = compraRepository.sumCostoTotalByLote(lote.getLoteId()).orElse(BigDecimal.ZERO);
        BigDecimal costoAlimentacion = alimentacionRepository.sumCostoAlimentacionByLote(lote.getLoteId()).orElse(BigDecimal.ZERO);
        BigDecimal costoSanitario = sanitarioRepository.sumCostoSanitarioByLote(lote.getLoteId()).orElse(BigDecimal.ZERO);
        return costoCompras.add(costoAlimentacion).add(costoSanitario);
    }

}