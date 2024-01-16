package com.school.sba.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.sba.entity.School;
import com.school.sba.exception.SchoolNotFoundByIdException;
import com.school.sba.repository.SchoolRepository;
import com.school.sba.service.SchoolService;
import com.school.sba.utility.ResponseStructure;
@Service
public class SchoolServiceImplimentation implements SchoolService
{

	@Autowired 
	private SchoolRepository repo;

	@Override
	public ResponseEntity<ResponseStructure<School>> saveSchool(School school) 
	{
		School school2 = repo.save(school);

		ResponseStructure<School> resp = new ResponseStructure<School>();
		resp.setStatus(HttpStatus.CREATED.value());
		resp.setMessage("Data inserted successfully");
		resp.setData(school2);

		return new ResponseEntity<ResponseStructure<School>>(resp,HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<ResponseStructure<School>> findSchoolById(int schoolId) {
		Optional<School> id = repo.findById(schoolId);
		if(id.isPresent())
		{
			School school = id.get();
			ResponseStructure<School> resp = new ResponseStructure<School>();
			resp.setStatus(HttpStatus.FOUND.value());
			resp.setMessage("School data recived successfully");
			resp.setData(school);
			return new ResponseEntity<ResponseStructure<School>>(resp,HttpStatus.FOUND);

		}
		else

			throw new SchoolNotFoundByIdException("School not found");
	}
	@Override
	public ResponseEntity<ResponseStructure<School>> updateSchool(int schoolId, School school) {
		Optional<School> id = repo.findById(schoolId);

		if (id.isPresent())
		{
			School s = id.get();
			school.setSchoolId(s.getSchoolId());
			School save = repo.save(school);


			ResponseStructure<School> resp = new ResponseStructure<School>();
			resp.setStatus(HttpStatus.OK.value());
			resp.setMessage("School Object updated successfully");
			resp.setData(save); 
			return new ResponseEntity<ResponseStructure<School>>(resp,HttpStatus.OK);

		}
		else

			throw new SchoolNotFoundByIdException("School not found");
	}

	@Override
	public ResponseEntity<ResponseStructure<List<School>>> findAllSchools() 
	{
		List<School> list = repo.findAll();

		ResponseStructure<List<School>> resp = new ResponseStructure<List<School>>();
		resp.setStatus(HttpStatus.FOUND.value());
		resp.setMessage("List of School object recived successfully");
		resp.setData(list);

		return new ResponseEntity<ResponseStructure<List<School>>>(resp,HttpStatus.FOUND);
	}

	@Override
	public ResponseEntity<ResponseStructure<School>> deleteSchool(int schoolId) {
		Optional<School> id = repo.findById(schoolId);
		if(id.isPresent())
		{
			School school = id.get();
			repo.delete(school);

			ResponseStructure<School> resp = new ResponseStructure<School>();
			resp.setStatus(HttpStatus.OK.value());
			resp.setMessage("School data deleted successfully");
			resp.setData(school);

			return new ResponseEntity<ResponseStructure<School>>(resp,HttpStatus.OK);
		}

		else 
			throw new SchoolNotFoundByIdException("School not found");
	}

}
