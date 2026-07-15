package com.tutorial.resourceserver.service;

import com.tutorial.resourceserver.entity.ClientMou9f;
import com.tutorial.resourceserver.repository.ClientMou9fRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SershCategory {
    @Autowired
    ClientMou9fRepository clientMou9fRepository;

    public List<ClientMou9f> searchClient(String activite){
       return   clientMou9fRepository.findByActivite(activite);
    }
}
