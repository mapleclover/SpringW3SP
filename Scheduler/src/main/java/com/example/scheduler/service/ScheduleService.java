package com.example.scheduler.service;

import com.example.scheduler.dto.ScheduleRequestDto;
import com.example.scheduler.dto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {
    ScheduleResponseDto saveSchedule(ScheduleRequestDto schedule);
    List<ScheduleResponseDto> getAllSchedules();
    List<ScheduleResponseDto> getAllSchedulesByFilters(String updateDate, String creator);
    ScheduleResponseDto getScheduleById(Long id);
    ScheduleResponseDto updateSchedule(Long id, String job, String creator, String password);
    void deleteSchedule(Long id, String password);
}
