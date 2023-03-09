package com.HappySchool.Project.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.HappySchool.Project.entities.Diretor;
import com.HappySchool.Project.repository.DiretorRepository;
import com.HappySchool.Project.servicesException.DataExceptions;
import com.HappySchool.Project.servicesException.EntityNotFoundExceptions;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DiretorService {

	@Autowired
	private DiretorRepository repository;

	public List<Diretor> findAll() {
		return repository.findAll();

	}

	public Diretor findById(Integer matricula) {
		try{Optional<Diretor> obj = repository.findById(matricula);
		return obj.get();
	}catch (NoSuchElementException e) {
		throw new EntityNotFoundExceptions("Matricula: " + matricula + " doesn't exist");
	}
	}
	public boolean cpfExists(String cpf) {
		Optional<Diretor> DiretorOptional = repository.findByCpf(cpf);
		return DiretorOptional.isPresent();
	}

	public Diretor insert(Diretor obj) {
		try {return repository.save(obj);

		}catch(DataIntegrityViolationException e){
			throw new DataExceptions("There are Null fields");
		}
		}

	public void delete(Integer matricula) {
		repository.findById(matricula).map(Diretor -> {
			repository.delete(Diretor);
			return Void.TYPE;
		}).orElseThrow(() -> new EntityNotFoundExceptions("Matricula: " + matricula + " doesn't exist"));

	}

	public Diretor update(Integer matricula, Diretor upDiretor) {
		try {
			Diretor entity = repository.getReferenceById(matricula);
			entity.setNome(upDiretor.getNome());
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new EntityNotFoundExceptions("Matricula: " + matricula + " doesn't exist");
		}catch(DataIntegrityViolationException e){
			throw new DataExceptions("There are Null fields");
		}
	}

}
