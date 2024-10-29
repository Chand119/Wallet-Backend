package com.icsd.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Accounts")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Account {
	@Id
	@SequenceGenerator(name="generator", sequenceName = "addId_seq",allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="generator")
	private int id;
	
	@ManyToOne
	
	@JoinColumn(name="customerFk")
	@JsonIgnore//it will not create json for customer object - account has customer - 
	//customer has list of accounts - it will cause - infinite recurson error - stack overflow error 
	//Many Accounts belong to one customer
	private Customer customer;
	@Enumerated(EnumType.STRING)
	private AccountType accountType;
	private double openingBalance;
	private LocalDate   openingDate=LocalDate.now();//openingDate
	private String description;
	
	
	@Override
	public String toString() {
	    return "Account [id=" + id + 
	           ", customerId='" + (customer != null ? customer.getCustomerId() : null) + 
	           "', accountType=" + accountType + 
	           ", openingBalance=" + openingBalance + 
	           ", openingDate=" + openingDate + 
	           ", description='" + description + "']";
	}


}
