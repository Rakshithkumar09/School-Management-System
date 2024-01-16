package com.school.sba.responsedto;

import com.school.sba.enums.UserRole;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UserResponse {
	private String userName;
	private String firstName;
	private String lastName;
	private String email;
	private long contactNo;
	private UserRole userRole;
}
