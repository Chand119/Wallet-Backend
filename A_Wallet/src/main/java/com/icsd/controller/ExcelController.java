package com.icsd.controller;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.icsd.dto.common.ApiResponse;
import com.icsd.exception.ResourceNotFoundException;
import com.icsd.model.Customer;
import com.icsd.service.CustomerService;
import com.icsd.service.ExcelService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/excel")
@CrossOrigin("*")
@Slf4j
public class ExcelController {
	
	private final  ExcelService excelService;
	
	private final  CustomerService customerService;
	
	
	
	
	@GetMapping("/downloadexcelfile")
	public ResponseEntity<InputStreamResource> downloadExcelSheet() {
	    List<Customer> lst = customerService.getAllCustomer();
	    if(ObjectUtils.isEmpty(lst)) {
	    	log.info("List it Empty/null");
	    	throw new ResourceNotFoundException("Can not generate the excel sheet as Customers List is empty");
	    }
	    
	    ByteArrayInputStream inputStream = excelService.generateExcelSheet(lst);
	    InputStreamResource file= new InputStreamResource(inputStream);
	    String fileName="customers.xlsx";
	    String contentType="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

	    return ResponseEntity.ok()
	            .contentType(MediaType.parseMediaType(contentType))
	            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
	            .body(file);
	}
	
	
	
	@PostMapping("/uploadexcelfile")
	public ResponseEntity<ApiResponse> uploadExcelFile(@RequestParam("file") MultipartFile file){
		String fileName=file.getOriginalFilename();
		
		if(file.isEmpty()||fileName==null || (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx"))) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.BAD_REQUEST.value(),"File is Empty/Invalid File(only Excel file is allowed)",null),HttpStatus.BAD_REQUEST);
		}
		try {
			if(file.getSize()>0) {
				Workbook workbook=WorkbookFactory.create(file.getInputStream());
				if(workbook.getNumberOfSheets()==0 || workbook.getSheetAt(0).getPhysicalNumberOfRows()==0) {
					 return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST.value(), "The Excel file is empty.", null), HttpStatus.BAD_REQUEST);
				}
				
			}
			List<String> emails=excelService.saveDataToDatabase(file);
			if(ObjectUtils.isEmpty(emails)) {
				return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.OK.value(), "Data Saved Successfully", emails),HttpStatus.OK);
			}
			return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.IM_USED.value(), "Data Saved Successfully Except(These may be duplicate email):", emails),HttpStatus.IM_USED);
			
	
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.SERVICE_UNAVAILABLE.value(), "Can't upload", null),HttpStatus.SERVICE_UNAVAILABLE);
		}
	}


}
