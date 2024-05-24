package sms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CreateStudentAccountGUI extends JFrame {
    private JTextField textFieldStudentID;
    private JTextField textFieldStudentUsername;
    private JTextField textFieldName;
    private JTextField textFieldClass;
    private JTextField textFieldSemester;
    private JTextField textFieldDOB;
    private JPasswordField passwordField;
    private JButton btnAction;
    private JButton btnSwitch;
    private JLabel lblStudentID;
    private JLabel lblName;
    private JLabel lblClass;
    private JLabel lblSemester;
    private JLabel lblDOB;

    private boolean isLoginMode = true;
    private GridBagConstraints gbc_1;
    private GridBagConstraints gbc_2;

    public CreateStudentAccountGUI() {
        getContentPane().setBackground(new Color(146, 28, 185));
        setBackground(new Color(128, 0, 255));
     // Maximize the window
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        initialize();
    }

    private void initialize() {
        setTitle("Student Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1365, 822);
        getContentPane().setLayout(new GridBagLayout());

        // Initialize GridBagConstraints for each component
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // lblStudentID
        lblStudentID = new JLabel("Student ID:");
        lblStudentID.setFont(new Font("Tahoma", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        getContentPane().add(lblStudentID, gbc);

        // textFieldStudentID
        textFieldStudentID = new JTextField();
        textFieldStudentID.setColumns(20);
        gbc = new GridBagConstraints(); // Create a new instance for each component
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        getContentPane().add(textFieldStudentID, gbc);

        // lblStudentUsername
        JLabel lblStudentUsername = new JLabel("Username:");
        lblStudentUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
        gbc = new GridBagConstraints(); // Create a new instance for each component
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        getContentPane().add(lblStudentUsername, gbc);

        // textFieldStudentUsername
        textFieldStudentUsername = new JTextField();
        textFieldStudentUsername.setColumns(20);
        gbc = new GridBagConstraints(); // Create a new instance for each component
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        getContentPane().add(textFieldStudentUsername, gbc);

        // lblName
        lblName = new JLabel("Name:");
        lblName.setFont(new Font("Tahoma", Font.PLAIN, 15));
        gbc = new GridBagConstraints(); // Create a new instance for each component
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        getContentPane().add(lblName, gbc);

        // textFieldName
        textFieldName = new JTextField();
        textFieldName.setColumns(20);
        gbc = new GridBagConstraints(); // Create a new instance for each component
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        getContentPane().add(textFieldName, gbc);

        // lblClass
        lblClass = new JLabel("Class:");
        lblClass.setFont(new Font("Tahoma", Font.PLAIN, 15));
        gbc = new GridBagConstraints(); // Create a new instance for each component
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        getContentPane().add(lblClass, gbc);

        // textFieldClass
        textFieldClass = new JTextField();
        textFieldClass.setColumns(20);
        gbc = new GridBagConstraints(); // Create a new instance for each component
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        getContentPane().add(textFieldClass, gbc);

        // lblSemester
        lblSemester = new JLabel("Semester:");
        lblSemester.setFont(new Font("Tahoma", Font.PLAIN, 15));
        gbc = new GridBagConstraints(); // Create a new instance for each component
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        getContentPane().add(lblSemester, gbc);

        // textFieldSemester
        textFieldSemester = new JTextField();
        textFieldSemester.setColumns(20);
        gbc = new GridBagConstraints(); // Create a new instance for each component
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        getContentPane().add(textFieldSemester, gbc);

        // lblDOB
        lblDOB = new JLabel("Date of Birth:");
        lblDOB.setFont(new Font("Tahoma", Font.PLAIN, 15));
        gbc = new GridBagConstraints(); // Create a new instance for each component
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        getContentPane().add(lblDOB, gbc);

        // textFieldDOB
        textFieldDOB = new JTextField();
        textFieldDOB.setColumns(20);
        gbc = new GridBagConstraints(); // Create a new instance for each component
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        getContentPane().add(textFieldDOB, gbc);

        // lblPassword
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
        gbc = new GridBagConstraints(); // Create a new instance for each component
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.EAST;
        getContentPane().add(lblPassword, gbc);

        // passwordField
        passwordField = new JPasswordField();
        passwordField.setColumns(20);
        gbc = new GridBagConstraints(); // Create a
    gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        getContentPane().add(passwordField, gbc);
                
                        // btnAction
                        btnAction = new JButton("Login");
                        btnAction.setForeground(new Color(255, 255, 255));
                        btnAction.setBackground(new Color(0, 0, 0));
                        gbc_1 = new GridBagConstraints(); // Create a new instance for each component
                        gbc_1.insets = new Insets(0, 0, 5, 0);
                        gbc_1.gridx = 1;
                        gbc_1.gridy = 7;
                        gbc_1.gridwidth = 2;
                        gbc_1.anchor = GridBagConstraints.CENTER;
                        getContentPane().add(btnAction, gbc_1);
                        btnAction.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                if (isLoginMode) {
                                    login();
                                } else {
                                    createAccount();
                                }
                            }
                        });
                
                        // btnSwitch
                        btnSwitch = new JButton("Switch to Create Account");
                        btnSwitch.setForeground(new Color(255, 255, 255));
                        btnSwitch.setBackground(new Color(0, 0, 0));
                        gbc_2 = new GridBagConstraints(); // Create a new instance for each component
                        gbc_2.insets = new Insets(0, 0, 5, 0);
                        gbc_2.gridx = 1;
                        gbc_2.gridy = 8;
                        gbc_2.gridwidth = 2;
                        gbc_2.anchor = GridBagConstraints.CENTER;
                        getContentPane().add(btnSwitch, gbc_2);
        btnSwitch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchMode();
            }
        });

        toggleFields(); // Call toggleFields() to initialize field visibility
    }

    private void switchMode() {
        isLoginMode = !isLoginMode;
        if (isLoginMode) {
            btnAction.setText("Login");
            btnSwitch.setText("Switch to Create Account");
        } else {
            btnAction.setText("Create Account");
            btnSwitch.setText("Switch to Login");
        }
        toggleFields();
    }

    private void toggleFields() {
        lblStudentID.setVisible(!isLoginMode);
        textFieldStudentID.setVisible(!isLoginMode);
        lblName.setVisible(!isLoginMode);
        textFieldName.setVisible(!isLoginMode);
        lblClass.setVisible(!isLoginMode);
        textFieldClass.setVisible(!isLoginMode);
        lblSemester.setVisible(!isLoginMode);
        textFieldSemester.setVisible(!isLoginMode);
        lblDOB.setVisible(!isLoginMode);
        textFieldDOB.setVisible(!isLoginMode);
    }

    private void login() {
        String username = textFieldStudentUsername.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        try (BufferedReader reader = new BufferedReader(new FileReader("students.txt"))) {
            String line;
            boolean isAuthenticated = false;
            String studentID = "";
            String name = "";
            String studentClass = "";
            String semester = "";
            String dob = "";

            while ((line = reader.readLine()) != null) {
                String[] studentData = line.split(",");
                if (studentData.length == 7 && studentData[1].equals(username) && studentData[3].equals(password)) {
                    studentID = studentData[0];
                    name = studentData[2];
                    studentClass = studentData[4];
                    semester = studentData[5];
                    dob = studentData[6];
                    isAuthenticated = true;
                    break;
                }
            }

            if (isAuthenticated) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                dispose();
                
                // Open the student GUI
                StudentGUI studentGUI = new StudentGUI(studentID, username, name, password, studentClass, semester, dob);
                studentGUI.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error reading student data", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void createAccount() {
        String studentID = textFieldStudentID.getText().trim();
        String username = textFieldStudentUsername.getText().trim();
        String name = textFieldName.getText().trim();
        String studentClass = textFieldClass.getText().trim();
        String semester = textFieldSemester.getText().trim();
        String dob = textFieldDOB.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (studentID.isEmpty() || username.isEmpty() || name.isEmpty() || studentClass.isEmpty() || semester.isEmpty() || dob.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Student student = new Student(studentID, username, name, password, studentClass, semester, dob, null);

        // Save student information to the file
        student.saveToFile("students.txt");

        JOptionPane.showMessageDialog(this, "Account created successfully!");
        dispose();
        
        // Open the student GUI
        StudentGUI studentGUI = new StudentGUI(studentID, username, name, password, studentClass, semester, dob);
        studentGUI.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CreateStudentAccountGUI frame = new CreateStudentAccountGUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
