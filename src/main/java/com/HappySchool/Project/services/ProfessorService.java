package com.HappySchool.Project.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.HappySchool.Project.entities.Professor;
import com.HappySchool.Project.repository.ProfessorRepository;
import com.HappySchool.Project.servicesException.EntityNotFoundExceptions;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProfessorService {

	@Autowired
	private ProfessorRepository repository;

	public List<Professor> findAll() {
		return repository.findAll();

	}

	public Professor findById(Integer matricula) {
		try{Optional<Professor> obj = repository.findById(matricula);
		return obj.get();
	}catch (NoSuchElementException e) {
		throw new EntityNotFoundExceptions("Matricula: " + matricula + " doesn't exist");
	}
	}
	public boolean cpfExists(String cpf) {
		Optional<Professor> ProfessorOptional = repository.findByCpf(cpf);
		return ProfessorOptional.isPresent();
	}

	public Professor insert(Professor obj) {
		return repository.save(obj);

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
		}
	}

}
