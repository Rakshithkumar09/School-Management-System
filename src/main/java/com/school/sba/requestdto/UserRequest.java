package com.school.sba.requestdto;

import com.school.sba.enums.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Builder
public class UserRequest {
	
	//@NotEmpty(message = "user namecan not be empty")
	private String userName;
	
	//@NotEmpty(message = "email canot be empty")
	//@Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
	//@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password must"
		//+ " contain at least one letter, one number, one special character")
	private String passward;
	//@NotEmpty(message = "email canot be empty")
	private String firstName;
	
	private String lastName;
	//@NotEmpty(message = "invalid mail")
	//@Email(regexp = "[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}", message = "invalid email ")
	private String email;
	
	private long contactNo;
	private UserRole userRole;
}
