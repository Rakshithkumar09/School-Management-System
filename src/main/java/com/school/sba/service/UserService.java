package com.school.sba.service;

import org.springframework.http.ResponseEntity;

import com.school.sba.requestdto.SchoolRequest;
import com.school.sba.requestdto.UserRequest;
import com.school.sba.responsedto.UserResponse;
import com.school.sba.utility.ResponseStructure;

import jakarta.validation.Valid;

public interface UserService {

	//ResponseEntity<ResponseStructure<UserResponse>> saveUser(UserRequest user);

	ResponseEntity<ResponseStructure<UserResponse>> findUserById(int userId);

	ResponseEntity<ResponseStructure<UserResponse>> deleteUser(int userId);

	ResponseEntity<ResponseStructure<UserResponse>> assignTeacherAndStudent(int programId, int userId);

	ResponseEntity<ResponseStructure<UserResponse>> addSubjectToTeacher(int subjectId,int userId);

	ResponseEntity<ResponseStructure<UserResponse>> registerAdmin( UserRequest userRequest);

	ResponseEntity<ResponseStructure<UserResponse>> addOtherUser( UserRequest user);


}
