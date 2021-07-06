package com.algaworks.algamoneyapi.dto;

import java.math.BigDecimal;

import com.algaworks.algamoneyapi.model.Categoria;

import lombok.Getter;
import lombok.Setter;

//@Data
@Getter
@Setter
public class LancamentoEstatisticaCategoria {
	
	private Categoria categoria;
	
	private BigDecimal total;

	public LancamentoEstatisticaCategoria(Categoria categoria, BigDecimal total) {
		this.categoria = categoria;
		this.total = total;
	}

}
