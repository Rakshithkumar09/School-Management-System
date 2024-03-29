package com.school.sba.entity;

import org.springframework.stereotype.Component;

import com.school.sba.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
 
@Component
@Entity
@Getter
@Setter 
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	@Column(unique = true)
	private String userName;
	private String passward;
	private String firstName;
	private String lastName;
	@Column(unique = true)
	private String email;
	private long contactNo;
	private UserRole userRole;
	
	private boolean isDelete;
		
	
}
