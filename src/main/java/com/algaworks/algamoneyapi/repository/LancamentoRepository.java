package com.algaworks.algamoneyapi.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.algaworks.algamoneyapi.model.Lancamento;
import com.algaworks.algamoneyapi.repository.lancamento.LancamentoRepositoryQuery;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery {
	
	@Query("from Lancamento l join fetch l.pessoa join fetch l.categoria order by l.id")
	List<Lancamento> findAll();
	
	List<Lancamento> findByDataVencimentoLessThanEqualAndDataPagamentoIsNull(LocalDate data);

}
