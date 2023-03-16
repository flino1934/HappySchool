package com.HappySchool.Project.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.HappySchool.Project.entities.Student;
import com.HappySchool.Project.repository.StudentRepository;
import com.HappySchool.Project.services.StudentService;
import com.HappySchool.Project.servicesException.DatabaseExceptions;
import com.HappySchool.Project.servicesException.EntityNotFoundExceptions;
import com.HappySchool.Project.tests.Factory;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
public class StudentServiceTests {

	@InjectMocks
	private StudentService service;

	@Mock
	private StudentRepository repository;

	private long existingId;
	private long nonExistingId;
	private long dependentId;
	private List<Student> students;
	private Student student;

	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		dependentId = 3L;
		student = Factory.createStudent();
		students = Arrays.asList(new Student(null, "John", "48374255854"), new Student(null, "Jane", "70409951820"));

		// methods void
		doNothing().when(repository).deleteById(existingId);
		doThrow(EntityNotFoundException.class).when(repository).deleteById(nonExistingId);
		doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
		// methods with return
		Mockito.when(repository.findAll()).thenReturn(students);
		Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(student);
		Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(student));
		Mockito.when(repository.findById(nonExistingId)).thenThrow(EntityNotFoundExceptions.class);

	}

	@Test
	public void InsertShouldReturnStudentWhenIdExists() {

		Student entity = service.insert(student);
		Assertions.assertNotNull(entity);
		verify(repository, Mockito.times(1)).save(student);
	}

	@Test
	public void UpdateShouldThrowStudentWhenIdDoesNotExist() {
		assertThrows(EntityNotFoundExceptions.class, () -> {
			service.update(nonExistingId, student);
		});

	}

	@Test
	public void UpdateShouldReturnStudentWhenIdExist() {
		Student entity = service.update(existingId, student);
		Assertions.assertNotNull(entity);
		verify(repository, Mockito.times(1)).findById(existingId);

	}

	@Test
	public void findByIdShouldReturnStudentWhenIdDoesNotExist() {
		assertThrows(EntityNotFoundExceptions.class, () -> {
			service.findById(nonExistingId);
		});
		verify(repository, Mockito.times(1)).findById(nonExistingId);
	}

	@Test
	public void findByIdShouldReturnStudentWhenIdExist() {
		Student result = service.findById(existingId);
		Assertions.assertNotNull(result);
		verify(repository, Mockito.times(1)).findById(existingId);
	}

	@Test
	public void testFindAll() {

		List<Student> result = service.findAll();

		Assertions.assertNotNull(result);
		verify(repository, Mockito.times(1)).findAll();
	}

	@Test
	public void deleteShouldThrownDatabaseExceptionWhendependentId() {

		assertThrows(DatabaseExceptions.class, () -> {
			service.delete(dependentId);
		});

		verify(repository, Mockito.times(1)).deleteById(dependentId);
	}

	@Test
	public void deleteShouldThrownEntityNotFoundExceptionWhenIdNotExists() {

		assertThrows(EntityNotFoundExceptions.class, () -> {
			service.delete(nonExistingId);
		});

		verify(repository, Mockito.times(1)).deleteById(nonExistingId);
	}

	@Test
	public void deleteShouldDoNothingWhenIdExists() {

		assertDoesNotThrow(() -> {
			service.delete(existingId);
		});

		verify(repository, Mockito.times(1)).deleteById(existingId);
	}

}
