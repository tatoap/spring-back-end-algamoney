package com.algaworks.algamoneyapi.exception;

public class CategoriaNaoEncontradaException extends EntidadeNaoEncontradaException {
	
	private static final long serialVersionUID = 1L;
	
	private static final String MSG_CATEGORIA_NAO_ENCONTRADO = "Não existe um cadastro de categoria com o código %d";

	public CategoriaNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public CategoriaNaoEncontradaException(Long categoriaId) {
		this(String.format(MSG_CATEGORIA_NAO_ENCONTRADO, categoriaId));
	}

}
