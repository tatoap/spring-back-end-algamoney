package com.algaworks.algamoneyapi.dto;

import java.math.BigDecimal;

import com.algaworks.algamoneyapi.model.Pessoa;
import com.algaworks.algamoneyapi.model.TipoLancamento;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LancamentoEstatisticaPessoa {
		
	private TipoLancamento tipo;
	
	private Pessoa pessoa;
	
	private BigDecimal total;

	public LancamentoEstatisticaPessoa(TipoLancamento tipo, Pessoa pessoa, BigDecimal total) {
		this.tipo = tipo;
		this.pessoa = pessoa;
		this.total = total;
	}

}
