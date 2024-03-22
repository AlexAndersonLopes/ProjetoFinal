package com.alexlopes.projetofinal.service;

import com.alexlopes.projetofinal.model.Categoria;
import com.alexlopes.projetofinal.model.Produto;
import com.alexlopes.projetofinal.repository.ProdutoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    //Cadastrar um Produto
    public Produto cadastrarProduto(Produto p, Categoria c) {
        p.setId(null);
        p.setCategoria(c);
        produtoRepository.save(p);
        return p;
    }

    //Mostrar Todos Produtos
    public List<Produto> mostrarTodosProdutos() {
        return produtoRepository.findAll();
    }

    //Mostrar Produto Pesquisando pelo ID
    public Produto mostrarProdutoPorId(Integer id) {
        return produtoRepository.findById(id).orElseThrow();
    }

    //Mostrar Lista de Produtos pesquisando pela categoria
    public List<Produto> mostrarProdutosPorCategoria(Categoria categoria) {
        return produtoRepository.findByCategoria(categoria);
    }

    //Atualizar dados do Produto
    public Produto atualizarProduto(Integer id, Produto p) {
        Produto produto = mostrarProdutoPorId(id);
        produto.setDescricao(p.getDescricao());
        produto.setFoto(p.getFoto());
        produto.setCategoria(p.getCategoria());
        produto.setMarca(p.getMarca());
        produto.setNome(p.getNome());
        produto.setPreco(p.getPreco());
        produto.setQuantidade(p.getQuantidade());
        produtoRepository.save(produto);
        return produto;
    }

    //Deletar Produto
    public void excluirProduto(Integer id) {
        Produto p = mostrarProdutoPorId(id);
        produtoRepository.deleteById(id);
    }
    
    public List<Produto> encontrarProdutosPorNome(String nome) {
        return produtoRepository.findByNomeContaining(nome);
    }
     
    
}
