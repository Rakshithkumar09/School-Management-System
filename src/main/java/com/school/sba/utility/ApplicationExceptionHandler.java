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

import com.school.sba.exception.AdminCannotBeAssignedToAcademicProgramException;
import com.school.sba.exception.AdminNotFoundException;
import com.school.sba.exception.IllegalRequestException;
import com.school.sba.exception.ProgramNotFoundByIdException;
import com.school.sba.exception.ScheduleNotFoundByIdException;
import com.school.sba.exception.SchoolNotFoundByIdException;
import com.school.sba.exception.SubjectNotFoundByIdException;
import com.school.sba.exception.TeacherNotFoundException;
import com.school.sba.exception.UserNotFoundByIdException;

@RestControllerAdvice
public class ApplicationExceptionHandler extends  ResponseEntityExceptionHandler
{ 



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
		return structure(HttpStatus.NOT_FOUND,  ex.getMessage(),"user Not Found With The Given Id");

	}

	@ExceptionHandler(IllegalRequestException.class)
	public ResponseEntity<Object> handelIllegalRequest(IllegalRequestException ex)
	{
		return structure(HttpStatus.BAD_REQUEST,  ex.getMessage(),"illegal request");

	} 

	@ExceptionHandler(ScheduleNotFoundByIdException.class)
	public ResponseEntity<Object> handelScheduleNotFoundById(ScheduleNotFoundByIdException ex)
	{
		return structure(HttpStatus.NOT_FOUND,ex.getMessage(),"Schedule Not Found With The Given Id");
	}


	@ExceptionHandler(AdminCannotBeAssignedToAcademicProgramException.class)
	public ResponseEntity<Object> handelAdminCannotBeAssigned(AdminCannotBeAssignedToAcademicProgramException ex)
	{
		return structure(HttpStatus.BAD_REQUEST,ex.getMessage(),"User Entered UserId Is Admin's");
	}

	@ExceptionHandler(ProgramNotFoundByIdException.class)
	public ResponseEntity<Object> handelProgramNotFoundById(ProgramNotFoundByIdException ex)
	{
		return structure(HttpStatus.NOT_FOUND ,ex.getMessage(),"Program Not Found With The Given Id");
	}
	@ExceptionHandler(SubjectNotFoundByIdException.class)
	public ResponseEntity<Object> handelSubjectNotFoundById(SubjectNotFoundByIdException ex)
	{
		return structure(HttpStatus.NOT_FOUND,ex.getMessage(),"Subject Not Found With The Given Id ");

	}
	@ExceptionHandler(TeacherNotFoundException.class)
	public ResponseEntity<Object> handelTeacherNotFound(TeacherNotFoundException ex)
	{

		return structure( HttpStatus.NOT_FOUND,ex.getMessage(),"Teacher Not Present In User");  
	}
	@ExceptionHandler(AdminNotFoundException.class)
	public ResponseEntity<Object> handelAdminNotfound(AdminNotFoundException ex)
	{
		return structure(HttpStatus.NOT_FOUND,ex.getMessage(),"entering the wrong user role only can register");
	}
} 
