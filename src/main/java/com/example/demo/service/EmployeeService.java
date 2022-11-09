package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;

	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	public Employee createEmployee(Employee employee) {
		Boolean existsEmail = employeeRepository.existsByEmail(employee.getEmail());
		if (existsEmail) {
			throw new BadRequestException("Email " + employee.getEmail() + " taken");
		}
		return employeeRepository.save(employee);
	}

	public boolean existsByEmail(String email) {
		return employeeRepository.existsByEmail(email);
	}

	public Employee getEmployeesByÎd(Long id) {
		return employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee does not exist!"));
	}

	public void deleteEmployee(Employee employee) {
		employeeRepository.delete(employee);
	}

}
