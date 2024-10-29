package com.icsd.service;

import java.util.List;

import javax.validation.Valid;

import com.icsd.dto.common.request.CustomerLoginDTO;
import com.icsd.dto.common.request.CustomerRequestDTO;
import com.icsd.dto.response.CustomerFnmLnmGenderDTO;
import com.icsd.model.Customer;

public interface CustomerService {
	public Customer isValidCustByEmailidAndPwd(CustomerLoginDTO customerlogin);
	
	public Customer saveCustomer(Customer cust);
	
	public Customer getCustomerByEmailid(String strEmailId);
	
	public Customer getCustomerByCustId(String strCustId);
	
	public Integer createCustomer(@Valid CustomerRequestDTO customerRequest);
	//It is used to indicate that a given object should be validated according to the constraints defined in its class using annotations such as @NotNull, @Size, @Email, etc.
	//Using @Valid improves the robustness of your application by ensuring that only valid data is processed, thereby reducing errors and increasing reliability.

	public List<Customer> findByFirstNameLike(String fn);
	
	public List<Customer> findByFirstNameContaining(String fnm);
	
	public List<Customer> findByfirstNameContains(String fnm);
	
	public List<Customer> findByfirstNameIsContaining(String fnm);
	
	public List<Customer> findByFirstNameIgnoreCase(String fn);
	
	public List<CustomerFnmLnmGenderDTO> findByLastName(String lnm);
	
	public Boolean existsByEmailIdAndPassword(String email, String password);
	
	public List<Customer> getAllCustomer();
	
	public void sendEmailOfExpiry();
	
	public List<String> getEmailsofAllCustomers();


}
