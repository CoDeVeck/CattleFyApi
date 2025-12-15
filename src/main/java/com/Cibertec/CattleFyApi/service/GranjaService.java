package com.Cibertec.CattleFyApi.service;

import com.Cibertec.CattleFyApi.dto.GranjaRequest;
import com.Cibertec.CattleFyApi.dto.GranjaResponse;
import com.Cibertec.CattleFyApi.models.Granja;
import com.Cibertec.CattleFyApi.models.Usuario;
import com.Cibertec.CattleFyApi.repository.IGranjaRepository;
import com.Cibertec.CattleFyApi.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
public class GranjaService {

    @Autowired
    private IGranjaRepository granjaRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Transactional
    public GranjaResponse registrarGranja(GranjaRequest request) throws IOException {

        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + request.getUsuarioId()));

        Granja granja = new Granja();
        granja.setUsuario(usuario);
        granja.setNombre(request.getNombre());
        granja.setDireccion(request.getDireccion());
        granja.setLatitud(request.getLatitud());
        granja.setLongitud(request.getLongitud());

        if (request.getImagen() != null && !request.getImagen().isEmpty()) {
            String imagenUrl = cloudinaryService.uploadImage(request.getImagen(), "granjas");
            granja.setImagenUrl(imagenUrl);
        }

        Granja granjaGuardada = granjaRepository.save(granja);

        return convertToDto(granjaGuardada);
    }

    /**
     * Convierte Granja a GranjaResponse.
     */
    private GranjaResponse convertToDto(Granja granja) {
        GranjaResponse dto = new GranjaResponse();

        dto.setGranjaId(granja.getGranjaId());

        if (granja.getUsuario() != null) {
            dto.setUsuarioId(granja.getUsuario().getUsuarioId());
        }

        dto.setNombre(granja.getNombre());
        dto.setDireccion(granja.getDireccion());
        dto.setLatitud(granja.getLatitud());
        dto.setLongitud(granja.getLongitud());
        dto.setImagenUrl(granja.getImagenUrl());

        return dto;
    }
}
