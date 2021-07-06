package com.algaworks.algamoneyapi.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.algaworks.algamoneyapi.model.TipoLancamento;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LancamentoEstatisticaDia {
	
	private TipoLancamento tipo; 
	
	private LocalDate dia;
	
	private BigDecimal total;

	public LancamentoEstatisticaDia(TipoLancamento tipo, LocalDate dia, BigDecimal total) {
		this.tipo = tipo;
		this.dia = dia;
		this.total = total;
	}

}
