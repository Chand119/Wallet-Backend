package com.icsd.service;

import java.time.LocalDate;
import java.util.List;

import com.icsd.dto.common.request.AccountRequestDTO;
import com.icsd.model.Account;
import com.icsd.model.AccountType;

public interface AccountService {

	public int saveAccount(AccountRequestDTO accReq);
	
	public List<Account> getAccountsByCustId(int intCustId);
	
	public List<Account> getAccountsByCustEmailId(String custEmailId);
	
	public List<Account> getAccountsLessThanOpBal(double openingBalance);
	
	public List<Account> getAccountsLessThanEqualOpBal(double openingBalance);
	
	public Account getAccountById(int accountNumber);
	
	public List<Account>  findDistinctByAccountTypeAndOpeningBalance(AccountType accType,double openingBalance);
	
	public List<Account>  findDistinctByOpeningBalance(double openingBalance);
	
	public List<String>  getDistinctAccType();
	
	public List<Account> findByOpeningBalanceGreaterThan(double openingBalance);
	
	public List<Account> findByOpeningDateBetween(LocalDate startDate,LocalDate endDate);
	
	List<Account> findByOpeningDateAfter(LocalDate dt);
	
	List<Account> findByOrderByOpeningBalanceAsc();
	
	List<Account> findByOpeningBalanceNot(double ob);
	
	//select * from account where account_type in ('CURRENT','RD','LOAN');
	List<Account> findByAccountTypeIn(List<AccountType> accTypes);
}
