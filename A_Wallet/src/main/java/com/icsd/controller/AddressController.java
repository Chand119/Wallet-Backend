package com.icsd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.icsd.dto.common.ApiResponse;
import com.icsd.service.AddressService;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@Data
@RequestMapping("/address")
@CrossOrigin(value = "*")
public class AddressController {
	
	@Autowired
	AddressService as;

	private final AddressService addressService;
	
	
	@GetMapping(value="/findByAddressLine2IsNotNull")
	public ResponseEntity<ApiResponse> findByAddressLine2IsNotNull()
	{
		ApiResponse apiresponse=new ApiResponse(HttpStatus.OK.value(), "addwith not nullvalue", as.findByAddressLine2IsNotNull());
		return new ResponseEntity<ApiResponse>(apiresponse,HttpStatus.OK);
	}
	
	
	@GetMapping(value="/findByAddressLine2IsNull")
	public ResponseEntity<ApiResponse> findByAddressLine2IsNull()
	{
		ApiResponse apiresponse=new ApiResponse(HttpStatus.OK.value(), "addwith nullvalue", as.findByAddressLine2IsNull());
		return new ResponseEntity<ApiResponse>(apiresponse,HttpStatus.OK);
	}
}
