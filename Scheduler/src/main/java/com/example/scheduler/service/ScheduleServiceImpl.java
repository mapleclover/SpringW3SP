package com.example.scheduler.service;

import com.example.scheduler.dto.ScheduleRequestDto;
import com.example.scheduler.dto.ScheduleResponseDto;
import com.example.scheduler.entity.Schedule;
import com.example.scheduler.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {
        Schedule schedule = new Schedule(dto.getJob(), dto.getCreator(), dto.getPassword());
        return scheduleRepository.saveSchedule(schedule);
    }

    @Override
    public List<ScheduleResponseDto> getAllSchedules() {
        return scheduleRepository.getAllSchedules();
    }

    @Override
    public List<ScheduleResponseDto> getAllSchedulesByFilters(String updateDate, String creator) {
        if(updateDate == null && creator == null) {
            return getAllSchedules();
        } else if(updateDate == null) {
            return scheduleRepository.getAllSchedulesByDate(creator);
        } else if(creator == null) {
            return scheduleRepository.getAllSchedulesByCreator(updateDate);
        }else{
            return scheduleRepository.getAllSchedulesByFilters(updateDate, creator);
        }
    }

    @Override
    public ScheduleResponseDto getScheduleById(Long id) {
        return scheduleRepository.getScheduleByIdOrElseThrow(id);
    }

    @Override
    public ScheduleResponseDto updateSchedule(Long id, String job, String creator, String password) {
        if(!scheduleRepository.CheckPasswordValidity(id, password)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        int updatedRow = scheduleRepository.updateSchedule(id, job, creator);

        if(updatedRow == 0)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return scheduleRepository.getScheduleByIdOrElseThrow(id);
    }

    @Override
    public void deleteSchedule(Long id, String password) {
        if(!scheduleRepository.CheckPasswordValidity(id, password)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        int deletedRow = scheduleRepository.deleteSchedule(id);

        if(deletedRow == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
