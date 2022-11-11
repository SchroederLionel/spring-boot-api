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

import com.example.demo.dto.EmployeeDTO;
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
		ArrayList<EmployeeDTO> employees = new ArrayList<EmployeeDTO>();
		employees.add(new EmployeeDTO("Lionel", "Schroeder", "schroederlionel@gmail.com", 57));
		employees.add(new EmployeeDTO("Lionel", "Schroeder", "schroederlionel@gmail.com", 57));

		Mockito.when(employeeService.getAllEmployees()).thenReturn(employees);
		List<EmployeeDTO> response = underTest.getAllEmployees();
		assertThat(response).isEqualTo(employees);
	}

	@Test
	void testCreatEmployee() {
		EmployeeDTO employee = new EmployeeDTO("Lionel", "Schroeder", "schroederlionel@gmail.com", 57);
		Mockito.when(employeeService.createEmployee(employee)).thenReturn(employee);
		EmployeeDTO response = underTest.creatEmployee(employee);
		assertThat(response).isEqualTo(employee);
	}

}
