package Main;

import javax.swing.JPanel;
import Entity.Player;
import Tile.TileManager;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable {
    private final int originalTileSize = 32; // each tile is 32x32 pixels without scale
    private final int tileScale = 2; // triples every 32x32 pixel tiles
    private final int tileSize = originalTileSize * tileScale; // actual tile size rendered is 96x96 pixels
    private final int maxScreenColumn = 16; // there is 16 tiles horizontally
    private final int maxScreenRow = 12; // there is 12 tiles vertically
    private final int screenWidth = tileSize * maxScreenColumn; // final resolution is 1536 pixels wide
    private final int screenHeight = tileSize * maxScreenRow; // final resolution is 1152 pixels tall
    private final int FPS = 60;

    Thread gameThread; // declare a thread instance
    KeyHandler keyHandler = new KeyHandler(); // instantiate a KeyHandler object
    TileManager tileManager = new TileManager(this); // instantiate a tile manager object
    Player player = new Player(this, keyHandler); // instantiate a player object

    public GamePanel () {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // sets the size to fit screen
        this.setBackground(Color.BLACK); // whole canvas is black by default
        this.addKeyListener(keyHandler); // call .addKeyListener() method pass our keyHandler
        this.setFocusable(true); // this tells the program to "focus" on receiving key presses
        this.setDoubleBuffered(true); // this method improves render performance
    }

    public void startGameThread () {
        gameThread = new Thread(this); // make a new thread when this method is called
        gameThread.start(); // run that thread's .start() method
    }

    @Override // override the run abstract method of the Runnable interface
    public void run () {
        double drawInterval = 1000000000 / FPS; // 1 billion nanoseconds (1 second) divided by 60, this simulates 60 actions in a second
        double delta = 0; // will keep track of how much time has passed
        long lastTime = System.nanoTime(); // stores the timestamp of the previous iteration
        long currentTime; // stores the timestamp of the current iteration

        while (gameThread != null) {
            currentTime = System.nanoTime(); // grab the current time of each loop
            delta += (currentTime - lastTime) / drawInterval; // add the elapsed time to delta (who keeps track of time)
            lastTime = currentTime; // the time of previous iteration now becomes the current time iteration
            if (delta >= 1) { // if delta has reached 1 second execute what's inside
                update(); // call .update() method
                repaint(); // call .repaint() method
                delta--; // delta was 1 so reset the time to 0
            }
        }
    }

    public void update () { // this method is responsible for updating all the entity coords
        player.update(); // when this method is run on the game loop it updates player's coords
    }

    public void paintComponent (Graphics graphics) {
        super.paintComponent(graphics); // call Graphics' class .paintComponent() method pass graphics
        Graphics2D graphics2D = (Graphics2D) graphics; // make a pointer of type Graphics2D from graphics but casted
        tileManager.draw(graphics2D); // draws background first before player
        player.draw(graphics2D); // call player's .draw() method and pass our pointer graphics2D
        graphics2D.dispose(); // dispose this graphics to save memory
    }

    public int getMaxScreenColumn () {
        return this.maxScreenColumn; // getter for maxScreenColumn
    }

    public int getMaxScreenRow () {
        return this.maxScreenRow; // getter for maxScreenRow
    }

    public int getTileSize () {
        return this.tileSize; // getter for tileSize
    }

    public int getScreenHeight () {
        return this.screenHeight; // getter for screenHeight
    }

    public int getScreenWidth () {
        return this.screenWidth; // getter for screenWidth
    }

    // no setters because these are final and we don't change them

}
