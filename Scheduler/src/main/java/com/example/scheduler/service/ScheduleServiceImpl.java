package com.example.scheduler.service;

import com.example.exception.ApplicationException;
import com.example.scheduler.dto.ScheduleSaveRequestDto;
import com.example.scheduler.dto.ScheduleUpdateRequestDto;
import com.example.scheduler.dto.ScheduleResponseDto;
import com.example.scheduler.entity.Member;
import com.example.scheduler.entity.Schedule;
import com.example.scheduler.repository.MemberRepository;
import com.example.scheduler.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ScheduleResponseDto save(ScheduleSaveRequestDto requestDto) {
        Member member = memberRepository.findByEmailAndPassword(requestDto.getMemberEmail(), requestDto.getPassword()).orElseThrow(
                () -> new ApplicationException("해당 정보와 일치하는 회원이 존재하지 않습니다.", HttpStatus.NOT_FOUND)
        );

        Schedule schedule = new Schedule(
                requestDto.getTask(),
                member
        );

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDto(
                savedSchedule.getId(),
                savedSchedule.getTask(),
                member.getName(),
                member.getEmail(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getUpdatedAt()
        );
    }

    @Transactional(readOnly = true)
    public ScheduleResponseDto findScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                () -> new ApplicationException("해당 id의 스케줄이 존재하지 않습니다.", HttpStatus.NOT_FOUND)
        );

        Member member = memberRepository.findById(schedule.getMember().getId()).orElseThrow(
                () -> new ApplicationException("해당 id의 회원이 존재하지 않습니다.", HttpStatus.NOT_FOUND)
        );

        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getTask(),
                member.getName(),
                member.getEmail(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt()
        );
    }

    @Transactional(readOnly = true)
    public Page<ScheduleResponseDto> findAll(String updatedDate, String memberName, Long memberId, int page, int size) {
        Page<Schedule> schedulePage = scheduleRepository.findAll(updatedDate, memberName, memberId, page, size);
        return schedulePage.map(schedule -> {
            Member member = schedule.getMember();
            return new ScheduleResponseDto(
                    schedule.getId(),
                    schedule.getTask(),
                    member.getName(),
                    member.getEmail(),
                    schedule.getCreatedAt(),
                    schedule.getUpdatedAt()
            );
        });
    }

    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, ScheduleUpdateRequestDto requestDto) {
        Member member = memberRepository.findByEmailAndPassword(requestDto.getMemberEmail(), requestDto.getPassword()).orElseThrow(
                () -> new ApplicationException("해당 정보와 일치하는 회원이 존재하지 않습니다.", HttpStatus.NOT_FOUND)
        );

        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                () -> new ApplicationException("해당 id의 스케줄이 존재하지 않습니다.", HttpStatus.NOT_FOUND)
        );

        if (!schedule.getMember().getId().equals(member.getId())) {
            throw new ApplicationException("해당 스케줄의 작성자가 아닙니다.", HttpStatus.FORBIDDEN);
        }

        schedule.update(requestDto.getTask());
        Schedule updatedSchedule = scheduleRepository.update(schedule);

        return new ScheduleResponseDto(
                updatedSchedule.getId(),
                updatedSchedule.getTask(),
                member.getName(),
                member.getEmail(),
                updatedSchedule.getCreatedAt(),
                updatedSchedule.getUpdatedAt()
        );
    }

    @Transactional
    public void deleteSchedule(Long id, String memberName, String password) {
        Member member = memberRepository.findByEmailAndPassword(memberName, password).orElseThrow(
                () -> new ApplicationException("해당 정보와 일치하는 회원이 존재하지 않습니다.", HttpStatus.NOT_FOUND)
        );

        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                () -> new ApplicationException("해당 id의 스케줄이 존재하지 않습니다.", HttpStatus.NOT_FOUND)
        );

        if (!schedule.getMember().getId().equals(member.getId())) {
            throw new ApplicationException("해당 스케줄의 작성자가 아닙니다.", HttpStatus.FORBIDDEN);
        }

        scheduleRepository.deleteById(id);
    }
}