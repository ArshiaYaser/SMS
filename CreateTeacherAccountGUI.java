package sms;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CreateTeacherAccountGUI extends JFrame {
    private JTextField textFieldTeacherID;
    private JTextField textFieldTeacherUsername;
    private JTextField textFieldName;
    private JTextField textFieldSubject;
    private JPasswordField passwordField;
    private JButton btnAction;
    private JButton btnSwitch;
    private JLabel lblTeacherID;
    private JLabel lblName;
    private JLabel lblSubject;

    private boolean isLoginMode = true;

    public CreateTeacherAccountGUI() {
        getContentPane().setBackground(new Color(146, 28, 185));
        setBackground(new Color(146, 28, 185));
        initialize();
    }

    private void initialize() {
        setTitle("Teacher Account Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new GridBagLayout());
     // Maximize the window
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        lblTeacherID = new JLabel("Teacher ID:");
        lblTeacherID.setFont(new Font("Tahoma", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        getContentPane().add(lblTeacherID, gbc);

        textFieldTeacherID = new JTextField();
        textFieldTeacherID.setColumns(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        getContentPane().add(textFieldTeacherID, gbc);

        JLabel lblTeacherUsername = new JLabel("Username:");
        lblTeacherUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        getContentPane().add(lblTeacherUsername, gbc);

        textFieldTeacherUsername = new JTextField();
        textFieldTeacherUsername.setColumns(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        getContentPane().add(textFieldTeacherUsername, gbc);

        lblName = new JLabel("Name:");
        lblName.setFont(new Font("Tahoma", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        getContentPane().add(lblName, gbc);

        textFieldName = new JTextField();
        textFieldName.setColumns(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        getContentPane().add(textFieldName, gbc);

        lblSubject = new JLabel("Subject:");
        lblSubject.setFont(new Font("Tahoma", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        getContentPane().add(lblSubject, gbc);

        textFieldSubject = new JTextField();
        textFieldSubject.setColumns(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        getContentPane().add(textFieldSubject, gbc);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        getContentPane().add(lblPassword, gbc);

        passwordField = new JPasswordField();
        passwordField.setColumns(20);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        getContentPane().add(passwordField, gbc);

        btnAction = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        getContentPane().add(btnAction, gbc);
        btnAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isLoginMode) {
                    login();
                } else {
                    createAccount();
                }
            }
        });

        btnSwitch = new JButton("Switch to Create Account");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        getContentPane().add(btnSwitch, gbc);
        btnSwitch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchMode();
            }
        });

        toggleFields();
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
        lblTeacherID.setVisible(!isLoginMode);
        textFieldTeacherID.setVisible(!isLoginMode);
        lblName.setVisible(!isLoginMode);
        textFieldName.setVisible(!isLoginMode);
        lblSubject.setVisible(!isLoginMode);
        textFieldSubject.setVisible(!isLoginMode);
    }

    private void login() {
        String username = textFieldTeacherUsername.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        try (BufferedReader reader = new BufferedReader(new FileReader("teachers.txt"))) {
            String line;
            boolean isAuthenticated = false;
            String teacherID = "";
            String name = "";
            String subject = "";

            while ((line = reader.readLine()) != null) {
                String[] teacherData = line.split(",");
                if (teacherData.length == 5 && teacherData[1].equals(username) && teacherData[3].equals(password)) {
                    teacherID = teacherData[0];
                    name = teacherData[2];
                    subject = teacherData[4];
                    isAuthenticated = true;
                    break;
                }
            }

            if (isAuthenticated) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                dispose();
                
                // Open the teacher GUI
                TeacherGUI teacherGUI = new TeacherGUI(teacherID, username, name, password, subject);
                teacherGUI.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error reading teacher data", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createAccount() {
        String teacherID = textFieldTeacherID.getText().trim();
        String username = textFieldTeacherUsername.getText().trim();
        String name = textFieldName.getText().trim();
        String subject = textFieldSubject.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (teacherID.isEmpty() || username.isEmpty() || name.isEmpty() || subject.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Teacher teacher = new Teacher(teacherID, username, name, password, subject);

        // Save teacher information to the file
        teacher.saveToFile("teachers.txt");

        JOptionPane.showMessageDialog(this, "Teacher account created successfully!");

        // After successfully creating the teacher account, open the TeacherGUI
        TeacherGUI teacherGUI = new TeacherGUI(teacherID, username, name, password, subject);
        teacherGUI.setVisible(true);

        // Close the current window
        dispose();
    }
   


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CreateTeacherAccountGUI frame = new CreateTeacherAccountGUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
