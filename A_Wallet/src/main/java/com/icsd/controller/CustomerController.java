package com.icsd.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.icsd.dto.common.ApiResponse;
import com.icsd.dto.common.request.CustomerLoginDTO;
import com.icsd.dto.common.request.CustomerRequestDTO;
import com.icsd.dto.response.CustomerFnmLnmGenderDTO;
import com.icsd.model.Customer;
import com.icsd.service.CustomerService;


import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/customer")
@CrossOrigin(value = "*")
@Slf4j
public class CustomerController {
	/*
	 
	  "data": {
      "firstName": "rohan",
      "lastName": "kumar",
      "emailId": "rb@rb.com",
      "contactNo": "987654321",
      "gender": "MALE",
      "password": "icsd",
      "confirmPassword": "icsd",
      "addressLine1": "delhi",
      "addressLine2": "sec 1123",
      "city": "delhi",
      "pincode": "132103",
      "state": "delhis"
  }
  */
	
	@Autowired 
	CustomerService customerservice;
	
	@PostMapping(value = "/createcustomer")
	//public ResponseEntity<ApiResponse> createCustomer(@RequestBody @Valid CustomerRequestDto customerRequest, BindingResult br) throws IcsdException
	public ResponseEntity<ApiResponse> createCustomer(@RequestBody @Valid CustomerRequestDTO customerRequest) //throws IcsdException
	{
		//log.info("inside create method of cusgtomer controller");
//		if(br.hasErrors())
//		{
//			//throw new IcsdException("Ename is null");
//		}
		System.out.println("Create Customer API called at "+new Date());
		System.out.println(customerRequest);//CustomerRequestDto [firstName=rohan, lastName=kumar, emailId=rb@rb.com, contactNo=987654321, gender=MALE, password=icsd, confirmPassword=icsd, addressLine1=delhi, addressLine2=sec 1123, city=delhi, State=delhis, pincode=132103]
		Integer generatedCustomerId=customerservice.createCustomer(customerRequest);
		//CustomerResponse customerResponse = new CustomerResponse(generatedCustomerId);
		
		//return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		//CustomerRequestDto cer=new CustomerRequestDto("rohan", "kumar", "rb@rb.com", "987654321", Gender.MALE, "icsd", "icsd", "delhi", "sec 1123", "delhi","delhis","132103");
		ApiResponse apiResponse = new ApiResponse(HttpStatus.OK.value(), "Customer Created Successfully",  generatedCustomerId);
		return new ResponseEntity<ApiResponse> (apiResponse,HttpStatus.OK);
		
	}
	
	@PostMapping(value="/isValidUser")
	public ResponseEntity<ApiResponse> isValidUser(@RequestBody  @Valid CustomerLoginDTO customerLogin)
	{
		log.info("authenticating user - valid or not ");
		//customerLogin.
		
		Customer res=customerservice.isValidCustByEmailidAndPwd(customerLogin);
		ApiResponse apiresponse=new ApiResponse(HttpStatus.OK.value(), "user is validated", res);
		return new ResponseEntity<ApiResponse>	(apiresponse,HttpStatus.OK);
	}

	
	@GetMapping(value="/get/findByLastName/{lnm}")
	public  ResponseEntity<ApiResponse> findByLastNameDTOResponse(@PathVariable String lnm)
	{
		log.info("inside controller findByLastName lnm="+ lnm);
		List<CustomerFnmLnmGenderDTO> lst=customerservice.findByLastName(lnm);
		if(lst.isEmpty())
		{
			log.info("no record found");
			ApiResponse apiresponse=new ApiResponse(HttpStatus.NOT_FOUND.value(), "no customer found ");
			return new ResponseEntity<ApiResponse>(apiresponse,HttpStatus.NOT_FOUND);
		}
		ApiResponse apiresponse=new ApiResponse(HttpStatus.FOUND.value(), "customer found ", lst);
		
		return new ResponseEntity<>(apiresponse,HttpStatus.OK);
	}
	
	@GetMapping(value="/get/findByFirstNameIgnoreCase/{fn}")
	public  ResponseEntity<ApiResponse> findByFirstNameIgnoreCase(@PathVariable String fn)
	{
		log.info("inside controller findByFirstNameIgnoreCase fn="+ fn);
		List<Customer> lst=customerservice.findByFirstNameIgnoreCase(fn);
		if(lst.isEmpty())
		{
			log.info("no record found");
			ApiResponse apiresponse=new ApiResponse(HttpStatus.NOT_FOUND.value(), "no customer found ");
			return new ResponseEntity<ApiResponse>(apiresponse,HttpStatus.NOT_FOUND);
		}
		ApiResponse apiresponse=new ApiResponse(HttpStatus.FOUND.value(), "customer found ", lst);
		
		return new ResponseEntity<>(apiresponse,HttpStatus.OK);
	}
	
	@GetMapping(value="/get/findByFirstNameLike/{fn}")
	public  ResponseEntity<ApiResponse> findByFirstNameLike(@PathVariable String fn)
	{
		log.info("inside controller findByFirstNameLike fn="+ fn);
		List<Customer> lst=customerservice.findByFirstNameLike(fn);
		if(lst.isEmpty())
		{
			log.info("no record found");
			ApiResponse apiresponse=new ApiResponse(HttpStatus.NOT_FOUND.value(), "no customer found ");
			return new ResponseEntity<ApiResponse>(apiresponse,HttpStatus.NOT_FOUND);
		}
		ApiResponse apiresponse=new ApiResponse(HttpStatus.FOUND.value(), "customer found ", lst);
		
		return new ResponseEntity<>(apiresponse,HttpStatus.OK);
	}
	
	@GetMapping(value="/get/findByfirstNameIsContaining/{fn}")
	public  ResponseEntity<ApiResponse> findByfirstNameIsContaining(@PathVariable String fn)
	{
		log.info("inside controller findByfirstNameIsContaining fn="+ fn);
		List<Customer> lst=customerservice.findByfirstNameIsContaining(fn);
		if(lst.isEmpty())
		{
			log.info("no record found");
			ApiResponse apiresponse=new ApiResponse(HttpStatus.NOT_FOUND.value(), "no customer found ");
			return new ResponseEntity<ApiResponse>(apiresponse,HttpStatus.NOT_FOUND);
		}
		ApiResponse apiresponse=new ApiResponse(HttpStatus.FOUND.value(), "customer found ", lst);
		
		return new ResponseEntity<>(apiresponse,HttpStatus.OK);
	}
	
	@GetMapping(value="/get/findByfirstNameContains/{fn}")
	public  ResponseEntity<ApiResponse> findByfirstNameContains(@PathVariable String fn)
	{
		log.info("inside controller findByfirstNameContains fn="+ fn);
		List<Customer> lst=customerservice.findByfirstNameContains(fn);
		if(lst.isEmpty())
		{
			log.info("no record found");
			ApiResponse apiresponse=new ApiResponse(HttpStatus.NOT_FOUND.value(), "no customer found ");
			return new ResponseEntity<ApiResponse>(apiresponse,HttpStatus.NOT_FOUND);
		}
		ApiResponse apiresponse=new ApiResponse(HttpStatus.FOUND.value(), "customer found ", lst);
		
		return new ResponseEntity<>(apiresponse,HttpStatus.OK);
	}
	
	@GetMapping(value="/get/findByFirstNameContaining/{fn}")
	public  ResponseEntity<ApiResponse> findByFirstNameContaining(@PathVariable String fn)
	{
		log.info("inside controller findByFirstNameContaining fn="+ fn);
		List<Customer> lst=customerservice.findByFirstNameContaining(fn);
		if(lst.isEmpty())
		{
			log.info("no record found");
			ApiResponse apiresponse=new ApiResponse(HttpStatus.NOT_FOUND.value(), "no customer found ");
			return new ResponseEntity<ApiResponse>(apiresponse,HttpStatus.NOT_FOUND);
		}
		ApiResponse apiresponse=new ApiResponse(HttpStatus.FOUND.value(), "customer found ", lst);
		
		return new ResponseEntity<>(apiresponse,HttpStatus.OK);
	}

	
	
	
	
	
	
	

}
