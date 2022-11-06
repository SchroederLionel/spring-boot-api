package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="addresses")
public class Address {
	
	@Id()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="address_id")
	private Long id;
	
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
	
	@ManyToOne
	private Employee employee;

	
	public Address() {}
	
	public Address(Long id, String street, String postalCode, String streetNumber, String city, String country) {
		super();
		this.id = id;
		this.street = street;
		this.postalCode = postalCode;
		this.streetNumber = streetNumber;
		this.city = city;
		this.country = country;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	
	

}
