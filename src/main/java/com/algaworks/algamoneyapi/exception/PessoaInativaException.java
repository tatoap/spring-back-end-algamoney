package com.algaworks.algamoneyapi.exception;

public class PessoaInativaException extends NegocioException {
	
	private static final long serialVersionUID = 1L;
	private static final String MSG_PESSOA_INATIVA = "Pessoa de código %d esta inativo.";

	public PessoaInativaException(String mensagem) {
		super(mensagem);
	}
	
	public PessoaInativaException(Long pessoaId) {
		this(String.format(MSG_PESSOA_INATIVA, pessoaId));
	}

}
