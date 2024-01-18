package com.school.sba.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.sba.entity.Subject;
import com.school.sba.repository.AcademicProgramRepository;
import com.school.sba.repository.SubjectRepository;
import com.school.sba.requestdto.SubjectRequest;
import com.school.sba.responsedto.AcademicProgramResponse;
import com.school.sba.service.SubjectService;
import com.school.sba.utility.ResponseStructure;
@Service
public class SubjectServiceImpl  implements SubjectService
{
	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private AcademicProgramServiceImpl academicProgramServiceImpl;

	@Autowired
	private AcademicProgramRepository academicProgramRepository;
 
	@Autowired
	private ResponseStructure<AcademicProgramResponse> structure;

  
	@Override
	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> addSubject(int programId,
			SubjectRequest subjectRequest) 
	{

		return	academicProgramRepository.findById(programId)
				.map(program ->
				{ //found academic program
					List<Subject> subjects = new ArrayList<Subject>();
					subjectRequest.getSubjectNames().forEach(name ->

					{ // iterating over each subject name

						/*
						 *  finding subject based on the name in the current iteration
							subjects.add(subject);  add the existing subject to the subjecct list
							return subject; 
						 */
						Subject subject3 = subjectRepository.findBySubjectName(name).map(subject -> subject).orElseGet(()->
						{ 
							//in not found ,create new subject
 
							Subject subject2 = new Subject();
							subject2.setSubjectName(name); 
							subjectRepository.save(subject2);
							
							return subject2;
 
						}); 
						subjects.add(subject3); 
					});
					program.setSubjects(subjects); // set subjects list to academic program 
					academicProgramRepository.save(program);//saving updated program to data base

					structure.setStatus(HttpStatus.CREATED.value());
					structure.setMessage("updated the subject list to Academic program");
					structure.setData(academicProgramServiceImpl.mapToAcademicResponse(program));
					return new ResponseEntity<ResponseStructure<AcademicProgramResponse>>(structure,HttpStatus.CREATED);
				})

				.orElseThrow(); 


	}

}
