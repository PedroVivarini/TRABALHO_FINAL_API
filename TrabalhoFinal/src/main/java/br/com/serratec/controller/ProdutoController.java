package br.com.serratec.controller;

import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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

@Tag(name="Produto",description="Cadastro de Produtos")
@RestController
@RequestMapping("/produtos")
public class ProdutoController {
	
	@Autowired
	private ProdutoService service;
	
		@Operation(summary = "Inserir produto", description = "Cadastra um novo produto no banco de dados")
		@ApiResponses(value = {@ApiResponse(responseCode = "200", content = {
			@Content(schema = @Schema(implementation = Produto.class),mediaType = "application/json") }, description = "Insere um produto no registro"),
			@ApiResponse(responseCode = "401",description = "Erro de Autenticação"),
			@ApiResponse(responseCode = "403",description = "Não há permissão para acessar o recurso"),
			@ApiResponse(responseCode = "404",description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "505",description = "Exceção interna da aplicação"),
		})
	
	@PostMapping
	public ResponseEntity<ProdutoResponseDTO> inserir (@Valid @RequestBody ProdutoRequestDTO produtoResquestDTO ) {
		ProdutoResponseDTO response = service.inserirProduto(produtoResquestDTO );
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@Operation(summary = "Listagem de produtos", description = "Lista todos os produtos cadastrados em um arquivo JSON")
		@ApiResponses(value = {@ApiResponse(responseCode = "200", content = {
			@Content(schema = @Schema(implementation = Produto.class),mediaType = "application/json") }, description = "Retorna todos os produtos"),
			@ApiResponse(responseCode = "401",description = "Erro de Autenticação"),
			@ApiResponse(responseCode = "403",description = "Não há permissão para acessar o recurso"),
			@ApiResponse(responseCode = "404",description = "Recurso não encontrado"),
			@ApiResponse(responseCode = "505",description = "Exceção interna da aplicação"),
		})
	
	@GetMapping
	public ResponseEntity<Set<ProdutoResponseDTO>> listar (){
		return ResponseEntity.ok(service.listar()); 
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ProdutoResponseDTO> atualizar(@PathVariable UUID id, @RequestBody ProdutoRequestDTO dto) {

	    ProdutoResponseDTO response = service.atualizar(id, dto);
	    return ResponseEntity.ok(response);
	}
}
