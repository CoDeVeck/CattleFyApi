package com.Cibertec.CattleFyApi.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Cibertec.CattleFyApi.models.RegistroVenta;

public interface IRegistroVentaRepository  extends JpaRepository<RegistroVenta, Integer>{
	
	@Query("""
		    SELECT COALESCE(SUM(v.precioTotal), 0)
		    FROM RegistroVenta v
		    WHERE v.fechaVenta >= :inicio
		      AND v.fechaVenta < :fin
		""")
	BigDecimal sumarVentasPorRango(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);

	@Query("""
		    SELECT v FROM RegistroVenta v
		    WHERE v.lote.granja.granjaId = :granjaId
		      AND (:tipoVenta IS NULL OR v.tipoVenta = :tipoVenta)
		      AND v.fechaVenta >= COALESCE(:fechaDesde, v.fechaVenta)
		      AND v.fechaVenta <= COALESCE(:fechaHasta, v.fechaVenta)
		""")
		List<RegistroVenta> findByGranjaWithFilters(
		    @Param("granjaId") Integer granjaId,
		    @Param("tipoVenta") String tipoVenta,
		    @Param("fechaDesde") LocalDateTime fechaDesde,
		    @Param("fechaHasta") LocalDateTime fechaHasta
		);

	@Query("""
		    SELECT COALESCE(AVG(v.roiEstimado), 0)
		    FROM RegistroVenta v
		    WHERE v.lote.granja.granjaId = :granjaId
		      AND EXTRACT(YEAR FROM v.fechaVenta) = :anio
		      AND EXTRACT(MONTH FROM v.fechaVenta) = :mes
		""")
		BigDecimal avgRoiByGranjaAndMes(
		    @Param("granjaId") Integer granjaId,
		    @Param("anio") Integer anio,
		    @Param("mes") Integer mes
		);
	    
	@Query("SELECT v FROM RegistroVenta v " +
	           "JOIN FETCH v.lote l " +
	           "WHERE l.loteId = :loteId " +
	           "ORDER BY v.fechaVenta DESC")
	List<RegistroVenta> findByLoteId(@Param("loteId") Integer loteId);
	    
	@Query("SELECT COALESCE(SUM(v.precioTotal), 0) FROM RegistroVenta v " +
	           "WHERE v.lote.granja.granjaId = :granjaId " +
	           "AND v.fechaVenta BETWEEN :fechaDesde AND :fechaHasta")
	    BigDecimal sumIngresosByGranjaAndPeriodo(
	        @Param("granjaId") Integer granjaId,
	        @Param("fechaDesde") LocalDateTime fechaDesde,
	        @Param("fechaHasta") LocalDateTime fechaHasta
	    );
	    
	@Query("SELECT COUNT(v) FROM RegistroVenta v " +
	           "WHERE v.lote.granja.granjaId = :granjaId " +
	           "AND EXTRACT(YEAR FROM v.fechaVenta) = :anio " +
	           "AND EXTRACT(MONTH FROM v.fechaVenta) = :mes")
	Long countVentasByGranjaAndMes(
	        @Param("granjaId") Integer granjaId,
	        @Param("anio") Integer anio,
	        @Param("mes") Integer mes
	    );
}
