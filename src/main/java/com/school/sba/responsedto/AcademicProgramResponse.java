package com.school.sba.responsedto;

import java.time.LocalTime;

import com.school.sba.enums.ProgramType;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Builder
public class AcademicProgramResponse 
{
	private int programId;
	private String programName;
	private LocalTime beginsAt;
	private LocalTime endsAt;
	
	private ProgramType programType;
}
