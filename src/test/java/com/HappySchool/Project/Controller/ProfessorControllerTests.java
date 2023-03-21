package com.HappySchool.Project.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.HappySchool.Project.controller.ProfessorController;
import com.HappySchool.Project.entities.Professor;
import com.HappySchool.Project.services.ProfessorService;
import com.HappySchool.Project.servicesException.DatabaseExceptions;
import com.HappySchool.Project.servicesException.EntityNotFoundExceptions;
import com.HappySchool.Project.tests.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ProfessorController.class)
public class ProfessorControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProfessorService service;

	@Autowired
	private ObjectMapper objectMapper;

	private Long existingId;
	private Long nonExistingId;
	private Long dependentId;
	private List<Professor> professors;
	private Professor professor;

	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		dependentId = 3L;
		professor = new Professor(null, "Oliver", "83134601052", "Java");
		professors = Arrays.asList(new Professor(null, "John", "48374255854", "Java"),
				new Professor(null, "Jane", "70409951820", "Python"));

		// Void methods
		doNothing().when(service).delete(existingId);
		doThrow(EntityNotFoundExceptions.class).when(service).delete(nonExistingId);
		doThrow(DatabaseExceptions.class).when(service).delete(dependentId);

		// methods with return

		// list students
		when(service.findAll()).thenReturn(professors);
		// student by id
		when(service.findById(existingId)).thenReturn(professor);
		// student by id
		when(service.findById(nonExistingId)).thenThrow(EntityNotFoundExceptions.class);
		// update
		when(service.update(eq(existingId), any())).thenReturn(professor);
		// update exception
		when(service.update(eq(nonExistingId), any())).thenThrow(EntityNotFoundExceptions.class);
		//
		when(service.insert(any())).thenReturn(professor);
	}

}
