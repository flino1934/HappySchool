package com.HappySchool.Project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.HappySchool.Project.entities.Grades;
import com.HappySchool.Project.services.GradesService;

@RestController
@RequestMapping(value = "/grades")
public class GradesController {

	@Autowired
	private GradesService service;

	@GetMapping
	public ResponseEntity<List<Grades>> findAll() {
		List<Grades> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

}
