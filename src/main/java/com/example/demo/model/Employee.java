package com.example.demo.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="employees")
public class Employee {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 @Column(name="id")
 private Long id;
 
 @Column(name="first_name")
 private String firstName;
 
 @Column(name="last_name")
 private String lastName;
 
 @Column(name="email")
 private String email;
 
 @Column(name="age")
 private Integer age;
 
 @OneToMany(targetEntity = Address.class,cascade = CascadeType.ALL,mappedBy="id")
 private List<Address> addresses;

 public Employee() {}
public Employee(Long id, String firstName, String lastName, String email, int age, List<Address>  addresses) {
	super();
	this.id = id;
	this.firstName = firstName;
	this.lastName = lastName;
	this.email = email;
	this.age = age;
	this.addresses = addresses;
}

public long getId() {
	return id;
}

public void setId(long id) {
	this.id = id;
}

public String getFirstName() {
	return firstName;
}

public void setFirstName(String firstName) {
	this.firstName = firstName;
}

public String getLastName() {
	return lastName;
}

public void setLastName(String lastName) {
	this.lastName = lastName;
}

public Integer getAge() {
	return age;
}

public void setAge(Integer age) {
	this.age = age;
}

public List<Address> getAddresses() {
	return addresses;
}

public void setAddresses(List<Address> addresses) {
	this.addresses = addresses;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}
 

 
 
 
}
