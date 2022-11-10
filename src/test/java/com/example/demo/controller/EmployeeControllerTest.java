package com.example.demo.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

	private EmployeeController underTest;
	@Mock
	private EmployeeService employeeService;
	@Mock
	private EmployeeRepository employeeRepository;

	@BeforeEach
	void setUp() {
		underTest = new EmployeeController(employeeService);
	}

	@Test
	void testGetAllEmployees() {
		ArrayList<Employee> employees = new ArrayList<Employee>();
		employees.add(new Employee("Lionel", "Schroeder", "schroederlionel@gmail.com", 57));
		employees.add(new Employee("Lionel", "Schroeder", "schroederlionel@gmail.com", 57));

		Mockito.when(employeeService.getAllEmployees()).thenReturn(employees);
		List<Employee> response = underTest.getAllEmployees();
		assertThat(response).isEqualTo(employees);
	}

	@Test
	void testCreatEmployee() {
		Employee employee = new Employee("Lionel", "Schroeder", "schroederlionel@gmail.com", 57);
		Mockito.when(employeeService.createEmployee(employee)).thenReturn(employee);
		Employee response = underTest.creatEmployee(employee);
		assertThat(response).isEqualTo(employee);
	}

}
