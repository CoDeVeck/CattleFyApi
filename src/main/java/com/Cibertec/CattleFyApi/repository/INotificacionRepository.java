package com.Cibertec.CattleFyApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Cibertec.CattleFyApi.models.Notificacion;

public interface INotificacionRepository  extends JpaRepository<Notificacion, Integer>{

	
	
	@Query("""
	        SELECT COUNT(n)
	        FROM Notificacion n
	        WHERE n.granja.granjaId = :granjaId
	          AND n.tipoNotificacion.codigo IN (
	            'MUERTE_ANIMAL',
	            'MORTALIDAD_ALTA',
	            'ENFERMERIA_SATURADA',
	            'ANIMAL_BAJO_PESO'
	          )
	          AND n.leida = false
	          AND n.descartada = false
	    """)
	 Long contarAlertasCriticasPorGranja(@Param("granjaId") Integer granjaId);
	
	
}
