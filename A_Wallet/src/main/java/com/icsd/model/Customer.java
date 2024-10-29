package com.icsd.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Customers",uniqueConstraints = {
		@UniqueConstraint(columnNames = "emailId")
})
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
	@Id
	@SequenceGenerator(name="generator",sequenceName = "custIdSeq",allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")	
	private int customerId;
	
	private String firstName;
	
	private String lastName;
	
	private String emailId;
	
	private String contactNo;
	
	@OneToOne
	@JoinColumn(name="addressFK")
	private Address address;
	
	@Enumerated(EnumType.STRING)
	 Gender gender;
	
	@Column(name="pwd")
	 private String password;
	
	private String confirmPassword;
	
	
	private LocalDate registrationDate;
	
	private LocalDate expiryDate;
	
	
	private boolean isDeleted=false;
	
	
	 @JsonManagedReference
	@OneToMany(targetEntity=Account.class,mappedBy="customer")
	private List<Account> accounts=new ArrayList<>();

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", emailId=" + emailId + ", contactNo=" + contactNo + ", address=" + address + ", gender=" + gender
				+ ", password=" + password + ", confirmPassword=" + confirmPassword + ", registrationDate="
				+ registrationDate + ",expiryDate="+expiryDate+", accounts=" + accounts + "]";
	}
//
//	public Customer(String firstName, String lastName, String emailId, String contactNo, Address address, Gender gender,
//			String password, String confirmPassword,  List<Account> accounts) {
//		super();
//		this.firstName = firstName;
//		this.lastName = lastName;
//		this.emailId = emailId;
//		this.contactNo = contactNo;
//		this.address = address;
//		this.gender = gender;
//		this.password = password;
//		this.confirmPassword = confirmPassword;
//		expiryDate = registrationDate.plusDays(10);
//		this.accounts = accounts;
//	}
//	



}
