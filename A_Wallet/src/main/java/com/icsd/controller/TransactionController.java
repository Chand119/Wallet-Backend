package com.icsd.controller;

import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.icsd.dto.common.ApiResponse;
import com.icsd.dto.common.request.TransactionDepositRequestDTO;
import com.icsd.model.Transaction;
import com.icsd.service.TransactionService;

import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping(value = "/transaction")
@CrossOrigin(value = "*")
@Slf4j

public class TransactionController {
	
	@Autowired
	 TransactionService transactionService;
	@PostMapping(value="/depositAmountInAccount")

	public ResponseEntity<ApiResponse> depoistAmountInAccount(@RequestBody @Valid  TransactionDepositRequestDTO tdReq)
	{
		//query
		//log.debug("inside depois1111t amount in acccount - inside transaction controller");//it is not working
		log.info("inside depoist22222 amount in acccount - inside transaction controller");
		System.out.println(tdReq);
		Transaction trans=transactionService.depositAmountInAccount(tdReq);
		ApiResponse apiresponse =new ApiResponse(HttpStatus.OK.value(),"transaction  completed successfully",trans.getTransactionId());
		return new ResponseEntity<ApiResponse>(apiresponse,HttpStatus.OK);
//		ApiResponse apiresponse=new ApiResponse(HttpStatus.OK.value()	, "account created successfully", accountNumber);
//		return new ResponseEntity<ApiResponse>(apiresponse,HttpStatus.OK);
//		
	}
	
	@PostMapping(value="/withDrawAmountFromAccount")
	public ResponseEntity<ApiResponse> withDrawAmountInAccount(@RequestBody @Valid  TransactionDepositRequestDTO tdReq)
	{
		//query
		//log.debug("inside depois1111t amount in acccount - inside transaction controller");//it is not working
		log.info("inside withdraw amount in acccount - inside transaction controller");
		System.out.println(tdReq);
		Transaction trans=transactionService.WithDrawAmountfromAccount(tdReq);
		if(trans==null) {
			log.info("Hey!!!!!!!!!!!!!");
			ApiResponse apiresponse=new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Insufficient Balance Found", null);
			return new ResponseEntity<ApiResponse>(apiresponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		ApiResponse apiresponse =new ApiResponse(HttpStatus.OK.value(),"Transaction  Completed Successfully",trans.getTransactionId());
		return new ResponseEntity<ApiResponse>(apiresponse,HttpStatus.OK);
//		ApiResponse apiresponse=new ApiResponse(HttpStatus.OK.value()	, "account created successfully", accountNumber);
//		return new ResponseEntity<ApiResponse>(apiresponse,HttpStatus.OK);
//		
	}
	
	@PostMapping(value="/fundTransfer")
	public ResponseEntity<ApiResponse> fundTransfer(@RequestBody @Valid  TransactionDepositRequestDTO tdReq)
	{
		//query
		//log.debug("inside depois1111t amount in acccount - inside transaction controller");//it is not working
		log.info("inside withdraw amount in acccount - inside transaction controller");
		System.out.println(tdReq);
		Transaction trans=transactionService.fundTransferFunction(tdReq);
		if(trans==null) {
			log.info("wowwwwwwwwwwwwwww322222222222222");
			ApiResponse apiresponse=new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Insufficient Balance Found", null);
			log.info("till here!!!!!!!");
			return new ResponseEntity<ApiResponse>(apiresponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		ApiResponse apiresponse =new ApiResponse(HttpStatus.OK.value(),"transaction  completed successfully",trans.getTransactionId());
		return new ResponseEntity<ApiResponse>(apiresponse,HttpStatus.OK);
//		ApiResponse apiresponse=new ApiResponse(HttpStatus.OK.value()	, "account created successfully", accountNumber);
//		return new ResponseEntity<ApiResponse>(apiresponse,HttpStatus.OK);
//		
	}
	
	@GetMapping(value="/getTransactionByAccNum/{accNo}")
	public ResponseEntity<ApiResponse> getTransactionByAccNum(
	        @PathVariable int accNo,
	        @RequestParam(required=false) String date) {

	    List<Transaction> lst;
	    
	    
	    if (date!=null&&!date.isEmpty()) {
	       
	        lst=transactionService.getTransactionsByAccountNumberAndDate(accNo, LocalDate.parse(date));
	    } else {
	        
	        lst=transactionService.getTransactionsByAccountNumber(accNo);
	    }

	    ApiResponse apiResponse = new ApiResponse(HttpStatus.OK.value(), "Transactions fetched successfully", lst);
	    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}

}
