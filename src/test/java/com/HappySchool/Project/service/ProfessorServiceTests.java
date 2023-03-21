package com.HappySchool.Project.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.HappySchool.Project.entities.Professor;
import com.HappySchool.Project.repository.ProfessorRepository;
import com.HappySchool.Project.services.ProfessorService;
import com.HappySchool.Project.servicesException.DatabaseExceptions;
import com.HappySchool.Project.servicesException.EntityNotFoundExceptions;
import com.HappySchool.Project.servicesException.RegistrationExceptions;
import com.HappySchool.Project.tests.Factory;

@ExtendWith(SpringExtension.class)
public class ProfessorServiceTests {

	@InjectMocks
	private ProfessorService service;

	@Mock
	private ProfessorRepository repository;

	private Long existingId;
	private Long nonExistingId;
	private List<Professor> Professors;
	private Professor Professor;
	private Professor SameCpfProfessor;
	private Professor SameCpfProfessor1;
	private String existingCpf;
	private Long dependentId;

	@BeforeEach
	void setUp() throws Exception {
		dependentId = 3L;
		existingId = 1L;
		nonExistingId = 1000L;
		existingCpf = "33457137056";
		SameCpfProfessor = Factory.createNewProfessor();
		SameCpfProfessor1 = Factory.SameCpfProfessor();
		Professor = Factory.createProfessor();
		Professors = Arrays.asList(new Professor(null, "John", "48374255854", "Java"), new Professor(null, "Jane", "70409951820", "Java"));

	}

	@Test
	@DisplayName("it should throw exception when exist a Professor with the same cpf")
	public void InsertShouldNotReturnProfessorWhenCpfExists() {

		// when
		when(repository.findByCpf(existingCpf)).thenReturn(Optional.of(SameCpfProfessor));

		// then
		assertThrows(RegistrationExceptions.class, () -> {
			service.insert(SameCpfProfessor1);
		});
		verify(repository, never()).save(any());
	}

	@Test
	@DisplayName("it should save a Professor")
	public void InsertShouldReturnProfessorWhenIdExists() {
		// when
		when(repository.save(any())).thenReturn(Professor);
		Professor savedProfessor = service.insert(Professor);

		// then
		assertNotNull(savedProfessor);
		assertEquals(Professor, savedProfessor);
		verify(repository, Mockito.times(1)).save(Professor);
	}

	@Test
	@DisplayName("it should update a Professor")
	public void UpdateShouldReturnProfessorWhenIdExist() {

		// when
		when(repository.findById(existingId)).thenReturn(Optional.of(Professor));
		when(repository.save(Professor)).thenReturn(Professor);
		Professor entity = service.update(existingId, Professor);

		// then
		assertNotNull(entity);
		assertEquals(Professor, entity);
		assertEquals(Professor.getNome(), entity.getNome());
		verify(repository, Mockito.times(1)).findById(existingId);
		verify(repository, Mockito.times(1)).save(Professor);

	}

	@Test
	@DisplayName("update an inexistent id should display an EntityNotFoundException")
	public void UpdateShouldReturnProfessorWhenIdDoesNotExist() {

		// when
		when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

		// then
		assertThrows(EntityNotFoundExceptions.class, () -> {
			service.update(nonExistingId, Professor);
		});
		verify(repository, Mockito.times(1)).findById(nonExistingId);
		verify(repository, Mockito.never()).save(any());
	}

	@Test
	@DisplayName("It should thrown EntityNotFoundException")
	public void findByIdShouldReturnProfessorWhenIdDoesNotExist() {
		// when
		Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

		// then
		assertThrows(EntityNotFoundExceptions.class, () -> {
			service.findById(nonExistingId);
		});
		verify(repository, Mockito.times(1)).findById(nonExistingId);
	}

	@Test
	@DisplayName("It should return a Professor by Id")
	public void findByIdShouldReturnProfessorWhenIdExist() {
		// when
	    when(repository.findById(existingId)).thenReturn(Optional.of(Professor));
		Professor result = service.findById(existingId);

		// then
		assertNotNull(result);
		assertEquals(Professor, result);
		verify(repository, Mockito.times(1)).findById(existingId);
	}

	@Test
	@DisplayName("Should return a list of Professors")
	public void testFindAll() {

		// when
		Mockito.when(repository.findAll()).thenReturn(Professors);
		List<Professor> result = service.findAll();

		// then
		Assertions.assertNotNull(result);
		assertEquals(Professors.size(), result.size());
		assertEquals(Professors, result);
		verify(repository, Mockito.times(1)).findAll();
	}

	@Test
	@DisplayName("Delete should thrown EntityNotFoundException")
	public void deleteShouldThrownEntityNotFoundExceptionWhenIdNotExists() {

		// when
		when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

		// then
		assertThrows(EntityNotFoundExceptions.class, () -> {
			service.delete(nonExistingId);
		});
		verify(repository, Mockito.times(1)).findById(nonExistingId);
		verify(repository, Mockito.never()).delete(any());
	}

	@Test
	@DisplayName("Delete should throw nothing")
	public void deleteShouldDoNothingWhenIdExists() {

		// when
		Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(Professor));
		service.delete(existingId);

		// then
		verify(repository, Mockito.times(1)).findById(existingId);
		verify(repository, Mockito.times(1)).delete(Professor);
	}
	

	@Test
	@DisplayName("Delete should thrown DatabaseException")
	public void deleteShouldThrownDatabaseExceptionWhenIdisDependent() {

	
		// when
		doThrow(DatabaseExceptions.class).when(repository).findById(dependentId);
		// then
		assertThrows(DatabaseExceptions.class, () -> {
			service.delete(dependentId);
		});
		verify(repository, Mockito.times(1)).findById(dependentId);
		verify(repository, Mockito.never()).delete(any());
	}

}
