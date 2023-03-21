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
		professor = new Professor(1L, "Oliver", "83134601052", "Java");
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
	@Test
	public void DeleteShouldReturnNoContentWhenIdExists() throws Exception {
		ResultActions result = mockMvc.perform(delete("/professors/{id}", existingId).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isNoContent());
	}
	
	@Test
	public void DeleteShouldReturnNotFoundWhenIdDoesNotExists() throws Exception {
		ResultActions result = mockMvc
				.perform(delete("/professors/{id}", nonExistingId).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isNotFound());
	}

	@Test
	public void DeleteShouldReturnDatabaseExceptionWhenIdIsDependent() throws Exception {
		ResultActions result = mockMvc
				.perform(delete("/professors/{id}", dependentId).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isBadRequest());
	}

	@Test
	public void InsertShouldCreateStudent() throws Exception {
		String jsonBody = objectMapper.writeValueAsString(professor);
		ResultActions result = mockMvc.perform(post("/professors").content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isCreated());
		result.andExpect(jsonPath("$.matricula").exists());
		result.andExpect(jsonPath("$.nome").exists());
		result.andExpect(jsonPath("$.cpf").exists());
		result.andExpect(jsonPath("$.especialidade").exists());
	}

	@Test
	public void updateShouldReturnStudentWhenIdExists() throws Exception {
		String jsonBody = objectMapper.writeValueAsString(professor);
		ResultActions result = mockMvc.perform(put("/professors/{id}", existingId).content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.matricula").exists());
		result.andExpect(jsonPath("$.nome").exists());
		result.andExpect(jsonPath("$.cpf").exists());
		result.andExpect(jsonPath("$.especialidade").exists());

	}

	@Test
	public void updateShouldReturnNotFoundWhenIdDoesNotExists() throws Exception {

		String jsonBody = objectMapper.writeValueAsString(professor);
		ResultActions result = mockMvc.perform(put("/professors/{id}", nonExistingId).content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isNotFound());

	}

	@Test
	public void findAllShouldReturnStudents() throws Exception {
		ResultActions result = mockMvc.perform(get("/professors").accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());

	}

	@Test
	public void findByIdShouldReturnStudentWhenIdExists() throws Exception {
		ResultActions result = mockMvc.perform(get("/professors/{id}", existingId).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.matricula").exists());
		result.andExpect(jsonPath("$.nome").exists());
		result.andExpect(jsonPath("$.cpf").exists());
		result.andExpect(jsonPath("$.especialidade").exists());


	}

	@Test
	public void findByIdShouldReturnEntityNotFoundExceptionsWhenIdExists() throws Exception {
		ResultActions result = mockMvc.perform(get("/professors/{id}", nonExistingId).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isNotFound());

	}


}
