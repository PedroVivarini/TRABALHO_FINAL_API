package br.com.serratec.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.serratec.dto.ProdutoRequestDTO;
import br.com.serratec.dto.ProdutoResponseDTO;
import br.com.serratec.entity.Produto;
import br.com.serratec.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Produto", description = "Cadastro de Produtos")
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @Operation(summary = "Inserir produto", description = "Cadastra um novo produto no banco de dados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = Produto.class), mediaType = "application/json")
        }, description = "Produto criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> inserir(@Valid @RequestBody ProdutoRequestDTO produtoRequestDTO) {
        ProdutoResponseDTO response = service.inserirProduto(produtoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Listar produtos", description = "Lista todos os produtos cadastrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = Produto.class), mediaType = "application/json")
        }, description = "Lista de produtos"),
        @ApiResponse(responseCode = "500", description = "Erro interno")
    })
    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @Operation(summary = "Listar produtos com paginação", description = "Lista produtos paginados por valor")
    @GetMapping("/paginacao")
    public ResponseEntity<Page<ProdutoResponseDTO>> listarPorPagina(
            @PageableDefault(page = 0, size = 3, sort = "valor", direction = Direction.ASC) Pageable pageable) {
        Page<ProdutoResponseDTO> pagina = service.listarPorPagina(pageable);
        return ResponseEntity.ok(pagina);
    }

    @Operation(summary = "Atualizar produto", description = "Atualiza um produto existente")
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizar(@PathVariable UUID id,
            @Valid @RequestBody ProdutoRequestDTO dto) {
        ProdutoResponseDTO response = service.atualizar(id, dto);
        return ResponseEntity.ok(response);
    }
}
