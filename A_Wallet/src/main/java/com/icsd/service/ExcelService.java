package com.icsd.service;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.icsd.model.Customer;

public interface ExcelService {
	
	public ByteArrayInputStream generateExcelSheet(List<Customer> lst);
	
	public List<String> saveDataToDatabase(MultipartFile file);

}
