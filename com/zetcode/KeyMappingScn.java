package com.zetcode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class KeyMappingScn extends JPanel {
    private Tetris parent;
    private String[] actions = { "Move Left", "Move Right", "Rotate Right", "Rotate Left",
                                 "Drop Down", "One Line Down", "Hold Block", "Pause" };
    // 각 action에 해당하는 키를 저장
    private HashMap<String, JButton> buttonMap = new HashMap<>();

    public KeyMappingScn(Tetris parent) {
        this.parent = parent;
        setLayout(new GridLayout(actions.length + 1, 2, 10, 10));

        for (String action : actions) {
            JLabel actionLabel = new JLabel(action, SwingConstants.RIGHT);
            JButton keyButton = new JButton(KeyEvent.getKeyText(parent.getKeyBinding(action)));

            // 마우스 왼클릭 시 새 키 설정 다이얼로그를 띄움
            keyButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        openKeyMappingDialog(action, keyButton);
                    }
                }
            });

            buttonMap.put(action, keyButton);
            add(actionLabel);
            add(keyButton);
        }

        // 저장 및 뒤로 가기 버튼
        JButton saveKeyMappinBtn = new JButton("Save and Back");
        saveKeyMappinBtn.addActionListener(e -> {
            KeyConfigManager.saveKeySet(parent);
            parent.showOptionScreen();
        });
        add(new JLabel()); // 빈 칸 (레이아웃 채우기)
        add(saveKeyMappinBtn);
    }

    private void openKeyMappingDialog(String action, JButton keyButton) {
        JDialog dialog = new JDialog((Frame) null, "Set Key for " + action, true);      // 새로운 다이얼로그 생성
        dialog.setSize(300, 150);
        dialog.setLayout(new BorderLayout());   // 레이아웃을 BorderLayout으로 설정하여 컴포넌트를 영역별로 배치할 수 있도록 함
        dialog.setLocationRelativeTo(this);     // 다이얼로그를 현재 창(this)의 중앙에 표시

        JLabel prompt = new JLabel("Press new key for: " + action, SwingConstants.CENTER);  // 변수 action에 해당하는 기능에 대해 새 키를 입력하라는 메시지를 중앙 정렬된 라벨로 생성
        dialog.add(prompt, BorderLayout.CENTER);

        // 다이얼로그가 키 입력을 받을 수 있도록 KeyListener 추가
        dialog.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int newKeyCode = e.getKeyCode();
                // 각 action 별로 Tetris 키 설정을 업데이트
                switch (action) {
                    case "Move Left":
                        parent.moveLeftKey = newKeyCode;
                        break;
                    case "Move Right":
                        parent.moveRightKey = newKeyCode;
                        break;
                    case "Rotate Right":
                        parent.rotateRightKey = newKeyCode;
                        break;
                    case "Rotate Left":
                        parent.rotateLeftKey = newKeyCode;
                        break;
                    case "Drop Down":
                        parent.dropDownKey = newKeyCode;
                        break;
                    case "One Line Down":
                        parent.oneLineDownKey = newKeyCode;
                        break;
                    case "Hold Block":
                        parent.holdBlockKey = newKeyCode;
                        break;
                    case "Pause":
                        parent.pauseKey = newKeyCode;
                        break;
                    default:
                        break;
                }
                // 버튼 글자 업데이트
                keyButton.setText(KeyEvent.getKeyText(newKeyCode));
                dialog.dispose();
            }
        });

        dialog.setFocusable(true);      // 다이얼로그를 포커스 받을 수 있게 설정 후 보이기
        dialog.setVisible(true);
    }
}
