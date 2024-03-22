package com.alexlopes.projetofinal.service;

import com.alexlopes.projetofinal.model.WhatsAppAPI;
import com.alexlopes.projetofinal.repository.WhatsAppAPIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WhatsAppAPIService {
    
    @Autowired
    private WhatsAppAPIRepository whatsappAPIRepository;

    
    public WhatsAppAPI salvar(WhatsAppAPI w){
        w.setId(null);
        whatsappAPIRepository.save(w);
        return w;
    }
    
    public WhatsAppAPI mostrarDadosId(Integer id){
        return whatsappAPIRepository.findById(id).orElseThrow();
    }
    
}
