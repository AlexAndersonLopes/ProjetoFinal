package com.alexlopes.projetofinal.service;

import com.alexlopes.projetofinal.model.Categoria;
import com.alexlopes.projetofinal.repository.CategoriaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {
    
    @Autowired
    CategoriaRepository categoriaRepository;

    //CADASTRAR UMA NOVA CATEGORIA
    public Categoria cadastrarCategoria(Categoria c) {
        c.setId(null);
        categoriaRepository.save(c);
        return c;
    }

    //Atualizar o nome de uma categoria
    public Categoria atualizarCategoria(Integer id, Categoria c) {
        Categoria categoria = mostrarCategoriaId(id);
        categoria.setNome(c.getNome());
        categoria.setFoto(c.getFoto());
        categoriaRepository.save(categoria);
        return categoria;
    }

    //Encontrar uma categoria procurando pelo id
    public Categoria mostrarCategoriaId(Integer id) {
        return categoriaRepository.findById(id).orElseThrow();
    }
    
    //Encontrar uma categoria procurando pelo nome
    public boolean mostrarCategoriaNome(String nome) {
        Categoria categoria = categoriaRepository.findByNome(nome);
        return categoria != null;
    }
    
    public List<Categoria> mostrarTodasCategorias(){
        return categoriaRepository.findAll();
    }

    //Excluir uma categoria
    public void excluirCategoria(Integer id) {
        Categoria c = mostrarCategoriaId(id);
        categoriaRepository.deleteById(id);
    }
    
}
