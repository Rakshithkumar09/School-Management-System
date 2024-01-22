package com.school.sba.serviceimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.sba.entity.School;
import com.school.sba.enums.UserRole;
import com.school.sba.exception.IllegalRequestException;
import com.school.sba.exception.SchoolNotFoundByIdException;
import com.school.sba.exception.UserNotFoundByIdException;
import com.school.sba.repository.SchoolRepository;
import com.school.sba.repository.UserRepository;
import com.school.sba.requestdto.SchoolRequest;
import com.school.sba.responsedto.SchoolResponse;
import com.school.sba.service.SchoolService;
import com.school.sba.utility.ResponseStructure;
@Service
public class SchoolServiceImplimentation implements SchoolService
{

	@Autowired 
	private SchoolRepository repo;
	@Autowired
	private UserRepository userRepo;

	@Autowired
	ResponseStructure<SchoolResponse> resp;

	private School mapToSchool(SchoolRequest schoolRequest)
	{
		return School.builder()
				.schoolName(schoolRequest.getSchoolName())
				.contactNumber(schoolRequest.getContactNumber())
				.emailId(schoolRequest.getEmailId())
				.address(schoolRequest.getAddress())
				.build(); 
	}

	private SchoolResponse mapToSchoolResponse (School school)

	{
		return SchoolResponse.builder()
				.schoolId(school.getSchoolId())
				.schoolName(school.getSchoolName())
				.contactNumber(school.getContactNumber())
				.emailId(school.getEmailId())
				.address(school.getAddress())
				.build();
	} 

	@Override
	public ResponseEntity<ResponseStructure<SchoolResponse>> saveSchool(int userId,SchoolRequest schoolRequest) 
	{ 
		return userRepo.findById(userId).map(u ->
		{
			if(u.getUserRole().equals(UserRole.ADMIN))
			{ 
				if(u.getSchool()==null)
				{
					School school = mapToSchool(schoolRequest);
					school = repo.save(school);
					u.setSchool(school);
					userRepo.save(u);

					resp.setStatus(HttpStatus.CREATED.value());
					resp.setMessage("school saved successfully");
					resp.setData(mapToSchoolResponse(school));

					return new ResponseEntity<ResponseStructure<SchoolResponse>>(resp,HttpStatus.CREATED);
				}else 
					throw new IllegalRequestException("illegal school request"); 
			}else 
				throw new IllegalRequestException("illegal user request");  

		}).orElseThrow(()-> new UserNotFoundByIdException("user not found"));

	} 

	@Override
	public ResponseEntity<ResponseStructure<SchoolResponse>> findSchoolById(int schoolId) 
	{
		School school = repo.findById(schoolId).orElseThrow(()-> new SchoolNotFoundByIdException("school not found"));

		resp.setStatus(HttpStatus.FOUND.value());
		resp.setMessage("Data inserted successfully");
		resp.setData(mapToSchoolResponse(school));

		return new ResponseEntity<ResponseStructure<SchoolResponse>>(resp,HttpStatus.FOUND) ;
	}

	
	}

	
