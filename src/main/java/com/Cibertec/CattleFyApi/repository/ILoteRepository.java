package com.Cibertec.CattleFyApi.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.Cibertec.CattleFyApi.dto.LoteSimpleDTO;
import com.Cibertec.CattleFyApi.dto.ReporteTotalAnimalesLecheDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Cibertec.CattleFyApi.models.Lote;
import org.springframework.web.bind.annotation.PathVariable;

public interface ILoteRepository  extends JpaRepository<Lote, Integer>{
    @Query("""
        SELECT COUNT(l) 
        FROM Lote l 
        WHERE l.granja.granjaId = :granjaId 
        AND l.estado = 'Activo'
    """)
    Long contarLotesActivos(@Param("granjaId") Long granjaId);

    @Query("""
            SELECT COUNT(l) FROM Lote l
            WHERE l.estado = 'Activo' AND
            l.granja.granjaId = :granjaId
            """)
    Long contarLotesActivos(@Param("granjaId") Integer granjaId);
    
    Optional<Lote> findByCodigoQr(String codigoQr);
    
    boolean existsByCodigoQr(String codigoQr);

    @Query("""
        SELECT l FROM Lote l
        WHERE (:granjaId IS NULL OR l.granja.granjaId = :granjaId)
        AND (:especieId IS NULL OR l.especie.especieId = :especieId)
        AND (:tipoLote IS NULL OR l.categoria.tipoLote = :tipoLote)
        AND l.estado != 'Inactivo'
    """)
    List<Lote> findLotesByFilters(
        @Param("granjaId") Integer granjaId,
        @Param("especieId") Integer especieId,
        @Param("tipoLote") String tipoLote
    );
    
    
    @Query("SELECT l FROM Lote l " +
            "JOIN FETCH l.especie e " +
            "JOIN FETCH l.categoria c " +
            "WHERE l.granja.granjaId = :granjaId " +
            "AND l.estado = :estado " +
            "AND c.tipoLote = :tipoLote " +
            "ORDER BY l.fechaCreacion DESC")
     List<Lote> findByGranjaIdAndEstadoAndTipoLote(
         @Param("granjaId") Integer granjaId,
         @Param("estado") String estado,
         @Param("tipoLote") String tipoLote
     );
     
     List<Lote> findByGranjaGranjaIdAndEstado(Integer granjaId, String estado);


     @Query(value = """
             Select
             	lt.lote_id,
             	lt.nombre
             FROM tb_granjas gr
             Inner Join tb_lotes lt ON lt.granja_id = gr.granja_id
             where gr.granja_id = :granjaId
             
             """, nativeQuery = true)
    List<LoteSimpleDTO> listaLotePorGranja(
            @Param("granjaId") Integer granjaId);




}
