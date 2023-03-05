package com.HappySchool.Project.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.HappySchool.Project.entities.Student;
import com.HappySchool.Project.repository.StudentRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class StudentService {

	@Autowired
	private StudentRepository repository;

	@Transactional(readOnly = true)
	public List<Student> findAll() {
		return repository.findAll();

	}

	@Transactional(readOnly = true)
	public Student findById(Integer matricula) {
		Optional<Student> obj = repository.findById(matricula);
		return obj.get();
	}

	@Transactional
	public Student insert(Student obj) {
		return repository.save(obj);
	}

	public void delete(Integer matricula) {
		repository.findById(matricula).map(Student -> {
			repository.delete(Student);
			return Void.TYPE;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estudante nao encontrado"));

	}

	@Transactional
	public Student update(Integer matricula, Student upstudent) {
		try {
			Student entity = repository.getReferenceById(matricula);
			entity.setNome(upstudent.getNome());
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundException("Matricula not found" + matricula);
		}
	}

}
