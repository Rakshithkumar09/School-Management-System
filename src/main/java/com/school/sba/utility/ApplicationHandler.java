package com.school.sba.utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.school.sba.exception.SchoolNotFoundByIdException;

@RestControllerAdvice
public class ApplicationHandler 
{ 
	@ExceptionHandler
   public ResponseEntity<ResponseStructure<String>> schoolNotFoundById(SchoolNotFoundByIdException ex)
   { 
	   ResponseStructure<String> req = new ResponseStructure<String>();
	   req.setStatus(HttpStatus.NOT_FOUND.value());
	   req.setMessage("School Object with the given Id doesn't exist");
	   req.setData(ex.getMessage());
	  
	   return new ResponseEntity<ResponseStructure<String>>(req,HttpStatus.NOT_FOUND);
   }
}
