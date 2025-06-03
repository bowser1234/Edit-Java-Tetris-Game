package com.zetcode;

import javax.swing.*;
import java.awt.*;

public class Resolution extends JPanel {

    public Resolution(Tetris parent) {
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Resolution Set", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        add(label, BorderLayout.NORTH);

        String[] resolutions = {"800x720", "1024x768", "1280x720", "1920x1080"};
        JComboBox<String> resolutionDropdown = new JComboBox<>(resolutions);
        add(resolutionDropdown, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JButton applyBtn = new JButton("적용");
        JButton resbackBtn = new JButton("뒤로가기");

        applyBtn.addActionListener(e -> {
            String selected = (String) resolutionDropdown.getSelectedItem();
            String[] dims = selected.split("x");
            int width = Integer.parseInt(dims[0]);
            int height = Integer.parseInt(dims[1]);

            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (topFrame != null) {
                topFrame.setSize(width, height);
                topFrame.setLocationRelativeTo(null); // 화면을 가운데로 정렬
            }
        });

        bottomPanel.add(applyBtn);
        resbackBtn.addActionListener(e -> parent.showOptionScreen());
        bottomPanel.add(resbackBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}
