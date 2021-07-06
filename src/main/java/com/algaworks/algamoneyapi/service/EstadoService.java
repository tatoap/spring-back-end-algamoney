package com.algaworks.algamoneyapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algamoneyapi.exception.EstadoNaoEncontradoException;
import com.algaworks.algamoneyapi.model.Estado;
import com.algaworks.algamoneyapi.repository.EstadoRepository;

@Service
public class EstadoService {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public Estado buscarOuFalhar(Long estadoId) {
		return estadoRepository.findById(estadoId)
				.orElseThrow(() -> new EstadoNaoEncontradoException(estadoId));
	}
	
}
