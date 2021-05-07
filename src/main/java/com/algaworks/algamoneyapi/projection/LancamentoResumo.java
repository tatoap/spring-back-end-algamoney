package com.algaworks.algamoneyapi.projection;

import java.math.BigDecimal;
import java.util.Date;

import com.algaworks.algamoneyapi.model.TipoLancamento;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LancamentoResumo {
	
	private Long id;
	private String descricao;
	private Date dataVencimento;
	private Date dataPagamento;
	private BigDecimal valor;
	private TipoLancamento tipo;
	private String categoria;
	private String pessoa;
	
	public LancamentoResumo(Long id, String descricao, Date dataVencimento, Date dataPagamento, BigDecimal valor,
			TipoLancamento tipo, String categoria, String pessoa) {
		this.id = id;
		this.descricao = descricao;
		this.dataVencimento = dataVencimento;
		this.dataPagamento = dataPagamento;
		this.valor = valor;
		this.tipo = tipo;
		this.categoria = categoria;
		this.pessoa = pessoa;
	}
	
	

}
