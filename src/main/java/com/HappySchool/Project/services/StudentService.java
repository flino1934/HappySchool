package com.HappySchool.Project.services;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.HappySchool.Project.entities.Student;
import com.HappySchool.Project.repository.StudentRepository;
import com.HappySchool.Project.servicesException.DataExceptions;
import com.HappySchool.Project.servicesException.DatabaseExceptions;
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

	public Student findById(Long matricula) {
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

	public void delete(Long matricula) {
		try {
			repository.deleteById(matricula);
		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundExceptions("Matricula: " + matricula + " doesn't exist");
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseExceptions("Cannot execute this action");
		}
	}

	public Student update(Long matricula, Student student) {
       try { var studentUpdate = findById(matricula);
        studentUpdate.setNome(student.getNome());
        studentUpdate.setCpf(student.getCpf());
        return repository.save(studentUpdate);
    }catch (EntityNotFoundException e) {
		throw new EntityNotFoundExceptions("Matricula: " + matricula + " doesn't exist");
	}
	
	}
}
