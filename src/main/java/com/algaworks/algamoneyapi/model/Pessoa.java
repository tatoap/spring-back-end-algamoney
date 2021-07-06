package com.algaworks.algamoneyapi.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.algaworks.algamoneyapi.core.validation.Groups;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "pessoa")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pessoa {
	
	@Id
	@NotNull(groups = Groups.PessoaId.class)
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(min = 3, max = 255)
	private String nome;
	
	@Valid
	@JsonIgnoreProperties("pessoa")
	@OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Contato> contatos;
	
	@Embedded
	private Endereco endereco;
	
	@NotNull
	private Boolean ativo = Boolean.TRUE;
	
	public void ativar() {
		setAtivo(true);
	}
	
	public void inativar() {
		setAtivo(false);
	}
	
	@JsonIgnore
	@Transient
	public boolean isInativo() {
		return !this.ativo;
	}

}
