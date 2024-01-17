package com.school.sba.entity;

import java.time.Duration;
import java.time.LocalTime;

import org.springframework.stereotype.Component;

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
public class Schedule 
{ 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int scheduleId;
	private LocalTime scheduleOpensAt;
	private LocalTime scheduleCloseAt;
	private int scheduleClassHourPerDay;
	private Duration scheduleClassHourLenghtInMinute;
	private LocalTime scheduleBreakTime;
	private Duration scheduleBreakLenghtInMinute;
	private LocalTime scheduleLunchTime;
	private Duration scheduleLunchLenghtInMinute;
 
	
} 
