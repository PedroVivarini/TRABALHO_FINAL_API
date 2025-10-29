package br.com.serratec.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.serratec.dto.PedidoRequestDTO;
import br.com.serratec.dto.PedidoResponseDTO;
import br.com.serratec.entity.Pedido;
import br.com.serratec.enums.StatusPedido;
import br.com.serratec.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Pedido", description = "Registro e edição de Pedidos")
@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @Operation(summary = "Listagem de pedidos", description = "Lista todos os pedidos do sistema em um arquivo JSON")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", content = {
		@Content(schema = @Schema(implementation = Pedido.class),mediaType = "application/json") }, description = "Retorna a listagem de pedidos"),
		@ApiResponse(responseCode = "401",description = "Erro de Autenticação"),
		@ApiResponse(responseCode = "403",description = "Não há permissão para acessar o recurso"),
		@ApiResponse(responseCode = "404",description = "Recurso não encontrado"),
		@ApiResponse(responseCode = "505",description = "Exceção interna da aplicação"),
	})
    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listar() {
        List<PedidoResponseDTO> response = pedidoService.listar();
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Inserir pedido", description = "Insere um pedido")
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> inserir(@Valid @RequestBody PedidoRequestDTO dto) {
        PedidoResponseDTO response = pedidoService.inserir(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Editar pedido", description = "Edita um pedido")
    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> editar(
            @PathVariable UUID id,
            @RequestParam(name = "status") StatusPedido novoStatus
    ) {
        
        PedidoResponseDTO response = pedidoService.editar(id, novoStatus);
        return ResponseEntity.ok(response);
    }
}