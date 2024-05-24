package sms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

interface AttendanceUpdateListener {
    void onAttendanceUpdate(String courseName, double attendancePercentage);
}

public class StudentGUI extends JFrame {
    // Fields
    private JTextField textFieldStudentID;
    private JTextField textFieldUsername;
    private JTextField textFieldName;
    private JTextField textFieldClass;
    private JTextField textFieldSemester;
    private JTextField textFieldDOB;
    private JTextArea textAreaFeedbackContent;
    private JTable table;
    private JButton btnEnrollCourse;
    private JButton btnViewAttendance;
    private JButton btnProgressReport;
    private JButton btnSubmitFeedback;
    private JButton btnViewEnrolledCourses;
    private JButton btnViewProfile;
    private JButton btnMessages;

    private List<Course> enrolledCourses;
    private Student student;

    public StudentGUI(String studentID, String username, String name, String password, String studentClass, String semester, String dob) {
        student = new Student(studentID, username, name, password, studentClass, semester, dob, enrolledCourses);
        enrolledCourses = loadEnrolledCoursesFromFile(studentID);
     // Maximize the window
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(new Color(146, 28, 185));
        initialize(studentID, username, name, studentClass, semester, dob); // Pass dob parameter
    }

    private void initialize(String studentID, String username, String name, String studentClass, String semester, String dob) {
        setTitle("Student Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1367, 786);
        getContentPane().setLayout(null); // Use null layout for precise positioning

        JLabel lblWelcome = new JLabel("Welcome to Your Dashboard!");
        lblWelcome.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblWelcome.setBounds(549, 49, 300, 30);
        getContentPane().add(lblWelcome);
        
        // Create buttons for different functionalities
        btnEnrollCourse = new JButton("Enroll Course");
        btnEnrollCourse.setBounds(50, 100, 150, 30);
        getContentPane().add(btnEnrollCourse);

        btnViewAttendance = new JButton("Track Attendance");
        btnViewAttendance.setBounds(50, 150, 150, 30);
        getContentPane().add(btnViewAttendance);

        btnProgressReport = new JButton("View Progress Report");
        btnProgressReport.setBounds(50, 200, 150, 30);
        getContentPane().add(btnProgressReport);

        btnSubmitFeedback = new JButton("Submit Feedback");
        btnSubmitFeedback.setBounds(50, 250, 150, 30);
        getContentPane().add(btnSubmitFeedback);
        
     // Add the "Messages" button
        btnMessages = new JButton("Messages");
        btnMessages.setBounds(50, 400, 150, 30);
        getContentPane().add(btnMessages);
        btnMessages.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openCommunicationSystem();
            }
        });

        btnViewEnrolledCourses = new JButton("View Enrolled Courses");
        btnViewEnrolledCourses.setBounds(50, 300, 150, 30);
        getContentPane().add(btnViewEnrolledCourses);

        btnViewProfile = new JButton("View Profile");
        btnViewProfile.setBounds(50, 353, 150, 30);
        getContentPane().add(btnViewProfile);

        // Add action listeners to the buttons
        btnEnrollCourse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enrollCourse(studentID);
            }
        });

        btnViewAttendance.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewAttendance();
            }
        });

        btnProgressReport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                progressReport();
            }
        });

        btnSubmitFeedback.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submitFeedback("");
            }
        });

        btnViewEnrolledCourses.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewEnrolledCourses(studentID);
            }
        });

        btnViewProfile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewProfile(studentID, username, name, studentClass, semester, dob);
            }
        });
    }

    private void viewProfile(String studentID, String username, String name, String studentClass, String semester, String dob) {
        StringBuilder profile = new StringBuilder();
        profile.append("Student ID: ").append(studentID).append("\n");
        profile.append("Username: ").append(username).append("\n");
        profile.append("Name: ").append(name).append("\n");
        profile.append("Class: ").append(studentClass).append("\n");
        profile.append("Semester: ").append(semester).append("\n");
        profile.append("Date of Birth: ").append(dob).append("\n");

        JOptionPane.showMessageDialog(this, profile.toString(), "Student Profile", JOptionPane.INFORMATION_MESSAGE);
    }


    private void enrollCourse(String studentID) {
        JFrame courseSelectionFrame = new JFrame("Available Courses");
        courseSelectionFrame.setBounds(100, 100, 600, 400);
        courseSelectionFrame.getContentPane().setLayout(new BorderLayout());

        // Read courses from file
        List<Course> availableCourses = Course.readCoursesFromFile("courses.txt");

        // Convert course list to an array for display
        String[] columnNames = {"Course Name", "Enrollment Key", "Schedule", "Curriculum", "Semester"};
        Object[][] rowData = new Object[availableCourses.size()][columnNames.length];
        for (int i = 0; i < availableCourses.size(); i++) {
            Course course = availableCourses.get(i);
            rowData[i][0] = course.getCourseName();
            rowData[i][1] = course.getEnrollmentKey();
            rowData[i][2] = course.getSchedule();
            rowData[i][3] = course.getCurriculum();
            rowData[i][4] = course.getSemester();
        }

        // Create a table to display courses
        JTable courseTable = new JTable(rowData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };
        JScrollPane scrollPane = new JScrollPane(courseTable);
        courseSelectionFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Add input fields for enrollment
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JTextField textFieldCourseName = new JTextField(10);
        panel.add(new JLabel("Enter Course Name to Enroll:"));
        panel.add(textFieldCourseName);

        JTextField textFieldEnrollmentKey = new JTextField(10);
        panel.add(new JLabel("Enter Enrollment Key:"));
        panel.add(textFieldEnrollmentKey);

        JButton btnEnroll = new JButton("Enroll");
        btnEnroll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String courseName = textFieldCourseName.getText();
                String enrollmentKey = textFieldEnrollmentKey.getText();
                boolean enrolled = false;
                for (Course course : availableCourses) {
                    if (course.getCourseName().equals(courseName) && course.getEnrollmentKey().equals(enrollmentKey)) {
                        enrolled = true;
                        // Add course to student's enrolled courses
                        enrolledCourses.add(course);
                        // Save enrolled courses to file
                        saveEnrolledCourses(studentID);
                        // Ensure the student is enrolled in the course
                        course.enrollStudent(studentID);
                        // Save enrolled students to the respective file
                        course.saveEnrolledStudentsToFile();
                        JOptionPane.showMessageDialog(courseSelectionFrame, "Enrolled in " + course.getCourseName(), "Enrollment Confirmation", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }
                }
                if (!enrolled) {
                    JOptionPane.showMessageDialog(courseSelectionFrame, "Invalid Course Name or Enrollment Key", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(btnEnroll);
        courseSelectionFrame.getContentPane().add(panel, BorderLayout.SOUTH);

        courseSelectionFrame.setVisible(true);
    }
    private List<Course> loadEnrolledCoursesFromFile(String studentID) {
        List<Course> enrolledCourses = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(studentID + "_courses.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Assuming each line contains the name of a course
                Course course = new Course(line, "", "", "", ""); // Fill in other details if needed
                enrolledCourses.add(course);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return enrolledCourses;
    }

    private void saveEnrolledCourses(String studentID) {
        try {
            File directory = new File("enrolled_courses");
            if (!directory.exists()) {
                directory.mkdir(); // Create the directory if it doesn't exist
            }
            File studentFile = new File(directory, studentID + "_courses.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(studentFile));

            // Write the enrolled courses to the file
            for (Course course : enrolledCourses) {
                writer.write(course.getCourseName());
                writer.newLine();
            }

            writer.close();
            System.out.println("Enrolled courses for student " + studentID + " saved successfully.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    private void progressReport() {
        // Implementation of generateReport method in the StudentGUI class
        try {
            // Get the student's ID
            String studentID = student.getId();

            // Create the file name with Student ID
            String fileName = studentID + "_grades_report.txt";

            // Read the report file
            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            // Create a table model to hold the report data
            DefaultTableModel tableModel = new DefaultTableModel(new Object[][]{}, new String[]{"Student ID", "Grade"});

            // Read each line from the report file
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line into attribute and value
                String[] parts = line.split(":");
                String attribute = parts[0].trim();
                String value = parts[1].trim();

                // Add the attribute and value to the table model
                tableModel.addRow(new Object[]{attribute, value});
            }

            // Close the reader
            reader.close();

            // Create a table to display the report data
            JTable table = new JTable(tableModel);

            // Create a scroll pane for the table
            JScrollPane scrollPane = new JScrollPane(table);

            // Create a frame to display the table
            JFrame reportFrame = new JFrame("Grade Report");
            reportFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            reportFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
            reportFrame.pack();
            reportFrame.setVisible(true);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error reading report: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void submitFeedback(String feedbackContent) {
        // Open a dialog for the user to enter feedback content
        JTextArea feedbackTextArea = new JTextArea(10, 30);
        JScrollPane scrollPane = new JScrollPane(feedbackTextArea);
        int result = JOptionPane.showConfirmDialog(this, scrollPane, "Enter Feedback", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String feedback = feedbackTextArea.getText();

            // Check if the feedback is empty
            if (feedback.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Feedback cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create a timestamp for the feedback
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timestamp = dateFormat.format(new Date());

            // Write the feedback to a file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("feedback.txt", true))) {
                writer.write("Timestamp: " + timestamp + "\n");
                writer.write("Student ID: " + student.getId() + "\n");
                writer.write("Feedback:\n" + feedback + "\n\n");
                writer.flush();
                JOptionPane.showMessageDialog(this, "Feedback submitted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "An error occurred while submitting feedback.", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    private void viewAttendance() {
        // Create a JFrame to display the attendance details
        JFrame attendanceFrame = new JFrame("Attendance Details");
        attendanceFrame.setSize(600, 400);
        attendanceFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        attendanceFrame.getContentPane().setLayout(new BorderLayout());

        
        // Create a JPanel to hold the table
        JPanel panel = new JPanel(new BorderLayout());

        // Load enrolled courses
        List<Course> enrolledCourses = loadEnrolledCoursesFromFile(student.getId());

        // Create column names for the table
        String[] columnNames = {"Course Name", "Attendance Percentage"};

        // Create a data array for the table
        Object[][] data = new Object[enrolledCourses.size()][2];

        // Iterate through enrolled courses
        for (int i = 0; i < enrolledCourses.size(); i++) {
            Course course = enrolledCourses.get(i);

            // Read attendance records from the file for this course
            List<Attendance> attendanceRecords = loadAttendanceRecordsFromFile(course.getCourseName());

            // Calculate attendance percentage
            double attendancePercentage = calculateAttendancePercentage(attendanceRecords);

            // Fill data array with course name and attendance percentage
            data[i][0] = course.getCourseName();
            data[i][1] = String.format("%.2f%%", attendancePercentage);
        }

        // Create the JTable with data and column names
        table = new JTable(data, columnNames); // Initialize the 'table' field
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Add the panel to the JFrame
        attendanceFrame.getContentPane().add(panel, BorderLayout.CENTER);

        // Set the visibility of the JFrame
        attendanceFrame.setVisible(true);
    }

    private List<Attendance> loadAttendanceRecordsFromFile(String courseName) {
        List<Attendance> attendanceRecords = new ArrayList<>();
        String filename = courseName + "_Attendance.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Parse each line to create Attendance objects
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String studentID = parts[0];
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(parts[2]);
                    boolean isPresent = Boolean.parseBoolean(parts[3]);
                    attendanceRecords.add(new Attendance(studentID, courseName, date, isPresent));
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return attendanceRecords;
    }

    private double calculateAttendancePercentage(List<Attendance> attendanceRecords) {
        if (attendanceRecords.isEmpty()) {
            return 0.0;
        }
        int totalClasses = attendanceRecords.size();
        int attendedClasses = 0;
        for (Attendance attendance : attendanceRecords) {
            if (attendance.isPresent()) {
                attendedClasses++;
            }
        }
        return (double) attendedClasses / totalClasses * 100;
    }
    
    public void updateAttendancePercentage(String courseName, double attendancePercentage) {
        // Get the table model
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        // Find the row corresponding to the course in the table
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 0).equals(courseName)) {
                // Update the attendance percentage
                model.setValueAt(String.format("%.2f%%", attendancePercentage), i, 1);
                break;
            }
        }
    }
    
    interface AttendanceUpdateListener {
        void onAttendanceUpdate(String courseName, double attendancePercentage);
    }
    
    private void openCommunicationSystem() {
        EventQueue.invokeLater(() -> {
            try {
                CommunicationSystem window = new CommunicationSystem(student.getId());
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    
    private void viewEnrolledCourses(String studentID) {
        List<Course> enrolledCourses = loadEnrolledCoursesFromFile(studentID);
        StringBuilder enrolledCoursesText = new StringBuilder("Enrolled Courses:\n");
        for (Course course : enrolledCourses) {
            enrolledCoursesText.append(course.getCourseName());
        }
        JOptionPane.showMessageDialog(this, enrolledCoursesText.toString(), "Enrolled Courses", JOptionPane.INFORMATION_MESSAGE);
    }
}