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

import com.HappySchool.Project.entities.Student;
import com.HappySchool.Project.services.StudentService;
import com.HappySchool.Project.servicesException.RegistrationExceptions;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/students")
public class StudentController {

	@Autowired
	private StudentService service;

	@GetMapping
	public ResponseEntity<List<Student>> findAll() {
		List<Student> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{matricula}")
	public ResponseEntity<Student> findById(@PathVariable Integer matricula) {
		Student obj = service.findById(matricula);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping
	public ResponseEntity<?> insert(@RequestBody @Valid Student student) {
		Student obj = service.insert(student);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{matricula}")
				.buildAndExpand(obj.getMatricula()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}

	@DeleteMapping(value = "/{matricula}")
	public ResponseEntity<Student> delete(@PathVariable Integer matricula) {
		service.delete(matricula);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{matricula}")
	public ResponseEntity<?> update(@PathVariable Integer matricula, @RequestBody Student newStudent) {

		newStudent = service.update(matricula, newStudent);
		return ResponseEntity.ok().body(newStudent);

	}

}
