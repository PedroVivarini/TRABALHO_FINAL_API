package br.com.serratec.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.serratec.dto.ProdutoRequestDTO;
import br.com.serratec.dto.ProdutoResponseDTO;
import br.com.serratec.entity.Categoria;
import br.com.serratec.entity.Produto;
import br.com.serratec.repository.CategoriaRepository;
import br.com.serratec.repository.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository repository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	public ProdutoResponseDTO inserirProduto(ProdutoRequestDTO dto) {
		Optional<Categoria> categoriaOptional = categoriaRepository.findById(dto.getIdCategoria());
		if (categoriaOptional.isPresent()) {
			Categoria categoria = categoriaOptional.get();

			Produto produto = new Produto();
			produto.setNome(dto.getNome());
			produto.setValor(dto.getValor());
			produto.setCategoria(categoria);

			produto = repository.save(produto);

			return new ProdutoResponseDTO(produto);

		} else {
			throw new RuntimeException("Categoria não encontrada!");

		}
	}
	
	public Set<ProdutoResponseDTO> listar() {
		Set<ProdutoResponseDTO> produtoDTO = new HashSet<>();
		for (Produto produto : repository.findAll()) {
			if (produto.getCategoria() != null) {
				produtoDTO.add(new ProdutoResponseDTO(produto.getId(), produto.getNome(), produto.getValor(),
						produto.getCategoria().getNome()));
			} else {
				throw new RuntimeException("Produto sem categoria: " + produto.getNome());
			}
		}
		return produtoDTO;
	}
	
	public Page<Produto> listarPorPagina(Pageable pageable) {
		
		return repository.findAll(pageable);
	}

	public ProdutoResponseDTO atualizar(UUID id, ProdutoRequestDTO dto) {
		Optional<Produto> produtoOptional = repository.findById(id);

		if (produtoOptional.isEmpty()) {
			throw new RuntimeException("Produto não encontrado");
		}

		Produto produto = produtoOptional.get();
		produto.setNome(dto.getNome());
		produto.setValor(dto.getValor());
		produto.setId(id);

		Produto produtoDTO = repository.save(produto);
		return new ProdutoResponseDTO(produtoDTO);
	}
}
