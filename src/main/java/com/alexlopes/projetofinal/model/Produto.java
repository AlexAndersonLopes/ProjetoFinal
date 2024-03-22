package com.alexlopes.projetofinal.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.text.DecimalFormat;
import lombok.Data;

@Data
@Entity
@Table(name = "produto")
public class Produto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @NotNull
    @Column(name = "nome")
    private String nome;
    
    @NotNull
    @Column(name = "marca")
    private String marca;
    
    @NotNull
    @Column(name = "quantidade")
    private int quantidade;
    
    @NotNull
    @Column(name = "preco")
    private double preco;
    
    @NotNull
    @Column(name = "descricao")
    @Size(max = 2000)
    private String descricao;
    
    @NotNull
    @Column(name = "foto")
    private String foto;
    
    @ManyToOne
    @NotNull
    @JoinColumn(name = "categoria")
    private Categoria categoria;
    
}
