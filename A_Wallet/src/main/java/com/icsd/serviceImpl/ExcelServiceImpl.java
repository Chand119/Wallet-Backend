package com.icsd.serviceImpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.icsd.model.Address;
import com.icsd.model.Customer;
import com.icsd.model.Gender;
import com.icsd.repo.AddressRepo;
import com.icsd.repo.CustomerRepo;
import com.icsd.service.ExcelService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class ExcelServiceImpl implements ExcelService{
	
	private final CustomerRepo customerRepo;
	
	private final AddressRepo addressRepo;

	@Override
	public ByteArrayInputStream generateExcelSheet(List<Customer> lst) {
		Workbook workbook=new XSSFWorkbook();
		
		Sheet sheet=workbook.createSheet("Customers Data");
		Row headerRow=sheet.createRow(0);
		headerRow.createCell(0).setCellValue("EmailId");
		headerRow.createCell(1).setCellValue("First Name");
		headerRow.createCell(2).setCellValue("Last Name");
		headerRow.createCell(3).setCellValue("Contact No");
		headerRow.createCell(4).setCellValue("Address Line 1");
		headerRow.createCell(5).setCellValue("Address Line 2");
		headerRow.createCell(6).setCellValue("City");
		headerRow.createCell(7).setCellValue("State");
		headerRow.createCell(8).setCellValue("Pincode");
		headerRow.createCell(9).setCellValue("Registration Date");
		headerRow.createCell(10).setCellValue("Expiry Date");
		
		int rowIndex=1;
		for (Customer customer : lst) {
			
			Row row=sheet.createRow(rowIndex++);
			row.createCell(0).setCellValue(customer.getEmailId());
			row.createCell(1).setCellValue(customer.getFirstName());
			row.createCell(2).setCellValue(customer.getLastName());
			row.createCell(3).setCellValue(customer.getContactNo());
			row.createCell(4).setCellValue(customer.getAddress().getAddressLine1());
			row.createCell(5).setCellValue(customer.getAddress().getAddressLine2());
			row.createCell(6).setCellValue(customer.getAddress().getCity());
			row.createCell(7).setCellValue(customer.getAddress().getState());
			row.createCell(8).setCellValue(customer.getAddress().getPincode());
			row.createCell(9).setCellValue(customer.getRegistrationDate().toString());
			row.createCell(10).setCellValue(customer.getExpiryDate().toString());
			
		}
		ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
		try {
			workbook.write(outputStream);
			workbook.close();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
		return new ByteArrayInputStream(outputStream.toByteArray());
		
	
	}
	
	public List<String> saveDataToDatabase(MultipartFile file) 
	{
		log.info("Starting to save the data");
		List<String> customerAlreadyExists=new ArrayList<>();
		List<String> existingEmails=customerRepo.getAllEmails();
		List<Customer> customersToAdd=new ArrayList<>();
		Map<String,Customer> processedCustomers=new HashMap<>();
		
		try {
			
		XSSFWorkbook workbook=new XSSFWorkbook(file.getInputStream());
		Sheet sheet=workbook.getSheetAt(0);
		log.info("proccessing the sheet");
		
		DataFormatter dataFormatter=new DataFormatter();
		for (Row row:sheet) {
			
			int num=row.getRowNum();
			String email=row.getCell(0).getStringCellValue();
			if(num==0) {
				continue;
			}
			if(!existingEmails.contains(email) && !processedCustomers.containsKey(email)){
				System.out.println("num"+num);
				Address address=Address.builder()
						.addressLine1(row.getCell(8).getStringCellValue())
						.addressLine2(row.getCell(9).getStringCellValue())
						.city(row.getCell(10).getStringCellValue())
						.state(row.getCell(11).getStringCellValue())
						.pincode(row.getCell(12).getStringCellValue())
						.build();
				
				
				Customer customer=Customer.builder()
						.emailId(dataFormatter.formatCellValue(row.getCell(0)))
	                    .firstName(dataFormatter.formatCellValue(row.getCell(1)))
	                    .lastName(dataFormatter.formatCellValue(row.getCell(2)))
	                    .contactNo(dataFormatter.formatCellValue(row.getCell(3)))
	                    .registrationDate(parseDate(dataFormatter.formatCellValue(row.getCell(4))))
	                    .expiryDate(parseDate(dataFormatter.formatCellValue(row.getCell(5))))
	                    .gender(dataFormatter.formatCellValue(row.getCell(6)).equalsIgnoreCase("MALE") ? Gender.MALE : Gender.FEMALE)
	                    .password(dataFormatter.formatCellValue(row.getCell(7)))
	                    .address(address)
	                    .build();
				
				
				processedCustomers.put(email, customer);
				customersToAdd.add(customer);
						
			
			}
			
			else {
				customerAlreadyExists.add(dataFormatter.formatCellValue(row.getCell(0)));
			}
			
			
		}
		if(!customersToAdd.isEmpty()) {
			  addressRepo.saveAll(customersToAdd.stream().map(Customer::getAddress).distinct().collect(Collectors.toList()));
			customerRepo.saveAll(customersToAdd);
			
		}
		workbook.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return customerAlreadyExists;
	}
	
	
	
	
	
	private LocalDate parseDate(String dateString) {
	    if (dateString == null || dateString.isEmpty()) {
	        return null;
	    }

	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    try {
	        return LocalDate.parse(dateString, formatter);
	    } catch (DateTimeParseException e) {
	        log.error("Invalid date format: " + dateString, e);
	        return null; 
	    }
	}



}
