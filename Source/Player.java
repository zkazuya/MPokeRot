import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Random;

public class Player extends Entity {
    GamePanel gamePanel; // default access modifier we already have a gamepanel but we're overwriting it
    KeyHandler keyHandler; // to lessen having to write many code
    private boolean isMoving = false;
    private int pixelCounter = 0; // this is important to check if we moved one tile
    private String direction = "down";

    public Player (GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel; // constructors lel
        this.keyHandler = keyHandler; // constructors lol
        setX(0); // this is the spawn point coordinate horizontally
        setY(0); // for vertical, these are arbitrary btw
        setSpeed(4); // this is the movement speed
        setAnimationSpeed(9); //determines how fast the sprite animation will be
        getPlayerImage(); // load all the sprites to the array
    }

    public void update () {
        if (isMoving == false) { // if we are NOT moving, decide who to update
            if (keyHandler.getUpPressed()) {
                direction = "up";
                isMoving = true;
            } else if (keyHandler.getDownPressed()) {
                direction = "down";
                isMoving = true;
            } else if (keyHandler.getLeftPressed()) {
                direction = "left";
                isMoving = true;
            } else if (keyHandler.getRightPressed()) {
                direction = "right";
                isMoving = true;
            } else spriteNumber = 0;
        }

        if (isMoving) { // if we ARE moving, ignore inputs and force player to finish the step (this disables the player being inside 1/2 of a tile or 1/3 of a tile)
            if (direction.equals("up")) setY(getY() - getSpeed()); // if pressed w move y up
            if (direction.equals("down")) setY(getY() + getSpeed()); // if pressed s move y down
            if (direction.equals("left")) setX(getX() - getSpeed()); // if pressed a move x left
            if (direction.equals("right")) setX(getX() + getSpeed()); // if pressed d move x right
            pixelCounter += getSpeed(); // increase pixelCounter by speed until it is equal to one tile

            spriteCounter++;
            if (spriteCounter > getAnimationSpeed()) {
                spriteNumber++;
                if (spriteNumber > 3) spriteNumber = 0;
                spriteCounter = 0;
            }

            if (pixelCounter >= gamePanel.getTileSize()) { // if the pixel counter is exactly one tile
                isMoving = false; // unlock the listener again
                pixelCounter = 0; // reset it to count again
                //removed resetting the spriteNumber = 0 when going to a new tile size for more flowy animation
            }
            checkEncounter();
        }
    }

    public void getPlayerImage () {
        try {
        downSprites[0] = ImageIO.read(new FileInputStream("Assets/Player/updown_0.png"));
        downSprites[1] = ImageIO.read(new FileInputStream("Assets/Player/updown_1.png"));
        downSprites[2] = ImageIO.read(new FileInputStream("Assets/Player/updown_2.png"));
        downSprites[3] = ImageIO.read(new FileInputStream("Assets/Player/updown_3.png"));
        upSprites[0] = ImageIO.read(new FileInputStream("Assets/Player/updown_4.png"));
        upSprites[1] = ImageIO.read(new FileInputStream("Assets/Player/updown_5.png"));
        upSprites[2] = ImageIO.read(new FileInputStream("Assets/Player/updown_6.png"));
        upSprites[3] = ImageIO.read(new FileInputStream("Assets/Player/updown_7.png"));
        rightSprites[0] = ImageIO.read(new FileInputStream("Assets/Player/leftright_0.png"));
        rightSprites[1] = ImageIO.read(new FileInputStream("Assets/Player/leftright_1.png"));
        rightSprites[2] = ImageIO.read(new FileInputStream("Assets/Player/leftright_2.png"));
        rightSprites[3] = ImageIO.read(new FileInputStream("Assets/Player/leftright_3.png"));
        leftSprites[0] = ImageIO.read(new FileInputStream("Assets/Player/leftright_4.png"));
        leftSprites[1] = ImageIO.read(new FileInputStream("Assets/Player/leftright_5.png"));
        leftSprites[2] = ImageIO.read(new FileInputStream("Assets/Player/leftright_6.png"));
        leftSprites[3] = ImageIO.read(new FileInputStream("Assets/Player/leftright_7.png"));
        } catch (IOException ioE) {
            ioE.printStackTrace();
        }
    }

    public void draw (Graphics2D graphics2D) {
        BufferedImage image = null;
        switch (direction) {
            case "up": 
                image = upSprites[spriteNumber];
                break;
            case "down": 
                image = downSprites[spriteNumber];
                break;
            case "left":
                image = leftSprites[spriteNumber];
                break;
            case "right": 
                image = rightSprites[spriteNumber];
                break;
        }
        graphics2D.drawImage(image, getX(), getY(), gamePanel.getTileSize(), gamePanel.getTileSize(), null);
    }

    public void checkEncounter () {
        int tileSize = gamePanel.getTileSize(); // shortcut variable
        int centerX = x + (tileSize / 2); // pan center la ini hit x
        int centerY = y + (tileSize / 2); // pan center hit y
        // kay hit nahinanabo kasi kun waray ito ada hiya ha butnga hit 2x2 nga grid which is diri sentro..

        // ig coconvert ta hira into row and column nga coords
        int column = centerX / tileSize;
        int row = centerY / tileSize;
        int tileNumber = gamePanel.tileManager.getTileNumber(column, row); // find what tile this is at
        if (gamePanel.tileManager.getTile(tileNumber).getEncounter()) {
            Random random = new Random();
            int roll = random.nextInt(1000);
            if (roll < 10) { // 10% chance to find a pokerot in grass
                PokeRot wildEnemy = new PokeRot("Tralalelo Tralala", 30, 40);
                wildEnemy.addMove(new Move("Scratch", 8));

                PokeRot playerLead = gamePanel.playerParty[0];
                gamePanel.battleSystem.startEncounter(playerLead, wildEnemy);

                gamePanel.gameState = GameState.BATTLESTATE;
            }
        }
    }

}
