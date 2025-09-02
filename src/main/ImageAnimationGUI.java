package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ImageAnimationGUI extends JFrame {
    JPanel1 panel; // This panel will hold our content

    // Constructor for the ImageAnimationGUI class
    public ImageAnimationGUI(){
        panel = new JPanel1(); // Create a new panel
        panel.setBounds(0, 0, 1920, 1080); // Set its size

        // Display a welcome message
        JLabel welcomeLabel = new JLabel("Welcome to our ICS3U group project.");
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 40)); // Set font and size
        welcomeLabel.setBounds(650, 1, 1000, 200); // Position the message
        welcomeLabel.setForeground(Color.WHITE); // Set its color

        // Provide an instruction to start
        JLabel clickToStartLabel = new JLabel("Click anywhere to start");
        clickToStartLabel.setFont(new Font("Serif", Font.BOLD, 40)); // Set font and size
        clickToStartLabel.setForeground(Color.WHITE); // Set its color
        clickToStartLabel.setBounds(760, 100, 1000, 200); // Position the instruction

        // Set the background color of the panel
        panel.setBackground(Color.BLACK);

        // Add a listener to the panel to detect mouse clicks
        panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose(); // Close the window when clicked
            }
            // Required methods to implement MouseListener
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set the window closing behavior
        add(panel); // Add the panel to the window
        pack(); // Resize the window to fit its content

        setLocationRelativeTo(null); // Center the window on the screen
        setVisible(true); // Make the window visible

        // Add the labels to the panel
        panel.add(welcomeLabel);
        panel.add(clickToStartLabel);
    }
}