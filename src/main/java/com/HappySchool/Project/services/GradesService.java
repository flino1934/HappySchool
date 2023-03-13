package com.HappySchool.Project.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.HappySchool.Project.entities.Grades;
import com.HappySchool.Project.repository.GradesRepository;

@Service
public class GradesService {

	@Autowired
	private GradesRepository repository;

	public List<Grades> findAll() {
		return repository.findAll();

	}

	

}
