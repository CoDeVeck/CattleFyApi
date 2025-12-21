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
     * Formato: QR_LOTE_XXX (donde XXX son 3 dígitos)
     */
    public String generarCodigoQrLote() {
        String codigoQr;
        int contador = 1;
        do {
            codigoQr = "QR_LOTE_" + String.format("%03d", contador);
            contador++;
            if (contador > 999) {
                throw new RuntimeException("Error al generar código QR único para Lote.");
            }
        } while (loteRepository.existsByCodigoQr(codigoQr));
        return codigoQr;
    }

    /**
     * Genera un código QR único para un Animal basado en su especie.
     * Formato: QR_XXX_YYY (donde XXX son 3 letras de la especie y YYY son 3 dígitos)
     *
     * @param especie Nombre de la especie del animal (ej: "BOVINO", "OVINO", "PORCINO")
     * @return Código QR único en formato QR_BOV_013
     */
    public String generarCodigoQrAnimal(String especie) {
        if (especie == null || especie.trim().isEmpty()) {
            throw new IllegalArgumentException("La especie no puede ser nula o vacía");
        }

        // Extraer las primeras 3 letras de la especie en mayúsculas
        String prefijoEspecie = especie.trim().toUpperCase().substring(0, Math.min(3, especie.length()));

        // Si la especie tiene menos de 3 caracteres, completar con guiones bajos
        while (prefijoEspecie.length() < 3) {
            prefijoEspecie += "_";
        }

        String codigoQr;
        int contador = 1;
        do {
            codigoQr = "QR_" + prefijoEspecie + "_" + String.format("%03d", contador);
            contador++;
            if (contador > 999) {
                throw new RuntimeException("Error al generar código QR único para Animal de especie: " + especie);
            }
        } while (animalRepository.existsByCodigoQr(codigoQr));

        return codigoQr;
    }

    /**
     * Versión sobrecargada que mantiene compatibilidad con código existente.
     * Genera un código QR genérico si no se especifica especie.
     */
    public String generarCodigoQrAnimal() {
        return generarCodigoQrAnimal("ANIMAL");
    }
}