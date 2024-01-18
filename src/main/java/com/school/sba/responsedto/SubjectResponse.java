package com.school.sba.responsedto;

import java.util.List;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubjectResponse 
{
	@Id
	   private int subjectId;
	   private List<String> subjectNames;
}
