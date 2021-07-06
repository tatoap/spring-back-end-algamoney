package com.algaworks.algamoneyapi.resource;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algamoneyapi.event.RecursoCriadoEvent;
import com.algaworks.algamoneyapi.model.Pessoa;
import com.algaworks.algamoneyapi.repository.PessoaRepository;
import com.algaworks.algamoneyapi.repository.filter.PessoaFilter;
import com.algaworks.algamoneyapi.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	//@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and hasAuthority('SCOPE_read')")
	public Page<Pessoa> listar(PessoaFilter pessoaFilter, Pageable pageable) {
		//return pessoaRepository.findAll();
		return pessoaRepository.filtrar(pessoaFilter, pageable);
	}
	
	@GetMapping("/{pessoaId}")
	//@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and hasAuthority('SCOPE_read')")
	public Pessoa buscar(@PathVariable Long pessoaId) {
		return pessoaService.buscarOuFalhar(pessoaId);
	}
	
	@PostMapping
	//@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and hasAuthority('SCOPE_write')")
	public ResponseEntity<Pessoa> salvar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
		pessoaService.salvar(pessoa);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoa.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoa);
	}
	
	@PutMapping("/{pessoaId}")
	//@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and hasAuthority('SCOPE_write')")
	public ResponseEntity<Pessoa> atualizar(@PathVariable Long pessoaId, @Valid @RequestBody Pessoa pessoa){
		Pessoa pessoaSalva = pessoaService.atualizar(pessoaId, pessoa);
		
		return ResponseEntity.ok(pessoaSalva);
	}
	
	@DeleteMapping("/{pessoaId}")
	//@PreAuthorize("hasAuthority('ROLE_REMOVER_PESSOA') and #oauth2.hasScope('write')")
	@PreAuthorize("hasAuthority('ROLE_REMOVER_PESSOA') and hasAuthority('SCOPE_write')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long pessoaId) {
		pessoaService.excluir(pessoaId);
	}
	
	@PutMapping("/{pessoaId}/ativo")
	//@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and hasAuthority('SCOPE_write')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long pessoaId) {
		pessoaService.ativar(pessoaId);
	}
	
	@DeleteMapping("/{pessoaId}/ativo")
	//@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and hasAuthority('SCOPE_write')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long pessoaId) {
		pessoaService.inativar(pessoaId);
	}
}
