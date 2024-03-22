package com.alexlopes.projetofinal.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "whatsappapi")
public class WhatsAppAPI {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Size(max = 200)
    @Column(name = "usertokenid")
    private String usertokenid;
    
    @Size(max = 200)
    @Column(name = "instanceid")
    private String instanceid;
    
    @Size(max = 200)
    @Column(name = "instancetoken")
    private String instancetoken;
    
}
