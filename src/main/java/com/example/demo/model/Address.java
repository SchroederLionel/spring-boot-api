package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
public class Address{};
/*@Entity
@Table(name="addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
	
	@Id()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="address_id")
	private Long addressId;
	
	@Column(name="street")
	private String street;
	
	@Column(name="postal_code")
	private String postalCode;
	
	@Column(name="street_number")
	private String streetNumber;
	
	
	@Column(name="city")
	private String city;
	
	@Column(name="country")
	private String country;
	
/*	@ManyToOne
	@JoinColumn(name="employee_id")
	private Employee employee;*/

	
	
	
	
	


