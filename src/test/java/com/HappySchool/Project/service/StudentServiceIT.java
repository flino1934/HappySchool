package com.HappySchool.Project.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
	void setUp() throws Exception{
		existingId = 1L;
		nonExistingId  = 1000L;
		countTotalStudents = 2L;
		dependentId = 2L;
	}
	
	@Test
	public void deleteShouldDeleteResourceWhenIdExists() {
		service.delete(existingId);
		assertEquals(countTotalStudents-1, repository.count());
	}
	

	@Test
	public void deleteShouldThrowEntityNotFoundWhenIdDoesNotExists() {
		assertThrows(EntityNotFoundExceptions.class, () -> {
			service.delete(nonExistingId);
		});
	}
	
	@Test
	@DisplayName("It should thrown databaseException")
	public void deleteShouldThrownDatabaseExceptionWhendependentId() {

		assertThrows(DatabaseExceptions.class, () -> {
	    service.delete(dependentId);
		});

	}
	
	
	

}
