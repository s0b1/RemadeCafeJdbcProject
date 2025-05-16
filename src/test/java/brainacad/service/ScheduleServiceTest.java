package brainacad.service;

import brainacad.model.Schedule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Date;
import java.time.LocalDate;

import static org.mockito.Mockito.*;

public class ScheduleServiceTest {

    private JdbcTemplate jdbcTemplate;
    private ScheduleService scheduleService;

    @BeforeEach
    void setup() {
        jdbcTemplate = mock(JdbcTemplate.class);
        scheduleService = new ScheduleService(jdbcTemplate);
    }

    @Test
    void testAddSchedule() {
        Schedule schedule = new Schedule(0, 1, LocalDate.of(2025, 5, 20), "morning");

        scheduleService.addSchedule(schedule);

        verify(jdbcTemplate).update(
                eq("INSERT INTO staff_schedule (staff_id, work_day, shift) VALUES (?, ?, ?)"),
                eq(schedule.getStaffId()),
                eq(Date.valueOf(schedule.getWorkDay())),
                eq(schedule.getShift())
        );
    }

    @Test
    void testUpdateScheduleByDate() {
        scheduleService.updateScheduleByDate(1, LocalDate.of(2025, 5, 20), "evening");

        verify(jdbcTemplate).update(
                eq("UPDATE staff_schedule SET shift = ? WHERE staff_id = ? AND work_day = ?"),
                eq("evening"),
                eq(1),
                eq(Date.valueOf(LocalDate.of(2025, 5, 20)))
        );
    }

    @Test
    void testDeleteScheduleByDay() {
        LocalDate date = LocalDate.of(2025, 5, 20);
        scheduleService.deleteScheduleByDay(date);

        verify(jdbcTemplate).update(
                eq("DELETE FROM staff_schedule WHERE work_day = ?"),
                eq(Date.valueOf(date))
        );
    }

    @Test
    void testDeleteScheduleByRange() {
        LocalDate from = LocalDate.of(2025, 5, 20);
        LocalDate to = LocalDate.of(2025, 5, 27);

        scheduleService.deleteScheduleByRange(from, to);

        verify(jdbcTemplate).update(
                eq("DELETE FROM staff_schedule WHERE work_day BETWEEN ? AND ?"),
                eq(Date.valueOf(from)),
                eq(Date.valueOf(to))
        );
    }
}
