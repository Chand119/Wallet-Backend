package com.icsd.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.icsd.dto.response.CustomerFnmLnmGenderDTO;
import com.icsd.model.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {
	
	public Optional<Customer> findByEmailIdAndPassword(String email, String password);
	
	public Optional<Customer> findByEmailId(String email);
	
//	SELECT * FROM movie WHERE title LIKE '%in%';
	
	List<Customer> findByFirstNameContaining(String fnm);
	List<Customer> findByfirstNameContains(String fnm);
	List<Customer> findByfirstNameIsContaining(String fnm);
	//We can expect each of the three methods to return the same results.
	
	List<Customer> findByFirstNameLike(String fn);
	// where UPPER(x.firstname) = UPPER(?1)
	List<Customer> findByFirstNameIgnoreCase(String fn);
	
	List<CustomerFnmLnmGenderDTO> findByLastName(String lnm);
	
	Boolean existsByEmailIdAndPassword(String email, String password);
	
	@Query("SELECT c FROM Customer c WHERE TRUNC(c.expiryDate) = TRUNC(:today) OR TRUNC(c.expiryDate) = TRUNC(:tomorrow)")
	public List<Customer> getCustomerByExpiryDate(@Param("today") LocalDate today, @Param("tomorrow") LocalDate tomorrow);

	@Query("SELECT c.emailId from Customer c ")
	public List<String> getAllEmails();

	
	



}
