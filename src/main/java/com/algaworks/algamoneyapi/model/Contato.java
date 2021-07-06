package com.algaworks.algamoneyapi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "contato")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Contato {
	
	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String nome;
	
	@NotBlank
	@Email
	private String email;
	
	@NotBlank
	private String telefone;
	
	@ManyToOne
	@JoinColumn(name = "pessoa_id")
	private Pessoa pessoa;
	
}
