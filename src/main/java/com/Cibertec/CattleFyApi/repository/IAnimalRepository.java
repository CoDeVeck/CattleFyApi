package com.Cibertec.CattleFyApi.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Cibertec.CattleFyApi.models.Animal;
import com.Cibertec.CattleFyApi.models.Lote;

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
    
    List<Animal> findByLoteAndEstado(Lote lote, String estado);
    
    Integer countByLoteAndEstado(Lote lote, String estado);
    
    Optional<Animal> findByCodigoQr(String codigoQr);

    List<Animal> findByLote_LoteId(Integer loteId);

    boolean existsByCodigoQr(String codigoQr);
    
    @Query("SELECT COALESCE(SUM(a.peso), 0) FROM Animal a " +
            "WHERE a.lote.loteId = :loteId " +
            "AND a.estado = :estado")
     Optional<BigDecimal> sumPesoByLoteAndEstado(
         @Param("loteId") Integer loteId,
         @Param("estado") String estado
     );
     
     @Query("SELECT COALESCE(AVG(a.peso), 0) FROM Animal a " +
            "WHERE a.lote.loteId = :loteId " +
            "AND a.estado = 'Vivo'")
     BigDecimal avgPesoByLoteIdAndEstadoVivo(@Param("loteId") Integer loteId);
}

