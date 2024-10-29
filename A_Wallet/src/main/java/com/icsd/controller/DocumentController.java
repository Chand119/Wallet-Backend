package com.icsd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.icsd.dto.common.ApiResponse;
import com.icsd.dto.common.request.DocumentDTO;
import com.icsd.model.Document;
import com.icsd.service.DocumentService;

@RestController
@RequestMapping("/documents")
@CrossOrigin("*")
public class DocumentController {
	
	@Autowired
	DocumentService documentservice;
	
	@PostMapping(value="/uploaddoc")
	private ResponseEntity<ApiResponse> uploadDoc(@ModelAttribute DocumentDTO docDTO){
		System.out.println(docDTO);
		//MultipartFile mf=docDTO.getMfile();
//		System.out.println(mf.getName());
//		System.out.println(mf.getContentType());
//		System.out.println(mf.getOriginalFilename());
//		System.out.println(mf.getSize());
    Document Doc=documentservice.saveDoc(docDTO);
    if(Doc==null) {
    	return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.BAD_REQUEST.value(), "Server Errror", null),HttpStatus.BAD_REQUEST);
    }
	ApiResponse apiResponse=new ApiResponse(HttpStatus.OK.value(), "File Uploaded Successfully", Doc);
	
	return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
	}
	@GetMapping(value="/viewdoc/{customerId}")
	public ResponseEntity<ApiResponse> getImages(@PathVariable int customerId){
		List<String> lst=documentservice.getImagesByCustomerId(customerId);
		ApiResponse apiresponse=new ApiResponse(HttpStatus.OK.value(), "Images Fetched successfully", lst);
		
		
		return new ResponseEntity<ApiResponse>(apiresponse,HttpStatus.OK);
	}

	@GetMapping(value="/deletedoc/{fileName}/{customerId}")
	public ResponseEntity<ApiResponse> deletedoc(@PathVariable("fileName") String fileName, @PathVariable("customerId") int customerId){
		boolean res=documentservice.deleteByFile(fileName, customerId);
		if(res) {
		ApiResponse apiResponse=new ApiResponse(HttpStatus.OK.value(), "Document Deleted SuccessFully", res);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
		}
		return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.BAD_REQUEST.value(), "Can't Delete the Doc", res),HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping(value="/checkdoc/{docname}/{customerId}")
	public ResponseEntity<ApiResponse> checkDoc(@PathVariable("docname") String docname, @PathVariable("customerId") int customerId)
	{
		boolean res=documentservice.checkDoc(docname, customerId);
		if(res) {
			return new ResponseEntity<ApiResponse>( new ApiResponse(HttpStatus.IM_USED.value(), "Document present", null),HttpStatus.IM_USED);
		}
		return new ResponseEntity<ApiResponse>(new ApiResponse(HttpStatus.OK.value(),"Document not available ",null),HttpStatus.OK);
	}
	
	
}
