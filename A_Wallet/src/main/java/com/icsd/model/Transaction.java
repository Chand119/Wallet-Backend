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

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name="Transactions")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class Transaction {
	
	@Id
	@SequenceGenerator(name="generator",sequenceName = "trsnId_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
	private int transactionId;
	
	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;

	private LocalDate transactionDate;
	private double amount;
	private String description;
	

	@ManyToOne
	@JoinColumn(name="frmAccFk")
	private Account fromAccount;
	
	@ManyToOne
	@JoinColumn(name="toAccFk")
	private Account toAccount;

	

	public Transaction(TransactionType transactionType, LocalDate transactionDate, double amount, String description,
			Account fromAccount, Account toAccount) {
		super();
		this.transactionType = transactionType;
		this.transactionDate = transactionDate;
		this.amount = amount;
		this.description = description;
		this.fromAccount = fromAccount;
		this.toAccount = toAccount;
	}

	


}
