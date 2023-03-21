package com.HappySchool.Project.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.HappySchool.Project.controller.StudentController;
import com.HappySchool.Project.entities.Student;
import com.HappySchool.Project.services.StudentService;
import com.HappySchool.Project.servicesException.DatabaseExceptions;
import com.HappySchool.Project.servicesException.EntityNotFoundExceptions;
import com.HappySchool.Project.tests.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StudentService service;

	@Autowired
	private ObjectMapper objectMapper;

	private Long existingId;
	private Long nonExistingId;
	private Long dependentId;
	private List<Student> students;
	private Student student;

	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		dependentId = 3L;
		student = Factory.createStudent();
		students = Arrays.asList(new Student(null, "John", "48374255854"), new Student(null, "Jane", "70409951820"));

		// Void methods
		doNothing().when(service).delete(existingId);
		doThrow(EntityNotFoundExceptions.class).when(service).delete(nonExistingId);
		doThrow(DatabaseExceptions.class).when(service).delete(dependentId);

		// methods with return

		// list students
		when(service.findAll()).thenReturn(students);
		// student by id
		when(service.findById(existingId)).thenReturn(student);
		// student by id
		when(service.findById(nonExistingId)).thenThrow(EntityNotFoundExceptions.class);
		// update
		when(service.update(eq(existingId), any())).thenReturn(student);
		// update exception
		when(service.update(eq(nonExistingId), any())).thenThrow(EntityNotFoundExceptions.class);
		//
		when(service.insert(any())).thenReturn(student);
	}

	@Test
	public void DeleteShouldReturnNoContentWhenIdExists() throws Exception {
		ResultActions result = mockMvc.perform(delete("/students/{id}", existingId).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isNoContent());
	}

	@Test
	public void DeleteShouldReturnNotFoundWhenIdDoesNotExists() throws Exception {
		ResultActions result = mockMvc
				.perform(delete("/students/{id}", nonExistingId).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isNotFound());
	}

	@Test
	public void DeleteShouldReturnDatabaseExceptionWhenIdIsDependent() throws Exception {
		ResultActions result = mockMvc
				.perform(delete("/students/{id}", dependentId).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isBadRequest());
	}

	@Test
	public void InsertShouldCreateStudent() throws Exception {
		String jsonBody = objectMapper.writeValueAsString(student);
		ResultActions result = mockMvc.perform(post("/students").content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isCreated());
		result.andExpect(jsonPath("$.matricula").exists());
		result.andExpect(jsonPath("$.nome").exists());
		result.andExpect(jsonPath("$.cpf").exists());
	}

	@Test
	public void updateShouldReturnStudentWhenIdExists() throws Exception {
		String jsonBody = objectMapper.writeValueAsString(student);
		ResultActions result = mockMvc.perform(put("/students/{id}", existingId).content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.matricula").exists());
		result.andExpect(jsonPath("$.nome").exists());
		result.andExpect(jsonPath("$.cpf").exists());
	}

	@Test
	public void updateShouldReturnNotFoundWhenIdDoesNotExists() throws Exception {

		String jsonBody = objectMapper.writeValueAsString(student);
		ResultActions result = mockMvc.perform(put("/students/{id}", nonExistingId).content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isNotFound());

	}

	@Test
	public void findAllShouldReturnStudents() throws Exception {
		ResultActions result = mockMvc.perform(get("/students").accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());

	}

	@Test
	public void findByIdShouldReturnStudentWhenIdExists() throws Exception {
		ResultActions result = mockMvc.perform(get("/students/{id}", existingId).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.matricula").exists());
		result.andExpect(jsonPath("$.nome").exists());
		result.andExpect(jsonPath("$.cpf").exists());

	}

	@Test
	public void findByIdShouldReturnEntityNotFoundExceptionsWhenIdExists() throws Exception {
		ResultActions result = mockMvc.perform(get("/students/{id}", nonExistingId).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isNotFound());

	}

}
