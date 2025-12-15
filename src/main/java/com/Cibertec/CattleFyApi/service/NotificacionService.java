package com.Cibertec.CattleFyApi.service;

import com.Cibertec.CattleFyApi.repository.INotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificacionService {

    @Autowired
    INotificacionRepository notificacionRepository;
    
    public Long totalAlertasCriticas(Integer granjaId) {
    	
    	Long alertas = notificacionRepository.contarAlertasCriticasPorGranja(granjaId);
    	return alertas;
    }
    
    
}
