package com.Cibertec.CattleFyApi.service;

import com.Cibertec.CattleFyApi.repository.ITipoNotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipoNotificacionService {

    @Autowired
    ITipoNotificacionRepository tipoNotificacionRepository;
}
