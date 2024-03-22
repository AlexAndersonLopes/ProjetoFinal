package com.alexlopes.projetofinal.repository;

import com.alexlopes.projetofinal.model.Categoria;
import com.alexlopes.projetofinal.model.Produto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    // Consulta produtos por categoria
    List<Produto> findByCategoria(Categoria categoria);

    // lista com todos os produtos
    @Override
    List<Produto> findAll();
    
    List<Produto> findByNomeContaining(String nome);

}
