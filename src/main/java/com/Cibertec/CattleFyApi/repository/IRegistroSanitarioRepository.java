package com.Cibertec.CattleFyApi.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Cibertec.CattleFyApi.models.RegistroSanitario;

public interface IRegistroSanitarioRepository extends JpaRepository<RegistroSanitario, Integer>{

    List<RegistroSanitario> findByAnimal_AnimalId(Integer AnimalId);
    List<RegistroSanitario> findByLote_LoteId(Integer loteId);

    @Query("SELECT COALESCE(SUM(s.costoPorDosis * s.cantidadDosis), 0) " +
            "FROM RegistroSanitario s " +
            "WHERE s.lote.loteId = :loteId")
     Optional<BigDecimal> sumCostoSanitarioByLote(@Param("loteId") Integer loteId);

}
