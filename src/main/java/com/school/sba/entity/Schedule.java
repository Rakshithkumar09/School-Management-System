package com.school.sba.entity;

import java.time.LocalTime;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Component
@Entity
@Getter
@Setter
public class Schedule 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int scheduleId;
	private LocalTime scheduleOpensAt;
	private LocalTime scheduleCloseAt;
	private int scheduleClassHourPerDay;
	private LocalTime scheduleClassHourLenght;
	private LocalTime scheduleBreakTime;
	private LocalTime scheduleBreakLenght;
	private LocalTime scheduleLunchTime;
	private LocalTime scheduleLunchLenght;

	
}
