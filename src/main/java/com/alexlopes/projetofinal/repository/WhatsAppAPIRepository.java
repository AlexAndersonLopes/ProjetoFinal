package com.alexlopes.projetofinal.repository;

import com.alexlopes.projetofinal.model.WhatsAppAPI;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WhatsAppAPIRepository  extends JpaRepository<WhatsAppAPI, Integer> {
    
}
