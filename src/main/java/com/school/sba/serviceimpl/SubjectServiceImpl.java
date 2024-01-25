package com.school.sba.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.sba.entity.Subject;
import com.school.sba.exception.ProgramNotFoundByIdException;
import com.school.sba.repository.AcademicProgramRepository;
import com.school.sba.repository.SubjectRepository;
import com.school.sba.requestdto.SubjectRequest;
import com.school.sba.responsedto.AcademicProgramResponse;
import com.school.sba.responsedto.SubjectResponse;
import com.school.sba.service.SubjectService;
import com.school.sba.utility.ResponseStructure;

@Service
public class SubjectServiceImpl implements SubjectService {
	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private AcademicProgramServiceImpl academicProgramServiceImpl;

	@Autowired
	private AcademicProgramRepository academicProgramRepository;

	@Autowired
	private ResponseStructure<AcademicProgramResponse> structure;

	@Autowired
	private ResponseStructure<List<SubjectResponse>> resp;

	private List<SubjectResponse> mapToListOfSubjectResponse(List<Subject> listOfSubject) 
	{
		List<SubjectResponse> listOfSubjectResponse = new ArrayList<>();

		listOfSubject.forEach(subject -> 
		{
			SubjectResponse sr = new SubjectResponse();
			sr.setSubjectId(subject.getSubjectId());
			sr.setSubjectNames(subject.getSubjectName());
			listOfSubjectResponse.add(sr);

		}); 

		return listOfSubjectResponse;

	}

	public ResponseEntity<ResponseStructure<AcademicProgramResponse>> addSubject(int programId,
			SubjectRequest subjectRequest) {
		return academicProgramRepository.findById(programId).map(program -> {
			List<Subject> subjects = (program.getSubjects() != null) ? program.getSubjects() : new ArrayList<Subject>();
			// to add new Add subjects that are specified by client
			subjectRequest.getSubjectNames().forEach(name -> {
				boolean ispresent = false;
				for (Subject subject : subjects) {
					ispresent = (name.equalsIgnoreCase(subject.getSubjectName())) ? true : false;
					if (ispresent)
						break;
				}
				if (!ispresent)
					subjects.add(subjectRepository.findBySubjectName(name)
							.orElseGet(() -> subjectRepository.save(Subject.builder().subjectName(name).build())));
			});

			// to remove subjects that are not specified by the client
			List<Subject> toBeRemoved = new ArrayList<Subject>();
			subjects.forEach(subject -> {
				boolean isPresent = false;
				for (String name : subjectRequest.getSubjectNames()) {
					isPresent = (subject.getSubjectName().equalsIgnoreCase(name)) ? true : false;
					if (isPresent)
						break;
				}
				if (!isPresent)
					toBeRemoved.add(subject);
			});
			subjects.removeAll(toBeRemoved);

			program.setSubjects(subjects);
			academicProgramRepository.save(program);
 
			structure.setStatus(HttpStatus.CREATED.value());
			structure.setMessage("updated the subject list to Academic program");
			structure.setData(academicProgramServiceImpl.mapToAcademicResponse(program));

			return new ResponseEntity<ResponseStructure<AcademicProgramResponse>>(structure, HttpStatus.CREATED);
		}).orElseThrow(() -> new ProgramNotFoundByIdException("program not found"));

	}

	@Override
	public ResponseEntity<ResponseStructure<List<SubjectResponse>>> findAll() {

		List<Subject> listOfSubject = subjectRepository.findAll();
		if (listOfSubject.isEmpty()) {
			resp.setStatus(HttpStatus.NOT_FOUND.value());
			resp.setMessage("no subjects found");
			resp.setData(mapToListOfSubjectResponse(listOfSubject));
			return new ResponseEntity<ResponseStructure<List<SubjectResponse>>>(resp, HttpStatus.NOT_FOUND);
		} else {

			resp.setStatus(HttpStatus.FOUND.value());
			resp.setMessage("all subjects found");
			resp.setData(mapToListOfSubjectResponse(listOfSubject));

			return new ResponseEntity<ResponseStructure<List<SubjectResponse>>>(resp, HttpStatus.FOUND);

		}
	}

}
