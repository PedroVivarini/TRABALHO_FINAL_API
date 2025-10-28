package br.com.serratec.entity.pk;

import java.io.Serializable;

import br.com.serratec.entity.Categoria;
import br.com.serratec.entity.Produto;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class PedidoProdutoPK implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name = "id_pedido")
	private Categoria categoria;
	
	
	@ManyToOne
	@JoinColumn(name = "id_produto")
	private Produto produto;


	public Categoria getCategoria() {
		return categoria;
	}


	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}


	public Produto getProduto() {
		return produto;
	}


	public void setProduto(Produto produto) {
		this.produto = produto;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
}
