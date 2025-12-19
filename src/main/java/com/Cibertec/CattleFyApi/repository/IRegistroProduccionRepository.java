package com.Cibertec.CattleFyApi.repository;

import com.Cibertec.CattleFyApi.dto.*;
import org.springframework.data.jpa.repository.JpaRepository;

import com.Cibertec.CattleFyApi.models.RegistroProduccion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PathVariable;

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

    @Query(value = """
             select
             	count(an.animal_id) as Total_Animales,
             	COALESCE(SUM(rp.cantidad),0) AS cantidad_de_leche
             from tb_granjas gr
             INNER Join tb_lotes lt ON lt.granja_id = gr.granja_id
             INNER Join tb_animales an ON an.lote_id = lt.lote_id
             INNER JOIN tb_registro_produccion rp ON rp.lote_id = lt.lote_id
             WHERE gr.granja_id = :granjaId
             AND rp.tipo_produccion = 'Leche'
             """,nativeQuery = true)
    List<ReporteTotalAnimalesLecheDto>totalAnimalesLeches(
            @PathVariable("granjaId")Integer granjaId
    );


    @Query(value = """
            SELECT
                SUM(CASE WHEN pr.tipo_produccion = 'Leche' THEN pr.cantidad ELSE 0 END) AS total_de_leche,
            	SUM(CASE WHEN pr.tipo_produccion = 'Huevos' THEN pr.cantidad ELSE 0 END) AS total_de_huevos,
            	AVG(CASE WHEN pr.tipo_produccion = 'Leche' THEN pr.cantidad ELSE 0 END) AS promedio_leches_por_dia,
            	AVG(CASE WHEN pr.tipo_produccion = 'Huevos' THEN pr.cantidad ELSE 0 END) AS promedio_huevos_por_dia,
            	COUNT(an.animal_id) AS total_nacimientos
            FROM tb_granjas gr
                INNER JOIN tb_lotes lt ON lt.granja_id = gr.granja_id
                INNER JOIN tb_animales an ON an.lote_id = lt.lote_id
                INNER JOIN tb_registro_produccion pr ON pr.lote_id = an.lote_id
            
            WHERE (:granja_id IS NULL OR gr.granja_id = :granja_id)
                AND (:lote_id IS NULL OR lt.lote_id = :lote_id)
                AND (:categoria_id IS NULL OR lt.categoria_id = :categoria_id)
                AND (:fecha_inicio IS NULL OR pr.fecha_registro >= TO_DATE(:fecha_inicio, 'YYYY-MM-DD'))
                AND (:fecha_fin IS NULL OR pr.fecha_registro <= TO_DATE(:fecha_fin, 'YYYY-MM-DD'))
            """, nativeQuery = true)
    List<ReporteProduccionReproduccion>ListaProduccionReproduccion(
            @Param("granja_id") Integer granja_id,
            @Param("lote_id") Integer lote_id,
            @Param("categoria_id") Integer categoria_id,
            @Param("fecha_inicio")String fecha_inicio,
            @Param("fecha_fin") String fecha_fin
    );

    @Query(value = """
            SELECT
                DATE(pr.fecha_registro) AS fecha,
                pr.tipo_produccion,
                SUM(pr.cantidad) AS cantidad_total
            
            FROM tb_granjas gr
            INNER JOIN tb_lotes lt ON lt.granja_id = gr.granja_id
            INNER JOIN tb_registro_produccion pr ON pr.lote_id = lt.lote_id
            
            WHERE (:granja_id IS NULL OR gr.granja_id = :granja_id)
                AND (:lote_id IS NULL OR lt.lote_id = :lote_id)
                AND (:fecha_inicio IS NULL OR pr.fecha_registro >= TO_DATE(:fecha_inicio, 'YYYY-MM-DD'))
                AND (:fecha_fin IS NULL OR pr.fecha_registro <= TO_DATE(:fecha_fin, 'YYYY-MM-DD'))
              AND pr.tipo_produccion IN ('Leche','Huevos')
            GROUP BY DATE(pr.fecha_registro), pr.tipo_produccion
            ORDER BY fecha ASC;
            """, nativeQuery = true)
    List<ReporteGrafico2> Grafico2List(
            @Param("granja_id") Integer granja_id,
            @Param("lote_id") Integer lote_id,
            @Param("fecha_inicio")String fecha_inicio,
            @Param("fecha_fin") String fecha_fin
    );


    @Query(value = """
            SELECT
            
                COALESCE((
                    SELECT SUM(vt.precio_total)
                    FROM tb_registro_venta vt
                    INNER JOIN tb_lotes lt2 ON lt2.lote_id = vt.lote_id
                    WHERE lt2.lote_id = lt.lote_id
            		AND (:fecha_inicio IS NULL OR lt.fecha_creacion >= TO_DATE(:fecha_inicio, 'YYYY-MM-DD'))
                    AND (:fecha_fin IS NULL OR lt.fecha_creacion <= TO_DATE(:fecha_fin, 'YYYY-MM-DD'))
                ), 0) AS ingresos,
            
            
                COALESCE((
                    SELECT SUM(rc.costo_total)
                    FROM tb_registro_compra rc
                    WHERE rc.lote_id = lt.lote_id
            		AND (:fecha_inicio IS NULL OR lt.fecha_creacion >= TO_DATE(:fecha_inicio, 'YYYY-MM-DD'))
                    AND (:fecha_fin IS NULL OR lt.fecha_creacion <= TO_DATE(:fecha_fin, 'YYYY-MM-DD'))
                ), 0) AS costo_compras,
            
            
                COALESCE((
                    SELECT SUM(ra.cantidad_kg * ra.costo_por_kg)
                    FROM tb_registro_alimentacion ra
                    WHERE ra.lote_id = lt.lote_id
            		AND (:fecha_inicio IS NULL OR lt.fecha_creacion >= TO_DATE(:fecha_inicio, 'YYYY-MM-DD'))
                    AND (:fecha_fin IS NULL OR lt.fecha_creacion <= TO_DATE(:fecha_fin, 'YYYY-MM-DD'))
                ), 0) AS costo_alimentacion,
            
            
                COALESCE((
                    SELECT SUM(
                        CASE
                            WHEN rs.tipo_aplicacion = 'Individual'
                                THEN rs.costo_por_dosis * rs.cantidad_dosis
                            WHEN rs.tipo_aplicacion = 'Masivo'
                                THEN rs.costo_por_dosis * rs.cantidad_dosis * rs.animales_tratados
                        END
                    )
                    FROM tb_registro_sanitario rs
                    WHERE rs.lote_id = lt.lote_id
            		AND (:fecha_inicio IS NULL OR lt.fecha_creacion >= TO_DATE(:fecha_inicio, 'YYYY-MM-DD'))
                    AND (:fecha_fin IS NULL OR lt.fecha_creacion <= TO_DATE(:fecha_fin, 'YYYY-MM-DD'))
                ), 0) AS costo_sanitario
            
            FROM tb_granjas gr
            INNER JOIN tb_lotes lt ON lt.granja_id = gr.granja_id
            
            WHERE (:granja_id IS NULL OR gr.granja_id = :granja_id)
                AND (:lote_id IS NULL OR lt.lote_id = :lote_id)
                AND (:fecha_inicio IS NULL OR lt.fecha_creacion >= TO_DATE(:fecha_inicio, 'YYYY-MM-DD'))
                AND (:fecha_fin IS NULL OR lt.fecha_creacion <= TO_DATE(:fecha_fin, 'YYYY-MM-DD'))
            
            
            """, nativeQuery = true)
    List<ReporteFinancieroDTO>reporteFinanciero(
            @Param("granja_id") Integer granja_id,
            @Param("lote_id") Integer lote_id,
            @Param("fecha_inicio")String fecha_inicio,
            @Param("fecha_fin") String fecha_fin
    );

}
