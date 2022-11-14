package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.EmployeeDTO;

import com.example.demo.service.EmployeeService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class EmployeeController {

	
	private final EmployeeService employeeService;

	@GetMapping("/employees")
	@ResponseStatus(HttpStatus.OK)
	public List<EmployeeDTO> getAllEmployees() {
		return employeeService.getAllEmployees();
	}

	@PostMapping("/employees")
	@ResponseStatus(HttpStatus.CREATED)
	public EmployeeDTO creatEmployee(@RequestBody @Valid EmployeeDTO employee) {
		return employeeService.createEmployee(employee);
	}

	@DeleteMapping("/employees/{employeeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteEmployee(@PathVariable("employeeId") long employeeId) {
		employeeService.deleteEmployee(employeeId);
	}

	@GetMapping("/employees/{id}")
	public EmployeeDTO getEmployeeById(@PathVariable Long id) {
		return employeeService.getEmployeeByÎd(id);
	}

}
