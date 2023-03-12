package com.HappySchool.Project.Tests;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.HappySchool.Project.entities.Student;
import com.HappySchool.Project.repository.StudentRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner{
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		Student st1 = new Student(null, "Maria Brown", "48374255854");
		Student st2 = new Student(null, "Alex Green", "70409951820");
		
		studentRepository.saveAll(Arrays.asList(st1,st2));
	}

}
