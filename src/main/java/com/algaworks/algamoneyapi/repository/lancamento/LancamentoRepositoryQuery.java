package com.algaworks.algamoneyapi.repository.lancamento;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.algamoneyapi.dto.LancamentoEstatisticaCategoria;
import com.algaworks.algamoneyapi.dto.LancamentoEstatisticaDia;
import com.algaworks.algamoneyapi.dto.LancamentoEstatisticaPessoa;
import com.algaworks.algamoneyapi.model.Lancamento;
import com.algaworks.algamoneyapi.projection.LancamentoResumo;
import com.algaworks.algamoneyapi.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {
	
	public List<LancamentoEstatisticaCategoria> porCategoria(LocalDate mesReferencia);
	public List<LancamentoEstatisticaDia> porDia(LocalDate mesReferencia);
	public List<LancamentoEstatisticaPessoa> porPessoa(LocalDate inicio, LocalDate fim);

	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);
	public Page<LancamentoResumo> resumir(LancamentoFilter lancamentoFilter, Pageable pageable);
	
}
