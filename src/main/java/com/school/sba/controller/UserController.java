package com.school.sba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.school.sba.requestdto.SchoolRequest;
import com.school.sba.requestdto.UserRequest;
import com.school.sba.responsedto.UserResponse;
import com.school.sba.service.UserService;
import com.school.sba.utility.ResponseStructure;

import jakarta.validation.Valid;

@RestController
public class UserController 
{
	@Autowired
    private UserService userService;
	
	@PostMapping("/users/register")
	public ResponseEntity<ResponseStructure<UserResponse>> saveUser(@RequestBody @Valid UserRequest user) 
	{
		return userService.saveUser(user);
		
	}
	@GetMapping("users/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> findUserById(@PathVariable int userId)
	{
		return userService.findUserById(userId);
	}
	@DeleteMapping("users/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> deleteUser(@PathVariable int userId)
	{
		return userService.deleteUser(userId);
	}
	 
	
	@PutMapping("/academic-programs/{programId}/users/{userId}")
		public ResponseEntity<ResponseStructure<UserResponse>> assignTeacherAndStudent(@PathVariable int programId,@PathVariable int userId)
		{
			return userService.assignTeacherAndStudent(programId,userId);
		
		} 
	@PutMapping("/subjects/{subjectId}/users/{userId}")
	public  ResponseEntity<ResponseStructure<UserResponse>> addSubjectToTeacher(@PathVariable int subjectId,@PathVariable int userId)
	{
		return userService.addSubjectToTeacher(subjectId,userId);
		
	}
	
	
}
  