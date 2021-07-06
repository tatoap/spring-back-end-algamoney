package com.algaworks.algamoneyapi.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "cidade")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cidade {
	
	@Id
	@EqualsAndHashCode.Include
	private Long id;
	
	private String nome;
	
	@ManyToOne
	@JoinColumn(name = "id_estado")
	private Estado estado;

}
