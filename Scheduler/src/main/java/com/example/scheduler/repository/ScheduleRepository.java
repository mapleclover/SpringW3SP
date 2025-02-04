package com.example.scheduler.repository;

import com.example.scheduler.dto.ScheduleResponseDto;
import com.example.scheduler.entity.Schedule;

import java.util.List;

public interface ScheduleRepository {
    ScheduleResponseDto saveSchedule(Schedule schedule);
    List<ScheduleResponseDto> getAllSchedules();
    List<ScheduleResponseDto> getAllSchedulesByDate(String updateDate);
    List<ScheduleResponseDto> getAllSchedulesByCreator(String creator);
    List<ScheduleResponseDto> getAllSchedulesByFilters(String updateDate, String creator);
    ScheduleResponseDto getScheduleByIdOrElseThrow(Long id);
    int updateSchedule(Long id, String job, String creator);
    Boolean CheckPasswordValidity(Long id, String password);
    int deleteSchedule(Long id);
}
