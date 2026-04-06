package main;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
    final int originalTileSize = 16; // 16x16 pixels for all
    final int scale = 3; // 3x scale for screen

    public final int tileSize = originalTileSize * scale; // 48x48 actual size displayed
    public final int maxScreenColumn = 16; // EDITABLE 16 pieces of 48x48 tiles (width)
    public final int maxScreenRow = 12; // EDITABLE 12 pieces of 48x48 tiles (height)
    public final int screenWidth = tileSize * maxScreenColumn; // 768 pixels horizontally
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels vertically

    int FPS = 60;

    TileManager tileM = new TileManager (this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this, keyH);

    public GamePanel () {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread () {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override // when game thread is started it runs this method
    public void run () {
        // GAME LOOP - update info and repaint graphics by FPS 
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        
        while (gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }

        }
        
    }

    public void update () {

        player.update();
    }

    public void paintComponent (Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);
        player.draw(g2);
        g2.dispose();
    }

}
