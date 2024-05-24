package sms;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoadingScreen extends JFrame {

    private static final String IMAGE_PATH = "C:\\Users\\Arshia\\Pictures\\2a994e0c-777f-4259-b6b1-0f07ffab15f4.png"; // Replace with your image path

    private JLabel imageLabel;
    private JLabel loadingLabel;
    private Timer timer;
    private int angle = 0;
    private boolean loadingFinished = false;

    public LoadingScreen() {
        super("Student Management System");

        // Set BorderLayout for the frame
        getContentPane().setLayout(new BorderLayout());

        // Load the image
        ImageIcon imageIcon = new ImageIcon(IMAGE_PATH);
        imageLabel = new JLabel(imageIcon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center the image horizontally
        imageLabel.setVerticalAlignment(SwingConstants.CENTER); // Center the image vertically

        // Create loading label with spinning animation
        loadingLabel = new JLabel("Loading...Loading...");
        loadingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loadingLabel.setForeground(Color.BLACK); // Set text color to black

        // Add components to the frame
        getContentPane().add(imageLabel, BorderLayout.CENTER); // Add image label to the center of the frame
        getContentPane().add(loadingLabel, BorderLayout.SOUTH); // Add loading label to the bottom of the frame

        // Set frame properties
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit on close
        setLocationRelativeTo(null); // Center the window

        // Optional: Customize background color
        getContentPane().setBackground(new Color(146, 28, 185));

        // Create and start the loading animation timer
        timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                angle += 5; // Increment angle for spinning animation
                if (angle >= 360) {
                    angle = 0; // Reset angle if it exceeds 360 degrees
                }
                loadingLabel.setIcon(new RotatedIcon(new ImageIcon("spinner.png"), angle)); // Replace "spinner.png" with your spinner image

                if (loadingFinished) {
                    timer.stop(); // Stop the loading animation timer
                    loadingLabel.setText(""); // Clear the loading text
                }
            }
        });
        timer.start();
    }

    public void displayLoadingScreen() {
        setVisible(true); // Make the window visible
    }

    public void hideLoadingScreen() {
        setVisible(false); // Hide the window
        loadingFinished = true; // Set loading finished flag to true
    }

    public static void main(String[] args) throws InterruptedException {
        LoadingScreen system = new LoadingScreen();
        system.displayLoadingScreen();

        // Simulate some loading activity (replace with your actual loading logic)
        Thread.sleep(5000); // Simulate 5 seconds of loading

        system.hideLoadingScreen();
        AuthenticationGUI authenticationGUI = new AuthenticationGUI();
        authenticationGUI.setVisible(true);
    }

    // Helper class for rotating an icon
    static class RotatedIcon implements Icon {
        private final Icon icon;
        private final double angleInDegrees;

        public RotatedIcon(Icon icon, double angleInDegrees) {
            this.icon = icon;
            this.angleInDegrees = angleInDegrees;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.rotate(Math.toRadians(angleInDegrees), x + getIconWidth() / 2.0, y + getIconHeight() / 2.0);
            icon.paintIcon(c, g2, x, y);
            g2.dispose();
        }

        @Override
        public int getIconWidth() {
            return icon.getIconWidth();
        }

        @Override
        public int getIconHeight() {
            return icon.getIconHeight();
        }
    }
}
