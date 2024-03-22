package com.alexlopes.projetofinal.repository;

import com.alexlopes.projetofinal.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{
    
    Categoria findByNome(String nome);
}




