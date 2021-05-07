package com.algaworks.algamoneyapi.repository.filter;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LancamentoFilter {

	private String descricao;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataVencimentoDe;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataVencimentoAte;
}
