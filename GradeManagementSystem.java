package sms;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GradeManagementSystem {

    JFrame frame;
    private JTextField textFieldStudentID;
    private JTextField textFieldAssignment;
    private JTextField textFieldQuiz;
    private JTextField textFieldExam;
    private JTextField textFieldCumulativeGrade;
    private JTable tableGrades;
    private DefaultTableModel tableModel;
    private List<Object[]> gradeData;
    private JLabel lblReportStatus;
    private JComboBox<String> comboBoxSubjects;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                GradeManagementSystem window = new GradeManagementSystem();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public GradeManagementSystem() {
        gradeData = new ArrayList<>();
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.getContentPane().setBackground(new Color(146, 28, 185));
        frame.setBounds(100, 100, 800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new GridBagLayout());

        // Setting up the panel for input fields
        JPanel panel = new JPanel();
        panel.setBackground(new Color(146, 28, 185));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbcPanel = new GridBagConstraints();
        gbcPanel.insets = new Insets(5, 5, 5, 5);
        gbcPanel.fill = GridBagConstraints.HORIZONTAL;
        gbcPanel.gridx = 0;
        gbcPanel.gridy = 0;
        gbcPanel.weightx = 1;
        gbcPanel.weighty = 0;
        frame.getContentPane().add(panel, gbcPanel);

        // Adding components to the panel
        GridBagConstraints gbc;

        JLabel lblStudentID = new JLabel("Student ID:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(lblStudentID, gbc);

        textFieldStudentID = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(textFieldStudentID, gbc);
        textFieldStudentID.setColumns(10);

        JLabel lblAssignment = new JLabel("Assignment Marks:");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(lblAssignment, gbc);

        textFieldAssignment = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(textFieldAssignment, gbc);
        textFieldAssignment.setColumns(10);

        JLabel lblQuiz = new JLabel("Quiz Marks:");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(lblQuiz, gbc);

        textFieldQuiz = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(textFieldQuiz, gbc);
        textFieldQuiz.setColumns(10);

        JLabel lblExam = new JLabel("Exam Marks:");
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(lblExam, gbc);

        textFieldExam = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 7;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(textFieldExam, gbc);
        textFieldExam.setColumns(10);

        JLabel lblCumulativeGrade = new JLabel("Cumulative Grade:");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(lblCumulativeGrade, gbc);

        textFieldCumulativeGrade = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(textFieldCumulativeGrade, gbc);
        textFieldCumulativeGrade.setColumns(10);

        JButton btnAddGrade = new JButton("Add Grade");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(btnAddGrade, gbc);

        JLabel lblSubject = new JLabel("Subject:");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(lblSubject, gbc);

        comboBoxSubjects = new JComboBox<>(new String[]{"Math", "Science", "History", "English"});
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(comboBoxSubjects, gbc);

        // Setting up the table and scroll pane
        JScrollPane scrollPane = new JScrollPane();
        GridBagConstraints gbcScrollPane = new GridBagConstraints();
        gbcScrollPane.gridx = 0;
        gbcScrollPane.gridy = 1;
        gbcScrollPane.gridwidth = 1;
        gbcScrollPane.weightx = 1;
        gbcScrollPane.weighty = 1;
        gbcScrollPane.fill = GridBagConstraints.BOTH;
        frame.getContentPane().add(scrollPane, gbcScrollPane);

        tableGrades = new JTable();
        tableModel = new DefaultTableModel(new Object[][]{}, new String[]{"Student ID", "Assignment Marks", "Quiz Marks", "Exam Marks", "Cumulative Grade", "Subject"});
        tableGrades.setModel(tableModel);
        scrollPane.setViewportView(tableGrades);

        lblReportStatus = new JLabel("");
        GridBagConstraints gbcReportStatus = new GridBagConstraints();
        gbcReportStatus.gridx = 0;
        gbcReportStatus.gridy = 2;
        gbcReportStatus.weightx = 1;
        gbcReportStatus.weighty = 0;
        gbcReportStatus.anchor = GridBagConstraints.WEST;
        frame.getContentPane().add(lblReportStatus, gbcReportStatus);

        JButton btnGenerateReport = new JButton("Generate Report");
        GridBagConstraints gbcBtnGenerateReport = new GridBagConstraints();
        gbcBtnGenerateReport.gridx = 0;
        gbcBtnGenerateReport.gridy = 3;
        frame.getContentPane().add(btnGenerateReport, gbcBtnGenerateReport);

        btnAddGrade.addActionListener(e -> {
            try {
                String studentID = textFieldStudentID.getText();
                String assignment = textFieldAssignment.getText();
                String quiz = textFieldQuiz.getText();
                String exam = textFieldExam.getText();
                String cumulativeGrade = textFieldCumulativeGrade.getText();
                String subject = comboBoxSubjects.getSelectedItem().toString();

                if (studentID.isEmpty() || assignment.isEmpty() || quiz.isEmpty() || exam.isEmpty() || cumulativeGrade.isEmpty() || subject.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in all fields", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Object[] rowData = {studentID, assignment, quiz, exam, cumulativeGrade, subject};
                tableModel.addRow(rowData);
                gradeData.add(rowData);

                // Clear fields after adding the grade
                textFieldStudentID.setText("");
                textFieldAssignment.setText("");
                textFieldQuiz.setText("");
                textFieldExam.setText("");
                textFieldCumulativeGrade.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error adding grade: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnGenerateReport.addActionListener(e -> generateReport());
    }

    private void generateReport() {
        try {
            // Check if any grades are added
            if (gradeData.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No grades added yet.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Get the first Student ID from the data
            String studentID = gradeData.get(0)[0].toString();

            // Create the file name with Student ID
            String fileName = studentID + "_grades_report.txt";

            // Create the FileWriter
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

            // Write the data to the file
            for (Object[] rowData : gradeData) {
                StringBuilder line = new StringBuilder();
                for (int i = 0; i < rowData.length; i++) {
                    String label;
                    switch (i) {
                        case 0:
                            label = "Student ID";
                            break;
                        case 1:
                            label = "Assignment Marks";
                            break;
                        case 2:
                            label = "Quiz Marks";
                            break;
                        case 3:
                            label = "Exam Marks";
                            break;
                        case 4:
                            label = "Cumulative Grade";
                            break;
                        case 5:
                            label = "Subject";
                            break;
                        default:
                            label = "";
                            break;
                    }
                    if (line.length() > 0) {
                        line.append(", ");
                    }
                    line.append(label).append(": ").append(rowData[i]);
                }
                line.append("\n");
                writer.write(line.toString());
            }

            // Close the writer
            writer.close();

            lblReportStatus.setText("Report saved successfully to " + fileName);
        } catch (IOException ex) {
            lblReportStatus.setText("Error saving report: " + ex.getMessage());
        }
    }

}
