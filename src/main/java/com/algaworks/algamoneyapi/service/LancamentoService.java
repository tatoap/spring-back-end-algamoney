package com.algaworks.algamoneyapi.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.algaworks.algamoneyapi.exception.LancamentoNaoEncontradoException;
import com.algaworks.algamoneyapi.exception.PessoaInativaException;
import com.algaworks.algamoneyapi.model.Categoria;
import com.algaworks.algamoneyapi.model.Lancamento;
import com.algaworks.algamoneyapi.model.Pessoa;
import com.algaworks.algamoneyapi.repository.LancamentoRepository;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Transactional
	public Lancamento salvar(@RequestBody Lancamento lancamento) {
		
		Pessoa pessoa = pessoaService.buscarOuFalhar(lancamento.getPessoa().getId());
		
		if (pessoa.isInativo()) {
			throw new PessoaInativaException(pessoa.getId());
		}
		
		Categoria categoria = categoriaService.buscarOuFalhar(lancamento.getCategoria().getId());
		
		System.out.println(lancamento.getPessoa().getId().toString());
		System.out.println(lancamento.getCategoria().getId().toString());
		
		lancamento.setPessoa(pessoa);
		lancamento.setCategoria(categoria);
		
		return lancamentoRepository.save(lancamento);
	}
	
	@Transactional
	public void excluir(@PathVariable Long lancamentoId) {
		Lancamento lancamento = buscarOuFalhar(lancamentoId);
		
		lancamentoRepository.delete(lancamento);
	}
	
	public Lancamento buscarOuFalhar(Long lancamentoId) {
		return lancamentoRepository.findById(lancamentoId)
				.orElseThrow(() -> new LancamentoNaoEncontradoException(lancamentoId));
	}
}
