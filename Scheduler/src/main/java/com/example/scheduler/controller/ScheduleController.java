package com.example.scheduler.controller;

import com.example.scheduler.dto.ScheduleSaveRequestDto;
import com.example.scheduler.dto.ScheduleUpdateRequestDto;
import com.example.scheduler.dto.ScheduleResponseDto;
import com.example.scheduler.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/schedules")
    public ResponseEntity<ScheduleResponseDto> save(
            @Valid @RequestBody ScheduleSaveRequestDto requestDto
    ) {
        return ResponseEntity.ok(scheduleService.save(requestDto));
    }

    @GetMapping("/schedules")
    public ResponseEntity<Page<ScheduleResponseDto>> findAll(
            @RequestParam(required = false) String updatedDate,
            @RequestParam(required = false) String memberName,
            @RequestParam(required = false) Long memberId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<ScheduleResponseDto> result = scheduleService.findAll(updatedDate, memberName, memberId, page - 1, size);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/schedules/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable Long id) {
        return ResponseEntity.ok(scheduleService.findScheduleById(id));
    }

    @PutMapping("/schedules/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long id,
            @Valid @RequestBody ScheduleUpdateRequestDto request
    ) {
        return ResponseEntity.ok(scheduleService.updateSchedule(id, request));
    }

    @DeleteMapping("/schedules/{id}")
    public void deleteSchedule(@PathVariable Long id, @RequestParam String memberName, @RequestParam String password) {
        scheduleService.deleteSchedule(id, memberName, password);
    }
}
//package com.example.scheduler.controller;
//
//import com.example.scheduler.dto.ScheduleRequestDto;
//import com.example.scheduler.dto.ScheduleResponseDto;
//import com.example.scheduler.service.ScheduleService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//
//@RestController
//@RequestMapping("/schedules")
//public class ScheduleController {
//
//    private final ScheduleService scheduleService;
//
//    public ScheduleController(ScheduleService scheduleService) {
//        this.scheduleService = scheduleService;
//    }
//
//    @PostMapping
//    public ResponseEntity<ScheduleResponseDto> saveSchedule(@RequestBody ScheduleRequestDto dto) {
//        return new ResponseEntity<>(scheduleService.saveSchedule(dto), HttpStatus.CREATED);
//    }
//
//    @GetMapping
//    public ResponseEntity<List<ScheduleResponseDto>> getAllSchedules() {
//        return new ResponseEntity<>(scheduleService.getAllSchedules(), HttpStatus.OK);
//    }
//
//    @GetMapping
//    public ResponseEntity<List<ScheduleResponseDto>> getSchedule(
//            @RequestParam(required = false) String updateDate,
//            @RequestParam(required = false) String creator) {
//        return new ResponseEntity<>(scheduleService.getAllSchedulesByFilters(updateDate, creator), HttpStatus.OK);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<ScheduleResponseDto> getScheduleById(@PathVariable Long id) {
//        return new ResponseEntity<>(scheduleService.getScheduleById(id), HttpStatus.OK);
//    }
//
//    @PatchMapping("/{id}")
//    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto dto) {
//        return new ResponseEntity<>(scheduleService.updateSchedule(id, dto.getJob(), dto.getCreator(), dto.getPassword()),HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto dto) {
//        scheduleService.deleteSchedule(id, dto.getPassword());
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//}
