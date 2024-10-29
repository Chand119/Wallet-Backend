package com.icsd.exception;

//import lombok.NoArgsConstructor;
//
//@NoArgsConstructor
public class IcsdException extends Exception {

	
	private static final long serialVersionUID = 1L;
	public IcsdException()
	{
		
	}
	public IcsdException(String message) {
		super(message);
	}
}
