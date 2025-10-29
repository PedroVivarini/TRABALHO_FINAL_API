package br.com.serratec.controller;

import java.util.Set;
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
import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
	
	@Autowired
	private ProdutoService service;
	
	
	@PostMapping
	public ResponseEntity<ProdutoResponseDTO> inserir (@Valid @RequestBody ProdutoRequestDTO produtoResquestDTO ) {
		ProdutoResponseDTO response = service.inserirProduto(produtoResquestDTO );
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping
	public ResponseEntity<Set<ProdutoResponseDTO>> listar (){
		return ResponseEntity.ok(service.listar()); 
	}
	
	@GetMapping("/paginacao")
	public Page<Produto> listarPorPagina(@PageableDefault(
			page = 0, size = 3, sort = "valor", 
			direction = Direction.ASC)Pageable pageable) {
		return service.listarPorPagina(pageable);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ProdutoResponseDTO> atualizar(@PathVariable UUID id, @RequestBody ProdutoRequestDTO dto) {

	    ProdutoResponseDTO response = service.atualizar(id, dto);
	    return ResponseEntity.ok(response);
	}
}