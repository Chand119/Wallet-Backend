package com.icsd.exception;

public class EntityAlreadyExistException extends RuntimeException 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public EntityAlreadyExistException()
	{
		
	}
	public EntityAlreadyExistException(String msg)
	{
		super(msg+ " "+ "Entity Already Exists exception fired ");
	}

}
