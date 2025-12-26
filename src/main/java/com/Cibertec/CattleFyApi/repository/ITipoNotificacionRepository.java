package com.Cibertec.CattleFyApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Cibertec.CattleFyApi.models.TipoNotificacion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ITipoNotificacionRepository  extends JpaRepository<TipoNotificacion, Integer>{
    @Query("""
        SELECT COUNT(n) 
        FROM Notificacion n 
        WHERE n.granja.granjaId = :granjaId 
        AND n.leida = false 
        AND n.descartada = false
    """)
    Long contarAlertasCriticas(@Param("granjaId") Long granjaId);
}
