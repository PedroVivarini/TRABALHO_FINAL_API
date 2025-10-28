package br.com.serratec.dto;

import java.util.UUID;
import br.com.serratec.entity.Cliente;

public class ClienteResponseDTO {

    private UUID id;
    private String nome;

    public ClienteResponseDTO() {
    }

    public ClienteResponseDTO(Cliente cliente) {
        this.id = cliente.getId(); 
        this.nome = cliente.getNome();
    }
        
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}