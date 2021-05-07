package com.algaworks.algamoneyapi.exception;

public class PessoaNaoEncontradaException extends EntidadeNaoEncontradaException {
	
	private static final long serialVersionUID = 1L;
	private static final String MSG_PESSOA_NAO_ENCONTRADA = "Não existe um cadastro de pessoa com o código %d";

	public PessoaNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public PessoaNaoEncontradaException(Long pessoaId) {
		this(String.format(MSG_PESSOA_NAO_ENCONTRADA, pessoaId));
	}
}
