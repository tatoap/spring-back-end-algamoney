package com.algaworks.algamoneyapi.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algamoneyapi.exception.CategoriaNaoEncontradaException;
import com.algaworks.algamoneyapi.exception.EntidadeEmUsoException;
import com.algaworks.algamoneyapi.model.Categoria;
import com.algaworks.algamoneyapi.repository.CategoriaRepository;

@Service
public class CategoriaService {
	
	private static final String MSG_CATEGORIA_EM_USO = "Categoria de código %d não pode ser excluido pois está em uso.";
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Transactional
	public Categoria salvar(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}
	
	@Transactional
	public void excluir(Long categoriaId) {
		try {
			categoriaRepository.deleteById(categoriaId);
			categoriaRepository.flush();
		} catch(EmptyResultDataAccessException e) {
			throw new CategoriaNaoEncontradaException(categoriaId);
		} catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_CATEGORIA_EM_USO, categoriaId));
		}
	}
	
	public Categoria buscarOuFalhar(Long categoriaId) {
		return categoriaRepository.findById(categoriaId)
				.orElseThrow(() -> new CategoriaNaoEncontradaException(categoriaId));
	}
}
