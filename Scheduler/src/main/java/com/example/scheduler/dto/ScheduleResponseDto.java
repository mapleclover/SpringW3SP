package com.example.scheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {
    private Long id;
    private String job;
    private String creator;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
