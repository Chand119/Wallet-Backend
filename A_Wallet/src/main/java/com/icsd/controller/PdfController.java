package com.icsd.controller;

import java.util.List;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.icsd.model.Customer;
import com.icsd.repo.CustomerRepo;
import com.icsd.service.PdfService;

import lombok.RequiredArgsConstructor;

@RequestMapping(value="/pdf")
@RequiredArgsConstructor
@CrossOrigin("*")
@RestController
public class PdfController {
	
	
	
	private final PdfService pdfService;
	private final CustomerRepo customerRepo;
	
	@GetMapping(value="/getCustomerPdf")
	public ResponseEntity<byte[]> generateCustomerPdf(){
		try {
			List<Customer> customers=customerRepo.findAll();
			byte[] pdfBytes=pdfService.generateCustomerPdf(customers);
			
			 HttpHeaders headers = new HttpHeaders();
	            headers.add("Content-Type", "application/pdf");
	            headers.add("Content-Disposition", "attachment; filename=customers.pdf");

	            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
			
		}
		catch (Exception e) {
			  e.printStackTrace();
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
