package sms;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CommunicationSystem {

    JFrame frame;
    private JTabbedPane tabbedPane;
    private JPanel panelMessages;
    private JPanel panelNotifications;
    private JPanel panelDiscussions;

    private JTextArea textAreaMessages;
    private JTextArea textAreaDiscussions;
    private JList<String> listNotifications;
    private DefaultListModel<String> notificationsModel;

    private JTextField textFieldMessageInput;
    private JButton btnSendMessage;
    private JTextField textFieldDiscussionInput;
    private JButton btnPostDiscussion;

    private JComboBox<String> emojiComboBox;
    private JTextField textFieldRecipient;

    private String currentUserID; // Store the ID of the currently logged-in user
    private Map<String, JTextArea> messageAreas; // Stores message areas for different users
    private Map<String, File> messageHistoryFiles; // Stores message history files for different users
    private File notificationsFile; // File to store notifications

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                CommunicationSystem window = new CommunicationSystem("Alice"); // Example: Set user ID
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public CommunicationSystem(String userID) {
        currentUserID = userID;
        notificationsFile = new File(userID + "_notifications.txt"); // Initialize notificationsFile
        messageAreas = new HashMap<>(); // Initialize messageAreas map
        messageHistoryFiles = new HashMap<>(); // Initialize messageHistoryFiles map
        initialize();
        // Initialize message areas and files
        messageAreas.put(userID, textAreaMessages); // Example: Initialize for the current user
        messageHistoryFiles.put(userID, new File(userID + "_messages.txt")); // Example: Initialize file for the current user
    }

    private void initialize() {
        frame = new JFrame();
        
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBackground(new Color(128, 128, 255));
        frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
        // Messages panel
        panelMessages = new JPanel();
        tabbedPane.addTab("Messages", null, panelMessages, null);
        panelMessages.setLayout(new BorderLayout(0, 0));

        textAreaMessages = new JTextArea();
        textAreaMessages.setBackground(new Color(128, 128, 255));
        textAreaMessages.setEditable(false);
        panelMessages.add(new JScrollPane(textAreaMessages), BorderLayout.CENTER);

        JPanel panelMessageInput = new JPanel();
        panelMessages.add(panelMessageInput, BorderLayout.SOUTH);
        panelMessageInput.setLayout(new BorderLayout(0, 0));

        textFieldMessageInput = new JTextField();
        panelMessageInput.add(textFieldMessageInput, BorderLayout.CENTER);
        textFieldMessageInput.setColumns(10);

        JPanel panelMessageControls = new JPanel();
        panelMessageInput.add(panelMessageControls, BorderLayout.NORTH);
        panelMessageControls.setLayout(new BorderLayout(0, 0));

        emojiComboBox = new JComboBox<>(new String[]{"😀", "😂", "😍", "👍", "❤️", "🎉", "😢", "😎"});
        panelMessageControls.add(emojiComboBox, BorderLayout.WEST);
        emojiComboBox.addActionListener(e -> appendEmojiToMessage());

        textFieldRecipient = new JTextField();
        panelMessageControls.add(textFieldRecipient, BorderLayout.CENTER);

        btnSendMessage = new JButton("Send");
        panelMessageInput.add(btnSendMessage, BorderLayout.EAST);
        btnSendMessage.addActionListener(e -> sendMessage());

        textFieldMessageInput.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessage();
                }
            }
        });

        // Notifications panel
        panelNotifications = new JPanel();
        tabbedPane.addTab("Notifications", null, panelNotifications, null);
        panelNotifications.setLayout(new BorderLayout(0, 0));

        notificationsModel = new DefaultListModel<>();
        listNotifications = new JList<>(notificationsModel);
        listNotifications.setBackground(new Color(128, 128, 192));
        panelNotifications.add(new JScrollPane(listNotifications), BorderLayout.CENTER);

        // Discussions panel
        panelDiscussions = new JPanel();
        tabbedPane.addTab("Discussions", null, panelDiscussions, null);
        panelDiscussions.setLayout(new BorderLayout(0, 0));

        textAreaDiscussions = new JTextArea();
        textAreaDiscussions.setBackground(new Color(0, 128, 192));
        textAreaDiscussions.setEditable(false);
        panelDiscussions.add(new JScrollPane(textAreaDiscussions), BorderLayout.CENTER);

        JPanel panelDiscussionInput = new JPanel();
        panelDiscussions.add(panelDiscussionInput, BorderLayout.SOUTH);
        panelDiscussionInput.setLayout(new BorderLayout(0, 0));

        textFieldDiscussionInput = new JTextField();
        panelDiscussionInput.add(textFieldDiscussionInput, BorderLayout.CENTER);
        textFieldDiscussionInput.setColumns(10);

        JPanel panelDiscussionControls = new JPanel();
        panelDiscussionInput.add(panelDiscussionControls, BorderLayout.NORTH);
        panelDiscussionControls.setLayout(new BorderLayout(0, 0));

        emojiComboBox = new JComboBox<>(new String[]{"😀", "😂", "😍", "👍", "❤️", "🎉", "😢", "😎"});
        panelDiscussionControls.add(emojiComboBox, BorderLayout.WEST);
        emojiComboBox.addActionListener(e -> appendEmojiToDiscussion());

        btnPostDiscussion = new JButton("Post");
        panelDiscussionInput.add(btnPostDiscussion, BorderLayout.EAST);
        btnPostDiscussion.addActionListener(e -> postDiscussion());

        textFieldDiscussionInput.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    postDiscussion();
                }
            }
        });

        loadMessageHistory();
        loadNotifications();
    }

    private void appendEmojiToMessage() {
        String emoji = (String) emojiComboBox.getSelectedItem();
        textFieldMessageInput.setText(textFieldMessageInput.getText() + emoji);
    }

    private void appendEmojiToDiscussion() {
        String emoji = (String) emojiComboBox.getSelectedItem();
        textFieldDiscussionInput.setText(textFieldDiscussionInput.getText() + emoji);
    }

    private void sendMessage() {
        String message = textFieldMessageInput.getText().trim();
        String recipient = textFieldRecipient.getText().trim(); // Get recipient ID from text field
        if (!message.isEmpty() && !recipient.isEmpty()) {
            JTextArea recipientTextArea = messageAreas.get(recipient);
            if (recipientTextArea != null) {
                recipientTextArea.append("From " + currentUserID + ": " + message + "\n");
                saveMessageToFile(recipient, message);
            }
            textFieldMessageInput.setText("");
            addNotification("Message sent to " + recipient);
        }
    }

    private void postDiscussion() {
        String discussion = textFieldDiscussionInput.getText().trim();
        if (!discussion.isEmpty()) {
            textAreaDiscussions.append("You: " + discussion + "\n");
            textFieldDiscussionInput.setText("");
        }
    }

    private void addNotification(String notification) {
        notificationsModel.addElement(notification);
        saveNotificationToFile(notification);
    }

    private void saveMessageToFile(String recipient, String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(messageHistoryFiles.get(recipient), true))) {
            writer.write("From " + currentUserID + ": " + message + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveNotificationToFile(String notification) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(notificationsFile, true))) {
            writer.write(notification + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadMessageHistory() {
        for (String user : messageAreas.keySet()) {
            File file = messageHistoryFiles.get(user);
            if (file.exists()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        messageAreas.get(user).append(line + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void loadNotifications() {
        if (notificationsFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(notificationsFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    notificationsModel.addElement(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
