package com.Cibertec.CattleFyApi.repository;

import com.Cibertec.CattleFyApi.dto.ReporteGrafico1;
import com.Cibertec.CattleFyApi.dto.ReporteProduccionEngordeDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import com.Cibertec.CattleFyApi.models.RegistroProduccion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.time.LocalDate;
import java.util.List;
public interface IRegistroProduccionRepository  extends JpaRepository<RegistroProduccion, Integer>{

    //REPORTE PRODUCCION

    @Query(value="""
            SELECT
                AVG(ps.peso_kg) AS peso_promedio,
            	SUM(ps.peso_kg) AS ganancia_KG
            FROM tb_granjas gr
            INNER JOIN tb_lotes lt ON lt.granja_id = gr.granja_id
            INNER JOIN tb_animales an ON an.lote_id = lt.lote_id
            INNER JOIN tb_registro_peso ps ON ps.animal_id = an.animal_id
            WHERE (:granja_id IS NULL OR gr.granja_id = :granja_id)
            AND (:lote_id IS NULL OR lt.lote_id = :lote_id)
            AND (:categoria_id IS NULL OR lt.categoria_id = :categoria_id)
            AND (:fecha_inicio IS NULL OR ps.fecha_pesaje >= TO_DATE(:fecha_inicio, 'YYYY-MM-DD'))
            AND (:fecha_fin IS NULL OR ps.fecha_pesaje <= TO_DATE(:fecha_fin, 'YYYY-MM-DD'))
            """, nativeQuery = true)
    List<ReporteProduccionEngordeDTO> listaDeProduccion(
            @Param("granja_id") Integer granja_id,
            @Param("lote_id") Integer lote_id,
            @Param("categoria_id") Integer categoria_id,
            @Param("fecha_inicio")String fecha_inicio,
            @Param("fecha_fin") String fecha_fin

    );

    @Query(value="""
            SELECT
                DATE(ps.fecha_pesaje) AS fecha,
                ps.peso_kg
            FROM tb_granjas gr
                INNER JOIN tb_lotes lt ON lt.granja_id = gr.granja_id
                INNER JOIN tb_animales an ON an.lote_id = lt.lote_id
                INNER JOIN tb_registro_peso ps ON ps.animal_id = an.animal_id
            WHERE (:granja_id IS NULL OR gr.granja_id = :granja_id)
                AND (:lote_id IS NULL OR lt.lote_id = :lote_id)
                AND (:categoria_id IS NULL OR lt.categoria_id = :categoria_id)
                AND (:fecha_inicio IS NULL OR ps.fecha_pesaje >= TO_DATE(:fecha_inicio, 'YYYY-MM-DD'))
                AND (:fecha_fin IS NULL OR ps.fecha_pesaje <= TO_DATE(:fecha_fin, 'YYYY-MM-DD'))
            """, nativeQuery = true)
    List<ReporteGrafico1> graficoUnoReporteProduccio(
            @Param("granja_id") Integer granja_id,
            @Param("lote_id") Integer lote_id,
            @Param("categoria_id") Integer categoria_id,
            @Param("fecha_inicio")String fecha_inicio,
            @Param("fecha_fin") String fecha_fin
    );
}
