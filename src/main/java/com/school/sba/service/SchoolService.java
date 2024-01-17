package com.school.sba.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.school.sba.entity.School;
import com.school.sba.requestdto.SchoolRequest;
import com.school.sba.responsedto.SchoolResponse;
import com.school.sba.utility.ResponseStructure;

public interface SchoolService 
{

public	ResponseEntity<ResponseStructure<SchoolResponse>> saveSchool(int userId,SchoolRequest schoolRequest);
 
public ResponseEntity<ResponseStructure<SchoolResponse>> findSchoolById(int schoolId);

public ResponseEntity<ResponseStructure<SchoolResponse>> updateSchool(int schoolId, School school);

//public ResponseEntity<ResponseStructure<List<School>>> findAllSchools();

public ResponseEntity<ResponseStructure<SchoolResponse>> deleteSchool(int schoolId); 

}
