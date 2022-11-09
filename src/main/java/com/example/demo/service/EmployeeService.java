package com.example.demo.service;

import static org.mockito.ArgumentMatchers.longThat;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.dto.EmployeeLocationDTO;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;

	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	public Employee createEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}
	
	public boolean existsByEmail(String email) {
		return employeeRepository.existsByEmail(email);
	}
	
	public Employee getEmployeesByÎdl(Long id) {
		return employeeRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Employee does not exist!"));
	}
	
	public void deleteEmployee(Employee employee) {
		 employeeRepository.delete(employee);
	}

}
