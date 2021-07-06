package com.algaworks.algamoneyapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.algamoneyapi.model.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {

	List<Cidade> findByEstadoId(Long estadoId);
	
}
