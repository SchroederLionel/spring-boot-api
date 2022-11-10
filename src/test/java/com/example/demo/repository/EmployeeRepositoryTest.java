package com.example.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.model.Employee;

@DataJpaTest
class EmployeeRepositoryTest {

	@Autowired
	private EmployeeRepository underTest;

	@Test
	void findEmployeesByAge() {
		// Given
		Employee employee = new Employee("Lionel", "Schroeder", "schroederlionel@gmail.com", 57);
		Employee employee2 = new Employee("Lionel", "Schroeder", "schroederlionel@gmail.com", 57);
		Employee employee3 = new Employee(12345678910L, "Lionel", "Schroeder", "schroederlionel@gmail.com", 50);
		underTest.save(employee);
		underTest.save(employee2);
		underTest.save(employee3);
		List<Employee> employees = underTest.findByAge(57);
		// When
		int expectedEmployeesAtAge = 2;
		// Then
		assertThat(employees.size()).isEqualTo(expectedEmployeesAtAge);
	}

	@Test
	void testExistsByEmail() {
		// Given
		Employee employee = new Employee("Lionel", "Schroeder", "schroederlionel@gmail.com", 57);
		underTest.save(employee);
		// When
		boolean exists = underTest.existsByEmail(employee.getEmail());
		// Then
		assertThat(exists).isTrue();
	}
	
	@Test
	void testIfEmailDoesNotEmail() {
		// Given
		String email="email@test.com";
		// When
		boolean exists = underTest.existsByEmail(email);
		// Then
		assertThat(exists).isFalse();
	}

	@Test
	void createEmplyoee() {
		// Given
		Employee employee = new Employee(12345678910L, "Lionel", "Schroeder", "schroederlionel@gmail.com", 57);
		employee = underTest.save(employee);

		// When
		Optional<Employee> savedEmployee = underTest.findById(employee.getEmployee_id());

		// Then
		assertThat(savedEmployee.get()).isEqualTo(employee);
	
	}

	@Test
	void retrieveAllEmployees() {
		// Given
		Employee employee = new Employee(12345678910L, "Lionel", "Schroeder", "schroederlionel@gmail.com", 57);
		Employee employee2 = new Employee(12345678910L, "Lionel", "Schroeder", "schroederlionel@gmail.com", 57);
		Employee employee3 = new Employee(12345678910L, "Lionel", "Schroeder", "schroederlionel@gmail.com", 50);
		underTest.save(employee);
		underTest.save(employee2);
		underTest.save(employee3);
		
		// When
		 List<Employee> employees = underTest.findAll();
		 int expectedNumberOfEmployees = 3;
		 
		 // Then
		 assertThat(employees.size()).isEqualTo(expectedNumberOfEmployees);
	}
	
	@Test
	void deleteEmployee() {
		// Given
		Employee employee = new Employee(12345678910L, "Lionel", "Schroeder", "schroederlionel@gmail.com", 57);
		employee = underTest.save(employee);
		
		// When
		underTest.delete(employee);
		Optional<Employee> savedEmployee = underTest.findById(employee.getEmployee_id());
		
		// Then
		assertThat(savedEmployee.isEmpty()).isTrue();
	}
	
	

}
