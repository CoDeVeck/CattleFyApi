package com.Cibertec.CattleFyApi.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.Cibertec.CattleFyApi.dto.AnimalRequest;
import com.Cibertec.CattleFyApi.dto.AnimalResponse;
import com.Cibertec.CattleFyApi.models.CategoriaManejo;
import com.Cibertec.CattleFyApi.models.Especie;
import com.Cibertec.CattleFyApi.repository.ICategoriaManejoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import com.Cibertec.CattleFyApi.models.Animal;
import com.Cibertec.CattleFyApi.models.Lote;
import com.Cibertec.CattleFyApi.repository.IAnimalRepository;
import com.Cibertec.CattleFyApi.repository.IEspecieRepository;
import com.Cibertec.CattleFyApi.repository.ILoteRepository;
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
    private ICategoriaManejoRepository categoriaManejoRepository;



	public Long totalAnimalesVivos(Integer granjaId) {
		Long animalesV = animalRepository.contarAnimalesVivos(granjaId);
		return animalesV;
	}

	// Utilizar al registrar nuevos animales, al trasladarlos, al venderlos y al
	// morir
	@Transactional
	public void actualizarEstadoLote(Integer loteId) {

		Long animalesVivos = animalRepository.contarAnimalesVivosPorLote(loteId);

		Lote lote = loteRepository.findById(loteId).get();

		if (animalesVivos > 0) {
			lote.setEstado("Activo");
		} else {
			lote.setEstado("Inactivo");
		}

		loteRepository.save(lote);
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
		dto.setFechaIngreso(animal.getFechaIngreso() != null ? animal.getFechaIngreso().toString() : null);
		dto.setFechaNacimiento(animal.getFechaNacimiento() != null ? animal.getFechaNacimiento().toString() : null);

		if (animal.getFechaNacimiento() != null) {
			long dias = java.time.temporal.ChronoUnit.DAYS.between(animal.getFechaNacimiento().toLocalDate(),
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

    @Transactional
    public AnimalResponse registrarAnimal(AnimalRequest req) {
        Animal animal = new Animal();
        Lote loteAsignado;
        Animal madreAsignada = new Animal();
        Especie especieAsignada;

        if ("Nacimiento".equalsIgnoreCase(req.getOrigen())) {

            madreAsignada = animalRepository.findByCodigoQr(req.getCodigoQrMadre())
                    .orElseThrow(() -> new EntityNotFoundException("Animal madre no encontrado con QR: " + req.getCodigoQrMadre()));

            loteAsignado = madreAsignada.getLote();

            especieAsignada = loteAsignado.getEspecie();

            animal.setPrecioCompra(BigDecimal.ZERO);

            if (req.getFechaNacimiento() == null) {
                throw new IllegalArgumentException("La Fecha de Nacimiento es obligatoria para origen 'Nacimiento'.");
            }
            animal.setMadre(madreAsignada);

        } else if ("Compra".equalsIgnoreCase(req.getOrigen())) {

            loteAsignado = loteRepository.findById(req.getIdLote())
                    .orElseThrow(() -> new EntityNotFoundException("Lote de compra no encontrado con ID: " + req.getIdLote()));

            especieAsignada = especieRepository.findById(req.getIdEspecie())
                    .orElseThrow(() -> new EntityNotFoundException("Especie no encontrada con ID: " + req.getIdEspecie()));

            if (req.getPrecioCompra() == null || req.getPrecioCompra() < 0) {
                throw new IllegalArgumentException("El Precio de Compra es obligatorio y debe ser mayor o igual a 0 para origen 'Compra'.");
            }
            animal.setPrecioCompra(new BigDecimal(req.getPrecioCompra()));

        } else {
            throw new IllegalArgumentException("El origen debe ser 'Nacimiento' o 'Compra'.");
        }

        Long animalesVivosEnLote = animalRepository.contarAnimalesVivosPorLote(loteAsignado.getLoteId());
        if (animalesVivosEnLote >= loteAsignado.getCapacidadMax()) {
            throw new EntityNotFoundException("El lote " + loteAsignado.getNombre() + " ha superado su capacidad máxima.");
        }

        animal.setCodigoQr(generadorQRS.generarCodigoQrAnimal());

        animal.setLote(loteAsignado);
        animal.setEspecie(especieAsignada);

        animal.setOrigen(req.getOrigen());
        animal.setFechaIngreso(LocalDateTime.now());
        animal.setFechaNacimiento(req.getFechaNacimiento());
        animal.setSexo(req.getSexo());
        animal.setPeso(BigDecimal.valueOf(req.getPeso()));
        animal.setEstado("Vivo");

        log.info("Subiendo imagen a Cloudinary...");
        String carpeta = obtenerCarpetaPorEspecie(especieAsignada.getEspecieId());
        String imagenUrl = cloudinaryService.uploadImage(req.getImagen(), carpeta);
        animal.setFotoUrl(imagenUrl);
        log.info("Imagen subida: {}", imagenUrl);

        Animal nuevoAnimal = animalRepository.save(animal);

        actualizarEstadoLote(nuevoAnimal.getLote().getLoteId());
        return convertToDto(nuevoAnimal);
    }

	public List<AnimalResponse> listarAnimalesPorLote(Integer loteId) {
		List<Animal> animales = animalRepository.findByLote_LoteIdAndEstado(loteId, "Vivo");
		return animales.stream().map(this::convertToDto).collect(Collectors.toList());
	}

	public AnimalResponse obtenerAnimalPorId(Integer animalId) {
		Animal animal = animalRepository.findById(animalId)
				.orElseThrow(() -> new EntityNotFoundException("Animal no encontrado con ID: " + animalId));

		return convertToDto(animal);
	}

	public AnimalResponse obtenerAnimalPorQr(String codigoQr) {
		Animal animal = animalRepository.findByCodigoQr(codigoQr)
				.orElseThrow(() -> new EntityNotFoundException("Animal no encontrado con código QR: " + codigoQr));

		return convertToDto(animal);
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
}
