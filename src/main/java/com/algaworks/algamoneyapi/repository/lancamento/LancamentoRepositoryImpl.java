package com.algaworks.algamoneyapi.repository.lancamento;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.ObjectUtils;

import com.algaworks.algamoneyapi.dto.LancamentoEstatisticaCategoria;
import com.algaworks.algamoneyapi.dto.LancamentoEstatisticaDia;
import com.algaworks.algamoneyapi.dto.LancamentoEstatisticaPessoa;
import com.algaworks.algamoneyapi.model.Lancamento;
import com.algaworks.algamoneyapi.projection.LancamentoResumo;
import com.algaworks.algamoneyapi.repository.filter.LancamentoFilter;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;
	
	private Long total(LancamentoFilter lancamentoFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates);
		criteria.select(builder.count(root));
		
		return manager.createQuery(criteria).getSingleResult();
	}

	private void adcionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDapagina = paginaAtual * totalRegistrosPorPagina;
		
		query.setFirstResult(primeiroRegistroDapagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}
	
	@Override
	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		
		criteria.where(predicates);
		
		TypedQuery<Lancamento> query = manager.createQuery(criteria);
		adcionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter));
	}

	@Override
	public Page<LancamentoResumo> resumir(LancamentoFilter lancamentoFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<LancamentoResumo> criteria = builder.createQuery(LancamentoResumo.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		criteria.select(builder.construct(LancamentoResumo.class,
				root.get("id"), root.get("descricao"),
				root.get("dataVencimento"), root.get("dataPagamento"),
				root.get("valor"), root.get("tipo"),
				root.get("categoria").get("nome"), 
				root.get("pessoa").get("nome")));
		
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		
		criteria.where(predicates);
		
		TypedQuery<LancamentoResumo> query = manager.createQuery(criteria);
		adcionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter));
	}
	
	@Override
	public List<LancamentoEstatisticaDia> porDia(LocalDate mesReferencia) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		
		CriteriaQuery<LancamentoEstatisticaDia> query = builder.createQuery(LancamentoEstatisticaDia.class);
		
		Root<Lancamento> root = query.from(Lancamento.class);
		
		query.select(builder.construct(LancamentoEstatisticaDia.class,
				root.get("tipo"),
				root.get("dataVencimento"),
				builder.sum(root.get("valor"))));
		
		LocalDate primeiroDia = mesReferencia.withDayOfMonth(1);
		LocalDate ultimoDia = mesReferencia.withDayOfMonth(mesReferencia.lengthOfMonth());
		
		query.where(
				builder.greaterThanOrEqualTo(root.get("dataVencimento"), primeiroDia),
				builder.lessThanOrEqualTo(root.get("dataVencimento"), ultimoDia));
		
		query.groupBy(root.get("tipo"), root.get("dataVencimento"));
		
		TypedQuery<LancamentoEstatisticaDia> typedQuery = manager.createQuery(query);
		
		return typedQuery.getResultList();
	}
	
	@Override
	public List<LancamentoEstatisticaPessoa> porPessoa(LocalDate inicio, LocalDate fim) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		
		CriteriaQuery<LancamentoEstatisticaPessoa> query = builder.createQuery(LancamentoEstatisticaPessoa.class);
		
		Root<Lancamento> root = query.from(Lancamento.class);
		
		query.select(builder.construct(LancamentoEstatisticaPessoa.class,
				root.get("tipo"),
				root.get("pessoa"),
				builder.sum(root.get("valor"))));
		
		query.where(
				builder.greaterThanOrEqualTo(root.get("dataVencimento"), inicio),
				builder.lessThanOrEqualTo(root.get("dataVencimento"), fim));
		
		query.groupBy(root.get("tipo"), root.get("pessoa"));
		
		TypedQuery<LancamentoEstatisticaPessoa> typedQuery = manager.createQuery(query);
		
		return typedQuery.getResultList();
	}
	
	@Override
	public List<LancamentoEstatisticaCategoria> porCategoria(LocalDate mesReferencia) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		
		CriteriaQuery<LancamentoEstatisticaCategoria> query = builder.createQuery(LancamentoEstatisticaCategoria.class);
		
		Root<Lancamento> root = query.from(Lancamento.class);
		
		query.select(builder.construct(LancamentoEstatisticaCategoria.class,
				root.get("categoria"),
				builder.sum(root.get("valor"))));
		
		LocalDate primeiroDia = mesReferencia.withDayOfMonth(1);
		LocalDate ultimoDia = mesReferencia.withDayOfMonth(mesReferencia.lengthOfMonth());
		
		query.where(
				builder.greaterThanOrEqualTo(root.get("dataVencimento"), primeiroDia),
				builder.lessThanOrEqualTo(root.get("dataVencimento"), ultimoDia));
		
		query.groupBy(root.get("categoria"));
		
		TypedQuery<LancamentoEstatisticaCategoria> typedQuery = manager.createQuery(query);
		
		return typedQuery.getResultList();
	}
	
	private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter, CriteriaBuilder builder,
			Root<Lancamento> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if (!ObjectUtils.isEmpty(lancamentoFilter.getDescricao())) {
			predicates.add(builder.like(
					builder.lower(root.get("descricao")), "%" + lancamentoFilter.getDescricao().toLowerCase() + "%"));
		}
		
		if (lancamentoFilter.getDataVencimentoAte() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("dataVencimento"), lancamentoFilter.getDataVencimentoDe()));
		}
		
		if (lancamentoFilter.getDataVencimentoAte() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("dataVencimento"), lancamentoFilter.getDataVencimentoAte()));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
