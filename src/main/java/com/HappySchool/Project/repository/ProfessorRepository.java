package com.HappySchool.Project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HappySchool.Project.entities.Professor;
import com.HappySchool.Project.entities.Professor;
import com.HappySchool.Project.entities.Student;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
	
	Optional<Professor> findByCpf(String cpf);

}
