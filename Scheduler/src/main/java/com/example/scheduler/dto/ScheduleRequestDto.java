package com.example.scheduler.dto;

import lombok.Getter;

@Getter
public class ScheduleRequestDto {
    private String job;
    private String creator;
    private String password;
}
