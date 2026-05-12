

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    private final int originalTileSize = 32; // each tile is 32x32 pixels without scale
    private final int tileScale = 2; // triples every 32x32 pixel tiles
    private final int tileSize = originalTileSize * tileScale; // actual tile size rendered is 96x96 pixels
    private final int playerScale = 3; //pinalaki yung player
    private final int playerSize = (32 * playerScale) - originalTileSize/2; //pinalaki yung player
    private final int maxScreenColumn = 16; // there is 16 tiles horizontally
    private final int maxScreenRow = 12; // there is 12 tiles vertically
    private final int screenWidth = tileSize * maxScreenColumn; // final resolution is 1536 pixels wide
    private final int screenHeight = tileSize * maxScreenRow; // final resolution is 1152 pixels tall
    private final int maxWorldColumn = 150; // TOTAL COLUMNS WIDE THE MAP
    private final int maxWorldRow = 100; // TOTAL ROW WIDE THE MAP
    private final int FPS = 60;
    private double zoom = 1.0;
    private boolean zooming;
    private float fadeAlpha = 0f;

    private int worldX;
    private int worldY;

    public GameFrame frame;
    public Thread gameThread;
    public KeyHandler keyHandler = new KeyHandler(this);
    public TileManager tileManager = new TileManager(this);
    public PokeRotRegistry pokeRotRegistry = new PokeRotRegistry();
    public Player player = new Player(this, keyHandler);
    public BattleUI battleUI = new BattleUI(this);
    public BattleSystem battleSystem = new BattleSystem(this);
    public GameState gameState;
    public Dialogue dialogue = new Dialogue(this);
    public TitlePanel titlePanel = new TitlePanel(this);
    public Pause pauseClass = new Pause(this);
    public PokeRotStats pokerotStats = new PokeRotStats(this, pauseClass);
    public EncounterManager encounterManager = new EncounterManager(this, player);
    public NPCManager npcManager = new NPCManager(this);
    public StarterSelection starterSelection = new StarterSelection(this);

    public GamePanel (GameFrame frame) {
        this.frame = frame;
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // sets the size to fit screen
        this.setBackground(Color.BLACK); // whole canvas is black by default
        this.addKeyListener(keyHandler); // call .addKeyListener() method pass our keyHandler
        this.setFocusable(true); // this tells the program to "focus" on receiving key presses
        this.setDoubleBuffered(true); // this method improves render performance
        gameState = GameState.TITLESCREEN; // by default game state is on ROAMSTATE
        
        npcManager.setUpNPC();
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
            pauseClass.update(keyHandler);
        } else if (gameState == GameState.BATTLESTATE) {
            battleSystem.update(); // enter battle system
        } else if (gameState == GameState.PAUSESTATE) {;
            pauseClass.update(keyHandler);
        } else if (gameState == GameState.TALKINGSTATE) {
            dialogue.update(keyHandler);
        } else if (gameState == GameState.TITLESCREEN) {
            titlePanel.update(keyHandler);
        } else if (gameState == GameState.STARTERSTATE) {
            starterSelection.update(keyHandler);
        } else if (gameState == GameState.TRANSITIONSTATE){
            if (zooming) {
                zoom += 0.03;
                 fadeAlpha += 0.02f;

                 fadeAlpha = Math.max(0f, Math.min(1f, fadeAlpha)); //controls the transparancy
                if (zoom >= 3.0){
                    zoom = 3.0;

                    gameState = GameState.BATTLESTATE;
                    //SoundHelper.playSound("Asset/Music/FightStart.wav");

                    zooming = false;

                    fadeAlpha = 0f;
                }
            }
        }
    }

    public void paintComponent (Graphics graphics) {
        super.paintComponent(graphics); // call Graphics' class .paintComponent() method pass graphics
        Graphics2D graphics2D = (Graphics2D) graphics; // make a pointer of type Graphics2D from graphics but casted
        
        if (gameState == GameState.ROAMSTATE) {
            tileManager.draw(graphics2D);
            npcManager.draw(graphics2D);
            player.draw(graphics2D);
        } else if (gameState == GameState.BATTLESTATE) {
            battleUI.drawBattleScreen(graphics2D);
        } else if (gameState == GameState.PAUSESTATE) {
            
            tileManager.draw(graphics2D);
            player.draw(graphics2D);
            npcManager.draw(graphics2D);
            pauseClass.draw(graphics2D); 
        } else if (gameState == GameState.TALKINGSTATE) {
            
            tileManager.draw(graphics2D);
            player.draw(graphics2D);
            npcManager.draw(graphics2D);
            dialogue.draw(graphics2D);
        } else if (gameState == GameState.TITLESCREEN) {
            titlePanel.draw(graphics2D);
        } else if (gameState == GameState.STARTERSTATE) {
            starterSelection.draw(graphics2D);
        } else if (gameState == GameState.TRANSITIONSTATE){
            graphics2D.scale(zoom, zoom);
            //This is makes it so that the zoom will be at the center of the screen and not top left
            double offSetX = (screenWidth - screenWidth * zoom) / 2;
            double offSetY = (screenHeight - screenHeight * zoom) / 2;

            graphics2D.translate(offSetX / zoom, offSetY / zoom);   
            tileManager.draw(graphics2D);
            npcManager.draw(graphics2D);
            player.draw(graphics2D);

            graphics2D.setColor(new Color(0, 0 , 0, (int)(fadeAlpha * 255)));
            graphics2D.fillRect(0, 0, screenWidth, screenHeight);
            graphics2D.setTransform(new AffineTransform());
        }
        graphics2D.dispose(); // saves memory
    }

    public int getMaxScreenColumn () { return this.maxScreenColumn; }
    public int getMaxScreenRow () { return this.maxScreenRow; }
    public int getTileSize () { return this.tileSize; }
    public int getScreenHeight () { return this.screenHeight; }
    public int getScreenWidth () { return this.screenWidth; }
    public int getWorldX () { return this.worldX; }
    public int getWorldY () { return this.worldY; }
    public int getMaxWorldColumn () { return this.maxWorldColumn; }
    public int getMaxWorldRow () { return this.maxWorldRow; }
    public int getPlayerSize() { return this.playerSize; }
    public Player getPlayer() { return this.player; }

    
    public void restartGame(){
        player = new Player(this, keyHandler);
    }

    public void startTransition(){
       // SoundHelper.playSound("Assets/Music/fight.wav");
        gameState = GameState.TRANSITIONSTATE;

        zoom = 1.0;
        zooming = true;
    }

}
