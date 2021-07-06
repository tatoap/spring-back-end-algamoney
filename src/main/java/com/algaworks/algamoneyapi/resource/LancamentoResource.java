package com.algaworks.algamoneyapi.resource;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.algamoneyapi.dto.Anexo;
import com.algaworks.algamoneyapi.dto.LancamentoEstatisticaCategoria;
import com.algaworks.algamoneyapi.dto.LancamentoEstatisticaDia;
import com.algaworks.algamoneyapi.event.RecursoCriadoEvent;
import com.algaworks.algamoneyapi.exception.CategoriaNaoEncontradaException;
import com.algaworks.algamoneyapi.exception.NegocioException;
import com.algaworks.algamoneyapi.exception.PessoaNaoEncontradaException;
import com.algaworks.algamoneyapi.model.Lancamento;
import com.algaworks.algamoneyapi.projection.LancamentoResumo;
import com.algaworks.algamoneyapi.repository.LancamentoRepository;
import com.algaworks.algamoneyapi.repository.filter.LancamentoFilter;
import com.algaworks.algamoneyapi.service.LancamentoService;
import com.algaworks.algamoneyapi.storage.S3;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

	@Autowired
	private LancamentoService lancamentoService;

	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private S3 s3;
	
	@PostMapping("/anexo")
	//@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and hasAuthority('SCOPE_write')")
	public Anexo uploadAnexo(@RequestParam MultipartFile anexo) throws Exception {
		String nome = s3.salvarTemporariamente(anexo);
		return new Anexo(nome, s3.configurarUrl(nome));
	}

	@GetMapping
	//@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and hasAuthority('SCOPE_read')")
	public Page<Lancamento> pesquisar(LancamentoFilter lancamentoFilter, Pageable pageable) {
		return lancamentoRepository.filtrar(lancamentoFilter, pageable);
	}
	
	@GetMapping(params = "resumo")
	//@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and hasAuthority('SCOPE_read')")
	public Page<LancamentoResumo> resumir(LancamentoFilter lancamentoFilter, Pageable pageable) {
		return lancamentoRepository.resumir(lancamentoFilter, pageable);
	}

	@GetMapping("/{lancamentoId}")
	//@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and hasAuthority('SCOPE_read')")
	public Lancamento buscar(@PathVariable Long lancamentoId) {
		Lancamento lancamentoSalvo = lancamentoService.buscarOuFalhar(lancamentoId);
		lancamentoSalvo.setUrlAnexo(this.s3.configurarUrl(lancamentoSalvo.getAnexo()));
		return lancamentoSalvo;
	}
	
	@GetMapping("/estatisticas/por-dia")
	//@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and hasAuthority('SCOPE_read')")
	public List<LancamentoEstatisticaDia> porDia() {
		return lancamentoRepository.porDia(LocalDate.now().withMonth(6));
	}
	
	@GetMapping("/estatisticas/por-categoria")
	//@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and hasAuthority('SCOPE_read')")
	public List<LancamentoEstatisticaCategoria> porCategoria() {
		return lancamentoRepository.porCategoria(LocalDate.now());
	}
	
	@GetMapping("/relatorios/por-pessoa")
	//@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and hasAuthority('SCOPE_read')")
	public ResponseEntity<byte[]> relatorioPorPessoa(
			@RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate inicio,
			@RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate fim) throws Exception {
		byte[] relatorio = lancamentoService.relatorioPorPessoa(inicio, fim);
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
				.body(relatorio);
	}

	@PostMapping
	//@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and hasAuthority('SCOPE_write')")
	public ResponseEntity<Lancamento> salvar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
		try {
			Lancamento lancamentoSalvo = lancamentoService.salvar(lancamento);
		
			publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getId()));
		
			return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
		} catch (PessoaNaoEncontradaException | CategoriaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@PutMapping("/{lancamentoId}")
	//@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and hasAuthority('SCOPE_write')")
	public ResponseEntity<Lancamento> atualizar(@PathVariable Long lancamentoId, @Valid @RequestBody Lancamento lancamento) {
		try {
			Lancamento lancamentoSalvo = lancamentoService.buscarOuFalhar(lancamentoId);
			
			lancamentoService.atualizar(lancamento, lancamentoId);
			
			return ResponseEntity.status(HttpStatus.OK).body(lancamentoSalvo);
		} catch (PessoaNaoEncontradaException | CategoriaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@DeleteMapping("/{lancamentoId}")
	//@PreAuthorize("hasAuthority('ROLE_REMOVER_LANCAMENTO') and #oauth2.hasScope('write')")
	@PreAuthorize("hasAuthority('ROLE_REMOVER_LANCAMENTO') and hasAuthority('SCOPE_write')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long lancamentoId) {
		lancamentoService.excluir(lancamentoId);
	}

}
