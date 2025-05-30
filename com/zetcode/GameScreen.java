package com.zetcode;

import java.awt.*;
import javax.swing.*;

public class GameScreen extends JPanel {

    private Board board;

    public GameScreen(Tetris parent) {
        setLayout(new GridBagLayout()); // GridBagLayout을 사용하여 중앙 정렬
        board = new Board(parent);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // 여백 설정
        add(board, gbc);
    }

    public void startGame() {
        board.setFocusable(true);
        board.requestFocusInWindow();
        board.start();
    }
}
