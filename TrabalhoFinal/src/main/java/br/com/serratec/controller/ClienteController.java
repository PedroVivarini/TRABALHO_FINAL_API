package br.com.serratec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

import br.com.serratec.dto.ClienteRequestDTO;
import br.com.serratec.dto.ClienteResponseDTO;
import br.com.serratec.entity.Cliente;
import br.com.serratec.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Cliente", description = "Cadastro de Clientes")
@RestController
@RequestMapping("/clientes") 
public class ClienteController {

    @Autowired
    private ClienteService clienteService; 

        @Operation(summary = "Listagem de Clientes", description = "Lista todos os peditos do sistema em um arquivo JSON")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", content = {
		@Content(schema = @Schema(implementation = Cliente.class),mediaType = "application/json") }, description = "Retorna a listagem de clientes"),
		@ApiResponse(responseCode = "401",description = "Erro de Autenticação"),
		@ApiResponse(responseCode = "403",description = "Não há permissão para acessar o recurso"),
		@ApiResponse(responseCode = "404",description = "Recurso não encontrado"),
		@ApiResponse(responseCode = "505",description = "Exceção interna da aplicação"),
	})
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listar() {
        return ResponseEntity.ok(clienteService.listar());
    }
    
    @Operation(summary = "Inserir Cliente", description = "Insere um cliente")
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> inserir(
            @Valid @RequestBody ClienteRequestDTO dto
    ) {
        ClienteResponseDTO clienteSalvo = clienteService.inserir(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
    }

    @Operation(summary = "Editar Cliente", description = "Edita um cliente")
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> editar(
            @PathVariable UUID id,           
            @Valid @RequestBody ClienteRequestDTO dto 
    ) {
        ClienteResponseDTO clienteAtualizado = clienteService.editar(id, dto);
        return ResponseEntity.ok(clienteAtualizado);
    }
}