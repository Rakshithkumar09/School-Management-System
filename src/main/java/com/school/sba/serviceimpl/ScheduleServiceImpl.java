package com.school.sba.serviceimpl;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.sba.entity.Schedule;
import com.school.sba.entity.School;
import com.school.sba.exception.SchoolNotFoundByIdException;
import com.school.sba.exception.UserNotFoundByIdException;
import com.school.sba.repository.ScheduleRepository;
import com.school.sba.repository.SchoolRepository;
import com.school.sba.requestdto.ScheduleRequest;
import com.school.sba.responsedto.ScheduleResponse;
import com.school.sba.service.ScheduleService;
import com.school.sba.utility.ResponseStructure;
@Service
public class ScheduleServiceImpl implements ScheduleService 
{
	@Autowired
	private ScheduleRepository scheduleRepo;

	@Autowired
	private SchoolRepository schoolRepo;
	@Autowired 
	private ResponseStructure<ScheduleResponse>  structure;

		 
	@Override
	public ResponseEntity<ResponseStructure<ScheduleResponse>> saveSchedule(int schoolId, ScheduleRequest scheduleRequest) 
	{
		return schoolRepo.findById(schoolId)
				.map(s ->{ 
					if(s.getSchedule()==null)
					{
						Schedule schedule	 = mapToSchedule(scheduleRequest);
						schedule = scheduleRepo.save(schedule);
						s.setSchedule(schedule);
						schoolRepo.save(s);

						structure.setStatus(HttpStatus.CREATED.value());
						structure.setMessage("schedule saved successfully");
						structure.setData(mapToScheduleResponse(schedule));

						return new  ResponseEntity<ResponseStructure<ScheduleResponse>>(structure,HttpStatus.CREATED);

					} 
					else {
						throw new RuntimeException();
					}
				}).orElseThrow(()-> new UserNotFoundByIdException("user not found"));

	}  

	private ScheduleResponse mapToScheduleResponse(Schedule schedule) {

		return ScheduleResponse.builder()
				.scheduleOpensAt(schedule.getScheduleOpensAt())
				.scheduleCloseAt(schedule.getScheduleCloseAt())
				.scheduleClassHourPerDay(schedule.getScheduleClassHourPerDay())
				.scheduleClassHourLenghtInMinute((int)(Duration.ofMinutes(schedule.getScheduleClassHourLenghtInMinute().toMinutes()).toMinutes()))
				.scheduleBreakTime(schedule.getScheduleBreakTime())
				.scheduleBreakLenghtInMinute((int)Duration.ofMinutes(schedule.getScheduleBreakLenghtInMinute().toMinutes()).toMinutes())
				.scheduleLunchTime(schedule.getScheduleLunchTime())
				.scheduleLunchLenghtInMinute((int)Duration.ofMinutes(schedule.getScheduleLunchLenghtInMinute().toMinutes()).toMinutes())
				.build();
	}  

	private Schedule mapToSchedule(ScheduleRequest scheduleRequest) {

		return Schedule.builder() 
				.scheduleOpensAt(scheduleRequest.getScheduleOpensAt())
				.scheduleCloseAt(scheduleRequest.getScheduleCloseAt())
				.scheduleClassHourPerDay(scheduleRequest.getScheduleClassHourPerDay())
				.scheduleClassHourLenghtInMinute(Duration.ofMinutes( scheduleRequest.getScheduleClassHourLenghtInMinute()))
		 		.scheduleBreakTime(scheduleRequest.getScheduleBreakTime())
				.scheduleBreakLenghtInMinute(Duration.ofMinutes(scheduleRequest.getScheduleBreakLenghtInMinute()))
				.scheduleLunchTime(scheduleRequest.getScheduleLunchTime())
				.scheduleLunchLenghtInMinute(Duration.ofMinutes(scheduleRequest.getScheduleLunchLenghtInMinute()))
				.build();
	}

	@Override
	public ResponseEntity<ResponseStructure<ScheduleResponse>> findSchedule(int schoolId) 
	{
		return scheduleRepo.findById(schoolId)
				.map(s ->{ 
					
					structure.setStatus(HttpStatus.CREATED.value());
					structure.setMessage("schedule saved successfully");
					structure.setData(mapToScheduleResponse(s));
					
					return new ResponseEntity<ResponseStructure<ScheduleResponse>>(structure,HttpStatus.FOUND);

				})
				.orElseThrow(()-> new SchoolNotFoundByIdException("School not found"));
		
		
	} 


}
