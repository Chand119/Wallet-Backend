package com.icsd.dto.common;


import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;


import lombok.Data;


@Data

public class ApiResponse {

	

	private Integer code;
	
	
	private String message;
	@JsonFormat(shape=JsonFormat.Shape.STRING,pattern="dd-MM-yyyy hh:mm:ss")// shape thid variable in this type when returning the json format// 
	private LocalDateTime timestamp;
	public ApiResponse()
	{
		this.timestamp=LocalDateTime.now();
	}
	
	private Object data;
	
	public ApiResponse(Integer code, String message, Object data) {
		
		this();
		this.code = code;
		this.message = message;
		this.data = data;
	}
	public ApiResponse(Integer code, String message) {
		
		this();
		this.code = code;
		this.message = message;
	}
	
	



	
	
}
