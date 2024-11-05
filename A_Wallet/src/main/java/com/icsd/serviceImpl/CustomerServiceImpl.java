package com.icsd.serviceImpl;
import com.icsd.dto.common.request.CustomerLoginDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import com.icsd.dto.common.request.CustomerRequestDTO;
import com.icsd.dto.response.CustomerFnmLnmGenderDTO;
import com.icsd.exception.EntityAlreadyExistException;
import com.icsd.exception.ResourceNotFoundException;
import com.icsd.model.Address;
import com.icsd.model.Customer;
import com.icsd.repo.AddressRepo;
import com.icsd.repo.CustomerRepo;
import com.icsd.service.CustomerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
	
	
	private final CustomerRepo customerRepo;
	

	
	 @Value("${expiry.days}")
	   private int expiryDays; 
	private final AddressRepo addressRepo;

	@Override
	public Customer isValidCustByEmailidAndPwd(CustomerLoginDTO customerlogin) {
		
			
			
			
			Optional<Customer> optCust=customerRepo.findByEmailIdAndPassword(customerlogin.getEmailId(), customerlogin.getPassword());
			
			if(optCust.isEmpty())
			{
				
				throw new ResourceNotFoundException("Customer Doesn't Exists for email id "+ customerlogin.getEmailId() );
			}
			
			return optCust.get();
		}


	@Override
	public Customer saveCustomer(Customer cust) {
		
		return customerRepo.save(cust);
	}

	@Override
	public Customer getCustomerByEmailid(String strEmailId) {
		log.info("finding customer for email id "+ strEmailId);
		Optional<Customer> optCust=customerRepo.findByEmailId(strEmailId);
		if(optCust.isEmpty())
		{
			
			log.info("customer is not present for email id "+ strEmailId);
			throw new  ResourceNotFoundException("customer do not exist for email id "+ strEmailId);
		}
		Customer cust=optCust.get();
		log.info("custome is present for eid "+ strEmailId);
		return cust;
	}

	
	@Override
	public Integer createCustomer(@Valid CustomerRequestDTO crDto) {
		log.info("Inside save customer of service with given request"+crDto);
		
		
		Optional<Customer> optCust=customerRepo.findByEmailId(crDto.getEmailId());
		if(optCust.isPresent())
		{
			
			throw new EntityAlreadyExistException("Customer email id is already existing ");
		}
		
		
		Address add=Address.builder().addressLine1(crDto.getAddressLine1())
				.addressLine2(crDto.getAddressLine2())
				.city(crDto.getCity())
				.state(crDto.getState())
				.pincode(crDto.getPincode())
				.build();
	
		Address addCreated=addressRepo.save(add);
		
		
		Customer cust=Customer.builder().firstName(crDto.getFirstName()).lastName(crDto.getLastName()).emailId(crDto.getEmailId())
				.contactNo(crDto.getContactNo()).address(addCreated).gender(crDto.getGender()).password(crDto.getPassword()).registrationDate(LocalDate.now()).expiryDate(LocalDate.now().plusDays(expiryDays)).build();
		
		log.info("Cust enttity saved",cust);;
		Customer custCreated=customerRepo.save(cust);
		
	
		
		
		return custCreated.getCustomerId();
		
	}

	
	@Override
	public List<Customer> findByFirstNameLike(String fn) {
		
		log.info("inside List<Customer> findByFirstNameLike(String fn)");
		//List<Customer> lst=customerRepo.findByFirstNameLike("%"+fn+"%");- 
		    /*
		 * //	List<Customer> findByFirstNameContaining(String fnm);
          //	List<Customer> findByfirstNameContains(String fnm);
         //	List<Customer> findByfirstNameIsContaining(String fnm);
           * same as like findByFirstNameLike("%"+fn+"%")*/
		
	  //	List<Customer> lst=customerRepo.findByFirstNameLike("%"+fn);//t in last ex scott-ex %t
		List<Customer> lst=customerRepo.findByFirstNameLike(fn+"%");//s at first ex smith,scott-ex s%
		
		return lst;
	}

	
	@Override
	public List<Customer> findByFirstNameContaining(String fnm) {
		
		List <Customer> lst=customerRepo.findByfirstNameIsContaining(fnm);
		return lst;
	}
	
	
	@Override
	public List<Customer> findByfirstNameContains(String fnm) {
		List <Customer> lst=customerRepo.findByfirstNameContains(fnm);
		return lst;
	}

	@Override
	public List<Customer> findByfirstNameIsContaining(String fnm) {
		List<Customer> lst=customerRepo.findByfirstNameIsContaining(fnm);
		return lst;
	}

	@Override
	public List<Customer> findByFirstNameIgnoreCase(String fn) {
		List<Customer> lst=customerRepo.findByFirstNameIgnoreCase(fn);
		return lst;
	}

	@Override
	public List<CustomerFnmLnmGenderDTO> findByLastName(String lnm) {
		List<CustomerFnmLnmGenderDTO> lst=customerRepo.findByLastName(lnm);
		return lst;
	}

	@Override
	public Boolean existsByEmailIdAndPassword(String email, String password)
	{
		return customerRepo.existsByEmailIdAndPassword(email, password);
		
	}


	@Override
	public Customer getCustomerByCustId(String strCustId) {
		
		return null;
	}


	@Override
	public void sendEmailOfExpiry() {
		List<Customer> lst=customerRepo.getCustomerByExpiryDate(LocalDate.of(2024,10,25),LocalDate.of(2024,10,25).plusDays(1));
		for (Customer customer : lst) {
			System.out.println(customer);
			System.out.println(customer.getExpiryDate());
		}
		
		
	}


	@Override
	public List<Customer> getAllCustomer() {
		// TODO Auto-generated method stub
		return customerRepo.findAll();
	}


	@Override
	public List<String> getEmailsofAllCustomers() {
		
		return customerRepo.getAllEmails();
	}


	@Override
	public List<Customer> getCustomerWithPagination(int pageSize, int offSet) {
		PageRequest pageRequest=PageRequest.of(offSet, pageSize);
		Page<Customer>lst=customerRepo.findAll(pageRequest);
		
		
		return lst.getContent();//slice
	}


	@Override
	public List<Customer> getCustomersWithPaginationAndSorting(String field, int pageSize, int offSet) {
		PageRequest pageRequest=PageRequest.of(offSet, pageSize, Sort.by(field).ascending());
		Page<Customer> customers=customerRepo.findAll(pageRequest);
		
		
		return customers.getContent();
	}


	@Override
	public boolean isValidCustomerId(int customerId) {
		return customerRepo.existsById(customerId);
	}

}
