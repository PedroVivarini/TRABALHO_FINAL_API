package br.com.serratec.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.serratec.dto.ProdutoRequestDTO;
import br.com.serratec.dto.ProdutoResponseDTO;
import br.com.serratec.entity.Categoria;
import br.com.serratec.entity.Produto;
import br.com.serratec.exception.DataConflictException;
import br.com.serratec.exception.ResourceNotFoundException;
import br.com.serratec.repository.CategoriaRepository;
import br.com.serratec.repository.ProdutoRepository;
import jakarta.transaction.Transactional;

@Service
public class ProdutoService {
	
	private final ProdutoRepository repository;
	private final CategoriaRepository categoriaRepository;

	public ProdutoService(ProdutoRepository repository, CategoriaRepository categoriaRepository) {
		this.repository = repository;
		this.categoriaRepository = categoriaRepository;
	}

	@Transactional 
	public ProdutoResponseDTO inserirProduto(ProdutoRequestDTO dto) throws DataConflictException {
		
		String nomeProduto = dto.getNome(); 
        if (repository.existsByNome(nomeProduto)) {
            throw new DataConflictException("Produto com esse nome ja cadastrado");
    }
		
        Categoria categoria = categoriaRepository.findById(dto.getIdCategoria())
            .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada!"));
		Produto produto = new Produto();
        
		produto.setNome(dto.getNome());
		produto.setValor(dto.getValor());
		produto.setCategoria(categoria);

		produto = repository.save(produto);
        
        return new ProdutoResponseDTO(produto);
	}
	
	@Transactional
    public Page<ProdutoResponseDTO> listarPorPagina(Pageable pageable) {
        Page<Produto> paginaDeProdutos = repository.findAll(pageable); 
        Page<ProdutoResponseDTO> paginaDeDTOs = paginaDeProdutos
            .map(ProdutoResponseDTO::new); 
        return paginaDeDTOs;
    }

	@Transactional
	public List<ProdutoResponseDTO> listar() {
		List<Produto> produtos = repository.findAll(); 
        return produtos.stream()
                       .map(ProdutoResponseDTO::new)
                       .collect(Collectors.toList());
	}

	@Transactional
	public ProdutoResponseDTO atualizar(UUID id, ProdutoRequestDTO dto) {
		Produto produto = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));


        Categoria categoria = categoriaRepository.findById(dto.getIdCategoria())
            .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada!"));

		produto.setNome(dto.getNome());
		produto.setValor(dto.getValor());
        produto.setCategoria(categoria); 

		Produto produtoAtualizado = repository.save(produto); 
		return new ProdutoResponseDTO(produtoAtualizado);
	}
    
}