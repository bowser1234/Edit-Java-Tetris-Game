package com.zetcode;

import java.awt.*;
import javax.swing.*;

public class Tetris extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JLabel statusbar;

    // 기본 키
    public int moveLeftKey;
    public int moveRightKey;
    public int rotateRightKey;
    public int rotateLeftKey;
    public int dropDownKey;
    public int oneLineDownKey;
    public int holdBlockKey;
    public int pauseKey;

    public Tetris() {
        initDefaultKeys();    // 기본 키 설정을 먼저 초기화
        initUI();
        KeyConfigManager.loadKeySet(this);    // 저장된 키 설정이 있으면 파일에서 불러오기
    }

    private void initUI() {
        statusbar = new JLabel(" 0");

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        TitleScreen titleScreen = new TitleScreen(this);
        GameScreen gameScreen = new GameScreen(this);
        OptionScreen optionScreen = new OptionScreen(this);
        Resolution resolutionScreen = new Resolution(this);
        KeyMappingScn keyMappingScn = new KeyMappingScn(this);


        mainPanel.add(titleScreen, "Title");
        mainPanel.add(gameScreen, "Game");
        mainPanel.add(optionScreen, "Option");
        mainPanel.add(resolutionScreen, "Resolution");
        mainPanel.add(keyMappingScn, "KeyMapping");

        add(mainPanel);
        add(statusbar, BorderLayout.SOUTH);

        setSize(1280, 720);
        setTitle("Tetris");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    
    // 초기 키 매핑
    private void initDefaultKeys() {
        moveLeftKey = KeyEvent.VK_LEFT;
        moveRightKey = KeyEvent.VK_RIGHT;
        rotateRightKey = KeyEvent.VK_DOWN;
        rotateLeftKey = KeyEvent.VK_UP;
        dropDownKey = KeyEvent.VK_SPACE;
        oneLineDownKey = KeyEvent.VK_D;
        holdBlockKey = KeyEvent.VK_C;
        pauseKey = KeyEvent.VK_P;
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

    // 화면 크기 설정 화면
    public void showResolutionScreen() {
        cardLayout.show(mainPanel, "Resolution");
    }

    // 키 설정 화면
    public void showKeyMappingScreen() {
        cardLayout.show(mainPanel, "KeyMapping");
    }

    // 현재 키 설정을 확인하기 위한 메서드 (OptionScreen에서 사용)
    public int getKeyBinding(String action) {
        switch (action) {
            case "Move Left":
                return moveLeftKey;
            case "Move Right":
                return moveRightKey;
            case "Rotate Right":
                return rotateRightKey;
            case "Rotate Left":
                return rotateLeftKey;
            case "Drop Down":
                return dropDownKey;
            case "One Line Down":
                return oneLineDownKey;
            case "Hold Block":
                return holdBlockKey;
            case "Pause":
                return pauseKey;
            default:
                return 0;
        }
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
