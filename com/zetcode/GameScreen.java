package com.zetcode;

import java.awt.*;
import javax.swing.*;

public class GameScreen extends JPanel {

    private Board board;

    public GameScreen(Tetris parent) {
        setLayout(new BorderLayout());
        board = new Board(parent);
        add(board, BorderLayout.CENTER);
    }

    public void startGame() {
        board.start();
    }
}
