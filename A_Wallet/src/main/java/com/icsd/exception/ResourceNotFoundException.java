package com.icsd.exception;

public class ResourceNotFoundException  extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	public ResourceNotFoundException()
	{
		
	}
	public ResourceNotFoundException(String msg)
	{
		super(msg + "  resource not found exception fired ");
	}
	

}
