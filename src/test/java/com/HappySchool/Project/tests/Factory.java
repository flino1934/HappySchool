package com.HappySchool.Project.tests;

import com.HappySchool.Project.entities.Professor;
import com.HappySchool.Project.entities.Student;

public class Factory {
	
	//Factory for Student

	public static Student createStudent() {
		Student student = new Student(1L, "Jane Doe", "70409951820");
		return student;
	}

	public static Student createStudentNewStudentToUpadate() {
		Student student = new Student(1L, "Jane", "70409951820");
		return student;
	}
	public static Student createStudentToUpdate() {
		Student student = new Student(1L, "Maria Brown", "48374255854");
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
	
	
	//Factory for Professor
	

	public static Professor createNewProfessor() {
		Professor newCpfProfessor = new Professor(4L, "Maria", "33457137056", "Java");		
		return newCpfProfessor;
	}

	public static Professor SameCpfProfessor() {
		Professor sameCpfProfessor = new Professor(1L, "Maria", "48374255854", "Java");			
		return sameCpfProfessor;
	}

	public static Professor createProfessor() {
		Professor createProfessor = new Professor(1L, "Joao", "48374255854", "Java");		
		return  createProfessor;
	}
	public static Professor createProfessorToUpdate() {
		Professor Professor = new Professor(3L, "Maria Brown", "48374255854", "Java");
		return Professor;
	}
	
	public static Professor createProfessorId3() {
		Professor createProfessor = new Professor(3L, "Joao", "48374255854", "Java");		
		return  createProfessor;
	}

}
