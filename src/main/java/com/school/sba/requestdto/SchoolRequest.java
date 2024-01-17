package com.school.sba.requestdto;

import com.school.sba.responsedto.SchoolResponse;

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
public class SchoolRequest 
{
	private String schoolName;
	private long contactNumber;
	private String emailId;
	private String address; 
}
