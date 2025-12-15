package com.Cibertec.CattleFyApi.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.Cibertec.CattleFyApi.repository.IAnimalRepository;
import com.Cibertec.CattleFyApi.repository.ILoteRepository;

@Component
public class GeneradorQRS {

    @Autowired
    private ILoteRepository loteRepository;

    @Autowired
    private IAnimalRepository animalRepository;

    /**
     * Genera un código QR único para un Lote.
     * Prefijo: LOT-
     */
    public String generarCodigoQrLote() {
        String codigoQr;
        int contador = 1;
        do {
            codigoQr = "LOT-" + String.format("%06d", contador);
            contador++;
            if (contador > 999999) { 
                throw new RuntimeException("Error al generar código QR único para Lote.");
            }
        } while (loteRepository.existsByCodigoQr(codigoQr));
        return codigoQr;
    }

    /**
     * Genera un código QR único para un Animal.
     * Prefijo: ANI-
     */
    public String generarCodigoQrAnimal() {
        String codigoQr;
        int contador = 1;
        do {
            codigoQr = "ANI-" + String.format("%06d", contador);
            contador++;
            if (contador > 999999) {
                throw new RuntimeException("Error al generar código QR único para Animal.");
            }
        } while (animalRepository.existsByCodigoQr(codigoQr));
        return codigoQr;
    }
}
