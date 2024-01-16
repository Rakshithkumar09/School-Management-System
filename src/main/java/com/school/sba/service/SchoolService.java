package com.school.sba.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.school.sba.entity.School;
import com.school.sba.utility.ResponseStructure;

public interface SchoolService 
{

public	ResponseEntity<ResponseStructure<School>> saveSchool(School school);

public ResponseEntity<ResponseStructure<School>> findSchoolById(int schoolId);

public ResponseEntity<ResponseStructure<School>> updateSchool(int schoolId, School school);

public ResponseEntity<ResponseStructure<List<School>>> findAllSchools();

public ResponseEntity<ResponseStructure<School>> deleteSchool(int schoolId); 

}
