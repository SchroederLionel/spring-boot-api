package com.example.demo.controller;

import static org.assertj.core.api.Assertions.assertThat;

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

import org.springframework.web.client.HttpClientErrorException;
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

	private final String SQL_INSERT_VALID_EMPLOYEE = ""
			+ "INSERT INTO employees (first_name,last_name,email,age) values('kevin','schmit','test@mail.com',57);";

	private final String SQL_INSERT_NOT_VALID_EMAIL = ""
			+ "INSERT INTO employees (first_name,last_name,email,age) values('kevin','schmit','test',57);";

	private final String SQL_INSERT_NOT_VALID_FIRST_NAME_AND_LAST_NAME = ""
			+ "INSERT INTO employees (first_name,last_name,email,age) values('','','test',57);";

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

	@Test
	void createEmployee() {
		EmployeeDTO response = restTemplete.postForObject(baseUrl, employee, EmployeeDTO.class);
		assertThat(response.getAge()).isIn(employee.getAge());
		assertThat(response.getFirstName()).isIn(employee.getFirstName());
		assertThat(response.getLastName()).isIn(employee.getLastName());
	}

	@Test
	@SqlGroup({ @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, statements = SQL_INSERT_VALID_EMPLOYEE),
			@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, statements = "delete from employees;") })
	void createEmployee_WithAlreadyExistingEmail_throwBadRequest() {
		try {
			restTemplete.postForObject(baseUrl, employee, EmployeeDTO.class);
		} catch (HttpClientErrorException ex) {
			List<Employee> employees = repo.findAll();
			int value = employees.size();
			assertThat(1).isEqualTo(value);
		}
	}

	@Test
	void createEmployee_WithNonValidEmail_throwMethodArgumentNotValidException() {
		EmployeeDTO invalid = new EmployeeDTO("test", "test_last", "badMail", 75);
		try {
			restTemplete.postForObject(baseUrl, invalid, EmployeeDTO.class);
		} catch (HttpClientErrorException ex) {
			List<Employee> employees = repo.findAll();
			int value = employees.size();
			assertThat(0).isEqualTo(value);
		}
	}

	@Test
	void createEmployee_WithNonValid_FIRST_AND_LASTNAME() {
		EmployeeDTO invalid_first_last_name_email = new EmployeeDTO("t", "", "goodemail.com", 75);
		try {
			restTemplete.postForObject(baseUrl, invalid_first_last_name_email, EmployeeDTO.class);
		} catch (HttpClientErrorException ex) {
			List<Employee> employees = repo.findAll();
			int expectedSize = 0;
			int value = employees.size();
			assertThat(expectedSize).isEqualTo(value);
			assertThat(ex.getMessage()).contains("First name must be between 2 and 100 characters");
			assertThat(ex.getMessage()).contains("Last name must be between 2 and 100 characters");
			assertThat(ex.getMessage()).contains("Invalid Email address");
		}
	}

	@Test
	void createEmployee_With_EMPTY_EMAIL() {
		EmployeeDTO invalid_first_last_name_email = new EmployeeDTO("t", "", "", 75);
		try {
			restTemplete.postForObject(baseUrl, invalid_first_last_name_email, EmployeeDTO.class);
		} catch (HttpClientErrorException ex) {
			List<Employee> employees = repo.findAll();
			int expectedSize = 0;
			int value = employees.size();
			assertThat(expectedSize).isEqualTo(value);
			assertThat(ex.getMessage()).contains("Email is to short");
		}
	}

	@Test
	public void deleteEmployee_returnsResponseCode204() {

	}

}
