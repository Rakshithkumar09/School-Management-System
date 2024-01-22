package com.school.sba.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.school.sba.entity.School;
import com.school.sba.requestdto.SchoolRequest;
import com.school.sba.responsedto.SchoolResponse;
import com.school.sba.service.SchoolService;
import com.school.sba.utility.ResponseStructure;
@RestController

public class SchoolController 
{ 
   
	@Autowired 
	private SchoolService schoolService;  
	
	@PostMapping("/users/{userId}/schools") 
   public ResponseEntity<ResponseStructure<SchoolResponse>>saveSchool(@PathVariable int userId, @RequestBody SchoolRequest schoolRequest)
   { 
	    return schoolService.saveSchool(userId,schoolRequest);
   }
	
	@GetMapping("schools/{schoolId}") 
	public ResponseEntity<ResponseStructure<SchoolResponse>> findSchool(@PathVariable int schoolId)
	{ 
		return schoolService.findSchoolById(schoolId);  
		 
	}
	
}
