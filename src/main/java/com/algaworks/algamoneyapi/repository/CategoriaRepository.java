package com.algaworks.algamoneyapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.algaworks.algamoneyapi.model.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
	
	@Query("from Categoria c order by c.nome")
	List<Categoria> findAll();

}
