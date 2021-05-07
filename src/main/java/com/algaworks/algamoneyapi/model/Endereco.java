package com.algaworks.algamoneyapi.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Embeddable
public class Endereco {
	
	@Column(name = "endereco_logradouro")
	private String logradouro;
	
	@Column(name = "endereco_numero")
	private String numero;
	
	@Column(name = "endereco_complemento")
	private String complemento;
	
	@Column(name = "endereco_bairro")
	private String bairro;
	
	@Column(name = "endereco_cep")
	@Size(min = 9, max = 9)
	private String cep;

	@Column(name = "endereco_cidade")
	private String cidade;
	
	@Column(name = "endereco_estado")
	@Size(min = 2, max = 2)
	private String estado;
}
