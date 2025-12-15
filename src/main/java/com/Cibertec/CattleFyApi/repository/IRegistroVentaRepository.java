package com.Cibertec.CattleFyApi.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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


}
