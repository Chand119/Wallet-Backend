package com.icsd.service;

import java.time.LocalDate;
import java.util.List;

import com.icsd.dto.common.request.TransactionDepositRequestDTO;
import com.icsd.model.Account;
import com.icsd.model.Transaction;

public interface TransactionService {
	public Transaction saveTransaction(Transaction trans);
	public Transaction WithDrawAmountfromAccount(TransactionDepositRequestDTO tdd);
	public Transaction fundTransferFunction(TransactionDepositRequestDTO tdd);
	public Transaction depositAmountInAccount(TransactionDepositRequestDTO tdd);
//	public Transaction depositAmountInAccount(TransactionDepositDTO tdd)
//	public int updateOpeningBalanceByAccountNumber(int accountNumber,double newOpeningBalance);
	public void updateOpeningBalanceByAccountNumber(Account acc,double newOpeningBalance);
	public List<Transaction> getTransactionsByAccountNumber(int accountNumber);
	public List<Transaction> getTransactionsByAccountNumberAndDate(int accNo, LocalDate localDate);
	
}