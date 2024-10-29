package com.icsd.exception;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


import com.icsd.dto.common.ApiResponse;

import lombok.extern.slf4j.Slf4j;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	//query - if i comment this function - than op is different incase i call deposit amount in account function - request dto- for customer id =0- i.e customer id should not be 0 
	//i want to check the ip is same but - op is diff - for commented and uncommented code.
	
//	@ExceptionHandler(Exception.class)
//	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
//	public ResponseEntity<ApiResponse> handleException(Exception ex,WebRequest webRequest){
//		log.debug("inside handle exception - 1");
//		if(ex instanceof MethodArgumentTypeMismatchException)
//		{
//			log.debug("inside handle exception  globalexcption handler - instance of number format exeption");
//			ApiResponse apiResponse = new ApiResponse(400,"pls enter valid number ",ex.getMessage());
//			return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.BAD_REQUEST);	
//		}
//		ApiResponse apiResponse = new ApiResponse(400,"something went wrong",ex.getMessage());
//		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.BAD_REQUEST);
//		
//	}
	
	@ExceptionHandler(IcsdException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ResponseEntity<ApiResponse> handleIcsdException(IcsdException ex){
		ApiResponse apiResponse = new ApiResponse(400,"something went wrong",ex.getMessage());
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.BAD_REQUEST);
	}
	
	
	

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EntityAlreadyExistException.class)
    public ResponseEntity<ApiResponse> handleEntityAlreadyExistsException(EntityAlreadyExistException ex,
                                                                          WebRequest webRequest) 
    {
    	log.info("handleEntityAlreadyExistsException fired");
        return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_ACCEPTABLE.value(), ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiResponse> handleResourecNotFoundExecption(ResourceNotFoundException ex, WebRequest webRequest) {
    	log.info("handleResourecNotFoundExecption fired");
    	
        return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_ACCEPTABLE.value(), ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    } 
}
