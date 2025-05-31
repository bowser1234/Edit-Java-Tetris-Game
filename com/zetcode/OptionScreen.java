package com.zetcode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class OptionScreen extends JPanel {

    public OptionScreen(Tetris parent) {
        setLayout(new GridLayout(3, 1, 10, 10));

        JLabel label = new JLabel("Option Screen", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        add(label);

        // 설정
        JCheckBox musicCheckbox = new JCheckBox("Enable Music");        // 음악 설정
        musicCheckbox.setSelected(true);
        add(musicCheckbox);

        // 조작키 설정
        JButton keySetBtn = new JButton("Key Setting");            // 조작키 설정 버튼
        keySetBtn.addActionListener(e -> {
            String[] actions = {"Move Left", "Move Right", "Rotate", "Soft Drop"};
            int[] keyBindings = new int[actions.length];
        
            for (int i = 0; i < actions.length; i++) {
                final int index = i;
        
                JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this),
                                             "Press a Key for " + actions[i], true);
                dialog.setLayout(new BorderLayout());
        
                JLabel msg = new JLabel("Press a key for '" + actions[i] + "'", SwingConstants.CENTER);
                msg.setFont(new Font("Arial", Font.PLAIN, 16));
                dialog.add(msg, BorderLayout.CENTER);
        
                dialog.setSize(300, 150);
                dialog.setLocationRelativeTo(this);
        
                // 키 리스너 추가
                dialog.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        keyBindings[index] = e.getKeyCode();
                        dialog.dispose();
                    }
                });
        
                dialog.setFocusable(true);           // 다이얼로그를 포커스 받을 수 있게 설정 후 보이기
                dialog.setVisible(true);
                dialog.requestFocusInWindow();
            }
        
            moveLeftKey = keyBindings[0];               // 설정 저장
            moveRightKey = keyBindings[1];
            rotateKey = keyBindings[2];
            softDropKey = keyBindings[3];
        
            // 설정 결과 출력
            String result = String.format(
                "Controls set:\n- Move Left: %s\n- Move Right: %s\n- Rotate: %s\n- Soft Drop: %s",
                KeyEvent.getKeyText(moveLeftKey),
                KeyEvent.getKeyText(moveRightKey),
                KeyEvent.getKeyText(rotateKey),
                KeyEvent.getKeyText(softDropKey)
            );
        
            JOptionPane.showMessageDialog(this, result);
        });
        add(keySetBtn);
        
        // 화면 해상도 설정
        JButton winResizeSetBtn = new JButton("win size setting");
        winResizeSetBtn.addActionListener(e -> parent.showResolutionScreen());
        add(winResizeSetBtn);


        // 돌아가기 버튼
        JButton backButton = new JButton("Return");
        backButton.addActionListener(e -> parent.showTitleScreen());
        add(backButton);
    }
}
