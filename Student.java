package sms;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Student extends Person {
    private String studentClass;
    private String semester;
    private String dob;
    private List<Course> enrolledCourses;
    private List<Attendance> attendanceRecords;

    public Student(String id, String username, String name, String password, String studentClass, String semester, String dob, List<Course> enrolledCourses) {
        super(id, username, name, password);
        this.studentClass = studentClass;
        this.semester = semester;
        this.dob = dob;
        this.enrolledCourses = enrolledCourses != null ? enrolledCourses : new ArrayList<>();
        this.attendanceRecords = new ArrayList<>();
    }

    // Getters and setters...
    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(List<Course> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    public List<Attendance> getAttendanceRecords() {
        return attendanceRecords;
    }

    public void setAttendanceRecords(List<Attendance> attendanceRecords) {
        this.attendanceRecords = attendanceRecords;
    }

    @Override
    protected void saveToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            StringBuilder sb = new StringBuilder();
            sb.append(id).append(",").append(username).append(",").append(name).append(",").append(password).append(",")
                .append(studentClass).append(",").append(semester).append(",").append(dob);

            writer.write(sb.toString());
            writer.newLine();
            System.out.println("Student data saved successfully."); // Logging
        } catch (IOException e) {
            System.err.println("Failed to save student data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Save enrolled courses to a file
    private void saveEnrolledCoursesToFile(String studentID) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(studentID + "_EnrolledCourses.txt"))) {
            for (Course course : enrolledCourses) {
                writer.write(course.getCourseName());
                writer.newLine();
            }
            System.out.println("Enrolled courses saved successfully.");
        } catch (IOException e) {
            System.err.println("Failed to save enrolled courses: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Save attendance records to a file
    public void saveAttendanceToFile() {
        String filename = id + "_Attendance.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            for (Attendance attendance : attendanceRecords) {
                writer.write(attendance.toString());
                writer.newLine();
            }
            System.out.println("Attendance records saved successfully.");
        } catch (IOException e) {
            System.err.println("Failed to save attendance records: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Load attendance records from a file
    public void loadAttendanceFromFile() {
        String filename = id + "_Attendance.txt";
        File file = new File(filename);
        if (!file.exists()) {
            return; // If file does not exist, do nothing
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String studentID = parts[0];
                    String courseName = parts[1];
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(parts[2]);
                    boolean isPresent = Boolean.parseBoolean(parts[3]);
                    Attendance attendance = new Attendance(studentID, courseName, date, isPresent);
                    attendanceRecords.add(attendance);
                }
            }
        } catch (IOException | java.text.ParseException e) {
            System.err.println("Failed to load attendance records: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
