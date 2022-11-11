package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.example.demo.dto.EmployeeDTO;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {
	@Mock
	private EmployeeRepository employeeRepository;
	private EmployeeService underTest;

	@BeforeEach
	void setUp() {
		underTest = new EmployeeService(new ModelMapper(), employeeRepository);
	}

	@Test
	void testGetAllEmployees() {
		// when
		underTest.getAllEmployees();
		// then
		verify(employeeRepository).findAll();
	}

	@Test
	void testCreateEmployee() {
		// Given
		EmployeeDTO employee = new EmployeeDTO("Lionelasda", "Schroeder", "tesasdasdt@gmail.com", 57);
		Employee em = new Employee(1L,"Lionelasda", "Schroeder", "tesasdasdt@gmail.com", 57);
		
		// When

		Mockito.when(employeeRepository.save(any(Employee.class))).thenReturn(em);
		underTest.createEmployee(employee);

		// then
		ArgumentCaptor<Employee> employeeArgumentCaptor = ArgumentCaptor.forClass(Employee.class);

		Mockito.verify(employeeRepository).save(employeeArgumentCaptor.capture());

		Employee captured = employeeArgumentCaptor.getValue();

		assertThat(captured.getEmail()).isEqualTo(employee.getEmail());
		assertThat(captured.getAge()).isEqualTo(employee.getAge());
		assertThat(captured.getLastName()).isEqualTo(employee.getLastName());

	}

	@Test
	void testCreateEmployeeIfEmailAlreadyExistsWillThrowBadRequestException() {
		// Given
		EmployeeDTO employee = new EmployeeDTO("Lionel", "Schroeder", "schroederlionel@gmail.com", 57);

		Mockito.when(employeeRepository.existsByEmail(employee.getEmail())).thenReturn(true);
		// When
		// then
		assertThatThrownBy(() -> underTest.createEmployee(employee)).isInstanceOf(BadRequestException.class)
				.hasMessageContaining("Email " + employee.getEmail() + " taken");

		// check if save was never called
		verify(employeeRepository, never()).save(any());
	}

	@Test
	void testGetEmployeesByÎdIfExists() {
		// Given
		long id = 1L;
		Employee employee = new Employee(id, "Lionel", "Schroeder", "schroederlionel@gmail.com", 57);
		Mockito.when(employeeRepository.findById(employee.getEmployee_id())).thenReturn(Optional.of(employee));
		// When
		EmployeeDTO response = underTest.getEmployeeByÎd(id);
		// Then

		assertThat(response.getEmail()).isEqualTo(employee.getEmail());
		assertThat(response.getFirstName()).isEqualTo(employee.getFirstName());
	}

	@Test
	void testGetEmployeesByÎdIfDoesNotExists() {
		// Given
		Employee employee = new Employee(12345678910L, "Lionel", "Schroeder", "schroederlionel@gmail.com", 57);
		Mockito.when(employeeRepository.findById(employee.getEmployee_id())).thenThrow(new ResourceNotFoundException(
				"Employee with the id " + employee.getEmployee_id() + " does not exist!"));
		// When

		// Then
		assertThatThrownBy(() -> underTest.getEmployeeByÎd(employee.getEmployee_id()))
				.isInstanceOf(ResourceNotFoundException.class)
				.hasMessageContaining("Employee with the id " + employee.getEmployee_id() + " does not exist!");
	}

	@Test
	void testDeleteEmployeeById() {
		// Given
		Long id = 12345678910L;
		// when
		Mockito.when(employeeRepository.existsById(id)).thenReturn(true);
		underTest.deleteEmployee(id);
		// Then
		Mockito.verify(employeeRepository).deleteById(id);

	}

	@Test
	void testDeleteEmployeeByIdThrowsResourceNotFound() {
		// Given
		Long id = 12345678910L;
		// when
		doThrow(new ResourceNotFoundException("Employee with the id " + id + " does not exist"))
				.when(employeeRepository).existsById(id);
		// Then
		assertThatThrownBy(() -> underTest.deleteEmployee(id)).isInstanceOf(ResourceNotFoundException.class)
				.hasMessage("Employee with the id " + id + " does not exist");

		// check if deleteById was never called
		verify(employeeRepository, never()).deleteById(id);

	}

	@Test
	void testEmployeeConversionFrom_DTO_TO_ENTITY() {
		// Given
		EmployeeDTO employeeDTO = new EmployeeDTO("Test_FIRST", "TEST_LAST", "test@email.com", 55);
		// WHEN
		Employee em = underTest.mapToEntity(employeeDTO);
		// THEN
		assertThat(employeeDTO.getEmail()).isEqualTo(em.getEmail());
		assertThat(employeeDTO.getFirstName()).isEqualTo(em.getFirstName());
		assertThat(employeeDTO.getLastName()).isEqualTo(em.getLastName());
	}

	@Test
	void testEmploeeConversionFrom_ENTITY_TO_DTO() {
		Employee employee = new Employee(1L, "Test_FIRST", "TEST_LAST", "test@email.com", 55);
		EmployeeDTO dto = underTest.mapToDTO(employee);
		assertThat(dto.getEmail()).isEqualTo(employee.getEmail());
		assertThat(dto.getFirstName()).isEqualTo(employee.getFirstName());
		assertThat(dto.getLastName()).isEqualTo(employee.getLastName());
	}

}
