package com.HappySchool.Project.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.HappySchool.Project.entities.Curso;
import com.HappySchool.Project.repository.CouseRepository;
import com.HappySchool.Project.servicesException.DataExceptions;
import com.HappySchool.Project.servicesException.EntityNotFoundExceptions;
import com.HappySchool.Project.servicesException.RegistrationExceptions;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CourseService {

	@Autowired
	private final CouseRepository repository;

	public CourseService(CouseRepository repository) {
		this.repository = repository;

	}

	public List<Curso> findAll() {
		return repository.findAll();

	}

	public Curso findById(Integer id) {
		return repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundExceptions("Course: " + id + " doesn't exist"));

	}

	public Curso insert(Curso obj) {
		if (repository.findByNome(obj.getNome())) {
			throw new RegistrationExceptions("This CPF already exist");
		}
		try {
			return repository.save(obj);
		} catch (DataIntegrityViolationException e) {
			throw new DataExceptions("There are Null fields");
		}
	}

	public void delete(Integer id) {
		repository.findById(id).map(Curso -> {
			repository.delete(Curso);
			return Void.TYPE;
		}).orElseThrow(() -> new EntityNotFoundExceptions("Course: " + id + " doesn't exist"));
	}

	public Curso update(Integer id, Curso upCurso) {
		try {
			Curso entity = repository.getReferenceById(id);
			entity.setNome(upCurso.getNome());
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundExceptions("Course: " + id + " doesn't exist");
		} catch (DataIntegrityViolationException e) {
			throw new DataExceptions("There are Null fields");
		}

	}
}
