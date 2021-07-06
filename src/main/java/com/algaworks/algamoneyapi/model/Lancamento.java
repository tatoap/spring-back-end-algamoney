package com.algaworks.algamoneyapi.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import com.algaworks.algamoneyapi.core.validation.Groups;
import com.algaworks.algamoneyapi.repository.listener.LancamentoAnexoListener;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EntityListeners(LancamentoAnexoListener.class)
@Table(name = "lancamento")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Lancamento {
	
	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String descricao;
	
	@NotNull
	//@JsonFormat(pattern = "dd/MM/yyyy")
	@Column(name = "data_vencimento")
	private LocalDate dataVencimento;
	
	//@JsonFormat(pattern = "dd/MM/yyyy")
	@Column(name = "data_pagamento")
	private LocalDate dataPagamento;
	
	@NotNull
	private BigDecimal valor;
	
	private String observacao;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private TipoLancamento tipo;
	
	@Valid
	@NotNull
	@ManyToOne
	@JoinColumn(name = "pessoa_id")
	@ConvertGroup(from = Default.class, to = Groups.PessoaId.class)
	@JsonIgnoreProperties("contato")
	private Pessoa pessoa;
	
	@Valid
	@NotNull
	@ManyToOne
	@JoinColumn(name = "categoria_id")
	@ConvertGroup(from = Default.class, to = Groups.CategoriaId.class)
	private Categoria categoria;
	
	private String anexo;
	
	@Transient
	private String urlAnexo;
	
	@JsonIgnore
	public boolean isReceita() {
		return TipoLancamento.RECEITA.equals(this.tipo);
	}

}
