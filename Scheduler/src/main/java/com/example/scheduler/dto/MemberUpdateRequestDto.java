package com.example.scheduler.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MemberUpdateRequestDto {

    @NotBlank(message = "name은 필수값입니다.")
    private String name;
    @NotBlank(message = "password은 필수값입니다.")
    private String password;
}