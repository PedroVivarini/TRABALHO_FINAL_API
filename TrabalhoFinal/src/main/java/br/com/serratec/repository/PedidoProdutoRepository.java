package br.com.serratec.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.serratec.entity.PedidoProduto;
import br.com.serratec.entity.pk.PedidoProdutoPK;

public interface PedidoProdutoRepository  extends JpaRepository<PedidoProduto, PedidoProdutoPK>{

}
