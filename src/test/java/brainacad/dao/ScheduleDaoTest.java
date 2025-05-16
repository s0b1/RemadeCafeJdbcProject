package brainacad.dao;

import brainacad.model.Schedule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ScheduleDaoTest {

    private JdbcTemplate jdbcTemplate;
    private ScheduleDao dao;

    @BeforeEach
    void setUp() {
        jdbcTemplate = Mockito.mock(JdbcTemplate.class);
        dao = new ScheduleDao(jdbcTemplate);
    }

    @Test
    void testCreateSchedule() {
        Schedule s = new Schedule(0, 1, LocalDate.of(2025, 5, 20), "morning");
        dao.create(s);
        verify(jdbcTemplate).update(anyString(), eq(1), eq(java.sql.Date.valueOf("2025-05-20")), eq("morning"));
    }

    @Test
    void testReadAllSchedules() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(List.of());
        List<Schedule> result = dao.readAll();
        assertNotNull(result);
    }
}