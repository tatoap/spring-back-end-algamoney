package com.algaworks.algamoneyapi.exception;

public class LancamentoNaoEncontradoException extends EntidadeNaoEncontradaException {
	
	private static final long serialVersionUID = 1L;
	
	private static final String MSG_LANCAMENTO_NAO_ENCONTRADO = "Não existe um lançamento com o código %d";

	public LancamentoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public LancamentoNaoEncontradoException(Long lancamentoId) {
		this(String.format(MSG_LANCAMENTO_NAO_ENCONTRADO, lancamentoId));
	}
}
