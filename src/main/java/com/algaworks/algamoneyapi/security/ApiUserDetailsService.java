package com.algaworks.algamoneyapi.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.algaworks.algamoneyapi.exception.UsuarioOuSenhaIncorretaException;
import com.algaworks.algamoneyapi.model.Usuario;
import com.algaworks.algamoneyapi.repository.UsuarioRepository;

@Service
public class ApiUserDetailsService implements UserDetailsService {
	
	private static final String MSG_USUARIO_SENHA_INCORRETO = "Usuário e/ou senha incorretos.";
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String email) {
		Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);
		Usuario usuario = usuarioOptional.orElseThrow(() -> new UsuarioOuSenhaIncorretaException(MSG_USUARIO_SENHA_INCORRETO));
		return new UsuarioSistema(usuario, getPermissoes(usuario));
	}

	private Collection<? extends GrantedAuthority> getPermissoes(Usuario usuario) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		usuario.getPermissoes().forEach(p -> authorities.add(new SimpleGrantedAuthority(p.getDescricao().toUpperCase())));
		return authorities;
	}
	
}
