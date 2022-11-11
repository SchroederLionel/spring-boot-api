package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.example.demo.dto.EmployeeDTO;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Service
public class EmployeeService {
	
	@Autowired
	private ModelMapper mapper;

	@Autowired
	private EmployeeRepository employeeRepository;

	public List<EmployeeDTO> getAllEmployees() {
		return employeeRepository.findAll().stream().map(employee-> mapToDTO(employee)).collect(Collectors.toList());
	}

	public EmployeeDTO createEmployee(EmployeeDTO employee) {
		Boolean existsEmail = employeeRepository.existsByEmail(employee.getEmail());
		if (existsEmail) {
			throw new BadRequestException("Email " + employee.getEmail() + " taken");
		}
		Employee em = mapToEntity(employee);
		Employee dto = employeeRepository.save(em);
		return mapToDTO(dto);
	}

	public EmployeeDTO getEmployeeByÎd(Long id) {
		Optional<Employee> employee = employeeRepository.findById(id);
		if(employee.isEmpty()) {
			throw new  ResourceNotFoundException("Employee with the id " + id + " does not exist!");
		}
		return mapToDTO(employee.get());
	
	}

	public void deleteEmployee(Long employeeId) {
		if (!employeeRepository.existsById(employeeId)) {
			throw new ResourceNotFoundException("Employee with the id " + employeeId + " does not exist");
		}
		employeeRepository.deleteById(employeeId);
	}

	EmployeeDTO mapToDTO(Employee employee) {
		EmployeeDTO employeeDto = mapper.map(employee, EmployeeDTO.class);
		return employeeDto;
	}

	Employee mapToEntity(EmployeeDTO employeeDto) {
		return mapper.map(employeeDto, Employee.class);
	}

}
