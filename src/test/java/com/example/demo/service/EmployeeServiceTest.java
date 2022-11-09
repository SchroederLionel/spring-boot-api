package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {
	@Mock
	private EmployeeRepository employeeRepository;
	private EmployeeService underTest;

	@BeforeEach
	void setUp() {
		underTest = new EmployeeService(employeeRepository);
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
		Employee employee = new Employee(12345678910L, "Lionel", "Schroeder", "schroederlionel@gmail.com", 57);
		// When
		underTest.createEmployee(employee);
		// then
		ArgumentCaptor<Employee> employeeArgumentCaptor = ArgumentCaptor.forClass(Employee.class);
		Employee captured = employeeArgumentCaptor.getValue();
		assertThat(captured).isEqualTo(employee);
	}

	@Test
	void testCreateEmployeeIfEmailAlreadyExistsWillThrowBadRequestException() {
		// Given
		Employee employee = new Employee(12345678910L, "Lionel", "Schroeder", "schroederlionel@gmail.com", 57);
		underTest.createEmployee(employee);
		Mockito.when(employeeRepository.existsByEmail(employee.getEmail()))
				.thenThrow(new BadRequestException("Email " + employee.getEmail() + " taken"));
		// When
		// then
		assertThatThrownBy(() -> underTest.createEmployee(employee)).isInstanceOf(BadRequestException.class)
				.hasMessageContaining("Email " + employee.getEmail() + " taken");
	}

	@Test
	void testExistsByEmail() {
		fail("Not yet implemented");
	}

	@Test
	void testGetEmployeesByÎdl() {
		fail("Not yet implemented");
	}

	@Test
	void testDeleteEmployee() {
		fail("Not yet implemented");
	}

}
