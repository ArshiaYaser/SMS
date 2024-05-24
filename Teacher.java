package sms;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Teacher extends Person {
    private String subject;
    private List<Course> coursesTaught;

    public Teacher(String id, String username, String name, String password, String subject) {
        super(id, username, name, password);
        this.subject = subject;
        this.coursesTaught = new ArrayList<>();
    }

    // Getters and setters...
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<Course> getCoursesTaught() {
        return coursesTaught;
    }

    public void setCoursesTaught(List<Course> coursesTaught) {
        this.coursesTaught = coursesTaught;
    }

    @Override
    protected void saveToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            StringBuilder sb = new StringBuilder();
            sb.append(id).append(",").append(username).append(",").append(name).append(",").append(password).append(",")
                .append(subject);

            writer.write(sb.toString());
            writer.newLine();
            System.out.println("Teacher data saved successfully."); // Logging
        } catch (IOException e) {
            System.err.println("Failed to save teacher data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    
    public List<String> getStudentsInCourse(String courseName) {
        for (Course course : coursesTaught) {
            if (course.getCourseName().equals(courseName)) {
                return course.getEnrolledStudents();
            }
        }
        return Collections.emptyList();
    }


    public List<String> loadCoursesFromFile(String filename) {
        List<String> courses = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                courses.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public void saveAttendanceToFile(String studentID, List<Attendance> attendanceRecords) {
        String filename = studentID + "_Attendance.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            for (Attendance attendance : attendanceRecords) {
                writer.write(attendance.toString());
                writer.newLine();
            }
            System.out.println("Attendance records saved successfully for student: " + studentID);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public List<Attendance> loadAttendanceFromFile(String filename) {
        List<Attendance> attendanceRecords = new ArrayList<>();
        File file = new File(filename);
        if (!file.exists()) {
            return attendanceRecords; // Return empty list if file does not exist
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
            e.printStackTrace();
        }
        return attendanceRecords;
    }
}
