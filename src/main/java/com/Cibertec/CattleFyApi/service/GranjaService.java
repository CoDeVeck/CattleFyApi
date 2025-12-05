package com.Cibertec.CattleFyApi.service;

import com.Cibertec.CattleFyApi.repository.IGranjaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GranjaService {

    @Autowired
    IGranjaRepository granjaRepository;

}
