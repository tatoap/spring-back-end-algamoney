package com.algaworks.algamoneyapi.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algamoneyapi.exception.EntidadeEmUsoException;
import com.algaworks.algamoneyapi.exception.PessoaNaoEncontradaException;
import com.algaworks.algamoneyapi.model.Pessoa;
import com.algaworks.algamoneyapi.repository.PessoaRepository;

@Service
public class PessoaService {
	
	private static final String MSG_PESSOA_EM_USO = "Pessoa de código %d não pode ser excluido pois está em uso.";

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Transactional
	public Pessoa salvar(Pessoa pessoa) {
		pessoa.getContatos().forEach(c -> c.setPessoa(pessoa));
		return pessoaRepository.save(pessoa);
	}
	
	@Transactional
	public Pessoa atualizar(Long pessoaId, Pessoa pessoa) {
		Pessoa pessoaSalva = buscarOuFalhar(pessoaId);
		
		pessoaSalva.getContatos().clear();
		pessoaSalva.getContatos().addAll(pessoa.getContatos());
		pessoaSalva.getContatos().forEach(c -> c.setPessoa(pessoaSalva));
		
		BeanUtils.copyProperties(pessoa, pessoaSalva, "id", "contatos");
		
		return pessoaRepository.save(pessoa);
	}
	
	@Transactional
	public void excluir(Long pessoaId) {
		try {
			pessoaRepository.deleteById(pessoaId);
			pessoaRepository.flush();
		} catch(EmptyResultDataAccessException e) {
			throw new PessoaNaoEncontradaException(pessoaId);
		} catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_PESSOA_EM_USO, pessoaId));
		}
	}
	
	@Transactional
	public void ativar(Long pessoaId) {
		Pessoa pessoaAtual = buscarOuFalhar(pessoaId);
		
		pessoaAtual.ativar();
	}
	
	@Transactional
	public void inativar(Long pessoaId) {
		Pessoa pessoaAtual = buscarOuFalhar(pessoaId);
		
		pessoaAtual.inativar();
	}
	
	public Pessoa buscarOuFalhar(Long pessoaId) {
		return pessoaRepository.findById(pessoaId)
				.orElseThrow(() -> new PessoaNaoEncontradaException(pessoaId));
	}
}
