package com.school.sba.exception;

import lombok.Getter;


@Getter
public class SchoolNotFoundByIdException extends RuntimeException 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String message;

	public SchoolNotFoundByIdException(String message) {
		this.message = message;
	}

}
