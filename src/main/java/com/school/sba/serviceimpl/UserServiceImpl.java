package com.school.sba.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.sba.entity.User;
import com.school.sba.enums.UserRole;
import com.school.sba.exception.UserNotFoundByIdException;
import com.school.sba.repository.UserRepository;
import com.school.sba.requestdto.SchoolRequest;
import com.school.sba.requestdto.UserRequest;
import com.school.sba.responsedto.UserResponse;
import com.school.sba.service.UserService;
import com.school.sba.utility.ResponseStructure;
@Service
public class UserServiceImpl implements UserService {

	@Autowired 
	private UserRepository userRepo;

	@Autowired 
	private ResponseStructure<UserResponse> sturcture;

	private User mapToUser(UserRequest request) 
	{
		return User.builder()
				.userName(request.getUserName())
				.passward(request.getPassward())
				.firstName(request.getFirstName())
				.lastName(request.getLastName())
				.email(request.getEmail())
				.contactNo(request.getContactNo())
				.userRole(request.getUserRole())
				.build();
	}


	private UserResponse mapToUserResponse(User user) 
	{

		return UserResponse.builder()
				.userName(user.getUserName())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.email(user.getEmail())
				.contactNo(user.getContactNo())
				.userRole(user.getUserRole()) 
				.build();
	}
	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> saveUser(UserRequest userRequest) 
	{
		User user2 = mapToUser(userRequest);
		if(user2.getUserRole().equals(UserRole.ADMIN)) 
		{ 
			boolean role = userRepo.existsByUserRole(userRequest.getUserRole());
			System.out.println("validating if admin present or not");

			if(role==false)
			{
				System.out.println("admin not present");
				User user = userRepo.save(mapToUser(userRequest));
				sturcture.setStatus(HttpStatus.CREATED.value());
				sturcture.setMessage("User Saved Successfully"); 
				sturcture.setData(mapToUserResponse(user));

				return  new ResponseEntity<ResponseStructure<UserResponse>>(sturcture,HttpStatus.CREATED);

			} else
			{
				System.out.println("exist");
				throw new UserNotFoundByIdException("user not found");
			}		    
		}
		else {
			User user3 = userRepo.save(mapToUser(userRequest));
			sturcture.setStatus(HttpStatus.CREATED.value());
			sturcture.setMessage("User Saved Successfully"); 
			sturcture.setData(mapToUserResponse(user3));

			return  new ResponseEntity<ResponseStructure<UserResponse>>(sturcture,HttpStatus.CREATED);
		}

	}


	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> findUserById(int userId) {
		   User user = userRepo.findById(userId)
				   .orElseThrow(()->new UserNotFoundByIdException("user not found"));
		   
		   sturcture.setStatus(HttpStatus.FOUND.value());
		   sturcture.setMessage("user found successfully");
		   sturcture.setData(mapToUserResponse(user));
		  
		return new ResponseEntity<ResponseStructure<UserResponse>>(sturcture,HttpStatus.FOUND);
	}


	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> deleteUser(int userId) 
	{
		User user = userRepo.findById(userId).orElseThrow(()-> new UserNotFoundByIdException("user not found "));
		user.setDelete(true);
		userRepo.save(user);
		
		  sturcture.setStatus(HttpStatus.OK.value());
		   sturcture.setMessage("user deleted successfully");
		   sturcture.setData(mapToUserResponse(user));
		
		return new ResponseEntity<ResponseStructure<UserResponse>>(sturcture,HttpStatus.OK);
	}




//	@Override
//	public ResponseEntity<ResponseStructure<UserResponse>> createSchool(int userId, SchoolRequest schoolRequest) 
//	{
//		User user = userRepo.findById(userId).orElseThrow(()->new UserNotFoundByIdException("user not found "));
//		if (user.getUserRole().equals(UserRole.ADMIN))
//		{ 
//			
//		}
//		return null;
//	}
	
}
