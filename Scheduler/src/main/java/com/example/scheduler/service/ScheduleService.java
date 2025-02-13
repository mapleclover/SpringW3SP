package com.example.scheduler.service;

import com.example.scheduler.dto.ScheduleResponseDto;
import com.example.scheduler.dto.ScheduleSaveRequestDto;
import com.example.scheduler.dto.ScheduleUpdateRequestDto;
import org.springframework.data.domain.Page;


public interface ScheduleService{
    ScheduleResponseDto save(ScheduleSaveRequestDto requestDto);
    ScheduleResponseDto findScheduleById(Long id);
    Page<ScheduleResponseDto> findAll(String updatedDate, String memberName, Long memberId, int page, int size);
    ScheduleResponseDto updateSchedule(Long id, ScheduleUpdateRequestDto requestDto);
    void deleteSchedule(Long id, String memberName, String password);
}