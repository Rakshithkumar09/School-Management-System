package com.school.sba.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.sba.entity.AcademicProgram;
import com.school.sba.entity.User;
import com.school.sba.enums.UserRole;
import com.school.sba.exception.AdminCannotBeAssignedToAcademicProgramException;
import com.school.sba.exception.AdminNotFoundException;
import com.school.sba.exception.ProgramNotFoundByIdException;
import com.school.sba.exception.SubjectNotFoundByIdException;
import com.school.sba.exception.TeacherNotFoundException;
import com.school.sba.exception.UserNotFoundByIdException;
import com.school.sba.repository.AcademicProgramRepository;
import com.school.sba.repository.SubjectRepository;
import com.school.sba.repository.UserRepository;
import com.school.sba.requestdto.UserRequest;
import com.school.sba.responsedto.UserResponse;
import com.school.sba.service.UserService;
import com.school.sba.utility.ResponseStructure;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private SubjectRepository repo;

	@Autowired
	private ResponseStructure<UserResponse> sturcture;

	@Autowired
	private AcademicProgramRepository academicProgramRepository;

	private User mapToUser(UserRequest request) {
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

	private UserResponse mapToUserResponse(User user) {
		List<String> list = new ArrayList<String>();
		List<AcademicProgram> listOfProgram = user.getAcademicProgram();

		if (listOfProgram.isEmpty()) 
		{

		} else 
		{
			listOfProgram.forEach(program -> {
				list.add(program.getProgramName());

			});
		} 

		list.forEach(s -> 
		{
			System.out.println(s);
		}); 

		return UserResponse.builder()
				.userName(user.getUserName())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.email(user.getEmail())
				.contactNo(user.getContactNo())
				.userRole(user.getUserRole())
				.academicProgram(list)
				.build();
	} 

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> saveUser(UserRequest userRequest) {
		User user2 = mapToUser(userRequest);
		if (user2.getUserRole().equals(UserRole.ADMIN)) 
		{
			boolean role = userRepo.existsByUserRole(userRequest.getUserRole());
 

			if (role == false)  
			{ 

				User user = userRepo.save(mapToUser(userRequest));
				sturcture.setStatus(HttpStatus.CREATED.value());
				sturcture.setMessage("User Saved Successfully");
				sturcture.setData(mapToUserResponse(user));

				return new ResponseEntity<ResponseStructure<UserResponse>>(sturcture, HttpStatus.CREATED);

			} 
			else 
			{ 

				throw new AdminNotFoundException("Admin Not Found");
			}
		} 
		else 
		{

			User user3 = userRepo.save(mapToUser(userRequest));
			sturcture.setStatus(HttpStatus.CREATED.value());
			sturcture.setMessage("User Saved Successfully");
			sturcture.setData(mapToUserResponse(user3));

			return new ResponseEntity<ResponseStructure<UserResponse>>(sturcture, HttpStatus.CREATED);

		}

	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> findUserById(int userId) 
	{
		User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundByIdException("user not found"));

		sturcture.setStatus(HttpStatus.FOUND.value());
		sturcture.setMessage("user found successfully");
		sturcture.setData(mapToUserResponse(user));

		return new ResponseEntity<ResponseStructure<UserResponse>>(sturcture, HttpStatus.FOUND);
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> deleteUser(int userId) 
	{
		User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundByIdException("user not found "));
		user.setDelete(true);
		userRepo.save(user);

		sturcture.setStatus(HttpStatus.OK.value());
		sturcture.setMessage("user deleted successfully");
		sturcture.setData(mapToUserResponse(user));

		return new ResponseEntity<ResponseStructure<UserResponse>>(sturcture, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> assignTeacherAndStudent(int programId, int userId) {

		return userRepo.findById(userId).map(user -> 
		{
			if (user.getUserRole().equals(UserRole.ADMIN)) 
			{
				throw new AdminCannotBeAssignedToAcademicProgramException("Admin can not assign to academic programs");
			} else 
			{   
				return academicProgramRepository.findById(programId).map(program -> 
				{
					program.getUser().add(user);
					user.getAcademicProgram().add(program);

					userRepo.save(user);
					academicProgramRepository.save(program);

					sturcture.setStatus(HttpStatus.OK.value());
					sturcture.setMessage("Assign Teacher and Student successfully");
					sturcture.setData(mapToUserResponse(user));

					return new ResponseEntity<ResponseStructure<UserResponse>>(sturcture, HttpStatus.OK);

				})
						.orElseThrow(() -> new ProgramNotFoundByIdException ("Program not found"));

			} 

		}).orElseThrow(() -> new UserNotFoundByIdException("user not found"));

	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> addSubjectToTeacher(int subjectId, int userId) 
	{
		return  userRepo.findById(userId)
				.map(user ->{
					if(user.getUserRole().equals(UserRole.TEACHER))
					{
						return	repo.findById(subjectId)
								.map(subject->
								{
									user.setSubject(subject);
									userRepo.save(user);

									sturcture.setStatus(HttpStatus.OK.value());
									sturcture.setMessage("subject assigned to teacher successfully");
									sturcture.setData(mapToUserResponse(user));

									return new ResponseEntity<ResponseStructure<UserResponse>>(sturcture,HttpStatus.OK);

								}).orElseThrow(()-> new SubjectNotFoundByIdException("Subject not found"));

					}
					else
						throw new TeacherNotFoundException("Teacher not found "); 
				})
				.orElseThrow(()-> new UserNotFoundByIdException("user not found"));

	}

	
}
