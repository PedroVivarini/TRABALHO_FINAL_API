package br.com.serratec.service;

import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.serratec.dto.ClienteRequestDTO;
import br.com.serratec.dto.ClienteResponseDTO;
import br.com.serratec.dto.Endereco;
import br.com.serratec.entity.Cliente;
import br.com.serratec.repository.ClienteRepository;

@Service
public class ClienteService {
	
	private final ClienteRepository repository;
	
	public ClienteService(ClienteRepository repository) {
		this.repository = repository;
	}

	public List<ClienteResponseDTO> listar() {
	    List<Cliente> clientes = repository.findAll();
	    List<ClienteResponseDTO> dtos = new ArrayList<>();

	    for (Cliente cliente : clientes) {
	        dtos.add(new ClienteResponseDTO(cliente));
	    }
        return dtos; 
	}
		
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ViaCepService viaCepService;

    @Autowired
    private EmailService emailService;
	
	@Transactional
	public ClienteResponseDTO inserir (ClienteRequestDTO dto) {
		Cliente cliente = new Cliente();
		cliente.setNome(dto.nome());
		cliente.setEmail(dto.email());
	    cliente.setCpf(dto.cpf());
	    cliente.setTelefone(dto.telefone());
		
		cliente = repository.save(cliente);
		
		return new ClienteResponseDTO(cliente);
	}

	@Transactional
	public ClienteResponseDTO editar(UUID id, ClienteRequestDTO dto) {
	    Cliente cliente = repository.findById(id)
	        .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o id: " + id));

	    cliente.setNome(dto.nome());
	    cliente.setEmail(dto.email());
        cliente.setCpf(dto.cpf());
        cliente.setTelefone(dto.telefone());
	    
	    cliente = repository.save(cliente);

	    return new ClienteResponseDTO(cliente);
	}
	
	   public Cliente salvarOuAtualizar(Cliente cliente) {
	        Endereco endereco = viaCepService.buscarCep(cliente.getCep());
	        cliente.setEndereco(endereco.getLogradouro());

	        Cliente salvo = clienteRepository.save(cliente);

	        String msg = "Olá " + cliente.getNome() + ", seus dados foram cadastrados/atualizados com sucesso!";
	        emailService.enviarEmail(cliente.getEmail(), "Confirmação de Cadastro", msg);

	        return salvo;
	    }
}