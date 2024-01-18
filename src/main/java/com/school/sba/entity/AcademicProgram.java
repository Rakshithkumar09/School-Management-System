package com.school.sba.entity;

import java.time.LocalTime;

import com.school.sba.enums.ProgramType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter 
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AcademicProgram 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int programId;
	private String programName;
	private LocalTime beginsAt;
	private LocalTime endsAt;
	
	private ProgramType programType;
	
	@ManyToOne
	private School school;
}
