package sms;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AuthenticationGUI extends JFrame {
    private JTextField textFieldUsername;
    private JPasswordField passwordField;
    private JButton btnTeacherLogin;
    private JButton btnStudentLogin;

    public AuthenticationGUI() {
        setBackground(new Color(0, 0, 0));
        getContentPane().setBackground(new Color(146, 28, 185));
        initialize();
    }

    private void initialize() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the window
        getContentPane().setLayout(null);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
        getContentPane().add(lblUsername);
        lblUsername.setBounds(479, 302, 80, 20);

        textFieldUsername = new JTextField();
        getContentPane().add(textFieldUsername);
        textFieldUsername.setColumns(10);
        textFieldUsername.setBounds(587, 303, 120, 20);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
        getContentPane().add(lblPassword);
        lblPassword.setBounds(479, 346, 80, 20);

        passwordField = new JPasswordField();
        getContentPane().add(passwordField);
        passwordField.setBounds(587, 347, 120, 20);

        JButton showPasswordButton = new JButton("Show Password");
        showPasswordButton.setFont(new Font("Tahoma", Font.PLAIN, 10));
        getContentPane().add(showPasswordButton);
        showPasswordButton.setBounds(587, 377, 120, 20);

        showPasswordButton.addActionListener(new ActionListener() {
            private boolean showingPassword = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (showingPassword) {
                    passwordField.setEchoChar('*');
                    showPasswordButton.setText("Show Password");
                } else {
                    passwordField.setEchoChar((char) 0);
                    showPasswordButton.setText("Hide Password");
                }
                showingPassword = !showingPassword;
            }
        });

        btnTeacherLogin = new JButton("Teacher Login");
        btnTeacherLogin.setForeground(new Color(255, 255, 255));
        btnTeacherLogin.setBackground(new Color(0, 0, 0));
        getContentPane().add(btnTeacherLogin);
        btnTeacherLogin.setBounds(396, 417, 120, 30);
        btnTeacherLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = textFieldUsername.getText();
                String password = new String(passwordField.getPassword());
                if (authenticate(username, password, "teacher")) {
                    openTeacherGUI();
                } else {
                    JOptionPane.showMessageDialog(AuthenticationGUI.this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton btnAdministratorLogin = new JButton("Administrator Login");
        btnAdministratorLogin.setForeground(new Color(255, 255, 255));
        btnAdministratorLogin.setBackground(new Color(0, 0, 0));
        getContentPane().add(btnAdministratorLogin);
        btnAdministratorLogin.setBounds(570, 417, 150, 30);
        btnAdministratorLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = textFieldUsername.getText();
                String password = new String(passwordField.getPassword());
                if (authenticate(username, password, "administrator")) {
                    openAdministratorGUI();
                } else {
                    JOptionPane.showMessageDialog(AuthenticationGUI.this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnStudentLogin = new JButton("Student Login");
        btnStudentLogin.setForeground(new Color(255, 255, 255));
        btnStudentLogin.setBackground(new Color(0, 0, 0));
        getContentPane().add(btnStudentLogin);
        btnStudentLogin.setBounds(774, 417, 120, 30);
        btnStudentLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = textFieldUsername.getText();
                String password = new String(passwordField.getPassword());
                if (authenticate(username, password, "student")) {
                    openCreateStudentAccountGUI();
                } else {
                    JOptionPane.showMessageDialog(AuthenticationGUI.this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JLabel lblStudentManagementSystem = new JLabel("STUDENT MANAGEMENT SYSTEM");
        lblStudentManagementSystem.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 40));
        getContentPane().add(lblStudentManagementSystem);
        lblStudentManagementSystem.setBounds(350, 48, 823, 49);
    }

    private boolean authenticate(String username, String password, String userType) {
        // Dummy authentication logic
        // Replace this with your actual authentication logic

        // Let's assume we have some hardcoded credentials for demonstration
        // In a real application, you would typically query a database or use some other authentication mechanism

        if (userType.equals("teacher")) {
            // Hardcoded teacher credentials
            String validUsername = "teacher";
            String validPassword = "teacher123";
            return username.equals(validUsername) && password.equals(validPassword);
        } else if (userType.equals("administrator")) {
            // Hardcoded administrator credentials
            String validUsername = "admin";
            String validPassword = "admin123";
            return username.equals(validUsername) && password.equals(validPassword);
        } else if (userType.equals("student")) {
            // Hardcoded student credentials
            String validUsername = "student";
            String validPassword = "student123";
            return username.equals(validUsername) && password.equals(validPassword);
        } else {
            // Invalid user type
            return false;
        }
    }

    private void openTeacherGUI() {
        // Open the Teacher GUI window
        CreateTeacherAccountGUI createteacheraccountGUI = new CreateTeacherAccountGUI();
        createteacheraccountGUI.setVisible(true);
        dispose(); // Close the login window
    }

    private void openAdministratorGUI() {
        // Open the Administrator GUI window
        CreateAdminAccountGUI createadminaccountGUI = new CreateAdminAccountGUI();
        createadminaccountGUI.setVisible(true);
        dispose(); // Close the login window
    }

    private void openCreateStudentAccountGUI() {
        // Open the Create Student Account GUI window
        CreateStudentAccountGUI createstudentaccountGUI = new CreateStudentAccountGUI();
        createstudentaccountGUI.setVisible(true);
        dispose(); // Close the login window
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AuthenticationGUI frame = new AuthenticationGUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
