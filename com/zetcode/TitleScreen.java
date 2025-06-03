package com.zetcode;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class TitleScreen extends JPanel {
    private JPanel menuPanel;
    private JButton pressBtn;

    public TitleScreen(Tetris parent) {
        setLayout(new BorderLayout());

        // 타이틀
        JLabel title = new JLabel("TETRIS", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 36));
        add(title, BorderLayout.NORTH);

        // Press Start 버튼만 있는 패널
        pressBtn = new JButton("Press Start");
        pressBtn.setFont(new Font("Arial", Font.PLAIN, 24));
        pressBtn.addActionListener(e -> showMenu());

        JPanel initialPanel = new JPanel();
        initialPanel.add(pressBtn);
        add(initialPanel, BorderLayout.CENTER);

        // 메뉴 패널 숨김
        menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(4, 1, 10, 10));
        menuPanel.setVisible(false);  // Press 버튼을 누를 때 까지 보이지 않게

        JButton singleBtn = new JButton("1P Game");
        JButton secondPlayBtn = new JButton("2P Game");
        JButton loadBtn = new JButton("Load");
        JButton scoreRecBtn = new JButton("Score Record");
        JButton optionBtn = new JButton("Option");
        JButton exitBtn = new JButton("Exit");

        singleBtn.addActionListener(e -> parent.showGameScreen());
        secondPlayBtn.addActionListener(e -> parent.showBattleScreen());
        loadBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "로드 기능은 아직 구현안 됨."));
        scoreRecBtn.addActionListener(new ActionListener() {
        	
            public void actionPerformed(ActionEvent e) {
                ScoreManager.showScoreRecords(TitleScreen.this);
            }
        });

        optionBtn.addActionListener(e -> parent.showOptionScreen());            // 옵션 화면
        exitBtn.addActionListener(new ActionListener() {	
        	
            public void actionPerformed(ActionEvent e) {
                ScoreManager.clearAllScores();
                System.exit(0);
            }
        });


        for (JButton b : new JButton[]{singleBtn, secondPlayBtn, loadBtn, scoreRecBtn, optionBtn, exitBtn}) {
            b.setFont(new Font("Arial", Font.PLAIN, 18));
            menuPanel.add(b);
        }

        add(menuPanel, BorderLayout.SOUTH);
    }

    private void showMenu() {
        pressBtn.setVisible(false);  // Start 버튼 숨기기
        menuPanel.setVisible(true);     // 메뉴 버튼 표시
        revalidate();
        repaint();
    }
}
