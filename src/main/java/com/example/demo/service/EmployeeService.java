package com.example.demo.service;

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
	 //return employeeRepository.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
 }
 
 public Employee createEmployee(Employee employee) {
	 Employee newEmployee = new Employee();
	 newEmployee.setAddresses(employee.getAddresses());
	 return employeeRepository.save(employee);
 }
 
 public Employee getEmployee(@PathVariable Long id) {
	 return employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee does not exist with id: "+id));
 }
 
 
	public Employee updateEmployee(@PathVariable Long id,Employee employeeDetails) {	
		Employee employee = employeeRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Employee does not exist with id: "+id));
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		employee.setEmail(employeeDetails.getEmail());
		Employee updatedEmployee = employeeRepository.save(employee);
		return updatedEmployee;
			
	}
	

	public boolean deleteEmployee(@PathVariable Long id, Employee employee){
		employeeRepository.deleteById(id);
		return true;
	}
 
 
 private EmployeeLocationDTO convertEntityToDto(Employee employee) {
	 EmployeeLocationDTO employeeLocationDTO = new EmployeeLocationDTO();
	 employeeLocationDTO.setEmail(employee.getEmail());
	 employeeLocationDTO.setFirstName(employee.getFirstName());
	 employeeLocationDTO.setUserId(employee.getId());
	 return employeeLocationDTO;
 }
}
