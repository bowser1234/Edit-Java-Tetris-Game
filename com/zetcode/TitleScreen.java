package com.zetcode;

import java.awt.*;
import javax.swing.*;

public class TitleScreen extends JPanel {

    public TitleScreen(Tetris parent) {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("TETRIS", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 36));
        add(title, BorderLayout.CENTER);

        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.PLAIN, 18));
        startButton.addActionListener(e->parent.showGameScreen());
        add(startButton, BorderLayout.SOUTH);
    }
}