package com.Cibertec.CattleFyApi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Cibertec.CattleFyApi.models.Animal;

public interface IAnimalRepository extends JpaRepository<Animal, Integer>{

    @Query("""
            SELECT COUNT(a) FROM Animal a
            WHERE a.estado = 'Vivo' AND
            a.lote.granja.granjaId = :granjaId
            """)
    Long contarAnimalesVivos(@Param("granjaId") Integer granjaId);

    @Query("""
            SELECT COUNT(a)
            FROM Animal a
            WHERE a.lote.loteId = :loteId AND a.estado = 'Vivo'
        """)
    Long contarAnimalesVivosPorLote(@Param("loteId") Integer loteId);
    
    List<Animal> findByLote_LoteIdAndEstado(Integer loteId, String estado);
    
    Optional<Animal> findByCodigoQr(String codigoQr);

    boolean existsByCodigoQr(String codigoQr);
}

