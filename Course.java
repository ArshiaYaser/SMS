package sms;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Course {
	private String courseName;
    private String enrollmentKey;
    private String schedule;
    private String curriculum;
    private String semester;
    private List<String> enrolledStudents;

    public Course(String courseName, String enrollmentKey, String schedule, String curriculum, String semester) {
        this.courseName = courseName;
        this.enrollmentKey = enrollmentKey;
        this.schedule = schedule;
        this.curriculum = curriculum;
        this.semester = semester;
        this.enrolledStudents = new ArrayList<>();
        // Read enrolled students from file when initializing the course
        readEnrolledStudentsFromFile();    }

    // Getters
    public String getCourseName() {
        return courseName;
    }

    public String getEnrollmentKey() {
        return enrollmentKey;
    }

    public String getSchedule() {
        return schedule;
    }

    public String getCurriculum() {
        return curriculum;
    }

    public String getSemester() {
        return semester;
    }

    public static void saveCoursesToFile(List<Course> courses, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Course course : courses) {
                writer.println(course.courseName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void saveEnrolledCoursesToFile(String courseName) {
        String filename = courseName + "_EnrolledStudents.txt"; // Using courseName as part of the filename
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String studentID : enrolledStudents) {
                writer.write(studentID);
                writer.newLine();
            }
            System.out.println("Enrolled students for course " + courseName + " saved successfully.");
        } catch (IOException e) {
            System.err.println("Failed to save enrolled students for course " + courseName + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void enrollStudent(String studentID) {
        if (!enrolledStudents.contains(studentID)) {
            enrolledStudents.add(studentID);
            saveEnrolledStudentsToFile(); // Save the updated list of enrolled student
        }
        }
    
    public void saveEnrolledStudentsToFile() {
        String filename = courseName + "_EnrolledStudents.txt"; // Using courseName as part of the filename
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (String studentID : enrolledStudents) {
                writer.println(studentID);
            }
            System.out.println("Enrolled students for course " + courseName + " saved successfully.");
        } catch (IOException e) {
            System.err.println("Failed to save enrolled students for course " + courseName + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to read enrolled students from the respective file
    private void readEnrolledStudentsFromFile() {
        String filename = courseName + "_students.txt"; // Using courseName as part of the filename
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                enrolledStudents.add(line);
            }
        } catch (IOException e) {
            // If the file doesn't exist or there is an error reading, it's okay, just continue with an empty list
        }
    }
    
    private List<Course> loadEnrolledCoursesFromFile(String studentID) {
        List<Course> enrolledCourses = new ArrayList<>();
        String filename = "enrolled_courses.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[0].equals(studentID)) {
                    // Found the student ID, add their enrolled courses to the list
                    for (int i = 1; i < parts.length; i++) {
                        // Assuming Course class has a constructor that accepts course details
                        String[] courseDetails = parts[i].split("\\|");
                        if (courseDetails.length == 5) {
                            enrolledCourses.add(new Course(
                                    courseDetails[0], // Course name
                                    courseDetails[1], // Enrollment key
                                    courseDetails[2], // Schedule
                                    courseDetails[3], // Curriculum
                                    courseDetails[4]  // Semester
                            ));
                        } else {
                            System.out.println("Invalid course format: " + parts[i]);
                        }
                    }
                    break; // No need to continue searching
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the IOException appropriately (e.g., log or display an error message)
        }

        return enrolledCourses;
    }


   


    public static List<Course> readCoursesFromFile(String filename) {
        List<Course> courses = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|"); // Use "\\" to escape the pipe character
                if (parts.length == 5) {
                    Course course = new Course(parts[0], parts[1], parts[2], parts[3], parts[4]);
                    courses.add(course);
                } else {
                    System.out.println("Invalid course format: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return courses;
    }
    
    public String toString() {
        return "Course Name: " + courseName +
               ", Enrollment Key: " + enrollmentKey +
               ", Schedule: " + schedule +
               ", Curriculum: " + curriculum +
               ", Semester: " + semester;
    }
    

    // Main method for testing
    public static void main(String[] args) {
        // Sample courses
        List<Course> courses = new ArrayList<>();
        courses.add(new Course("Mathematics", "12345", "Mon/Wed/Fri 9:00 AM - 10:00 AM", "Algebra, Geometry, Calculus", "First"));
        courses.add(new Course("Physics", "54321", "Tue/Thu 11:00 AM - 12:30 PM", "Mechanics, Thermodynamics, Electromagnetism", "First"));
        courses.add(new Course("Computer Programming", "67890", "Mon/Wed/Fri 1:00 PM - 2:30 PM", "Introduction to Programming, Data Structures", "First"));

        // Save courses to file
        saveCoursesToFile(courses, "courses.txt");
    }
    
    public List<String> getEnrolledStudents() {
        return enrolledStudents;
    }

}

