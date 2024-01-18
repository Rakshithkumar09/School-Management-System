package com.school.sba.utility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.school.sba.exception.IllegalRequestException;
import com.school.sba.exception.ScheduleNotFoundByIdException;
import com.school.sba.exception.SchoolNotFoundByIdException;
import com.school.sba.exception.UserNotFoundByIdException;

@RestControllerAdvice
public class ApplicationExceptionHandler extends  ResponseEntityExceptionHandler
{ 
	
//	@ExceptionHandler
//   public ResponseEntity<ResponseStructure<String>> schoolNotFoundById(SchoolNotFoundByIdException ex)
//   { 
//	   ResponseStructure<String> req = new ResponseStructure<String>();
//	   req.setStatus(HttpStatus.NOT_FOUND.value());
//	   req.setMessage("School Object with the given Id doesn't exist");
//	   req.setData(ex.getMessage());
//	  
//	   return new ResponseEntity<ResponseStructure<String>>(req,HttpStatus.NOT_FOUND);
//   }
	
// ----------------------------------------------------------------------------------------------
	
	private ResponseEntity<Object> structure(HttpStatus status,String message,Object rootCause )
	{
		return new ResponseEntity<Object>(Map.of(
				"status",status.value(),
				"message",message,
				"rootCause",rootCause 
				),status);
	}
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		List<ObjectError> allErrors = ex.getAllErrors();
		Map<String, String> errors = new HashMap<String, String>();
		allErrors.forEach(error ->
		{
			     FieldError  fieldError = (FieldError) error;
			     errors.put(fieldError.getField(), fieldError.getDefaultMessage());
			     
		});
		
		return structure(HttpStatus.BAD_REQUEST,"Failed to save the data",errors);
	}
	
	@ExceptionHandler(UserNotFoundByIdException.class)
	public ResponseEntity<Object> handelUserNotFoundById(UserNotFoundByIdException ex)
	{
		return structure(HttpStatus.NOT_FOUND,  ex.getMessage(),"user not found with the given id");
		 
	}
	@ExceptionHandler(IllegalRequestException.class)
	public ResponseEntity<Object> handelIllegalRequest(IllegalRequestException ex)
	{
		return structure(HttpStatus.BAD_REQUEST,  ex.getMessage(),"illegal request");
		
	} 
	@ExceptionHandler(ScheduleNotFoundByIdException.class)
	public ResponseEntity<Object> handelScheduleNotFoundById(ScheduleNotFoundByIdException ex)
	{
		return structure(HttpStatus.NOT_FOUND,ex.getMessage(),"Schedule not found by given id");
	}
	 
}
