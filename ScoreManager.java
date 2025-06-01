package com.zetcode;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ScoreManager {

    private static final String SCORE_FILE = "scores.txt"; //여기에 점수 저장

    public static void saveScore(int numLinesRemoved) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCORE_FILE, true))) {
            writer.write(String.valueOf(numLinesRemoved));
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showScoreRecords(Component parent) {
        List<String> scores = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(SCORE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                scores.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String message;
        if (scores.isEmpty()) {
            message = "아직 저장된 점수가 없습니다.";
        } else {
            message = "저장된 점수 목록:\n";
            for (int i = 0; i < scores.size(); i++) {
                message += (i + 1) + ". " + scores.get(i) + "줄\n";
            }
        }

        JOptionPane.showMessageDialog(parent, message, "점수 기록", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void clearAllScores() { //기록된 모든 점수 삭제
        File file = new File("scores.txt");
        if (file.exists()) {
            file.delete();
        }
    }

}

