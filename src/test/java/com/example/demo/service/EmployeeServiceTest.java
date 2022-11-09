package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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

		verify(employeeRepository).save(employeeArgumentCaptor.capture());

		Employee captured = employeeArgumentCaptor.getValue();

		assertThat(captured).isEqualTo(employee);
	}

	@Test
	void testCreateEmployeeIfEmailAlreadyExistsWillThrowBadRequestException() {
		// Given
		Employee employee = new Employee(12345678910L, "Lionel", "Schroeder", "schroederlionel@gmail.com", 57);

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
		Employee employee = new Employee(12345678910L, "Lionel", "Schroeder", "schroederlionel@gmail.com", 57);
		Mockito.when(employeeRepository.findById(employee.getEmployee_id())).thenReturn(Optional.of(employee));
		// When
		// Then
		assertThat(underTest.getEmployeeByÎd(employee.getEmployee_id())).isEqualTo(employee);
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
	void testDeleteEmployee() {
		fail("Not yet implemented");
	}

}
