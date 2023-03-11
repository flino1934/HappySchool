package com.HappySchool.Project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HappySchool.Project.entities.Curso;
import com.HappySchool.Project.entities.Student;

public interface CouseRepository extends JpaRepository<Curso, Integer> {
	
	boolean findByNome(String nome);

}
