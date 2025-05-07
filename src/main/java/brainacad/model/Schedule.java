package brainacad.model;

import java.time.LocalDate;

public class Schedule
{
    private int id;
    private int staffId;
    private LocalDate workDay;
    private String shift;

    public Schedule()
    {

    }

    public Schedule(int id, int staffId, LocalDate workDay, String shift)
    {
        this.id = id;
        this.staffId = staffId;
        this.workDay = workDay;
        this.shift = shift;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getStaffId() { return staffId; }
    public void setStaffId(int staffId) { this.staffId = staffId; }

    public LocalDate getWorkDay() { return workDay; }
    public void setWorkDay(LocalDate workDay) { this.workDay = workDay; }

    public String getShift() { return shift; }
    public void setShift(String shift) { this.shift = shift; }

    @Override
    public String toString() {
        return "StaffSchedule{id=" + id + ", staffId=" + staffId + ", workDay=" + workDay + ", shift='" + shift + "'}";
    }
}