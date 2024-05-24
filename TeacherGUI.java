package sms;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TeacherGUI extends JFrame {
    private List<AttendanceUpdateListener> listeners = new ArrayList<>();
    private Teacher teacher;
    private JComboBox<String> courseComboBox;
    private StudentGUI studentGUI;

    public TeacherGUI(String id, String username, String name, String password, String subject) {
        this.studentGUI = studentGUI;
        teacher = new Teacher(id, username, name, password, subject);
        getContentPane().setBackground(new Color(146, 28, 185));
        initialize(username, name, subject);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the window
    }

    private void initialize(String username, String name, String subject) {
        setTitle("Teacher Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1367, 786);

        // Use GridBagLayout for centered alignment
        getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel lblWelcome = new JLabel("Welcome to Your Dashboard!");
        lblWelcome.setFont(new Font("Tahoma", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        getContentPane().add(lblWelcome, gbc);

        // Course selection ComboBox
        JLabel lblSelectCourse = new JLabel("Select Course:");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 1;
        getContentPane().add(lblSelectCourse, gbc);

        List<String> courses = loadCoursesFromFile("courses.txt");
        courseComboBox = new JComboBox<>(courses.toArray(new String[0]));
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 1;
        gbc.gridy = 1;
        getContentPane().add(courseComboBox, gbc);

        // Button to mark attendance
        JButton markAttendanceButton = new JButton("Mark Attendance");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        getContentPane().add(markAttendanceButton, gbc);

        // Button to view profile
        JButton viewProfileButton = new JButton("View Profile");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        getContentPane().add(viewProfileButton, gbc);

        JButton MessagesButton = new JButton("Messages");
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        getContentPane().add(MessagesButton, gbc);

        // Action listener for mark attendance button
        markAttendanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCourse = (String) courseComboBox.getSelectedItem();
                if (selectedCourse != null) {
                    openAttendanceMarkingWindow(selectedCourse);
                }
            }
        });

        // Action listener for view profile button
        viewProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewProfile();
            }
        });

        // Action listener for open communication system button
        MessagesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openCommunicationSystem(teacher);
            }
        });

        JButton btnOpenGradeManagementSystem = new JButton("Open Grade Management System");
        btnOpenGradeManagementSystem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openGradeManagementSystem();
            }
        });
        getContentPane().add(btnOpenGradeManagementSystem);
    }

    private void openGradeManagementSystem() {
        // Create an instance of GradeManagementSystem and pass the subject
        GradeManagementSystem gradeManagementSystem = new GradeManagementSystem();
        gradeManagementSystem.frame.setVisible(true); // Assuming frame is the JFrame of GradeManagementSystem
    }

    private List<String> loadEnrolledStudentsFromFile(String courseName) {
        List<String> enrolledStudents = new ArrayList<>();
        String filename = courseName + "_EnrolledStudents.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String studentID;
            while ((studentID = reader.readLine()) != null) {
                enrolledStudents.add(studentID);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return enrolledStudents;
    }

    private void viewProfile() {
        // Display the teacher's profile information in a dialog
        String profileInfo = "Teacher Profile:\n" +
                "ID: " + teacher.getId() + "\n" +
                "Username: " + teacher.getUsername() + "\n" +
                "Name: " + teacher.getName() + "\n" +
                "Subject: " + teacher.getSubject();
        JOptionPane.showMessageDialog(this, profileInfo, "Teacher Profile", JOptionPane.INFORMATION_MESSAGE);
    }

    private void openAttendanceMarkingWindow(String selectedCourse) {
        // Create a new JFrame for the attendance marking window
        JFrame attendanceWindow = new JFrame("Mark Attendance for " + selectedCourse);
        attendanceWindow.setSize(600, 400);
        attendanceWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        attendanceWindow.getContentPane().setLayout(new BorderLayout());

        // Load the enrolled students for the selected course
        List<String> enrolledStudents = loadEnrolledStudentsFromFile(selectedCourse);

        // Create column names for the table
        String[] columnNames = {"Student ID", "Name", "Class", "Attendance"};

        // Create a data array for the table
        Object[][] data = new Object[enrolledStudents.size()][4];

        // Iterate through enrolled students
        for (int i = 0; i < enrolledStudents.size(); i++) {
            String studentID = enrolledStudents.get(i);
            // Load student details from file
            String[] studentDetails = loadStudentDetailsFromFile(studentID);
            // Fill data array with student details
            data[i][0] = studentID;
            data[i][1] = studentDetails[0]; // Name
            data[i][2] = studentDetails[1]; // Class
            // Initialize checkbox state as false
            data[i][3] = false;
        }

        // Create the JTable with data and column names
        JTable table = new JTable(data, columnNames) {
            // Override the isCellEditable method to make cells non-editable
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // Allow editing only for the checkbox column
            }
        };

        // Add a listener to update the checkbox state in the data array
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());
                if (col == 3) {
                    Boolean isChecked = (Boolean) data[row][col];
                    data[row][col] = !isChecked; // Toggle checkbox state
                    table.repaint(); // Repaint the table to update the checkbox appearance
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        attendanceWindow.getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Button to submit attendance
        JButton submitButton = new JButton("Submit Attendance");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logic to submit attendance
                try {
                    String attendanceFileName = selectedCourse + "_Attendance.txt";
                    PrintWriter writer = new PrintWriter(new FileWriter(attendanceFileName, true));
                    for (int i = 0; i < data.length; i++) {
                        String studentID = (String) data[i][0];
                        boolean isChecked = (Boolean) data[i][3];
                        // Write attendance information to the file
                        writer.println(studentID + "," + (isChecked ? "Present" : "Absent"));
                    }
                    writer.close();
                    // Calculate and save attendance percentage for each student
                    for (int i = 0; i < data.length; i++) {
                        String studentID = (String) data[i][0];
                        double attendancePercentage = calculateAttendancePercentage(selectedCourse, studentID);
                        saveAttendancePercentage(selectedCourse, studentID, attendancePercentage);
                    }
                    // Close the attendance marking window after submitting attendance
                    attendanceWindow.dispose();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(attendanceWindow, "Error saving attendance.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add the submit button to the attendance window
        attendanceWindow.getContentPane().add(submitButton, BorderLayout.SOUTH);

        // Set the visibility of the attendance window
        attendanceWindow.setVisible(true);
    }

    // Method to save attendance percentage to a file for each student ID and subject
    private void saveAttendancePercentage(String courseName, String studentID, double attendancePercentage) {
        try {
            String filename = studentID + "_" + courseName + "_AttendancePercentage.txt";
            PrintWriter writer = new PrintWriter(new FileWriter(filename));
            writer.println(attendancePercentage);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to calculate attendance percentage for a student in a course
    private double calculateAttendancePercentage(String courseName, String studentID) {
        // Load attendance records for the student in the course
        List<Attendance> attendanceRecords = loadAttendanceRecords(courseName, studentID);
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

    private List<Attendance> loadAttendanceRecords(String courseName, String studentID) {
        List<Attendance> attendanceRecords = new ArrayList<>();
        String filename = courseName + "_Attendance.txt";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Define the date format

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[0].equals(studentID)) {
                    boolean isPresent = parts[1].equalsIgnoreCase("Present");
                    Date date = null;
                    try {
                        date = dateFormat.parse(parts[2]); // Parse the date string to a Date object
                    } catch (ParseException e) {
                        e.printStackTrace(); // Handle parsing exception
                    }
                    Attendance attendance = new Attendance(studentID, courseName, date, isPresent);
                    attendanceRecords.add(attendance);
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle file IO exception
        }
        return attendanceRecords;
    }

    // Method to load name and class from students.txt file
    private String[] loadStudentDetailsFromFile(String studentID) {
        // File path where student details are stored
        String filePath = "students.txt";

        // Array to store student details
        String[] studentDetails = new String[2]; // Assuming two details: name and class

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 7 && parts[0].equals(studentID)) {
                    studentDetails[0] = parts[2]; // Name
                    studentDetails[1] = parts[4]; // Class
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle file reading errors
        }

        return studentDetails;
    }

    public void registerAttendanceUpdateListener(AttendanceUpdateListener listener) {
        listeners.add(listener);
    }

    private void notifyAttendanceUpdate(String courseName, double attendancePercentage) {
        for (AttendanceUpdateListener listener : listeners) {
            listener.onAttendanceUpdate(courseName, attendancePercentage);
        }
    }
    
  /// Method to open communication system
    private void openCommunicationSystem(Teacher teacher) {
        // Get the admin's ID using the getter method
        String ID = teacher.getId();
        // Create an instance of CommunicationSystem and pass the admin's ID
        CommunicationSystem communicationSystem = new CommunicationSystem(ID);
        communicationSystem.frame.setVisible(true); // Assuming frame is the JFrame of CommunicationSystem
    }
    

    private List<String> loadCoursesFromFile(String filename) {
        List<String> courses = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                courses.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses;
    }
}

