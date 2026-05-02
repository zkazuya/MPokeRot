import javax.swing.JPanel;
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

    Thread gameThread;
    KeyHandler keyHandler = new KeyHandler();
    TileManager tileManager = new TileManager(this);
    Player player = new Player(this, keyHandler);
    BattleUI battleUI = new BattleUI(this);
    BattleSystem battleSystem = new BattleSystem(this);
    GameState gameState;
    Dialogue dialogue = new Dialogue(this);
    TitlePanel titlePanel = new TitlePanel(this);
    EncounterManager encounterManager = new EncounterManager(this);

    public PokeRot[] playerParty = new PokeRot[6];
    public PokeRot[] enemyParty = new PokeRot[6];

    public GamePanel () {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // sets the size to fit screen
        this.setBackground(Color.BLACK); // whole canvas is black by default
        this.addKeyListener(keyHandler); // call .addKeyListener() method pass our keyHandler
        this.setFocusable(true); // this tells the program to "focus" on receiving key presses
        this.setDoubleBuffered(true); // this method improves render performance
        playerParty[0] = new PokeRot("Bananini Chimpanzini", 70, 49); // 45 it hp pero gin bago ko laanay to 70
        enemyParty[0] = new PokeRot("Tung Tung Sahur", 39, 52);
        playerParty[1] = new PokeRot("Tralalelo Tralala", 44, 48);

        playerParty[0].addMove(new Move("Tackle", 7));
        playerParty[0].addMove(new Move("Growl", 0));
        enemyParty[0].addMove(new Move("Tackle", 7));
        gameState = GameState.TITLESCREEN; // by default game state is on ROAMSTATE
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
        } // this happens so fast that we can't even see it in action but it works
    }

    public void update () { // this method is responsible for updating all the entity coords
        if (gameState == GameState.ROAMSTATE) { // if current state is in roaming state keep calling these
            player.update(); // update player movement and draw
            encounterManager.update();
        } else if (gameState == GameState.BATTLESTATE) {
            battleSystem.update(); // enter battle system
        } else if (gameState == GameState.PAUSESTATE) {
            //nothing yet
        } else if (gameState == GameState.TALKINGSTATE) {
            dialogue.update(keyHandler);
        } else if (gameState == GameState.TITLESCREEN) {
            titlePanel.update(keyHandler);
        }
    }

    public void paintComponent (Graphics graphics) {
        super.paintComponent(graphics); // call Graphics' class .paintComponent() method pass graphics
        Graphics2D graphics2D = (Graphics2D) graphics; // make a pointer of type Graphics2D from graphics but casted
        
        if (gameState == GameState.ROAMSTATE) {
            tileManager.draw(graphics2D);
            player.draw(graphics2D);
        } else if (gameState == GameState.BATTLESTATE) {
            battleUI.drawBattleScreen(graphics2D);
        } else if (gameState == GameState.PAUSESTATE) {
            //nothing yet
        } else if (gameState == GameState.TALKINGSTATE) {
            tileManager.draw(graphics2D);
            player.draw(graphics2D);
            dialogue.draw(graphics2D);
        } else if (gameState == GameState.TITLESCREEN) {
            titlePanel.draw(graphics2D);
        }
        graphics2D.dispose(); // saves memory
    }

    public int getMaxScreenColumn () { return this.maxScreenColumn; }
    public int getMaxScreenRow () { return this.maxScreenRow; }
    public int getTileSize () { return this.tileSize; }
    public int getScreenHeight () { return this.screenHeight; }
    public int getScreenWidth () { return this.screenWidth; }
}
