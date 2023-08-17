package main;

import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 pixels for default size for characters and map tiles
    final int scale = 3;  // multiplyer scale for tiles

    public final int tileSize = originalTileSize * scale;  // 48x48 pixel size for assets
    public final int maxScreenCol = 16;  // max columns for screen
    public final int maxScreenRow = 12;  // max rows on screen
    public final int screenWidth = tileSize * maxScreenCol;  // 768 px
    public final int screenHeight = tileSize * maxScreenRow; // 576 px

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    // FPS
    int FPS = 60;

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;  // create a thread to have motion in the window
    public CollisionChecker cChecker = new CollisionChecker(this);
    public Player player = new Player(this, keyH);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);  // bg color
        this.setDoubleBuffered(true);  // can improve rendering performance
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this); // passing this GamePanel class
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS;  // using nanoseconds, 1 second drawInterval / FPS = 0.01666 seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        // create core game loop
        while (gameThread != null) {

            currentTime = System.nanoTime();  //current time in nanoseconds
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);  // add past time to the timer
            lastTime = currentTime;  // subtract last time from current time

            if(delta >= 1){
                update();  // Update information such as character position
                repaint();  // Draw the screen with the updated information
                delta--;
                drawCount++;
            }

            // display FPS on console
            if(timer >= 1000000000){
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);

        player.draw(g2);

        g2.dispose();
    }
}
