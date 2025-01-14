package com.HappySchool.Project.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.HappySchool.Project.entities.Professor;
import com.HappySchool.Project.services.ProfessorService;
import com.HappySchool.Project.servicesException.RegistrationExceptions;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/professors")
public class ProfessorController {

	@Autowired
	private ProfessorService service;

	@GetMapping
	public ResponseEntity<List<Professor>> findAll() {
		List<Professor> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{matricula}")
	public ResponseEntity<Professor> findById(@PathVariable Long matricula) {
		Professor obj = service.findById(matricula);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping
	public ResponseEntity<?> insert(@RequestBody @Valid Professor Professor) {
		Professor obj = service.insert(Professor);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{matricula}")
				.buildAndExpand(obj.getMatricula()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}

	@DeleteMapping(value = "/{matricula}")
	public ResponseEntity<Professor> delete(@PathVariable Long matricula) {
		service.delete(matricula);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{matricula}")
	public ResponseEntity<?> update(@PathVariable Long matricula, @RequestBody Professor newProfessor) {

		newProfessor = service.update(matricula, newProfessor);
		return ResponseEntity.ok().body(newProfessor);

	}

}
