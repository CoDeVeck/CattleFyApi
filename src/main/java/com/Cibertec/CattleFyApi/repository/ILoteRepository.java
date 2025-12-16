package com.Cibertec.CattleFyApi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Cibertec.CattleFyApi.models.Lote;

public interface ILoteRepository  extends JpaRepository<Lote, Integer>{

    @Query("""
            SELECT COUNT(l) FROM Lote l
            WHERE l.estado = 'Activo' AND
            l.granja.usuario.usuarioId = :usuarioId
            """)
    Long contarLotesActivos(@Param("granjaId") Integer granjaId);
    
    Optional<Lote> findByCodigoQr(String codigoQr);
    
    boolean existsByCodigoQr(String codigoQr);
    
    @Query("""
        SELECT l FROM Lote l WHERE l.granja.granjaId = :granjaId
        AND (:especieId IS NULL OR l.especie.especieId = :especieId)
        AND (:tipoLote IS NULL OR l.categoria.tipoLote = :tipoLote)
        AND l.estado != 'Inactivo'
    """)
    List<Lote> findLotesByFilters(
        @Param("granjaId") Integer granjaId,
        @Param("especieId") Integer especieId,
        @Param("tipoLote") String tipoLote
    );
}
