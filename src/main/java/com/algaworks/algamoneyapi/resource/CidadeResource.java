package com.algaworks.algamoneyapi.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algamoneyapi.model.Cidade;
import com.algaworks.algamoneyapi.repository.CidadeRepository;
import com.algaworks.algamoneyapi.service.EstadoService;

@RestController
@RequestMapping("/cidades")
public class CidadeResource {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoService estadoService;
	
	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public List<Cidade> pesquisar(@RequestParam Long estadoId){
		System.out.println(estadoId);
		estadoService.buscarOuFalhar(estadoId);
		
		return cidadeRepository.findByEstadoId(estadoId);
	}
} 
