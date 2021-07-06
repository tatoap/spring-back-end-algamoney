package com.algaworks.algamoneyapi.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "estado")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Estado {

	@Id
	@EqualsAndHashCode.Include
	private Long id;
	
	private String nome;
	
}
