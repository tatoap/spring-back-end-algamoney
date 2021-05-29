package com.algaworks.algamoneyapi.exception;

public class UsuarioOuSenhaIncorretaException extends NegocioException {
	
	private static final long serialVersionUID = 1L;
	
	//private static final String MSG_USUARIO_SENHA_INCORRETA = "Senha informada não coincide com a senha do usuário";
	
	public UsuarioOuSenhaIncorretaException(String mensagem) {
		super(mensagem);
	}
	
}
