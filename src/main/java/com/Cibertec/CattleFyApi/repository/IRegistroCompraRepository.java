package com.Cibertec.CattleFyApi.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Cibertec.CattleFyApi.models.RegistroCompra;

public interface IRegistroCompraRepository  extends JpaRepository<RegistroCompra, Integer>{

	@Query("SELECT COALESCE(SUM(c.costoTotal), 0) FROM RegistroCompra c " +
	           "WHERE c.lote.loteId = :loteId")
	Optional<BigDecimal> sumCostoTotalByLote(@Param("loteId") Integer loteId);
}
