package sms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdministratorGUI extends JFrame {

    private String id;
    private String username;
    private String name;
    private String password;
    private Admin admin;

    public AdministratorGUI(String id, String username, String name, String password) {
        admin = new Admin(id, username, name, password);
        this.id = id;
        this.username = username;
        this.name = name;
        this.password = password;
        getContentPane().setBackground(new Color(146, 28, 185));
        initialize();
    }

    private void initialize() {
        setTitle("Administrator GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1364, 810);
        getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Create buttons panel
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        buttonPanel.setBackground(new Color(146, 28, 185));
        getContentPane().add(buttonPanel);

        // Create buttons for viewing records
        JButton viewTeacherButton = new JButton("View Teacher Records");
        viewTeacherButton.setForeground(new Color(255, 255, 255));
        getContentPane().add(viewTeacherButton);
        viewTeacherButton.setBackground(new Color(0, 0, 0));
        viewTeacherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openTeacherRecordsWindow();
            }
        });

        // Add "Messages" button
        JButton messagesButton = new JButton("Messages");
        messagesButton.setForeground(new Color(255, 255, 255));
        getContentPane().add(messagesButton);
        messagesButton.setBackground(new Color(0, 0, 0));
        messagesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openCommunicationSystem(admin);
            }
        });

        JButton viewStudentButton = new JButton("View Student Records");
        viewStudentButton.setForeground(new Color(255, 255, 255));
        getContentPane().add(viewStudentButton);
        viewStudentButton.setBackground(new Color(0, 0, 0));
        viewStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openStudentRecordsWindow();
            }
        });

        // Add a button for viewing feedback
        JButton btnViewFeedback = new JButton("View Feedback");
        btnViewFeedback.setForeground(new Color(255, 255, 255));
        btnViewFeedback.setBackground(new Color(0, 0, 0));
        btnViewFeedback.setBounds(50, 350, 150, 30);
        getContentPane().add(btnViewFeedback);

        // Add action listener for the View Feedback button
        btnViewFeedback.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openFeedbackWindow();
            }
        });

        // Add "View Profile" button
        JButton viewProfileButton = new JButton("View Profile");
        viewProfileButton.setForeground(new Color(255, 255, 255));
        getContentPane().add(viewProfileButton);
        viewProfileButton.setBackground(new Color(0, 0, 0));
        viewProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewProfile();
            }
        });
    }

    // Method to open window for viewing teacher records
    private void openTeacherRecordsWindow() {
        JFrame teacherRecordsFrame = new JFrame("Teacher Records");
        teacherRecordsFrame.setSize(500, 400);
        teacherRecordsFrame.setLocationRelativeTo(null); // Center the window
        teacherRecordsFrame.getContentPane().setLayout(new BorderLayout());

        // Create table to display teacher records
        JTable teacherTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(teacherTable);
        teacherRecordsFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Load teacher records into the table
        loadTeacherRecords(teacherTable);

        teacherRecordsFrame.setVisible(true);
    }

    // Method to open communication system
    private void openCommunicationSystem(Admin admin) {
        // Get the admin's ID using the getter method
        String adminId = admin.getId();
        // Create an instance of CommunicationSystem and pass the admin's ID
        CommunicationSystem communicationSystem = new CommunicationSystem(adminId);
        communicationSystem.frame.setVisible(true); // Assuming frame is the JFrame of CommunicationSystem
    }

    private void openFeedbackWindow() {
        JFrame feedbackFrame = new JFrame("Feedback");
        feedbackFrame.setSize(600, 400);
        feedbackFrame.setLocationRelativeTo(null); // Center the window
        feedbackFrame.getContentPane().setLayout(new BorderLayout());

        // Create table to display feedback
        JTable feedbackTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(feedbackTable);
        feedbackFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Load feedback data into the table
        loadFeedbackData(feedbackTable);

        feedbackFrame.setVisible(true);
    }

    private void loadFeedbackData(JTable feedbackTable) {
        // Load feedback data from the file
        List<String[]> feedbackData = loadFeedbackDataFromFile();

        // Create column names for the table
        String[] columnNames = {"Student ID", "Feedback"};

        // Convert feedbackData to a two-dimensional array
        Object[][] rowData = new Object[feedbackData.size()][2];
        for (int i = 0; i < feedbackData.size(); i++) {
            rowData[i] = feedbackData.get(i);
        }

        // Create a table model with the data
        DefaultTableModel model = new DefaultTableModel(rowData, columnNames);

        // Set the model to the feedbackTable
        feedbackTable.setModel(model);
    }

    private List<String[]> loadFeedbackDataFromFile() {
        List<String[]> feedbackData = new ArrayList<>();
        // Read feedback data from the file
        try (BufferedReader reader = new BufferedReader(new FileReader("feedback.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line into Student ID and Feedback
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String studentID = parts[0].trim();
                    String feedback = parts[1].trim();
                    feedbackData.add(new String[]{studentID, feedback});
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return feedbackData;
    }

    // Method to open window for viewing student records
    private void openStudentRecordsWindow() {
        JFrame studentRecordsFrame = new JFrame("Student Records");
        studentRecordsFrame.setSize(500, 400);
        studentRecordsFrame.setLocationRelativeTo(null); // Center the window
        studentRecordsFrame.getContentPane().setLayout(new BorderLayout());

        // Create table to display student records
        JTable studentTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(studentTable);
        studentRecordsFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Load student records into the table
        loadStudentRecords(studentTable);

        studentRecordsFrame.setVisible(true);
    }

    // Method to load teacher records into the table
    private void loadTeacherRecords(JTable teacherTable) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Username");
        model.addColumn("Name");
        model.addColumn("Subject");

        // Get all teacher records
        List<Teacher> teachers = getAllTeachers();

        // Add data to the model
        for (Teacher teacher : teachers) {
            Object[] rowData = {teacher.getId(), teacher.getUsername(), teacher.getName(), teacher.getSubject()};
            model.addRow(rowData);
        }

        // Set table model
        teacherTable.setModel(model);
    }

    // Method to load student records into the table
    private void loadStudentRecords(JTable studentTable) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Username");
        model.addColumn("Name");
        model.addColumn("Class");

        // Get all student records
        List<Student> students = getAllStudents();

        // Add data to the model
        for (Student student : students) {
            Object[] rowData = {student.getId(), student.getUsername(), student.getName(), student.getStudentClass()};
            model.addRow(rowData);
        }

        // Set table model
        studentTable.setModel(model);
    }

    // Method to retrieve all teacher records
    private List<Teacher> getAllTeachers() {
        // Assuming teachers are stored in a file named "teachers.txt"
        List<Teacher> teachers = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("teachers.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String id = parts[0];
                    String username = parts[1];
                    String name = parts[2];
                    String password = parts[3];
                    String subject = parts[4];
                    Teacher teacher = new Teacher(id, username, name, password, subject);
                    teachers.add(teacher);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return teachers;
    }

    // Method to retrieve all student records
    private List<Student> getAllStudents() {
        // Assuming students are stored in a file named "students.txt"
        List<Student> students = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("students.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 7) {
                    String id = parts[0];
                    String username = parts[1];
                    String name = parts[2];
                    String password = parts[3];
                    String studentClass = parts[4];
                    String semester = parts[5];
                    String dob = parts[6];
                    // Assuming enrolled courses are stored in a file named "<id>_EnrolledCourses.txt"
                    List<Course> enrolledCourses = new ArrayList<>();
                    BufferedReader courseReader = new BufferedReader(new FileReader(id + "_courses.txt"));
                    String courseLine;
                    while ((courseLine = courseReader.readLine()) != null) {
                        Course course = new Course(courseLine, "", "", "","");
                        enrolledCourses.add(course);
                    }
                    courseReader.close();
                    Student student = new Student(id, username, name, password, studentClass, semester, dob, enrolledCourses);
                    students.add(student);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return students;
    }

    // Method to view administrator profile
    private void viewProfile() {
        String profileInfo = "Administrator Profile:\n" +
                "ID: " + id + "\n" +
                "Username: " + username + "\n" +
                "Name: " + name;
        JOptionPane.showMessageDialog(this, profileInfo, "Administrator Profile", JOptionPane.INFORMATION_MESSAGE);
    }
}
