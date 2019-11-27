package com.dinesh.org.SocialInteractionManagementApp.exception;

public class DuplicateWebsiteException extends Exception
{
	private static final long serialVersionUID = 1L;
	private String message;

	public DuplicateWebsiteException(String message)
	{
		super();
		this.setMessage(message);
	}

	public DuplicateWebsiteException()
	{
		super();
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}
	
	
}
