package com.example.scheduler.repository;

import com.example.scheduler.dto.ScheduleResponseDto;
import com.example.scheduler.entity.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcTempleteScheduleRepository implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTempleteScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("job", schedule.getJob());
        parameters.put("creator", schedule.getCreator());
        parameters.put("password", schedule.getPassword());
        parameters.put("createDate", schedule.getCreateDate());
        parameters.put("updateDate", schedule.getUpdateDate());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new ScheduleResponseDto(key.longValue(), schedule.getJob(), schedule.getCreator(), schedule.getCreateDate(), schedule.getUpdateDate());
    }

    @Override
    public List<ScheduleResponseDto> getAllSchedules() {
        return jdbcTemplate.query("select * from schedule ORDER BY updateDate Desc", ScheduleRowMapper());
    }

    @Override
    public List<ScheduleResponseDto> getAllSchedulesByDate(String updateDate) {
        return jdbcTemplate.query("select * from schedule where DATE(updateDate) = DATE(?) ORDER BY updateDate Desc", ScheduleRowMapper(), updateDate);
    }

    @Override
    public List<ScheduleResponseDto> getAllSchedulesByCreator(String creator) {
        return jdbcTemplate.query("select * from schedule where creator = ? ORDER BY updateDate Desc", ScheduleRowMapper(), creator);
    }

    @Override
    public List<ScheduleResponseDto> getAllSchedulesByFilters(String updateDate, String creator) {
        return jdbcTemplate.query("select * from schedule where DATE(updateDate) = DATE(?) and creator = ? ORDER BY updateDate Desc", ScheduleRowMapper(), updateDate, creator);
    }

    @Override
    public ScheduleResponseDto getScheduleByIdOrElseThrow(Long id) {
        List<ScheduleResponseDto> result = jdbcTemplate.query("select * from schedule where id = ?", ScheduleRowMapper(), id);
        return result.stream().findAny().orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public int updateSchedule(Long id, String job, String creator) {
        return jdbcTemplate.update("update schedule set job = ?, creator = ? where id = ?", job, creator, id);
    }

    @Override
    public Boolean CheckPasswordValidity(Long id, String password) {
        List<Integer> result = jdbcTemplate.query("select 1 from schedule where id = ? and password = ?", (rs, rowNum) -> 1, id, password);
        return !result.isEmpty();
    }

    @Override
    public int deleteSchedule(Long id) {
        return jdbcTemplate.update("delete from schedule where id = ?", id);
    }

    private RowMapper<ScheduleResponseDto> ScheduleRowMapper(){
        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ScheduleResponseDto(
                        rs.getLong("id"),
                        rs.getString("job"),
                        rs.getString("creator"),
                        rs.getTimestamp("createDate").toLocalDateTime(),
                        rs.getTimestamp("updateDate").toLocalDateTime());
            }
        };
    }
}
