import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Random;

public class Player extends Entity {
    GamePanel gamePanel;
    KeyHandler keyHandler;
    private boolean isMoving = false;
    private int pixelCounter = 0; // THIS VARIABLE IS IMPORTANT TO MAKE SURE WE GO EXACTLY ONE TILE EACH AWSD MOVEMENT
    private String direction = "down"; // DIRECTION THE PLAYER IS FACING

    public Player (GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        setX(0); // Y COORDINATE OF PLAYER AT SPAWN CHANGEABLE
        setY(0); // X COORDINATE OF PLAYER AT SPAWN CHANGEABLE
        setSpeed(4); // MOVEMENT SPEED IS 4 CHANGEABLE
        setAnimationSpeed(9); // DETERMINES ANIMATION SPEED
        getPlayerImage(); // LOAD ALL PLAYER SPRITES TO THE ARRAY
    }

    public void update () {
        if (isMoving == false) { // IF WE ARE NOT MOVING, DECIDE OUR CURRENT DIRECTION
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

        if (isMoving) { // IF WE ARE MOVING IGNORE INPUT UNTIL WE WALK EXACTLY ONE TILE (THIS DISABLES BEING 1/2 OR 1/3 INSIDE A TILE)
            if (direction.equals("up")) setY(getY() - getSpeed()); // IF W PRESSED MOVE Y UP
            if (direction.equals("down")) setY(getY() + getSpeed()); // IF S PRESSED MOVE Y DOWN
            if (direction.equals("left")) setX(getX() - getSpeed()); // IF A PRESSED MOVE X TO LEFT
            if (direction.equals("right")) setX(getX() + getSpeed()); // IF D PRESSED MOVE X TO RIGHT
            pixelCounter += getSpeed(); // INCREASE PIXELCOUNTER BY SPEED UNTIL IT HITS ONE TILE

            spriteCounter++;
            if (spriteCounter > getAnimationSpeed()) {
                spriteNumber++;
                if (spriteNumber > 3) spriteNumber = 0;
                spriteCounter = 0;
            }

            if (pixelCounter >= gamePanel.getTileSize()) { // IF THE PIXEL COUNTER IS NOW EXACTLY ONE TILE
                isMoving = false; // UNLOCK LISTENER AGAIN
                pixelCounter = 0; // RESET IT TO COUNT AGAIN

                //if (steppingOnTallGrass) {
                    //gamePanel.encounterManager.checkEncounter();
                //}
            }
        }
    }

    public void getPlayerImage () {
        try {
            downSprites[0] = ImageIO.read(getClass().getResourceAsStream("/Assets/Player/updown_0.png"));
            downSprites[1] = ImageIO.read(getClass().getResourceAsStream("/Assets/Player/updown_1.png"));
            downSprites[2] = ImageIO.read(getClass().getResourceAsStream("/Assets/Player/updown_2.png"));
            downSprites[3] = ImageIO.read(getClass().getResourceAsStream("/Assets/Player/updown_3.png"));
            upSprites[0] = ImageIO.read(getClass().getResourceAsStream("/Assets/Player/updown_4.png"));
            upSprites[1] = ImageIO.read(getClass().getResourceAsStream("/Assets/Player/updown_5.png"));
            upSprites[2] = ImageIO.read(getClass().getResourceAsStream("/Assets/Player/updown_6.png"));
            upSprites[3] = ImageIO.read(getClass().getResourceAsStream("/Assets/Player/updown_7.png"));  
            rightSprites[0] = ImageIO.read(getClass().getResourceAsStream("/Assets/Player/leftright_0.png"));
            rightSprites[1] = ImageIO.read(getClass().getResourceAsStream("/Assets/Player/leftright_1.png"));
            rightSprites[2] = ImageIO.read(getClass().getResourceAsStream("/Assets/Player/leftright_2.png"));
            rightSprites[3] = ImageIO.read(getClass().getResourceAsStream("/Assets/Player/leftright_3.png"));
            leftSprites[0] = ImageIO.read(getClass().getResourceAsStream("/Assets/Player/leftright_4.png"));
            leftSprites[1] = ImageIO.read(getClass().getResourceAsStream("/Assets/Player/leftright_5.png"));
            leftSprites[2] = ImageIO.read(getClass().getResourceAsStream("/Assets/Player/leftright_6.png"));
            leftSprites[3] = ImageIO.read(getClass().getResourceAsStream("/Assets/Player/leftright_7.png"));
        } catch (IOException ioE) {
            ioE.printStackTrace();
        }
    }

    public void draw (Graphics2D graphics2D) {
        BufferedImage image = null;
        switch (direction) {
            case "up" -> image = upSprites[spriteNumber];
            case "down" -> image = downSprites[spriteNumber];
            case "left" -> image = leftSprites[spriteNumber];
            case "right" -> image = rightSprites[spriteNumber];
        }
        graphics2D.drawImage(image, getX(), getY(), gamePanel.getTileSize(), gamePanel.getTileSize(), null);
    }

}
