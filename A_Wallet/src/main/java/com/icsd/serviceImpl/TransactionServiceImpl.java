package com.icsd.serviceImpl;

import java.time.LocalDate;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icsd.dto.common.request.TransactionDepositRequestDTO;
import com.icsd.exception.ResourceNotFoundException;
import com.icsd.model.Account;
import com.icsd.model.Transaction;
import com.icsd.model.TransactionType;
import com.icsd.repo.AccountRepo;
import com.icsd.repo.TransactionRepo;
import com.icsd.service.AccountService;
import com.icsd.service.TransactionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	 TransactionRepo transRepo;
	
	@Autowired
	 AccountRepo accRepo;
	
	// query - is it ok - we are creating reference of impl class not reference of
	// interface class
	@Autowired
	 AccountService accService;

	@Override
	public Transaction saveTransaction(Transaction trans) {
		// TODO Auto-generated method stub
		return null;
	}

	// query - can we use any other method- jaise most of the code same hai withdraw
	// karne ka our depoosit karne ka
	
	@Override
	public Transaction WithDrawAmountfromAccount(TransactionDepositRequestDTO tdd) {
		int accountNumber = tdd.getAccountNumber();

		Account acc = accService.getAccountById(accountNumber);
		log.info(
				"acc reterived from account servie inside transaction service implementation - we use accoutservice as a dependency inisde transaction service ");
		log.info("To acocunt reterieved is " + acc);
		if(acc.getOpeningBalance()<tdd.getAmount()) {
			return null;
		}
		double newOpeningBalance = acc.getOpeningBalance() - tdd.getAmount();
		log.info("updating opening balance in account");
		// negative balance validation
		updateOpeningBalanceByAccountNumber(acc, newOpeningBalance);
		TransactionType transactionType = TransactionType.DEBIT;
		LocalDate transactionDate = LocalDate.now();
		double amount = tdd.getAmount();
		String description = tdd.getDescription();
		log.info("reteriving from account from account table ");
		Account fromAccount = accService.getAccountById(tdd.getFromAccountNumber());
		log.info("from account reterieved " + fromAccount);
		Account toAcc = acc;
		log.info("saving transaction ");
		Transaction trans = new Transaction(transactionType, transactionDate, amount, description, fromAccount, toAcc);

		transRepo.save(trans);
		log.info("transaction saved with details " + trans);

		return trans;
	}

	
	
	@Override
	public Transaction fundTransferFunction(TransactionDepositRequestDTO tdd) {
		// TODO Auto-generated method stub
		int fromAccountNumber = tdd.getFromAccountNumber();// 2
		int toAccountNumber = tdd.getAccountNumber();// 1
		Account fromAcc = accService.getAccountById(fromAccountNumber);
		System.out.println("Fund Transfer fromAcc=: " + fromAcc);
		Account toAcc = accService.getAccountById(toAccountNumber);
		System.out.println("Fund Transfer toAcc=: " + toAcc);
		Transaction trans=null;
		
		if(toAcc.getOpeningBalance()<tdd.getAmount()) {
			log.info("wowwww");
			return trans;
		}
		double oldOpeningBalFromAccout = toAcc.getOpeningBalance();
		double newOpeningBalFromAcc = oldOpeningBalFromAccout - tdd.getAmount();
		System.out.println("fund Transfer fromAccountNumber: " + fromAccountNumber + " newOpeningBalFromAcc="
				+ newOpeningBalFromAcc);
		updateOpeningBalanceByAccountNumber(toAcc, newOpeningBalFromAcc);

		// Transaction
		// tid ttye dt amt ddesc fa ta- credit
		// int transactionId=getId("transaction", "transactionId");
		TransactionType transactionType = TransactionType.DEBIT;
		LocalDate transactionDate = LocalDate.now();
		System.out.println("todays date is " + transactionDate.toString());// todays date is 2022-09-08
		String description = tdd.getDescription();

		 trans = new Transaction(transactionType, transactionDate, tdd.getAmount(), description, fromAcc,
				toAcc);

		transRepo.save(trans);

		// toAcc = ob=ob+amt
		// transactionId=getId("transaction", "transactionId");
		transactionType = TransactionType.CREDIT;
		double oldOpeningBalToAcc = fromAcc.getOpeningBalance();
		double newOpeningBalToAcc = oldOpeningBalToAcc + tdd.getAmount();
		updateOpeningBalanceByAccountNumber(fromAcc, newOpeningBalToAcc);

		trans = new Transaction(transactionType, transactionDate, tdd.getAmount(), description, fromAcc, toAcc);
		transRepo.save(trans);

		return trans;
	}
	
	

	@Override
	public Transaction depositAmountInAccount(TransactionDepositRequestDTO tdd) {
		int accountNumber = tdd.getAccountNumber();
//		Optional<Account> optAccount=accRepo.findById(accountNumber);
//		if(optAccount.isEmpty())
//		{
//			throw new ResourceNotFoundException("ToAccount Entity not exist for account number "+ accountNumber);
//		}
		Account acc = accService.getAccountById(accountNumber);
		//
		log.info(
				"acc reterived from account servie inside transaction service implementation - we use accoutservice as a dependency inisde transaction service ");
		log.info("To acocunt reterieved is " + acc);
		double newOpeningBalance = acc.getOpeningBalance() + tdd.getAmount();
		log.info("updating opening balance in account");
		updateOpeningBalanceByAccountNumber(acc, newOpeningBalance);
		TransactionType transactionType = TransactionType.CREDIT;
		LocalDate transactionDate = LocalDate.now();
		double amount = tdd.getAmount();
		String description = tdd.getDescription();
		log.info("reteriving from account from account table ");
		Account fromAccount = accService.getAccountById(tdd.getFromAccountNumber());
		log.info("from account reterieved " + fromAccount);
		Account toAcc = acc;
		log.info("saving transaction ");
		Transaction trans = new Transaction(transactionType, transactionDate, amount, description, fromAccount, toAcc);

		transRepo.save(trans);
		log.info("transaction saved with details " + trans);

		return trans;
	}
	
	

	@Override
	public void updateOpeningBalanceByAccountNumber(Account acc, double newOpeningBalance) {
		
		acc.setOpeningBalance(newOpeningBalance);
		Account acc1=accRepo.save(acc);
		
		log.info("account is updated with new updatedbalance " + newOpeningBalance + " accno" + acc.getId());
		
	}

	
	
	@Override
	public List<Transaction> getTransactionsByAccountNumber(int accountNumber) {
	
	List<Transaction> lst= transRepo.findDebitsByAccountId(accountNumber, TransactionType.DEBIT);
	lst.addAll(transRepo.findCreditsByAccountId(accountNumber, TransactionType.CREDIT));
	if(lst.isEmpty()) {
		throw new ResourceNotFoundException("No Transaction for this User");
	}
	return lst;
	}

	@Override
	public List<Transaction> getTransactionsByAccountNumberAndDate(int accNo, LocalDate localDate) {
		List<Transaction> lst= transRepo.findByAccountIdAndDate(accNo, localDate);
		if(lst.isEmpty()) {
			throw new ResourceNotFoundException("No Transactions for this User");
			
		}
		return lst;
	}

}
