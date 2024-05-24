package sms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateAdminAccountGUI extends JFrame {
    private JTextField textFieldAdminID;
    private JTextField textFieldAdminUsername;
    private JTextField textFieldName;
    private JPasswordField passwordField;
    private JButton btnAction;
    private JButton btnSwitch;
    private JLabel lblAdminID;
    private JLabel lblName;

    private boolean isLoginMode = true;

    public CreateAdminAccountGUI() {
        getContentPane().setBackground(new Color(146, 28, 185));
        setBackground(new Color(128, 0, 255));
        initialize();
    }

    private void initialize() {
        setTitle("Admin Account Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the window
        getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        lblAdminID = new JLabel("Admin ID:");
        lblAdminID.setFont(new Font("Tahoma", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        getContentPane().add(lblAdminID, gbc);

        textFieldAdminID = new JTextField();
        textFieldAdminID.setColumns(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        getContentPane().add(textFieldAdminID, gbc);

        JLabel lblAdminUsername = new JLabel("Username:");
        lblAdminUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        getContentPane().add(lblAdminUsername, gbc);

        textFieldAdminUsername = new JTextField();
        textFieldAdminUsername.setColumns(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        getContentPane().add(textFieldAdminUsername, gbc);

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

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        getContentPane().add(lblPassword, gbc);

        passwordField = new JPasswordField();
        passwordField.setColumns(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        getContentPane().add(passwordField, gbc);

        // Set button background to black and foreground to white
        btnAction = new JButton("Login");
        btnAction.setBackground(Color.BLACK);
        btnAction.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 4;
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

        // Set button background to black and foreground to white
        btnSwitch = new JButton("Switch to Create Account");
        btnSwitch.setBackground(Color.BLACK);
        btnSwitch.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 5;
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
        lblAdminID.setVisible(!isLoginMode);
        textFieldAdminID.setVisible(!isLoginMode);
        lblName.setVisible(!isLoginMode);
        textFieldName.setVisible(!isLoginMode);
    }

    private void login() {
        String enteredUsername = textFieldAdminUsername.getText().trim();
        String enteredPassword = new String(passwordField.getPassword()).trim();

        // Load admin accounts from file
        List<Admin> admins = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("admins.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String id = parts[0];
                    String username = parts[1];
                    String name = parts[2];
                    String password = parts[3];
                    Admin admin = new Admin(id, username, name, password);
                    admins.add(admin);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading admin accounts", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if any admin account matches the entered credentials
        boolean loggedIn = false;
        for (Admin admin : admins) {
            if (admin.getUsername().equals(enteredUsername) && admin.getPassword().equals(enteredPassword)) {
                loggedIn = true;
                JOptionPane.showMessageDialog(this, "Login successful!");
                // Open AdminGUI if login is successful
                EventQueue.invokeLater(() -> {
                    try {
                        AdministratorGUI adminGUI = new AdministratorGUI(admin.getId(), admin.getUsername(), admin.getName(), admin.getPassword());
                        adminGUI.setVisible(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                // Close the CreateAdminAccountGUI
                dispose();
                break; // Exit loop once logged in
            }
        }

        // If login failed, display an error message
        if (!loggedIn) {
            JOptionPane.showMessageDialog(this, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void createAccount() {
        String adminID = textFieldAdminID.getText().trim();
        String username = textFieldAdminUsername.getText().trim();
        String name = textFieldName.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (adminID.isEmpty() || username.isEmpty() || name.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Admin admin = new Admin(adminID, username, name, password);

        // Save admin information to the file
        admin.saveToFile("admins.txt");

        JOptionPane.showMessageDialog(this, "Admin account created successfully!");

        // Open AdminGUI immediately after creating the admin account
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AdministratorGUI adminGUI = new AdministratorGUI(adminID, username, name, password);
                    adminGUI.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Close the CreateAdminAccountGUI
        dispose();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CreateAdminAccountGUI frame = new CreateAdminAccountGUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
