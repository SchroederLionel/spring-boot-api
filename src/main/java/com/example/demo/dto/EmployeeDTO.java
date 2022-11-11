package com.example.demo.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
	private Long employee_id;

	@NotNull
	@Size(min = 2, max = 100, message = "Fist name must be between 2 and 100 characters")
	private String firstName;

	@NotNull
	@Size(min = 2, max = 100, message = "Fist name must be between 2 and 100 characters")
	private String lastName;

	@NotNull
	@Size(min = 2, message = "Email is to short")
	@Email(message = "Invalid Email address")
	private String email;

	@NotNull
	@Min(16)
	@Max(100)
	private Integer age;

	public EmployeeDTO(@NotNull String firstName, @NotNull String lastName,
			@Email(message = "Invalid Email address") String email, @Min(16) @Max(100) int age) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.age = age;
	}

}
