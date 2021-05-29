package com.algaworks.algamoneyapi.repository.pessoa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.algaworks.algamoneyapi.model.Pessoa;
import com.algaworks.algamoneyapi.repository.filter.PessoaFilter;

public interface PessoaRepositoryQuery {
	
	@Query("from Pessoa p order by p.nome")
	Page<Pessoa> filtrar(PessoaFilter pessoaFilter, Pageable pageable);

}
