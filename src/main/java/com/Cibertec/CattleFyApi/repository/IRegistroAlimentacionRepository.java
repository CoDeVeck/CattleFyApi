package com.Cibertec.CattleFyApi.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Cibertec.CattleFyApi.models.RegistroAlimentacion;

public interface IRegistroAlimentacionRepository  extends JpaRepository<RegistroAlimentacion, Integer>{
	List<RegistroAlimentacion> findByLote_LoteId(Integer loteId);

	@Query("SELECT COALESCE(SUM(a.cantidadKg * a.costoPorKg), 0) " +
	           "FROM RegistroAlimentacion a " +
	           "WHERE a.lote.loteId = :loteId")
	    Optional<BigDecimal> sumCostoAlimentacionByLote(@Param("loteId") Integer loteId);
	
}
