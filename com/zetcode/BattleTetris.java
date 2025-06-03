package com.zetcode;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class BattleTetris extends JPanel {
    private BattleTetrisPanel player1Panel;
    private BattleTetrisPanel player2Panel;
    private Tetris parent;

    public BattleTetris(Tetris parent) {
        this.parent = parent;
        setLayout(new GridLayout(1, 2));  // 두 게임판을 좌우로 배치

        // 플레이어 1: 방향키
        player1Panel = new BattleTetrisPanel(
                parent,
                Color.BLUE,
                KeyEvent.VK_LEFT,
                KeyEvent.VK_RIGHT,
                KeyEvent.VK_DOWN,
                KeyEvent.VK_UP
        );
        // 플레이어 2: WASD
        player2Panel = new BattleTetrisPanel(
                parent,
                Color.RED,
                KeyEvent.VK_A,
                KeyEvent.VK_D,
                KeyEvent.VK_S,
                KeyEvent.VK_W
        );

        add(player1Panel);
        add(player2Panel);
        
        setVisible(true);
    }

    private boolean gameOver = false; // 전체 게임 종료 여부를 저장

    public void handleGameOver() {
        if (!gameOver) { // 중복 실행 방지
            gameOver = true; // 게임 종료 상태 설정

            // 게임 루프 중지
            player1Panel.stopGame();
            player2Panel.stopGame();

            // 다이얼로그 표시
            Timer timer = new Timer(3000, e -> {
                int option = JOptionPane.showConfirmDialog(this, "타이틀 화면으로?", "End", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    parent.showTitleScreen();
                }
            });

            timer.setRepeats(false);
            timer.start();
        }
    }

}



class BattleTetrisPanel extends JPanel implements ActionListener {
    private final int ROWS = 20;
    private final int COLS = 10;
    private final int BLOCK_SIZE = 30;
    private int[][] board; // 0은 비어있음, 그 외는 블록의 RGB 값

    private Timer timer;
    private int timerDelay = 500;  // 500ms마다 게임 루프 실행

    private Tetris parent;


    // 각 플레이어의 조작 키
    private int keyLeft, keyRight, keyDown, keyRotate;

    // 현재 움직이는 테트로미노
    private Tetromino currentPiece;
    private int curRow = 0, curCol = COLS / 2;


    private Color pieceColor;
    private Random rand = new Random();

    public BattleTetrisPanel(Tetris parent, Color pieceColor, int keyLeft, int keyRight, int keyDown, int keyRotate) {
        this.parent = parent;
        this.pieceColor = pieceColor;
        this.keyLeft = keyLeft;
        this.keyRight = keyRight;
        this.keyDown = keyDown;
        this.keyRotate = keyRotate;

        setPreferredSize(new Dimension(COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE));
        board = new int[ROWS][COLS];

        // 포커스 전환을 막아 Key Bindings가 계속 동작하도록 함
        setFocusTraversalKeysEnabled(false);
        initKeyBindings();

        spawnPiece();
        timer = new Timer(timerDelay, this);
        timer.start();
        
    }

    // Key Bindings를 사용하여 윈도우 전체에서 해당 패널의 키 입력 처리
    private void initKeyBindings() {
        // 좌측 이동
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyLeft, 0), "moveLeft");
        getActionMap().put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (canMove(curRow, curCol - 1, currentPiece.getShape())) {
                    curCol--;
                    repaint();
                }
            }
        });

        // 우측 이동
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyRight, 0), "moveRight");
        getActionMap().put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (canMove(curRow, curCol + 1, currentPiece.getShape())) {
                    curCol++;
                    repaint();
                }
            }
        });

        // 하단 이동
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyDown, 0), "moveDown");
        getActionMap().put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (canMove(curRow + 1, curCol, currentPiece.getShape())) {
                    curRow++;
                    repaint();
                }
            }
        });

        // 회전
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyRotate, 0), "rotate");
        getActionMap().put("rotate", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[][] rotated = rotateMatrix(currentPiece.getShape());
                if (canMove(curRow, curCol, rotated)) {
                    currentPiece.setShape(rotated);
                    repaint();
                }
            }
        });
    }

    private boolean gameOver = false; // 게임 종료 여부를 저장하는 변수

    public void checkGameOver() {
        if (isGameOver() && !gameOver) { // 중복 실행 방지
            gameOver = true;  // 플레이어가 게임 오버 상태로 변경
            ((BattleTetris) getParent()).handleGameOver(); // 전체 게임을 중지 요청
        }
    }

    public void stopGame() {
        gameOver = true; // 개별적으로도 게임 루프를 멈춤
    }

    public boolean isGameOver() {
        // 최상단 줄에 블록이 있는 경우
        for (int c = 0; c < COLS; c++) {
            if (board[0][c] != 0) { 
                return true;
            }
        }

        // 새로운 테트로미노가 소환될 때 움직일 공간이 없는 경우
        int[][] shape = currentPiece.getShape();
        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[r].length; c++) {
                if (shape[r][c] != 0) { 
                    int boardRow = curRow + r;
                    int boardCol = curCol + c;
                    if (boardRow >= 0 && board[boardRow][boardCol] != 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }



    // 새로운 테트로미노 생성
    private void spawnPiece() {
        currentPiece = getRandomTetromino();
        int[][] shape = currentPiece.getShape();
        curRow = 0;
        curCol = COLS / 2 - shape[0].length / 2;

        // 새 테트로미노 생성 후 게임 오버 여부 확인
        checkGameOver();

    }

    // 무작위 테트로미노 반환
    private Tetromino getRandomTetromino() {
        int[][][] shapes = {
            { { 1, 1, 1, 1 } },              // ㅡ 블록
            { { 1, 1 }, { 1, 1 } },           // ㅁ 블록
            { { 0, 1, 0 }, { 1, 1, 1 } },      // ㅗ 블록
            { { 1, 0 }, { 1, 0 }, { 1, 1 } },  // L 블록
            { { 0, 1 }, { 0, 1 }, { 1, 1 } },  // J 블록
            { { 0, 1, 1 }, { 1, 1, 0 } },      // S 블록
            { { 1, 1, 0 }, { 0, 1, 1 } }       // Z 블록
        };

        Color[] colors = {
            Color.CYAN,
            Color.YELLOW,
            Color.MAGENTA,
            Color.ORANGE,
            Color.BLUE,
            Color.GREEN,
            Color.RED
        };

        int index = rand.nextInt(shapes.length);
        return new Tetromino(shapes[index], colors[index]);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 배경
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        // 보드에 고정된 블록 그리기
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (board[r][c] != 0) {
                    g.setColor(new Color(board[r][c]));
                    g.fillRect(c * BLOCK_SIZE, r * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                }
            }
        }

        // 현재 움직이는 테트로미노 그리기
        if (currentPiece != null) {
            g.setColor(currentPiece.getColor());
            int[][] shape = currentPiece.getShape();
            for (int r = 0; r < shape.length; r++) {
                for (int c = 0; c < shape[r].length; c++) {
                    if (shape[r][c] != 0) {
                        int x = (curCol + c) * BLOCK_SIZE;
                        int y = (curRow + r) * BLOCK_SIZE;
                        g.fillRect(x, y, BLOCK_SIZE, BLOCK_SIZE);
                    }
                }
            }
        }

        // 격자 선 그리기
        g.setColor(Color.DARK_GRAY);
        for (int i = 0; i <= ROWS; i++) {
            g.drawLine(0, i * BLOCK_SIZE, COLS * BLOCK_SIZE, i * BLOCK_SIZE);
        }
        for (int i = 0; i <= COLS; i++) {
            g.drawLine(i * BLOCK_SIZE, 0, i * BLOCK_SIZE, ROWS * BLOCK_SIZE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOver) {
            return; // 게임이 종료되었으면 루프를 중단
        }

        if (canMove(curRow + 1, curCol, currentPiece.getShape())) {
            curRow++;
        } else {
            fixToBoard();
            clearLines();
            spawnPiece();
            checkGameOver(); // 게임 오버 여부 확인
        }
        repaint();
    }

    // 조각 이동 가능 여부 확인
    private boolean canMove(int newRow, int newCol, int[][] shape) {
        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[r].length; c++) {
                if (shape[r][c] != 0) {
                    int boardRow = newRow + r;
                    int boardCol = newCol + c;
                    if (boardCol < 0 || boardCol >= COLS || boardRow >= ROWS)
                        return false;
                    if (boardRow >= 0 && board[boardRow][boardCol] != 0)
                        return false;
                }
            }
        }
        return true;
    }

    // 현재 조각을 보드에 고정
    private void fixToBoard() {
        int[][] shape = currentPiece.getShape();
        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[r].length; c++) {
                if (shape[r][c] != 0) {
                    int boardRow = curRow + r;
                    int boardCol = curCol + c;
                    if (boardRow >= 0 && boardRow < ROWS && boardCol >= 0 && boardCol < COLS) {
                        board[boardRow][boardCol] = currentPiece.getColor().getRGB();
                    }
                }
            }
        }
    }

    // 완성된 줄 삭제
    private void clearLines() {
        int linesCleared = 0;
        for (int r = 0; r < ROWS; r++) {
            boolean fullLine = true;
            for (int c = 0; c < COLS; c++) {
                if (board[r][c] == 0) {
                    fullLine = false;
                    break;
                }
            }
            if (fullLine) {
                linesCleared++;
                for (int row = r; row > 0; row--) {
                    board[row] = board[row - 1].clone();
                }
                board[0] = new int[COLS];
            }
        }
    }

    // 테트로미노 행렬 90도 회전
    private int[][] rotateMatrix(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        int[][] rotated = new int[m][n];
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                rotated[c][n - 1 - r] = matrix[r][c];
            }
        }
        return rotated;
    }
    
}

class Tetromino {
    private int[][] shape;
    private Color color;

    public Tetromino(int[][] shape, Color color) {
        this.shape = shape;
        this.color = color;
    }

    public int[][] getShape() {
        return shape;
    }

    public void setShape(int[][] shape) {
        this.shape = shape;
    }

    public Color getColor() {
        return color;
    }
}
