package com.HappySchool.Project.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.HappySchool.Project.entities.Student;
import com.HappySchool.Project.repository.StudentRepository;
import com.HappySchool.Project.services.StudentService;
import com.HappySchool.Project.servicesException.DatabaseExceptions;
import com.HappySchool.Project.servicesException.EntityNotFoundExceptions;
import com.HappySchool.Project.servicesException.RegistrationExceptions;
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
	private Student newCpfStudent;
	private Student SameCpfStudent;
	private String existingCpf;

	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		dependentId = 3L;
		existingCpf = "48374255854";
		newCpfStudent = Factory.createNewStudent();
		SameCpfStudent = Factory.SameCpfStudent();
		student = Factory.createStudent();
		students = Arrays.asList(new Student(null, "John", "48374255854"), new Student(null, "Jane", "70409951820"));

		// methods void

		// delete by id
		doNothing().when(repository).deleteById(existingId);
		// student id not found to delete
		doThrow(EntityNotFoundException.class).when(repository).deleteById(nonExistingId);
		// id dependent in another table
		doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);

		// methods with return

		// list students
		Mockito.when(repository.findAll()).thenReturn(students);
		// save student
		Mockito.when(repository.save(any())).thenReturn(student);
		// student by id
		Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(student));
		// student id not found
		Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
		// findByCpf inside insert method
		Mockito.when(repository.findByCpf(existingCpf)).thenReturn(Optional.of(newCpfStudent));

	}

	@Test
	@DisplayName("it should not save a student with the same cpf")
	public void InsertShouldNotReturnStudentWhenCpfExists() {
		assertThrows(RegistrationExceptions.class, () -> {
			service.insert(SameCpfStudent);
		});
		 verify(repository, never()).save(student);
	}

	@Test
	@DisplayName("it should save a student")
	public void InsertShouldReturnStudentWhenIdExists() {

		Student entity = service.insert(student);
		assertNotNull(entity);
		assertEquals(student, entity);
		verify(repository, Mockito.times(1)).save(student);
	}

	@Test
	@DisplayName("it should update a student")
	public void UpdateShouldReturnStudentWhenIdExist() {
		Student entity = service.update(existingId, student);
		assertNotNull(entity);
		assertEquals(student, entity);
	    assertEquals(student.getNome(), entity.getNome());
		verify(repository, Mockito.times(1)).findById(existingId);
		verify(repository, Mockito.times(1)).save(student);

	}

	@Test
	@DisplayName("it should display an EntityNotFoundException")
	public void UpdateShouldReturnStudentWhenIdDoesNotExist() {

		assertThrows(EntityNotFoundExceptions.class, () -> {
			service.update(nonExistingId, student);
		});
		verify(repository, Mockito.times(1)).findById(nonExistingId);
		verify(repository, Mockito.never()).save(any());
	}

	@Test
	@DisplayName("It should thrown EntityNotFoundException")
	public void findByIdShouldReturnStudentWhenIdDoesNotExist() {
		assertThrows(EntityNotFoundExceptions.class, () -> {
			service.findById(nonExistingId);
		});
		verify(repository, Mockito.times(1)).findById(nonExistingId);
	}

	@Test
	@DisplayName("It should return a student by Id")
	public void findByIdShouldReturnStudentWhenIdExist() {
		Student result = service.findById(existingId);
		assertNotNull(result);
		assertEquals(student, result);
		verify(repository, Mockito.times(1)).findById(existingId);
	}

	@Test
	@DisplayName("Should return a list of students")
	public void testFindAll() {

		List<Student> result = service.findAll();

		Assertions.assertNotNull(result);
		assertEquals(students.size(), result.size());
		assertEquals(students, result);
		verify(repository, Mockito.times(1)).findAll();
	}

	@Test
	@DisplayName("It should thrown databaseException")
	public void deleteShouldThrownDatabaseExceptionWhendependentId() {

		assertThrows(DatabaseExceptions.class, () -> {
			service.delete(dependentId);
		});

		verify(repository, Mockito.times(1)).deleteById(dependentId);
	}

	@Test
	@DisplayName("It should thrown EntityNotFoundException")
	public void deleteShouldThrownEntityNotFoundExceptionWhenIdNotExists() {

		assertThrows(EntityNotFoundExceptions.class, () -> {
			service.delete(nonExistingId);
		});

		verify(repository, Mockito.times(1)).deleteById(nonExistingId);
	}

	@Test
	@DisplayName("It should throw nothing")
	public void deleteShouldDoNothingWhenIdExists() {

		assertDoesNotThrow(() -> {
			service.delete(existingId);
		});

		verify(repository, Mockito.times(1)).deleteById(existingId);
	}

}
