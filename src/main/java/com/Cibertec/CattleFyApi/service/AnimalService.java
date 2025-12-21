package com.Cibertec.CattleFyApi.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.Cibertec.CattleFyApi.dto.AnimalHistPesaje;
import com.Cibertec.CattleFyApi.dto.AnimalHistSanitario;
import com.Cibertec.CattleFyApi.dto.AnimalHistTraslado;
import com.Cibertec.CattleFyApi.dto.AnimalMuerteReq;
import com.Cibertec.CattleFyApi.dto.AnimalPesoReq;
import com.Cibertec.CattleFyApi.dto.AnimalRequest;
import com.Cibertec.CattleFyApi.dto.AnimalResponse;
import com.Cibertec.CattleFyApi.dto.AnimalTrasReq;
import com.Cibertec.CattleFyApi.models.*;
import com.Cibertec.CattleFyApi.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import com.Cibertec.CattleFyApi.util.GeneradorQRS;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class AnimalService {

	@Autowired
	private IAnimalRepository animalRepository;

	@Autowired
	private ILoteRepository loteRepository;

	@Autowired
	private GeneradorQRS generadorQRS;

	@Autowired
	private IEspecieRepository especieRepository;

    @Autowired
    private CloudinaryService cloudinaryService;
    
    @Autowired
    private IRegistroPesoRepository pesoRepository;
    
    @Autowired
    private IRegistroMovilidadRepository movilidadRepository;
    
    @Autowired
    private IRegistroMuerteRepository muerteRepository;
    
    @Autowired
    private IRegistroSanitarioRepository sanitarioRepository;

    @Autowired
    private IRegistroCompraRepository registroCompraRepository;



	public Long totalAnimalesVivos(Integer granjaId) {
		Long animalesV = animalRepository.contarAnimalesVivos(granjaId);
		return animalesV;
	}

    @Transactional
    public AnimalResponse registrarAnimal(AnimalRequest req) {
        Animal animal = new Animal();
        Lote loteAsignado;
        Animal madreAsignada = new Animal();
        Especie especieAsignada;

        if ("Nacimiento".equalsIgnoreCase(req.getOrigen())) {

            if (req.getCodigoQrMadre() == null || req.getCodigoQrMadre().isEmpty()) {
                throw new IllegalArgumentException("El código QR de la madre es obligatorio para origen 'Nacimiento'.");
            }

            madreAsignada = animalRepository.findByCodigoQr(req.getCodigoQrMadre())
                    .orElseThrow(() -> new EntityNotFoundException("Animal madre no encontrado con QR: " + req.getCodigoQrMadre()));

            if (!"Vivo".equalsIgnoreCase(madreAsignada.getEstado())) {
                throw new IllegalArgumentException("La madre debe estar en estado 'Vivo' para registrar una cría.");
            }

            if (!"H".equalsIgnoreCase(madreAsignada.getSexo())) {
                throw new IllegalArgumentException("El animal madre debe ser hembra (sexo 'H').");
            }

            loteAsignado = madreAsignada.getLote();
            log.info("Lote heredado de la madre: {} (ID: {})", loteAsignado.getNombre(), loteAsignado.getLoteId());

            especieAsignada = madreAsignada.getEspecie();
            log.info("Especie heredada: {} (ID: {})", especieAsignada.getNombre(), especieAsignada.getEspecieId());

            log.info("Categoría de manejo heredada del lote: {} (ID: {})",
                    loteAsignado.getCategoria().getNombre(),
                    loteAsignado.getCategoria().getCategoriaId());

            animal.setPrecioCompra(BigDecimal.ZERO);
            animal.setMadre(madreAsignada);

            animal.setCodigoQr(generadorQRS.generarCodigoQrAnimal(madreAsignada.getEspecie().getNombre()));

            if (req.getFechaNacimiento() == null) {
                throw new IllegalArgumentException("La fecha de nacimiento es obligatoria para origen 'Nacimiento'.");
            }

        } else if ("Compra".equalsIgnoreCase(req.getOrigen())) {

            loteAsignado = loteRepository.findById(req.getIdLote())
                    .orElseThrow(() -> new EntityNotFoundException("Lote no encontrado con ID: " + req.getIdLote()));

            especieAsignada = especieRepository.findById(req.getIdEspecie())
                    .orElseThrow(() -> new EntityNotFoundException("Especie no encontrada con ID: " + req.getIdEspecie()));

            if (!loteAsignado.getEspecie().getEspecieId().equals(especieAsignada.getEspecieId())) {
                throw new IllegalArgumentException("La especie seleccionada no coincide con la especie del lote.");
            }

            if (req.getPrecioCompra() == null || req.getPrecioCompra() < 0) {
                throw new IllegalArgumentException("El precio de compra es obligatorio y debe ser mayor o igual a 0 para origen 'Compra'.");
            }

            if (req.getProveedor() == null || req.getProveedor().trim().isEmpty()) {
                throw new IllegalArgumentException("El proveedor es obligatorio para origen 'Compra'.");
            }

            animal.setPrecioCompra(new BigDecimal(req.getPrecioCompra()));

        } else {
            throw new IllegalArgumentException("El origen debe ser 'Nacimiento' o 'Compra'.");
        }

        Long animalesVivosEnLote = animalRepository.contarAnimalesVivosPorLote(loteAsignado.getLoteId());
        if (animalesVivosEnLote >= loteAsignado.getCapacidadMax()) {
            throw new IllegalArgumentException("El lote '" + loteAsignado.getNombre() + "' ha alcanzado su capacidad máxima (" + loteAsignado.getCapacidadMax() + " animales).");
        }

        Especie especie = especieRepository.findById(req.getIdEspecie()).orElseThrow(() ->
                new EntityNotFoundException("No se hallo el id de de especie" + req.getIdEspecie()));

        animal.setCodigoQr(generadorQRS.generarCodigoQrAnimal(especie.getNombre()));
        animal.setLote(loteAsignado);
        animal.setEspecie(especieAsignada);
        animal.setOrigen(req.getOrigen());
        animal.setFechaIngreso(LocalDateTime.now());
        animal.setFechaNacimiento(req.getFechaNacimiento());
        animal.setSexo(req.getSexo());
        animal.setPeso(BigDecimal.valueOf(req.getPeso()));
        animal.setEstado("Vivo");


        if (req.getImagen() == null || req.getImagen().isEmpty()) {
            throw new IllegalArgumentException("La imagen del animal es obligatoria.");
        }

        log.info("Subiendo imagen a Cloudinary...");
        String carpeta = obtenerCarpetaPorEspecie(especieAsignada.getEspecieId());
        String imagenUrl = cloudinaryService.uploadImage(req.getImagen(), carpeta);
        animal.setFotoUrl(imagenUrl);
        log.info("Imagen subida exitosamente: {}", imagenUrl);

        Animal nuevoAnimal = animalRepository.save(animal);
        log.info("Animal registrado exitosamente con ID: {} y QR: {}", nuevoAnimal.getAnimalId(), nuevoAnimal.getCodigoQr());

        // Si el origen es Compra, registrar en la tabla registro_compra
        if ("Compra".equalsIgnoreCase(req.getOrigen())) {
            RegistroCompra registroCompra = new RegistroCompra();
            registroCompra.setLote(loteAsignado);
            registroCompra.setProveedorNombre(req.getProveedor());
            registroCompra.setFechaCompra(LocalDateTime.now());
            registroCompra.setCantidadAnimales(1);
            registroCompra.setCostoTotal(new BigDecimal(req.getPrecioCompra()));
            registroCompra.setObservaciones("Ninguna");

            registroCompraRepository.save(registroCompra);
            log.info("Registro de compra creado exitosamente para el animal ID: {}", nuevoAnimal.getAnimalId());
        }

        return convertToDto(nuevoAnimal);
    }

	public List<AnimalResponse> listarAnimalesPorLote(Integer loteId) {
		List<Animal> animales = animalRepository.findByLote_LoteIdAndEstado(loteId, "Vivo");
		return animales.stream().map(this::convertToDto).collect(Collectors.toList());
	}

	public Animal obtenerAnimalPorId(Integer animalId) {
		Animal animal = animalRepository.findById(animalId)
				.orElseThrow(() -> new EntityNotFoundException("Animal no encontrado con ID: " + animalId));
		return animal;
	}

	public AnimalResponse obtenerAnimalPorQr(String codigoQr) {
		Animal animal = animalRepository.findByCodigoQr(codigoQr)
				.orElseThrow(() -> new EntityNotFoundException("Animal no encontrado con código QR: " + codigoQr));
 
		return convertToDto(animal);
	}

	@Transactional
	public AnimalResponse registrarPeso(AnimalPesoReq req) {
	    Animal animal = obtenerAnimalPorId(req.getIdAnimal());
	    
	    if (!"Vivo".equalsIgnoreCase(animal.getEstado())) {
	        throw new IllegalArgumentException("No se puede registrar peso de un animal que no está vivo.");
	    }
	    
	    RegistroPeso reg = new RegistroPeso();
	    BigDecimal pesoAnterior = animal.getPeso();
	    BigDecimal diferenciaPeso = req.getPeso().subtract(pesoAnterior);

	    reg.setAnimal(animal);
	    reg.setGananciaKg(diferenciaPeso);
	    reg.setPesoKg(req.getPeso());
	    reg.setFechaPesaje(LocalDateTime.now());
	    
	    pesoRepository.save(reg);

	    animal.setPeso(req.getPeso());
	    animalRepository.save(animal);

	    return convertToDto(animal);
	}

	@Transactional
	public AnimalResponse marcarBaja(AnimalMuerteReq req) {
	    Animal animal = obtenerAnimalPorId(req.getIdAnimal());
	    
	    if (!"Vivo".equalsIgnoreCase(animal.getEstado())) {
	        throw new IllegalArgumentException("El animal ya está marcado como '" + animal.getEstado() + "'.");
	    }
	    
	    RegistroMuerte reg = new RegistroMuerte();

	    reg.setAnimal(animal);
	    reg.setLote(animal.getLote());
	    reg.setCausaMuerte(req.getCausaMuerte());
	    reg.setFechaMuerte(LocalDateTime.now());
	    
	    muerteRepository.save(reg);

	    animal.setEstado("Muerto");
	    animalRepository.save(animal);

	    return convertToDto(animal);
	}

	@Transactional
	public AnimalResponse trasladarAnimal(AnimalTrasReq req) {
	    Animal animal = obtenerAnimalPorId(req.getIdAnimal());
	    
	    if (!"Vivo".equalsIgnoreCase(animal.getEstado())) {
	        throw new IllegalArgumentException("Solo se pueden trasladar animales que estén vivos.");
	    }
	    
	    Lote loteDestino = loteRepository
	            .findById(req.getIdLoteDestino())
	            .orElseThrow(() ->
	                new EntityNotFoundException(
	                    "No se encontró lote con el ID " + req.getIdLoteDestino()
	                )
	            );
	    
	    Lote loteOrigen = animal.getLote();
	    
	    if (loteOrigen.getLoteId().equals(loteDestino.getLoteId())) {
	        throw new IllegalArgumentException("El animal ya se encuentra en el lote de destino.");
	    }
	    
	    if (!loteDestino.getEspecie().getEspecieId().equals(animal.getEspecie().getEspecieId())) {
	        throw new IllegalArgumentException("El lote de destino no acepta animales de la especie '" + animal.getEspecie().getNombre() + "'.");
	    }
	    
	    Long animalesVivosEnLoteDestino = animalRepository.contarAnimalesVivosPorLote(loteDestino.getLoteId());
	    if (animalesVivosEnLoteDestino >= loteDestino.getCapacidadMax()) {
	        throw new IllegalArgumentException("El lote de destino '" + loteDestino.getNombre() + "' ha alcanzado su capacidad máxima.");
	    }
	    
	    RegistroMovilidad reg = new RegistroMovilidad();

	    reg.setAnimal(animal);
	    reg.setLoteOrigen(loteOrigen);
	    reg.setLoteDestino(loteDestino);
	    reg.setMotivo(req.getMotivo());
	    reg.setFechaMovimiento(LocalDateTime.now());
	    
	    movilidadRepository.save(reg);

	    animal.setLote(loteDestino);
	    animalRepository.save(animal);

	    return convertToDto(animal);
	}


	//HISTORIALES
	
	public List<AnimalHistPesaje> listarHistorialPesaje (Integer idAnimal) {
		List<RegistroPeso> historial = pesoRepository.findByAnimal_AnimalId(idAnimal);
		return historial.stream().map(this::convertirHistorialPeso).collect(Collectors.toList());
	}
	
	public List<AnimalHistSanitario> listarHistorialSanitario (Integer idAnimal) {
		List<RegistroSanitario> historial = sanitarioRepository.findByAnimal_AnimalId(idAnimal);
		return historial.stream().map(this::convertirHistorialSanitario).collect(Collectors.toList());
	}
	
	public List<AnimalHistTraslado> listarHistorialTraslado (Integer idAnimal) {
		List<RegistroMovilidad> historial = movilidadRepository.findByAnimal_AnimalId(idAnimal);
		return historial.stream().map(this::convertirHistorialTraslado).collect(Collectors.toList());
	}
	
	
	// Helper para la ubicacion de las carpetas
	private String obtenerCarpetaPorEspecie (Integer idEspecie) {
		return switch (idEspecie) {
		case 1 -> "CattelFy/vacuno";
		case 2 -> "CattelFy/porcino";
		case 3 -> "CattelFy/caprino";
		case 4 -> "CattelFy/pavino";
		default -> "CattelFy/avicola";
		};
	}

    /**
     * Convierte Animal a AnimalDTO.
     */
    private AnimalResponse convertToDto(Animal animal) {
        AnimalResponse dto = new AnimalResponse();
        dto.setIdAnimal(animal.getAnimalId());
        dto.setCodigoQr(animal.getCodigoQr());

        if (animal.getEspecie() != null) {
            dto.setIdEspecie(animal.getEspecie().getEspecieId());
            dto.setEspecie(animal.getEspecie().getNombre());
        }

        if (animal.getLote() != null) {
            dto.setIdLote(animal.getLote().getLoteId());
            dto.setLote(animal.getLote().getNombre());
        }

        if (animal.getMadre() != null) {
            dto.setIdMadre(animal.getMadre().getAnimalId());
            dto.setCodigoQrMadre(animal.getMadre().getCodigoQr());
        } else {
            dto.setIdMadre(0);
            dto.setCodigoQrMadre(null);
        }

        dto.setOrigen(animal.getOrigen());
        dto.setSexo(animal.getSexo());
        dto.setFechaIngreso(animal.getFechaIngreso() != null ? animal.getFechaIngreso() : null);
        dto.setFechaNacimiento(animal.getFechaNacimiento() != null ? animal.getFechaNacimiento() : null);

        if (animal.getFechaNacimiento() != null) {
            long dias = java.time.temporal.ChronoUnit.DAYS.between(animal.getFechaNacimiento(),
                    java.time.LocalDate.now());
            dto.setEdadEnDias((int) dias);
        } else {
            dto.setEdadEnDias(0);
        }

        dto.setPeso(animal.getPeso() != null ? animal.getPeso().doubleValue() : null);
        dto.setPrecioCompra(animal.getPrecioCompra() != null ? animal.getPrecioCompra().toString() : "0.00");
        dto.setEstado(animal.getEstado());
        dto.setFoto_url(animal.getFotoUrl());

        return dto;
    }
    
    private AnimalHistPesaje convertirHistorialPeso(RegistroPeso reg) {
        AnimalHistPesaje dto = new AnimalHistPesaje();
        dto.setIdAnimal(reg.getAnimal().getAnimalId());
        dto.setPeso(reg.getPesoKg() != null ? reg.getPesoKg(): null);
        dto.setFecha(reg.getFechaPesaje());
        dto.setGananciaPeso(reg.getGananciaKg() != null ? reg.getGananciaKg(): null);
        
        String dieta = null;
        if (reg.getAnimal() != null 
            && reg.getAnimal().getLote() != null 
            && reg.getAnimal().getLote().getCategoria() != null) {
            dieta = reg.getAnimal().getLote().getCategoria().getDietaRecomendada();
        }
        dto.setDieta(dieta);
        
        return dto;
    }
    
    private AnimalHistSanitario convertirHistorialSanitario(RegistroSanitario reg) {
        AnimalHistSanitario dto = new AnimalHistSanitario();
        dto.setIdAnimal(reg.getAnimal().getAnimalId());
        dto.setNombreTrat(reg.getNombreProducto());
        dto.setTipoProtocolo(reg.getProtocoloTipo());
        dto.setFecha(reg.getFechaAplicacion());
        dto.setDosis(reg.getCantidadDosis() != null ? reg.getCantidadDosis() : null);
        dto.setPrecio(reg.getCostoPorDosis() != null ? reg.getCostoPorDosis() : null);
        
        return dto;
    }
    
    private AnimalHistTraslado convertirHistorialTraslado(RegistroMovilidad reg) {
        AnimalHistTraslado dto = new AnimalHistTraslado();
        dto.setIdAnimal(reg.getAnimal().getAnimalId());
        dto.setMotivo(reg.getMotivo());
        
        if (reg.getLoteOrigen() != null) {
            dto.setLoteOrigen(reg.getLoteOrigen().getNombre());
        }
        
        if (reg.getLoteDestino() != null) {
            dto.setLoteTraslado(reg.getLoteDestino().getNombre());
        }
        
        if (reg.getAnimal() != null && reg.getAnimal().getEspecie() != null) {
            dto.setEspecie(reg.getAnimal().getEspecie().getNombre());
        }
        
        return dto;
    }
}
