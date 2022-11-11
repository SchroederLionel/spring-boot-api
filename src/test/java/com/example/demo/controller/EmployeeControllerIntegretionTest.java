package com.example.demo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;

import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.EmployeeDTO;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeeControllerIntegretionTest {

	@LocalServerPort
	private int port;

	private String baseUrl = "http://localhost";

	private static RestTemplate restTemplete;

	private static EmployeeDTO employee;

	@Autowired
	EmployeeRepository repo;

	@BeforeAll
	public static void init() {
		restTemplete = new RestTemplate();
		employee = new EmployeeDTO("test_firstName", "test_schmidt", "test@mail.com", 51);
	}

	@BeforeEach
	public void setUp() {
		baseUrl = baseUrl.concat(":").concat(port + "").concat("/api/employees");

	}

	final String varname1 = ""
			+ "INSERT INTO employees (first_name,last_name,email,age) values('kevin','schmit','test@mail.com',57);";

	@Test
	void createEmployee() {
		EmployeeDTO response = restTemplete.postForObject(baseUrl, employee, EmployeeDTO.class);
		assertThat(response.getAge()).isIn(employee.getAge());
		assertThat(response.getFirstName()).isIn(employee.getFirstName());
		assertThat(response.getLastName()).isIn(employee.getLastName());
	}

	@Test
	@SqlGroup({ @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, statements = varname1),
			@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, statements = "delete from employees;") })
	void createEmployee_WithAlreadyExistingEmail_throwBadRequest() {
		
	
	
		List<Employee> employees = repo.findAll();
		int value = employees.size();
		assertThat(1).isEqualTo(value);

	}

	@Test
	public void deleteEmployee_returnsResponseCode204() {

	}

}
