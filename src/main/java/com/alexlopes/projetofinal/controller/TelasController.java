package com.alexlopes.projetofinal.controller;

import com.alexlopes.projetofinal.model.Categoria;
import com.alexlopes.projetofinal.model.Produto;
import com.alexlopes.projetofinal.model.WhatsAppAPI;
import com.alexlopes.projetofinal.service.CategoriaService;
import com.alexlopes.projetofinal.service.ProdutoService;
import com.alexlopes.projetofinal.service.WhatsAppAPIService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class TelasController {

    @Autowired
    CategoriaService categoriaService;

    @Autowired
    ProdutoService produtoService;

    @Autowired
    WhatsAppAPIService whatsAppAPIService;

    @GetMapping("/inicio")
    public String paginaInicial(Model model) {
        irJanelaInicial(model);
        return "index";
    }

    @GetMapping("/")
    public String paginaInicial3(Model model) {
        irJanelaInicial(model);
        return "index";
    }

    @GetMapping("/menuRestrito")
    public String areaRestritaMenu(Model model) {
        Categoria categoria = new Categoria();
        Produto produto = new Produto();
        model.addAttribute("produto", produto);
        model.addAttribute("categoria", categoria);
        return "menuRestrito";
    }

    @GetMapping("/cadastroCategoria")
    public String irTesteCadastrarCategoria(Model model) {
        Categoria categoria = new Categoria();
        model.addAttribute("categoria", categoria);
        return "cadastrarCategoria";
    }

    @GetMapping("/cadastrarProduto")
    public String irTesteCadastrarProduto(Model model) {
        List<Categoria> categoria = categoriaService.mostrarTodasCategorias();
        Produto produto = new Produto();

        List<Categoria> listaTodasCategorias = categoriaService.mostrarTodasCategorias();

        model.addAttribute("listaTodasCategorias", listaTodasCategorias);
        model.addAttribute("produto", produto);
        model.addAttribute("categoria", categoria);
        return "cadastrarProduto";
    }

    @GetMapping("/mostrarTodasCategorias")
    public String irMostrarTodasCategorias(Model model) {
        List<Categoria> listaTodasCategorias = categoriaService.mostrarTodasCategorias();

        model.addAttribute("listaTodasCategorias", listaTodasCategorias);
        return "mostrarTodasCategorias";
    }

    @PostMapping("/cadastrarProduto")
    public String cadastrarProduto(@ModelAttribute Produto produto, @RequestParam("image") MultipartFile fotos, @RequestParam("idCategoria") Integer idCategoria) {
        try {
            byte[] bytes = fotos.getBytes();
            Path path = Paths.get("src/main/resources/static/imagens/" + fotos.getOriginalFilename());
            Files.write(path, bytes);

            Categoria categoria = categoriaService.mostrarCategoriaId(idCategoria);
            produto.setFoto(fotos.getOriginalFilename());

            produtoService.cadastrarProduto(produto, categoria);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/menuRestrito";
    }

    @GetMapping("/imagens/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = new FileSystemResource("src/main/resources/static/imagens/" + filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/salvarCategoria")
    public String salvarCategoria(@ModelAttribute Categoria categoria, @RequestParam("fotos") MultipartFile foto) {
        try {
            if (!categoriaService.mostrarCategoriaNome(categoria.getNome())) {
                byte[] bytes = foto.getBytes();
                Path path = Paths.get("src/main/resources/static/imagens/" + foto.getOriginalFilename());
                Files.write(path, bytes);

                categoria.setFoto(foto.getOriginalFilename());
                categoriaService.cadastrarCategoria(categoria);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/menuRestrito";
    }

    private void irJanelaInicial(Model model) {
        List<Categoria> listaTodasCategorias = categoriaService.mostrarTodasCategorias();
        WhatsAppAPI whats = new WhatsAppAPI();
        whats = whatsAppAPIService.mostrarDadosId(1);

        model.addAttribute("whats", whats);
        model.addAttribute("listaTodasCategorias", listaTodasCategorias);
    }

    @GetMapping("/conectarWhatsApp")
    public String irConectarZap(Model model) {
        WhatsAppAPI whats = new WhatsAppAPI();
        whats = whatsAppAPIService.mostrarDadosId(1);
        model.addAttribute("whats", whats);
        return "conectarWhatsApp";
    }

    @GetMapping("/todosProdutos")
    public String irTodosProdutos(Model model) {
        List<Produto> produtos = produtoService.mostrarTodosProdutos();
        List<Categoria> listaTodasCategorias = categoriaService.mostrarTodasCategorias();

        model.addAttribute("listaTodasCategorias", listaTodasCategorias);
        model.addAttribute("produtos", produtos);

        return "mostrarTodosProdutos";
    }

    @GetMapping("/mostrarProdutosCategoria")
    public String irProdutosPorCategoria(Model model, @RequestParam String id) {
        Integer idCategoria = Integer.parseInt(id);
        Categoria categoria = new Categoria();
        categoria = categoriaService.mostrarCategoriaId(idCategoria);

        List<Produto> produtos = produtoService.mostrarProdutosPorCategoria(categoria);
        List<Categoria> listaTodasCategorias = categoriaService.mostrarTodasCategorias();

        model.addAttribute("idCategoriaSelecionada", idCategoria);
        model.addAttribute("listaTodasCategorias", listaTodasCategorias);
        model.addAttribute("categoria", categoria);
        model.addAttribute("produtos", produtos);

        return "mostrarProdutosCategoria";
    }

    @GetMapping("/mostrarProdutosRestrito")
    public String irMostrarProdutosRestrito(Model model, @RequestParam(required = false, defaultValue = "") String nome) {
        List<Produto> listaProdutos = new ArrayList<>();
        if (nome.isEmpty()) {
            listaProdutos = produtoService.mostrarTodosProdutos();
            // Ordena a lista de produtos pelo nome em ordem alfabética
            Collections.sort(listaProdutos, (Produto p1, Produto p2) -> p1.getNome().compareToIgnoreCase(p2.getNome()));
        } else {
            listaProdutos = produtoService.encontrarProdutosPorNome(nome);
        }
        model.addAttribute("listaProdutos", listaProdutos);
        return "mostrarProdutosRestrito";
    }

    @GetMapping("/detalhes")
    public String irDetalhesProduto(Model model, @RequestParam String id) {
        Integer idCategoria = Integer.parseInt(id);
        Produto produto = new Produto();
        produto = produtoService.mostrarProdutoPorId(idCategoria);

        model.addAttribute("produto", produto);
        return "detalhesProduto";
    }

    @GetMapping("/excluirCategoria")
    public String irExcluirCategoria(Model model, @RequestParam String id) {
        Integer idCategoria = Integer.parseInt(id);
        Categoria categoria = categoriaService.mostrarCategoriaId(idCategoria);

        model.addAttribute("categoria", categoria);
        return "excluirCategoria";
    }

    @GetMapping("/excluirProduto")
    public String irExcluirProduto(Model model, @RequestParam String id) {
        Integer idProduto = Integer.parseInt(id);
        Produto produto = produtoService.mostrarProdutoPorId(idProduto);

        model.addAttribute("produto", produto);
        return "excluirProduto";
    }

    @GetMapping("/alterarCategoria")
    public String irAlterarCategoria(Model model, @RequestParam String id) {
        Integer idCategoria = Integer.parseInt(id);
        Categoria categoria = categoriaService.mostrarCategoriaId(idCategoria);

        model.addAttribute("categoria", categoria);
        return "alterarCategoria";
    }

    @GetMapping("/alterarProduto")
    public String irAlterarProduto(Model model, @RequestParam String id) {
        Integer idCategoria = Integer.parseInt(id);
        Produto produto = produtoService.mostrarProdutoPorId(idCategoria);
        List<Categoria> listaTodasCategorias = categoriaService.mostrarTodasCategorias();

        model.addAttribute("listaTodasCategorias", listaTodasCategorias);
        model.addAttribute("produto", produto);
        return "alterarProduto";
    }

    @GetMapping("/carrinho")
    public String mostrarCarrinho(Model model) {
        WhatsAppAPI whats = new WhatsAppAPI();
        whats = whatsAppAPIService.mostrarDadosId(1);

        model.addAttribute("whats", whats);
        return "carrinho";
    }

    @PutMapping("/venderProduto")
    public ResponseEntity<String> restirarEstoque(Model model, @RequestParam String id, @RequestParam Integer qtd) {
        try {
            Integer idProduto = Integer.parseInt(id);
            Produto produto = produtoService.mostrarProdutoPorId(idProduto);
            produto.setQuantidade(produto.getQuantidade() - qtd);
            produto = produtoService.atualizarProduto(idProduto, produto);
            return ResponseEntity.ok("Venda de produto processada com sucesso");
        } catch (Exception e) {
            // Se ocorrer um erro durante o processamento da venda, retorne uma resposta de erro (por exemplo, 400 Bad Request)
            return ResponseEntity.badRequest().body("Erro ao processar a venda de produto: " + e.getMessage());
        }
    }

    @DeleteMapping("/excluirCategoriaId")
    public ResponseEntity<String> excluirCategoria(@RequestParam String id) {
        try {
            Integer idcat = Integer.parseInt(id);
            Categoria categoria = categoriaService.mostrarCategoriaId(idcat);
            categoriaService.excluirCategoria(categoria.getId());

            return ResponseEntity.ok("Categoria Excluída com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao excluir uma categoria: " + e.getMessage());
        }
    }

    @DeleteMapping("/excluirProdutoId")
    public ResponseEntity<String> excluirProduto(@RequestParam String id) {
        try {
            Integer idProduto = Integer.parseInt(id);
            Produto produto = produtoService.mostrarProdutoPorId(idProduto);
            if (produto != null) {
                produtoService.excluirProduto(idProduto);
                return ResponseEntity.ok("Produto excluído com sucesso");
            } else {
                return ResponseEntity.badRequest().body("Produto não encontrado");
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Erro ao excluir o produto: " + e.getMessage());
        }
    }

    @PutMapping("/alterarCategoriaId/{id}")
    public ResponseEntity<Categoria> alterarCategoriaId(@PathVariable String id, @RequestParam(value = "imagem", required = false) MultipartFile fotos, @ModelAttribute Categoria c) {
        try {
            Integer idcat = Integer.parseInt(id);
            if (fotos != null && !fotos.isEmpty()) {
                byte[] bytes = fotos.getBytes();
                Path path = Paths.get("src/main/resources/static/imagens/" + fotos.getOriginalFilename());
                Files.write(path, bytes);
                c.setFoto(fotos.getOriginalFilename());
            } else {
                Categoria categoriaExistente = categoriaService.mostrarCategoriaId(idcat);
                c.setFoto(categoriaExistente.getFoto());
            }
            Categoria atualizar = categoriaService.atualizarCategoria(idcat, c);
            return new ResponseEntity<>(atualizar, HttpStatus.OK);
        } catch (IOException | NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/alterarProdutoId/{id}")
    public ResponseEntity<Produto> alterarProdutoId(@PathVariable String id, @RequestParam(value = "imagem", required = false) MultipartFile imagem, @ModelAttribute Produto p) {
        try {
            Integer idcat = Integer.parseInt(id);
            if (imagem != null && !imagem.isEmpty()) {
                byte[] bytes = imagem.getBytes();
                Path path = Paths.get("src/main/resources/static/imagens/" + imagem.getOriginalFilename());
                Files.write(path, bytes);
                p.setFoto(imagem.getOriginalFilename());
            } else {
                Produto produtoExistente = produtoService.mostrarProdutoPorId(idcat);
                p.setFoto(produtoExistente.getFoto());
            }
            Produto atualizar = produtoService.atualizarProduto(idcat, p);
            return new ResponseEntity<>(atualizar, HttpStatus.OK);
        } catch (IOException | NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
