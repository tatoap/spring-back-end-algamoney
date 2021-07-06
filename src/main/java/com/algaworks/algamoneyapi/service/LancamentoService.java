package com.algaworks.algamoneyapi.service;

import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.algaworks.algamoneyapi.dto.LancamentoEstatisticaPessoa;
import com.algaworks.algamoneyapi.exception.LancamentoNaoEncontradoException;
import com.algaworks.algamoneyapi.exception.ReportException;
import com.algaworks.algamoneyapi.mail.Mailer;
import com.algaworks.algamoneyapi.model.Categoria;
import com.algaworks.algamoneyapi.model.Lancamento;
import com.algaworks.algamoneyapi.model.Pessoa;
import com.algaworks.algamoneyapi.model.Usuario;
import com.algaworks.algamoneyapi.repository.LancamentoRepository;
import com.algaworks.algamoneyapi.repository.UsuarioRepository;
import com.algaworks.algamoneyapi.storage.S3;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class LancamentoService {
	
	private static final String DESTINATARIOS = "ROLE_PESQUISAR_LANCAMENTO";
	
	private static final Logger logger = LoggerFactory.getLogger(LancamentoService.class);

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private S3 s3;
	
	@Autowired
	private Mailer mailer;
	
	@Scheduled(cron = "0 0 6 * * *" )
	//@Scheduled(fixedDelay = 1000 * 60 * 30)
	public void avisarSobreLancamentosVencidos() {
		if (logger.isDebugEnabled()) {
			logger.debug("Preparando o envio de "
					+ "e-mails de aviso de lançamentos vencidos!");
		}
		
		List<Lancamento> vencidos = lancamentoRepository
				.findByDataVencimentoLessThanEqualAndDataPagamentoIsNull(LocalDate.now());
		
		if (vencidos.isEmpty()) {
			logger.info("Sem lançamentos vencidos para aviso.");
			
			return;
		}
		
		logger.info("Existem {} lançamentos vencidos.", vencidos.size());
		
		List<Usuario> destinatarios = usuarioRepository.findByPermissoesDescricao(DESTINATARIOS);
		
		if (destinatarios.isEmpty()) {
			logger.warn("Existem lançamentos vencidos, mas não foram "
					+ "encontrados os destinatários!");
			
			return;
		}
		
		mailer.avisarSobreLancamentosVencidos(vencidos, destinatarios);
		
		logger.info("Envio de e-mail(s) de aviso concluído.");
	}
	
	@Transactional
	public Lancamento salvar(@RequestBody Lancamento lancamento) {
		
		Pessoa pessoa = pessoaService.buscarOuFalhar(lancamento.getPessoa().getId());
		
		Categoria categoria = categoriaService.buscarOuFalhar(lancamento.getCategoria().getId());
		
		System.out.println(lancamento.getPessoa().getId().toString());
		System.out.println(lancamento.getCategoria().getId().toString());
		
		lancamento.setPessoa(pessoa);
		lancamento.setCategoria(categoria);
		
		if (StringUtils.hasText(lancamento.getAnexo())) {
			s3.salvar(lancamento.getAnexo());
		}
			
		return lancamentoRepository.save(lancamento);
	}
	
	public Lancamento atualizar(Lancamento lancamento, Long lancamentoId) {
		Lancamento lancamentoSalvo = buscarOuFalhar(lancamentoId);
				
		if (ObjectUtils.isEmpty(lancamento.getAnexo())
				&& StringUtils.hasText(lancamentoSalvo.getAnexo())) {
			
			s3.remover(lancamentoSalvo.getAnexo());
			
		} else if (StringUtils.hasText(lancamento.getAnexo()) 
				&& !lancamento.getAnexo().equals(lancamentoSalvo.getAnexo())) {
			
			s3.substituir(lancamentoSalvo.getAnexo(), lancamento.getAnexo());
			
		}
		
		BeanUtils.copyProperties(lancamento, lancamentoSalvo, "id");
		
		return lancamentoRepository.save(lancamento);
		
	}
		
	@Transactional
	public void excluir(@PathVariable Long lancamentoId) {
		Lancamento lancamento = buscarOuFalhar(lancamentoId);
		
		lancamentoRepository.delete(lancamento);
	}
	
	public byte[] relatorioPorPessoa(LocalDate inicio, LocalDate fim) throws Exception {
		try {
			List<LancamentoEstatisticaPessoa> dados = lancamentoRepository.porPessoa(inicio, fim);
			
			Map<String, Object> parametros = new HashMap<>();
			parametros.put("DT_INICIO", Date.valueOf(inicio));
			parametros.put("DT_FIM", Date.valueOf(fim));
			parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));
			
			InputStream inputStream = this.getClass().getResourceAsStream("/relatorios/lancamentos-por-pessoa.jasper");
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros,
					new JRBeanCollectionDataSource(dados));
			
			return JasperExportManager.exportReportToPdf(jasperPrint);
		} catch (Exception e) {
			throw new ReportException("Não foi possível emitir o relatório!");
		}
	}
	
	public Lancamento buscarOuFalhar(Long lancamentoId) {
		return lancamentoRepository.findById(lancamentoId)
				.orElseThrow(() -> new LancamentoNaoEncontradoException(lancamentoId));
	}
	
}
