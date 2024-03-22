package com.alexlopes.projetofinal.controller;

import com.alexlopes.projetofinal.model.WhatsAppAPI;
import com.alexlopes.projetofinal.service.WhatsAppAPIService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/whatsapp")
public class WhatsAppAPIController {
    
     @Autowired
    private WhatsAppAPIService whatsappAPIService;

    @PostMapping("/create")
    public ResponseEntity<WhatsAppAPI> create(@Valid @RequestBody WhatsAppAPI whatsappAPI) {
        WhatsAppAPI savedWhatsAppAPI = whatsappAPIService.salvar(whatsappAPI);
        return new ResponseEntity<>(savedWhatsAppAPI, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WhatsAppAPI> getById(@PathVariable Long id) {
        WhatsAppAPI whatsappAPI = whatsappAPIService.mostrarDadosId(Integer.SIZE);
        if (whatsappAPI != null) {
            return new ResponseEntity<>(whatsappAPI, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
}
