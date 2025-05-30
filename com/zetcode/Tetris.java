package com.zetcode;

import java.awt.*;
import javax.swing.*;

public class Tetris extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JLabel statusbar;

    public Tetris() {
        initUI();
    }

    private void initUI() {
        statusbar = new JLabel(" 0");

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        TitleScreen titleScreen = new TitleScreen(this);
        GameScreen gameScreen = new GameScreen(this);
        OptionScreen optionScreen = new OptionScreen(this);
        Resolution resolutionScreen = new Resolution(this);


        mainPanel.add(titleScreen, "Title");
        mainPanel.add(gameScreen, "Game");
        mainPanel.add(optionScreen, "Option");
        mainPanel.add(resolutionScreen, "Resolution");

        add(mainPanel);
        add(statusbar, BorderLayout.SOUTH);

        setSize(1280, 720);
        setTitle("Tetris");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    // 타이틀 화면
    public void showTitleScreen() {
        cardLayout.show(mainPanel, "Title");
    }

    // 게임 시작
    public void showGameScreen() {
        cardLayout.show(mainPanel, "Game");
        ((GameScreen) mainPanel.getComponent(1)).startGame();
    }

    // 옵션 화면
    public void showOptionScreen() {
        cardLayout.show(mainPanel, "Option");
    }

    public void showResolutionScreen() {
        cardLayout.show(mainPanel, "Resolution");
    }

    public JLabel getStatusBar() {
        return statusbar;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Tetris game = new Tetris();
            game.setVisible(true);
        });
    }
}
