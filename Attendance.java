package sms;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Attendance {
    private String studentID;
    private String courseName;
    private Date date;
    private boolean isPresent;

    public Attendance(String studentID, String courseName, Date date2, boolean isPresent) {
        this.studentID = studentID;
        this.courseName = courseName;
        this.date = date2;
        this.isPresent = isPresent;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return studentID + "," + courseName + "," + sdf.format(date) + "," + isPresent;
    }
}
