package com.icsd.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.icsd.model.Transaction;
import com.icsd.model.TransactionType;

public interface TransactionRepo  extends JpaRepository<Transaction, Integer>{

	public List<Transaction> findByFromAccount_Id(int accno);

	@Query("SELECT t FROM Transaction t WHERE t.fromAccount.id = :accountNumber AND t.transactionType = :transactionType")
	List<Transaction> findDebitsByAccountId(@Param("accountNumber") int accountNumber, @Param("transactionType") TransactionType transactionType);
	

	@Query("SELECT t FROM Transaction t WHERE t.fromAccount.id = :accountNumber AND t.transactionType = :transactionType")
	List<Transaction> findCreditsByAccountId(@Param("accountNumber") int accountNumber, @Param("transactionType") TransactionType transactionType);
	
	@Query("SELECT t FROM Transaction t WHERE t.fromAccount.id = :accountNumber AND t.transactionDate = :transactionDate")
	List<Transaction> findByAccountIdAndDate(@Param("accountNumber") int accountNumber, @Param("transactionDate") LocalDate transactionDate);

    
}
