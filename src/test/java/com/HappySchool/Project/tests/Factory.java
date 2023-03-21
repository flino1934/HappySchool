package com.HappySchool.Project.tests;

import com.HappySchool.Project.entities.Student;

public class Factory {

	public static Student createStudent() {
		Student student = new Student(1L, "Jane Doe", "70409951820");
		return student;
	}

	public static Student createStudentToUpdate() {
		Student student = new Student(1L, "Jane", "70409951820");
		return student;
	}

	public static Student createNewStudent() {
		Student newstudent = new Student(1L, "Jane Doe", "48374255854");
		return newstudent;
	}

	public static Student SameCpfStudent() {
		Student sameCpfstudent = new Student(1L, "Maria Brown", "48374255854");
		return sameCpfstudent;
	}

	public static Student CreateStudent5() {
		Student sameCpfstudent = new Student(5L, "Maria", "33457137056");
		return sameCpfstudent;
	}

}
