package br.com.serratec.entity;

import java.time.LocalDate;

import br.com.serratec.entity.pk.CategoriaProdutoPK;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class CategoriaProduto {

	@EmbeddedId
	private CategoriaProdutoPK id = new CategoriaProdutoPK();

	private LocalDate dataProduto;

	public CategoriaProduto() {
	}

	public CategoriaProduto(Categoria categoria, Produto produto, LocalDate dataProduto) {

		id.setCategoria(categoria);
		id.setProduto(produto);
		this.dataProduto = dataProduto;
	}

	public void setCategoria(Categoria categoria) {
		id.setCategoria(categoria);
	}

	public Categoria getCategoria() {
		return id.getCategoria();
	}

	public void setProduto(Produto produto) {
		id.setProduto(produto);
	}

	public Produto getProduto() {
		return id.getProduto();
	}

	public CategoriaProdutoPK getId() {
		return id;
	}

	public void setId(CategoriaProdutoPK id) {
		this.id = id;
	}

	public LocalDate getDataProduto() {
		return dataProduto;
	}

	public void setDataProduto(LocalDate dataProduto) {
		this.dataProduto = dataProduto;
	}

}
