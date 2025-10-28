package br.com.serratec.entity;

import java.time.LocalDate;

import br.com.serratec.entity.pk.PedidoProdutoPK;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class PedidoProduto {

	@EmbeddedId
	private PedidoProdutoPK id = new PedidoProdutoPK();

	private LocalDate dataPedido;
	private Double quantidade;
	public PedidoProduto(PedidoProdutoPK id, LocalDate dataPedido, Double quantidade) {
		super();
		this.id = id;
		this.dataPedido = dataPedido;
		this.quantidade = quantidade;
	}
	public PedidoProdutoPK getId() {
		return id;
	}
	public void setId(PedidoProdutoPK id) {
		this.id = id;
	}
	public LocalDate getDataPedido() {
		return dataPedido;
	}
	public void setDataPedido(LocalDate dataPedido) {
		this.dataPedido = dataPedido;
	}
	public Double getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}


	
	
}
