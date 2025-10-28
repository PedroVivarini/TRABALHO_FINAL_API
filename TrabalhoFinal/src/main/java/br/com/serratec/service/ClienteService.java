package br.com.serratec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.serratec.dto.Endereco;
import br.com.serratec.entity.Cliente;
import br.com.serratec.repository.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ViaCepService viaCepService;

    @Autowired
    private EmailService emailService;

    public Cliente salvarOuAtualizar(Cliente cliente) {
    	
        // Consulta CEP e preenche endereço
        Endereco endereco = viaCepService.buscarCep(cliente.getCep());
        cliente.setEndereco(endereco.getLogradouro());

        Cliente salvo = clienteRepository.save(cliente);

        // Envia email de confirmação
        String msg = "Olá " + cliente.getNome() + ", seus dados foram cadastrados/atualizados com sucesso!";
        emailService.enviarEmail(cliente.getEmail(), "Confirmação de Cadastro", msg);

        return salvo;
    }
}
