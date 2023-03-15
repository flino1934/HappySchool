package com.HappySchool.Project.tests;

import com.HappySchool.Project.entities.Student;

public class Factory {
	
	public static Student createStudent() {
		Student student = new Student(null, "Maria Brown", "48374255854");
		return student;
	}

}
