package com.zetcode;

import java.awt.event.KeyEvent;
import java.io.*;
import java.util.HashMap;

public class KeyConfigManager {

    public static void loadKeySet(Tetris parent) {
        HashMap<String, Integer> keyMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("keymappings.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    keyMap.put(parts[0].trim(), Integer.parseInt(parts[1].trim()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        parent.moveLeftKey = keyMap.getOrDefault("moveLeft", KeyEvent.VK_LEFT);
        parent.moveRightKey = keyMap.getOrDefault("moveRight", KeyEvent.VK_RIGHT);
        parent.rotateRightKey = keyMap.getOrDefault("rotateRight", KeyEvent.VK_DOWN);
        parent.rotateLeftKey = keyMap.getOrDefault("rotateLeft", KeyEvent.VK_UP);
        parent.dropDownKey = keyMap.getOrDefault("dropDown", KeyEvent.VK_SPACE);
        parent.oneLineDownKey = keyMap.getOrDefault("oneLineDown", KeyEvent.VK_D);
        parent.holdBlockKey = keyMap.getOrDefault("holdBlock", KeyEvent.VK_C);
        parent.pauseKey = keyMap.getOrDefault("pause", KeyEvent.VK_P);
    }

    public static void saveKeySet(Tetris parent) {
        HashMap<String, Integer> keyMap = new HashMap<>();
        keyMap.put("moveLeft", parent.moveLeftKey);
        keyMap.put("moveRight", parent.moveRightKey);
        keyMap.put("rotateRight", parent.rotateRightKey);
        keyMap.put("rotateLeft", parent.rotateLeftKey);
        keyMap.put("dropDown", parent.dropDownKey);
        keyMap.put("oneLineDown", parent.oneLineDownKey);
        keyMap.put("holdBlock", parent.holdBlockKey);
        keyMap.put("pause", parent.pauseKey);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("keymappings.txt"))) {
            for (String key : keyMap.keySet()) {
                writer.write(key + "=" + keyMap.get(key));
                writer.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
