package br.com.serratec.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
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
	private ProdutoRepository produtoRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	public ProdutoResponseDTO inserir(ProdutoRequestDTO dto) {
		Optional<Categoria> categoriaOptional = categoriaRepository.findById(dto.getIdCategoria());
		if (categoriaOptional.isPresent()) {
			Categoria categoria = categoriaOptional.get();

			Produto produto = new Produto();
			produto.setNome(dto.getNome());
			produto.setValor(dto.getValor());
			produto.setCategoria(categoria);

			produto = produtoRepository.save(produto);

			return new ProdutoResponseDTO(produto);

		} else {
			throw new RuntimeException("Categoria não encontrada!");

		}
	}

	public Set<ProdutoResponseDTO> listar() {
		Set<ProdutoResponseDTO> produtoDTO = new HashSet<>();
		for (Produto produto : produtoRepository.findAll()) {
			if (produto.getCategoria() != null) {
				produtoDTO.add(new ProdutoResponseDTO(produto.getId(), produto.getNome(), produto.getValor(),
						produto.getCategoria().getNome()));
			} else {
				throw new RuntimeException("Produto sem categoria: " + produto.getNome());
			}
		}
		return produtoDTO;
	}
}
