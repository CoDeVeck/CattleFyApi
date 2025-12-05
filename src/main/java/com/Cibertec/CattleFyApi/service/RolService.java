package com.Cibertec.CattleFyApi.service;

import com.Cibertec.CattleFyApi.repository.IRolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolService {

    @Autowired
    IRolRepository rolRepository;
}
