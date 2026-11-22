package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ImageAnimationGUI extends JFrame {
    private final JPanel1 panel;

    public ImageAnimationGUI() {
        // Use the usable display bounds, which exclude the taskbar.
        Rectangle displayBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        panel = new JPanel1(displayBounds.getSize());

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Close the title screen when the user clicks anywhere.
                dispose();
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // A borderless window uses the full usable display area for the title screen.
        setUndecorated(true);
        setContentPane(panel);
        setBounds(displayBounds);
        setVisible(true);
    }
}