package com.Cibertec.CattleFyApi.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.Cibertec.CattleFyApi.repository.IAnimalRepository;
import org.springframework.stereotype.Service;

@Service
public class AnimalService {

	 @Autowired
	 IAnimalRepository animalRepository; 
}
