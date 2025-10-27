package br.com.serratec.service;

import java.util.List;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.serratec.dto.CategoriaResponseDTO;
import br.com.serratec.dto.CategoriaRequestDTO;
import br.com.serratec.entity.Categoria;
import br.com.serratec.repository.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;
	
	
	public CategoriaResponseDTO inserir (CategoriaRequestDTO dto) {
		Categoria categoria = new Categoria();
		categoria.setNome(dto.getNome());
		
		categoria = repository.save(categoria);
		
		return new  CategoriaResponseDTO(categoria);
		
	}
	
	public Set<CategoriaResponseDTO> listar(){
		List<Categoria> categorias = repository.findAll();
		Set<CategoriaResponseDTO> categoriaresponse = new HashSet<>();
		
		for (Categoria categoria : categorias) {
			categoriaresponse.add(new CategoriaResponseDTO(categoria));
		}
		return categoriaresponse;
	}
}