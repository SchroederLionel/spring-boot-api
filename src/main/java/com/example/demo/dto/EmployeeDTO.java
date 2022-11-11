package com.example.demo.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
	private Long employee_id;

	@NotNull
	private String firstName;

	@NotNull
	private String lastName;

	@Email(message = "Invalid Email address")
	private String email;

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
