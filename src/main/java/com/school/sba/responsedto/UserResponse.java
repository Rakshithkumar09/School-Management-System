package com.school.sba.responsedto;

import java.util.List;

import com.school.sba.entity.AcademicProgram;
import com.school.sba.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
	private int userId;
	private String userName;
	private String firstName;
	private String lastName;
	private String email;
	private long contactNo;
	private UserRole userRole;
	 
	private List<String> academicProgram;
}
 