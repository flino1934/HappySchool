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

import com.HappySchool.Project.entities.Student;
import com.HappySchool.Project.repository.StudentRepository;
import com.HappySchool.Project.services.StudentService;
import com.HappySchool.Project.servicesException.DatabaseExceptions;
import com.HappySchool.Project.servicesException.EntityNotFoundExceptions;
import com.HappySchool.Project.servicesException.RegistrationExceptions;
import com.HappySchool.Project.tests.Factory;

@ExtendWith(SpringExtension.class)
public class StudentServiceTests {

	@InjectMocks
	private StudentService service;

	@Mock
	private StudentRepository repository;

	private Long existingId;
	private Long nonExistingId;
	private List<Student> students;
	private Student student;
	private Student studentToUpdate;
	private Student SameCpfStudent;
	private Student SameCpfStudent1;
	private String existingCpf;
	private Long dependentId;

	@BeforeEach
	void setUp() throws Exception {
		dependentId = 2L;
		existingId = 1L;
		nonExistingId = 1000L;
		existingCpf = "48374255854";
		SameCpfStudent = Factory.createNewStudent();
		SameCpfStudent1 = Factory.SameCpfStudent();
		student = Factory.createStudent();
		studentToUpdate = Factory.createStudentNewStudentToUpadate();
		students = Arrays.asList(new Student(null, "John", "48374255854"), new Student(null, "Jane", "70409951820"));

	}

	@Test
	@DisplayName("it should throw exception when exist a student with the same cpf")
	public void InsertShouldNotReturnStudentWhenCpfExists() {

		// when
		when(repository.findByCpf(existingCpf)).thenReturn(Optional.of(SameCpfStudent));

		// then
		assertThrows(RegistrationExceptions.class, () -> {
			service.insert(SameCpfStudent1);
		});
		verify(repository, never()).save(any());
	}

	@Test
	@DisplayName("it should save a student")
	public void InsertShouldReturnStudentWhenIdExists() {
		// when
		when(repository.save(any())).thenReturn(student);
		Student savedStudent = service.insert(student);

		// then
		assertNotNull(savedStudent);
		assertEquals(student, savedStudent);
		verify(repository, Mockito.times(1)).save(student);
	}

	@Test
	@DisplayName("it should update a student")
	public void UpdateShouldReturnStudentWhenIdExist() {

		// when
		when(repository.findById(existingId)).thenReturn(Optional.of(student));
		when(repository.save(any())).thenReturn(student);
		Student studentAlreadyupdated = service.update(existingId, studentToUpdate);

		// then
		assertNotNull(studentAlreadyupdated);
		assertEquals(student, studentAlreadyupdated);
		assertEquals(studentToUpdate.getNome(), studentAlreadyupdated.getNome());
		verify(repository, Mockito.times(1)).findById(existingId);
		verify(repository, Mockito.times(1)).save(student);

	}

	@Test
	@DisplayName("update an inexistent id should display an EntityNotFoundException")
	public void UpdateShouldReturnStudentWhenIdDoesNotExist() {

		// when
		when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

		// then
		assertThrows(EntityNotFoundExceptions.class, () -> {
			service.update(nonExistingId, student);
		});
		verify(repository, Mockito.times(1)).findById(nonExistingId);
		verify(repository, Mockito.never()).save(any());
	}

	@Test
	@DisplayName("It should thrown EntityNotFoundException")
	public void findByIdShouldReturnStudentWhenIdDoesNotExist() {
		// when
		Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

		// then
		assertThrows(EntityNotFoundExceptions.class, () -> {
			service.findById(nonExistingId);
		});
		verify(repository, Mockito.times(1)).findById(nonExistingId);
	}

	@Test
	@DisplayName("It should return a student by Id")
	public void findByIdShouldReturnStudentWhenIdExist() {
		// when
	    when(repository.findById(existingId)).thenReturn(Optional.of(student));
		Student result = service.findById(existingId);

		// then
		assertNotNull(result);
		assertEquals(student, result);
		verify(repository, Mockito.times(1)).findById(existingId);
	}

	@Test
	@DisplayName("Should return a list of students")
	public void testFindAll() {

		// when
		Mockito.when(repository.findAll()).thenReturn(students);
		List<Student> result = service.findAll();

		// then
		assertNotNull(result);
		assertEquals(students.size(), result.size());
		assertEquals(students, result);
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
		Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(student));
		service.delete(existingId);

		// then
		verify(repository, Mockito.times(1)).findById(existingId);
		verify(repository, Mockito.times(1)).delete(student);
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
