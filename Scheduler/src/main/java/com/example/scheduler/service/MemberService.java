package com.example.scheduler.service;

import com.example.scheduler.dto.MemberResponseDto;
import com.example.scheduler.dto.MemberSaveRequestDto;
import com.example.scheduler.dto.MemberUpdateRequestDto;

public interface MemberService {
    MemberResponseDto save(MemberSaveRequestDto requestDto);
    MemberResponseDto findById(Long id);
    MemberResponseDto update(Long id, MemberUpdateRequestDto requestDto);
    void delete(Long id, String password);

}
