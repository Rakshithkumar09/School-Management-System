package com.school.sba.requestdto;

import java.time.LocalTime;

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
public class ScheduleRequest 
{
	private LocalTime scheduleOpensAt;
	private LocalTime scheduleCloseAt;
	private int scheduleClassHourPerDay;
	private int scheduleClassHourLenghtInMinute;
	private LocalTime scheduleBreakTime;
	private int scheduleBreakLenghtInMinute;
	private LocalTime scheduleLunchTime;
	private int scheduleLunchLenghtInMinute;
}
 