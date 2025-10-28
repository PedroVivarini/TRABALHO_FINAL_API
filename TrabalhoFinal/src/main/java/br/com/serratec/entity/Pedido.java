package br.com.serratec.entity;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_pedido")
public class Pedido {
	
	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	private LocalDate dataPedido;
	
	@ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
	private Cliente cliente;
	
	public Pedido() {
		// TODO Auto-generated constructor stub
	}

	public Pedido(UUID id, LocalDate dataPedido, Cliente cliente) {
		super();
		this.id = id;
		this.dataPedido = dataPedido;
		this.cliente = cliente;
	}

	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	
	public LocalDate getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(LocalDate dataPedido) {
		this.dataPedido = dataPedido;
	}

	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}
