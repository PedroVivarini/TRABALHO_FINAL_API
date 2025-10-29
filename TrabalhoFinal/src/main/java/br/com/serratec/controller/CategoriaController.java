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

import br.com.serratec.dto.CategoriaRequestDTO;
import br.com.serratec.dto.CategoriaResponseDTO;
import br.com.serratec.entity.Categoria;
import br.com.serratec.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Categoria", description = "Cadastro de Categorias")
@RestController
@RequestMapping("/categorias")
public class CategoriaController {
	
	@Autowired
	private CategoriaService service;
	
	@Operation(summary = "Inserir Categoria", description = "Insere uma categoria")
	@PostMapping
	public ResponseEntity<CategoriaResponseDTO> inserir (@Valid @RequestBody CategoriaRequestDTO categoriaResquestDTO){
		CategoriaResponseDTO response = service.inserirCategoria(categoriaResquestDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	
        @Operation(summary = "Listagem de Categorias", description = "Lista todos as categorias do sistema em um arquivo JSON")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", content = {
		@Content(schema = @Schema(implementation = Categoria.class),mediaType = "application/json") }, description = "Retorna a listagem de categorias"),
		@ApiResponse(responseCode = "401",description = "Erro de Autenticação"),
		@ApiResponse(responseCode = "403",description = "Não há permissão para acessar o recurso"),
		@ApiResponse(responseCode = "404",description = "Recurso não encontrado"),
		@ApiResponse(responseCode = "505",description = "Exceção interna da aplicação"),
	})
	@GetMapping
	public ResponseEntity<Set<CategoriaResponseDTO>> listar (){
		return ResponseEntity.ok(service.listar());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CategoriaResponseDTO> atualizar(@PathVariable UUID id, @RequestBody CategoriaRequestDTO dto) {

	    CategoriaResponseDTO response = service.atualizar(id, dto);
	    return ResponseEntity.ok(response);
	}
}
