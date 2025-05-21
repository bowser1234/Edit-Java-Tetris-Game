package com.zetcode;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;

/*
Java Tetris game clone

Author: Jan Bodnar
Website: https://zetcode.com
 */
public class Tetris extends JFrame {

    private JLabel statusbar;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public Tetris() {

        initUI();
    }

    private void initUI() {
        statusbar = new JLabel(" 0");

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        TitleScreen titleScreen = new TitleScreen(this);
        GameScreen gameScreen = new GameScreen(this);

        mainPanel.add(titleScreen, "Title");
        mainPanel.add(gameScreen, "Game");

        add(mainPanel);
        add(statusbar, BorderLayout.SOUTH);

        setSize(200, 400);
        setTitle("Tetris");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void showGameScreen() {
        cardLayout.show(mainPanel, "Game");
        ((GameScreen) mainPanel.getComponent(1)).startGame();
    }

    public JLabel getStatusBar() {
        return statusbar;
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {

            var game = new Tetris();
            game.setVisible(true);
        });
    }
}
