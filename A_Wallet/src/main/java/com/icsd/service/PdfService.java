package com.icsd.service;

import java.util.List;


import com.icsd.model.Customer;


public interface PdfService {
	
	public byte[] generateCustomerPdf(List<Customer> customers);

}
