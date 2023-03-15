package com.HappySchool.Project.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.HappySchool.Project.entities.Professor;
import com.HappySchool.Project.repository.ProfessorRepository;
import com.HappySchool.Project.servicesException.DataExceptions;
import com.HappySchool.Project.servicesException.EntityNotFoundExceptions;
import com.HappySchool.Project.servicesException.RegistrationExceptions;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProfessorService {

	@Autowired
	private ProfessorRepository repository;

	public List<Professor> findAll() {
		return repository.findAll();

	}

	public Professor findById(Integer matricula) {
		return repository.findById(matricula)
				.orElseThrow(() -> new EntityNotFoundExceptions("Course " + matricula + " doesn't exist"));
	}

	public boolean cpfExists(String cpf) {
		Optional<Professor> ProfessorOptional = repository.findByCpf(cpf);
		return ProfessorOptional.isPresent();
	}

	public Professor insert(Professor obj) {
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
		repository.findById(matricula).map(Professor -> {
			repository.delete(Professor);
			return Void.TYPE;
		}).orElseThrow(() -> new EntityNotFoundExceptions("Matricula: " + matricula + " doesn't exist"));

	}

	public Professor update(Integer matricula, Professor upProfessor) {
		try {
			Professor entity = repository.getReferenceById(matricula);
			entity.setNome(upProfessor.getNome());
			entity.setEspecialidade(upProfessor.getEspecialidade());
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundExceptions("Matricula: " + matricula + " doesn't exist");
		} catch (DataIntegrityViolationException e) {
			throw new DataExceptions("There are Null fields");
		}
	}

}
