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
import com.school.sba.service.SchoolService;
import com.school.sba.utility.ResponseStructure;
@RestController
@RequestMapping("/schools")
public class SchoolController 
{
   
	@Autowired
	private SchoolService schoolService;  
	
	@PostMapping
   public ResponseEntity<ResponseStructure<School>> saveSchool(@RequestBody School school)
   {
	    return schoolService.saveSchool(school);
   }
	@GetMapping("/{schoolId}") 
	public ResponseEntity<ResponseStructure<School>> findSchool(@PathVariable int schoolId)
	{ 
		return schoolService.findSchoolById(schoolId);  
		
	}
	@PutMapping
	public ResponseEntity<ResponseStructure<School>> updateSchool(@RequestParam int  schoolId , @RequestBody School school)
	{
		return schoolService.updateSchool(schoolId,school);
		 
	}
	@GetMapping("/finds")
	public ResponseEntity<ResponseStructure<List< School>>> findAll()
	{
		return schoolService.findAllSchools();
		
	} 
	@DeleteMapping
	public  ResponseEntity<ResponseStructure<School>> deleteSchool(@RequestParam int schoolId)
	{
		return schoolService.deleteSchool(schoolId);
	}
}
