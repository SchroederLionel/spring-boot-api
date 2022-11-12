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

import com.example.demo.constants.Constants;
import com.example.demo.dto.EmployeeDTO;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeeControllerIntegretionTest {

	private static final String SQL_INSERT_VALID_EMPLOYEE = ""
			+ "INSERT INTO employees (first_name,last_name,email,age) values('kevin','schmit','test@mail.com',57);";
	private static final String SQL_DELETE_EMPLOYEE = "delete from employees;";

	private static final EmployeeDTO employeeDTO_VALID = new EmployeeDTO("test_firstName", "test_schmidt",
			"test@mail.com", 51);
	private static final EmployeeDTO employeeDTO_INVALID_EMAIL = new EmployeeDTO("test", "test_last", "badMail", 75);
	private static final EmployeeDTO employeeDTO_INVALID_FIRSTNAME_LASTNAME_EMAIL = new EmployeeDTO("t", "",
			"goodemail.com", 75);
	private static final EmployeeDTO employeeDTO_EMPTY_EMAIL = new EmployeeDTO("test_firstName", "test_schmidt",
			"test@mail.com", 75);

	@LocalServerPort
	private int port;

	private String baseUrl = "http://localhost";

	private static RestTemplate restTemplete;

	@Autowired
	EmployeeRepository repo;

	@BeforeAll
	public static void init() {
		restTemplete = new RestTemplate();
	}

	@BeforeEach
	public void setUp() {
		baseUrl = baseUrl.concat(":").concat(port + "").concat("/api/employees");
	}

	@Test
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, statements = SQL_DELETE_EMPLOYEE)
	void createEmployee() {
		EmployeeDTO response = restTemplete.postForObject(baseUrl, employeeDTO_VALID, EmployeeDTO.class);
		assertThat(response.getAge()).isIn(employeeDTO_VALID.getAge());
		assertThat(response.getFirstName()).isIn(employeeDTO_VALID.getFirstName());
		assertThat(response.getLastName()).isIn(employeeDTO_VALID.getLastName());
	}

	@Test
	@SqlGroup({
			@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, statements = { SQL_DELETE_EMPLOYEE,
					SQL_INSERT_VALID_EMPLOYEE }),
			@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, statements = SQL_DELETE_EMPLOYEE) })
	void createEmployee_WithAlreadyExistingEmail_throwBadRequest() {
		try {
			// Throws exception because employeeDTO email already exists.
			restTemplete.postForObject(baseUrl, employeeDTO_VALID, EmployeeDTO.class);
		} catch (HttpClientErrorException ex) {
			List<Employee> employees = repo.findAll();
			int value = employees.size();
			assertThat(value).isEqualTo(1);
		}
	}

	@Test
	void createEmployee_WithNonValidEmail_throwMethodArgumentNotValidException() {
		try {
			restTemplete.postForObject(baseUrl, employeeDTO_INVALID_EMAIL, EmployeeDTO.class);
		} catch (HttpClientErrorException ex) {
			List<Employee> employees = repo.findAll();
			int value = employees.size();
			assertThat(value).isEqualTo(0);
		}
	}

	@Test
	void createEmployee_WithNonValid_FIRST_AND_LASTNAME() {
		try {
			restTemplete.postForObject(baseUrl, employeeDTO_INVALID_FIRSTNAME_LASTNAME_EMAIL, EmployeeDTO.class);
		} catch (HttpClientErrorException ex) {
			List<Employee> employees = repo.findAll();
			int expectedSize = 0;
			int value = employees.size();
			assertThat(value).isEqualTo(expectedSize);
			assertThat(ex.getMessage()).contains(Constants.FIRST_NAME_INVALID);
			assertThat(ex.getMessage()).contains(Constants.LAST_NAME_INVALID);
			assertThat(ex.getMessage()).contains(Constants.EMAIL_INVALID);
		}
	}

	@Test
	void createEmployee_With_EMPTY_EMAIL() {
		try {
			restTemplete.postForObject(baseUrl, employeeDTO_EMPTY_EMAIL, EmployeeDTO.class);
		} catch (HttpClientErrorException ex) {
			List<Employee> employees = repo.findAll();
			int expectedSize = 0;
			int value = employees.size();
			assertThat(expectedSize).isEqualTo(value);
			assertThat(ex.getMessage()).contains(Constants.EMAIL_TO_SHORT);
		}
	}

	@Test
	public void deleteEmployee_returnsResponseCode204() {

	}

}
