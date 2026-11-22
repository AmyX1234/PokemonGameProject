package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class JPanel1 extends JPanel implements ActionListener {
    // Pokemon displayed on the title screen and their two animation frames.
    private static final String[] POKEMON = {"Squirtle", "Charmander", "Bulbasaur"};

    private final BufferedImage[][] pokemonImages = new BufferedImage[POKEMON.length][2];
    private final Timer timer;
    private int currentImageIndex;

    // Use the display's usable size so the title screen fits without clipping.
    public JPanel1(Dimension displaySize) {
        setPreferredSize(displaySize);
        setBackground(Color.BLACK);

        // Load both frames for each Pokemon from the project's Source Folder.
        try {
            for (int pokemonIndex = 0; pokemonIndex < POKEMON.length; pokemonIndex++) {
                for (int frameIndex = 0; frameIndex < pokemonImages[pokemonIndex].length; frameIndex++) {
                    String imageName = "Player/" + POKEMON[pokemonIndex] + "_down" + (frameIndex + 1) + "_16x16.png";
                    BufferedImage image = ImageIO.read(AssetLoader.getFile(imageName));
                    if (image == null) {
                        throw new IOException("Unsupported or invalid image: " + imageName);
                    }
                    pokemonImages[pokemonIndex][frameIndex] = image;
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load title screen Pokemon images.", e);
        }

        timer = new Timer(1000, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Derive text sizes and positions from the current panel size.
        int width = getWidth();
        int height = getHeight();
        int titleFontSize = Math.max(20, Math.min(width / 32, height / 12));
        int instructionFontSize = Math.max(16, Math.min(width / 48, height / 20));

        drawCenteredText(g2, "Welcome to Pokemon Game Project.", titleFontSize, height / 6);
        drawCenteredText(g2, "Click anywhere to start", instructionFontSize, height * 3 / 10);

        // Keep the Pokemon evenly spaced and preserve each image's aspect ratio.
        int spriteSize = Math.min(width / 7, height * 7 / 25);
        int spriteTop = height * 11 / 20;
        for (int i = 0; i < POKEMON.length; i++) {
            BufferedImage image = pokemonImages[i][currentImageIndex];
            double scale = Math.min((double) spriteSize / image.getWidth(),
                    (double) spriteSize / image.getHeight());
            int imageWidth = (int) (image.getWidth() * scale);
            int imageHeight = (int) (image.getHeight() * scale);
            int centerX = width * (i + 1) / (POKEMON.length + 1);
            int imageX = centerX - imageWidth / 2;
            int imageY = spriteTop + (spriteSize - imageHeight) / 2;

            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
            g2.drawImage(image, imageX, imageY, imageWidth, imageHeight, null);
        }

        g2.dispose();
    }

    private void drawCenteredText(Graphics2D g2, String text, int fontSize, int baseline) {
        g2.setFont(new Font(Font.SERIF, Font.BOLD, fontSize));
        g2.setColor(Color.WHITE);
        FontMetrics metrics = g2.getFontMetrics();
        g2.drawString(text, (getWidth() - metrics.stringWidth(text)) / 2, baseline);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Advance all Pokemon together to the next animation frame.
        currentImageIndex = (currentImageIndex + 1) % pokemonImages[0].length;
        repaint();
    }
}
