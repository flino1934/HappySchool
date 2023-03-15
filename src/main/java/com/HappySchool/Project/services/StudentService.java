package com.HappySchool.Project.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.HappySchool.Project.entities.Student;
import com.HappySchool.Project.repository.CouseRepository;
import com.HappySchool.Project.repository.ProfessorRepository;
import com.HappySchool.Project.repository.StudentRepository;
import com.HappySchool.Project.servicesException.DataExceptions;
import com.HappySchool.Project.servicesException.EntityNotFoundExceptions;
import com.HappySchool.Project.servicesException.RegistrationExceptions;

import jakarta.persistence.EntityNotFoundException;

@Service
public class StudentService {

	private final StudentRepository repository;

	public StudentService(StudentRepository repository) {
		this.repository = repository;

	}

	public List<Student> findAll() {
		return repository.findAll();

	}

	public Student findById(Integer matricula) {
		return repository.findById(matricula)
				.orElseThrow(() -> new EntityNotFoundExceptions("Course " + matricula + " doesn't exist"));
		}
	

	public boolean cpfExists(String cpf) {
		Optional<Student> StudentOptional = repository.findByCpf(cpf);
		return StudentOptional.isPresent();
	}

	public Student insert(Student obj) {
		if (cpfExists(obj.getCpf())) {
			throw new RegistrationExceptions("This CPF already exist");
		}
		try {
			return repository.save(obj);

		} catch (DataIntegrityViolationException e) {
			throw new DataExceptions("There are Null fields");
		}
	}

	public void delete(Integer matricula) {
		repository.findById(matricula).map(Student -> {
			repository.delete(Student);
			return Void.TYPE;
		}).orElseThrow(() -> new EntityNotFoundExceptions("Matricula: " + matricula + " doesn't exist"));

	}

	public Student update(Integer matricula, Student upstudent) {
		try {
			Student entity = repository.getReferenceById(matricula);
			entity.setNome(upstudent.getNome());
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundExceptions("Matricula: " + matricula + " doesn't exist");
		} catch (DataIntegrityViolationException e) {
			throw new DataExceptions("There are Null fields");
		}

	}
}
