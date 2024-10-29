package com.icsd.repo;

import java.time.LocalDate;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.icsd.model.Account;
import com.icsd.model.AccountType;




public interface AccountRepo extends JpaRepository<Account	, Integer>
{
	//************************************
	
//	@Query("SELECT u from User u WHERE upper(u.lastName) like %:lastName% ")
//    List<User> findAllCustom(@Param("lastName") String lastName);
//
//    @Query(value = "SELECT count(*) from TABLE_USER", nativeQuery = true)
//    int getAllUserCount();
//
//    @Query("SELECT u from User u WHERE upper(u.lastName) like %:lastName% ")
//    User getCustomUser(@Param("lastName") String user);
	
	//**************************************/
	
	
	
	//	List<ProdModel> findByCatCatId(int catid);
	 //List<Customer> findByCustomerCustomerId(int custid);
	List<Account> findByCustomerCustomerId(int custid);
	 // findByCountryId(String countryId);
	// List<Apple> findByAppleIdLessThan(Long appleId);
	
	List<Account> findByOpeningBalanceLessThan(double ob);
	
	List<Account> findByOpeningBalanceGreaterThan(double ob);
	
	List<Account> findByOpeningBalanceLessThanEqual(double ob);
	
	List<Account> findDistinctByAccountTypeAndOpeningBalance(AccountType accType,double openingBalance);
	
	List<Account> findDistinctByOpeningBalance(double openingBalance);
	//List<Account> findByTitleLike(String title);
	
	 @Query(value = "select DISTINCT(a.accountType) from Account a")
	  List<String> getDistinctAccTypes();
	 	
	List<Account> findByOpeningDateBetween(LocalDate start, LocalDate end);
	
	List<Account> findByOpeningDateAfter(LocalDate dt);
	
	List<Account>  findByOrderByOpeningBalanceAsc()
	;
	List<Account>  findByOrderByOpeningBalanceDesc();
	//List<Account>  findByOpeningBalanceOrderByLastnameDesc();
	List<Account> findByOpeningBalanceNot(double ob);
	
	//select * from account where account_type in ('CURRENT','RD','LOAN');
	List<Account> findByAccountTypeIn(List<AccountType> accTypes);
	
	List<Account> findByAccountTypeNotIn(List<AccountType> accTypes);
}
