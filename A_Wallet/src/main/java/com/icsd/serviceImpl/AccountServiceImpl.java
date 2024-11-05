package com.icsd.serviceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icsd.dto.common.request.AccountRequestDTO;
import com.icsd.exception.ResourceNotFoundException;
import com.icsd.model.Account;
import com.icsd.model.AccountType;
import com.icsd.model.Customer;
import com.icsd.repo.AccountRepo;
import com.icsd.repo.CustomerRepo;
import com.icsd.service.AccountService;
import com.icsd.service.CustomerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
	
	
	private final CustomerRepo customerRepo;
	
	@Autowired
	AccountRepo accountRepo;
	@Autowired
	CustomerService customerService;

	
	@Override
	public int saveAccount(AccountRequestDTO accReq) {
		
		log.info("create a new account");
		
		Optional<Customer> optionalCust= customerRepo.findById(accReq.getCustomerId());
		if(!optionalCust.isPresent())//If a value is present, returns true, otherwise false.
		{
			//it means customer is not ther in db
			throw new ResourceNotFoundException("Customer is not present in database");
		}
		
		Customer cust=optionalCust.get();
		cust.setCustomerId(accReq.getCustomerId());
		
		Account account=Account.builder()
				.accountType(accReq.getAccountType())
				.customer(cust).openingBalance(Double.parseDouble(accReq.getOpeningBalance())).description(accReq.getDescription())
				.openingDate(LocalDate.now()).build();
		
		int accNumberCreated=accountRepo.save(account).getId();
		return accNumberCreated;
	}

	@Override
public List<Account> getAccountsByCustId(int intCustid) {
		
		log.info("getting accounts by custid for customer id "+ intCustid);
		//if() -- strcustid - - can have character and in that case it will raise - number format exception
		//how- which one is better method- requestdto par check kaise kare..?
		//int custId=Integer.parseInt(strCustId);
		
		List<Account>  lstAccounts=accountRepo.findByCustomerCustomerId(intCustid);
		if(lstAccounts.isEmpty())
		{
			throw new ResourceNotFoundException("No Account found for customer id "+ intCustid);
		}
		return lstAccounts;
	}

	@Override
	public Account getAccountById(int accountNumber) {
		// TODO Auto-generated method stub
		
		Optional<Account> optAcc=accountRepo.findById(accountNumber);
		if(optAcc.isEmpty())
		{
			throw new ResourceNotFoundException("Account is not existing for account number "+ accountNumber);
		}
		return optAcc.get();
		
	}


	@Override
	public List<Account> getAccountsByCustEmailId(String custEmailId)
	{
		Customer cust= customerService.getCustomerByEmailid(custEmailId);
		List<Account> lst=getAccountsByCustId(cust.getCustomerId());
		
		return lst;
	}

	@Override
	public List<Account> getAccountsLessThanEqualOpBal(double openingBalance) {
		log.info("inside  getAccountsLessThanOpBalEqual");
		List<Account> lst=accountRepo.findByOpeningBalanceLessThanEqual(openingBalance);
		return lst;
	}


	

	@Override
	public List<Account> findDistinctByOpeningBalance(double openingBalance) {
		log.info("inside find distinct By  op balance - "+ openingBalance);		
		List<Account> lst=	accountRepo.findDistinctByOpeningBalance(openingBalance);
		return lst;
	}

	
	@Override
	public List<String> getDistinctAccType() {
		log.info("inside get distinct acc type");
		return  accountRepo.getDistinctAccTypes();
	}

	@Override
	public List<Account> findByOpeningBalanceGreaterThan(double openingBalance) {
		log.info("inside findByOpeningBalanceGreaterThan - "+ openingBalance);		
		List<Account> lst=	accountRepo.findByOpeningBalanceGreaterThan(openingBalance);
		return lst;
	}

	@Override
	public List<Account> findByOpeningDateBetween(LocalDate startDate, LocalDate endDate) {
		
		return accountRepo.findByOpeningDateBetween(startDate, endDate);
	}

	@Override
	public List<Account> findByOpeningDateAfter(LocalDate dt) {
		
		return accountRepo.findByOpeningDateAfter(dt);
	}

	@Override
	public List<Account> findByOrderByOpeningBalanceAsc() {
		
		//return accountRepo.findByOrderByOpeningBalanceAsc();
		return accountRepo.findByOrderByOpeningBalanceDesc();
	}


	@Override
	public List<Account> findByOpeningBalanceNot(double ob) {
	
		return accountRepo.findByOpeningBalanceNot(ob);
	}

	@Override
	public List<Account> findByAccountTypeIn(List<AccountType> accTypes) {

		//return accountRepo.findByAccountTypeIn(accTypes);
		return accountRepo.findByAccountTypeNotIn(accTypes);
	}

	@Override
	public List<Account> getAccountsLessThanOpBal(double openingBalance) {
		log.info("inside  getAccountsLessThanOpBal");
		List<Account> lst=accountRepo.findByOpeningBalanceLessThan(openingBalance);
		return lst;
	}

	@Override
	public List<Account> findDistinctByAccountTypeAndOpeningBalance(AccountType accType, double openingBalance) {
		
		log.info("inside find distinct By accctype and op balance - accType "+ accType+" op bal= "+ openingBalance);		
		List<Account> lst=	accountRepo.findDistinctByAccountTypeAndOpeningBalance(accType, openingBalance);
		return lst;
		
	}
	
}
