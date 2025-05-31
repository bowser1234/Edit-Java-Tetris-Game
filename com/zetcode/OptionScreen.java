package com.zetcode;

import javax.swing.*;
import java.awt.*;

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
        JButton keySetBtn = new JButton("key setting");
        keySetBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "키설정 기능은 아직 구현 안됨."));
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
