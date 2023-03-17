package com.HappySchool.Project.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.HappySchool.Project.entities.Student;
import com.HappySchool.Project.repository.StudentRepository;
import com.HappySchool.Project.services.StudentService;
import com.HappySchool.Project.servicesException.DatabaseExceptions;
import com.HappySchool.Project.servicesException.EntityNotFoundExceptions;

@SpringBootTest
public class StudentServiceIT {

	@Autowired
	private StudentService service;

	@Autowired
	private StudentRepository repository;

	private Long existingId;
	private Long nonExistingId;
	private Long countTotalStudents;
	private Long dependentId;

	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		countTotalStudents = 3L;
		dependentId = 3L;
	}

	@Test
	@DisplayName("It should thrown databaseException")
	public void deleteShouldThrownDatabaseExceptionWhendependentId() {

		assertThrows(DatabaseExceptions.class, () -> {
			service.delete(dependentId);
		});

	}

	@Test
	@DisplayName("Delete should not result anything")
	public void deleteShouldDeleteResourceWhenIdExists() {
		service.delete(existingId);
		assertEquals(countTotalStudents - 1, repository.count());
	}

	

	@Test
	@DisplayName("Delete should throw EntityNotFound")
	public void deleteShouldThrowEntityNotFoundWhenIdDoesNotExists() {
		assertThrows(EntityNotFoundExceptions.class, () -> {
			service.delete(nonExistingId);
		});
	}
	
	@Test
	@DisplayName("Should return a list of students")
	public void testFindAll() {

		List<Student> students = service.findAll();
		assertNotNull(students);
		assertEquals(countTotalStudents, students.size());
	}
	
	@Test
	@DisplayName("It should return a student by Id")
	public void findByIdShouldReturnStudentWhenIdExist() {
		Student result = service.findById(existingId);
		assertNotNull(result);
		assertEquals(repository.findById(existingId).get().getCpf(), result.getCpf());
	}
	
	@Test
	@DisplayName("It should thrown EntityNotFoundException")
	public void findByIdShouldReturnStudentWhenIdDoesNotExist() {
		assertThrows(EntityNotFoundExceptions.class, () -> {
			service.findById(nonExistingId);
		});
	}

}
