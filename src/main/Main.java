package main;

import main.GamePanel;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // to close window on exit
        window.setResizable(false);  // so can't resize
        window.setTitle("2D Adventure");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);  // add gamePanel to window

        window.pack();  // to see the game window

        window.setLocationRelativeTo(null);  // display window at center of screen
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();

    }
}
