package com.school.sba.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.sba.entity.AcademicProgram;
import com.school.sba.exception.SchoolNotFoundByIdException;
import com.school.sba.repository.AcademicProgramRepository;
import com.school.sba.repository.SchoolRepository;
import com.school.sba.requestdto.AcademicProgramRequest;
import com.school.sba.responsedto.AcademicProgramResponse;
import com.school.sba.service.AcademicProgramService;
import com.school.sba.utility.ResponseStructure;

@Service
public class AcademicProgramServiceImpl implements AcademicProgramService
{

	@Autowired
	private AcademicProgramRepository academicProgramRepository;

	@Autowired
	private SchoolRepository schoolRepository;

	@Autowired
	ResponseStructure<AcademicProgramResponse> structure;
 


	@Override
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> addAcademicProgram(int schoolId,
			AcademicProgramRequest academicProgramRequest) 
	{
        
		  return schoolRepository.findById(schoolId)
		.map(school -> {
			AcademicProgram academicProgram = academicProgramRepository.save(mapToAcademicProgram(academicProgramRequest));
			 school.getAcademicProgram().add(academicProgram); 
			 school  = schoolRepository.save(school);
			 academicProgram.setSchool(school);
           	 academicProgram= academicProgramRepository.save(academicProgram); 
			 structure.setStatus(HttpStatus.CREATED.value());
			 structure.setMessage("AcademicProgram added successfully");
			 structure.setData(mapToAcademicResponse( academicProgram));
			 
			 return new ResponseEntity<ResponseStructure<AcademicProgramResponse>>(structure,HttpStatus.CREATED);

               })
		.orElseThrow(()-> new SchoolNotFoundByIdException("school not found"));
		 
	}

	private AcademicProgramResponse mapToAcademicResponse(AcademicProgram academicProgram) {
		
		return AcademicProgramResponse.builder()
				.programId(academicProgram.getProgramId())
				.programName(academicProgram.getProgramName())
				.programType(academicProgram.getProgramType())
				.beginsAt(academicProgram.getBeginsAt())
				.endsAt(academicProgram.getEndsAt())
				.build();
	}
	
	private AcademicProgram mapToAcademicProgram(AcademicProgramRequest academicProgramRequest) {

		return AcademicProgram.builder()
				.programName(academicProgramRequest.getProgramName())
				.beginsAt(academicProgramRequest.getBeginsAt())
				.endsAt(academicProgramRequest.getEndsAt())
				.programType(academicProgramRequest.getProgramType())
				.build();
	}

}
