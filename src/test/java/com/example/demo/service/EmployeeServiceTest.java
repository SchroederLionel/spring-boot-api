package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.repository.EmployeeRepository;

class EmployeeServiceTest {
	@Mock
	private EmployeeRepository employeeRepository;
	private AutoCloseable autoClosable;
	private EmployeeService underTest;
	
	@BeforeEach
	void setUp() {
		autoClosable = MockitoAnnotations.openMocks(this);
		underTest = new EmployeeService(employeeRepository);
		
	}
	
	@AfterEach
	void tearDown() throws Exception{
		autoClosable.close();
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
		fail("Not yet implemented");
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
