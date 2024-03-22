package com.alexlopes.projetofinal.controller;

import com.alexlopes.projetofinal.model.Categoria;
import com.alexlopes.projetofinal.model.Produto;
import com.alexlopes.projetofinal.service.CategoriaService;
import com.alexlopes.projetofinal.service.ProdutoService;
import jakarta.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    ProdutoService produtoService;

    @Autowired
    CategoriaService categoriaService;

    //MOSTRAR TODOS PRODUTOS
    @GetMapping("/mostrarTodos")
    public ResponseEntity<List<Produto>> mostrarTodosFilmes() {
        List<Produto> p = produtoService.mostrarTodosProdutos();
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    //Mostrar um produto especifico, por id
    @GetMapping("/mostrarProdutosPorId/{id}")
    public ResponseEntity<Produto> mostrarProdutoPorId(@PathVariable Integer id) {
        Produto p = produtoService.mostrarProdutoPorId(id);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    //Mostrar produtos por categoria
    @GetMapping("/mostrarProdutosPorCategoria/{categoriaid}")
    public ResponseEntity<List<Produto>> mostrarProdutosPorCategoria(@PathVariable("categoriaid") Integer categoriaid) {
        Categoria categoria = new Categoria();
        categoria.setId(categoriaid);
        List<Produto> produtos = produtoService.mostrarProdutosPorCategoria(categoria);
        return ResponseEntity.ok(produtos);
    }

    //Excluir um produto
    @DeleteMapping("/excluirProduto/{id}")
    public ResponseEntity<Produto> excluirProduto(@PathVariable Integer id) {
        produtoService.excluirProduto(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //salvar produto em uma categoria
    @PostMapping("/salvarProduto/{idCategoria}")
    public ResponseEntity<Produto> salvarProduto(@Valid @RequestBody Produto produto, @PathVariable Integer idCategoria) {
        Categoria c = categoriaService.mostrarCategoriaId(idCategoria);
        var novoProduto = produtoService.cadastrarProduto(produto, c);
        return new ResponseEntity<>(novoProduto, HttpStatus.OK);
    }

    //Atualizar um produto
    @PutMapping("/atualizarProduto/{id}")
    public ResponseEntity<Produto> alterarProduto(@PathVariable Integer id, @RequestParam(value = "imagem", required = false) MultipartFile imagem, @RequestBody Produto produto) {
        try {
            Produto produtoEncontrado = produtoService.mostrarProdutoPorId(id);
            if (produtoEncontrado == null) {
                return ResponseEntity.notFound().build();
            }
            if (imagem != null && !imagem.isEmpty()) {
                byte[] bytes = imagem.getBytes();
                Path path = Paths.get("src/main/resources/static/imagens/" + imagem.getOriginalFilename());
                Files.write(path, bytes);
                produtoEncontrado.setFoto(imagem.getOriginalFilename());
            } else {
                produto.setFoto(produtoEncontrado.getFoto());
            }

            Categoria categoriaEncontrada = produtoEncontrado.getCategoria();

            produtoEncontrado.setCategoria(categoriaEncontrada);
            produtoEncontrado.setDescricao(produto.getDescricao());
            produtoEncontrado.setMarca(produto.getMarca());
            produtoEncontrado.setNome(produto.getNome());
            produtoEncontrado.setPreco(produto.getPreco());
            produtoEncontrado.setQuantidade(produto.getQuantidade());

            Produto cadastrarProduto = produtoService.atualizarProduto(id, produtoEncontrado);
            return new ResponseEntity<>(cadastrarProduto, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
