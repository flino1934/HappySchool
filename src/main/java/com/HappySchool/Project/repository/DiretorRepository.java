package com.HappySchool.Project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HappySchool.Project.entities.Diretor;

public interface DiretorRepository extends JpaRepository<Diretor, Integer> {
	
	Optional<Diretor> findByCpf(String cpf);

}
