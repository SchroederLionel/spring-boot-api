package com.example.demo.dto;

import lombok.Getter;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@RequiredArgsConstructor
@Accessors(fluent=true) @Getter
public class EmployeeLocationDTO {
	private Long employeeId;
	private String email;
	private String firstName;
	
	
}
