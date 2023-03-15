package com.HappySchool.Project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HappySchool.Project.entities.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
	
	Optional<Student> findByCpf(String cpf);

}
