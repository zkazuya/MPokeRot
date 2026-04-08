package Entity;

import Main.GamePanel;
import Main.KeyHandler;
import java.awt.Graphics2D;
import java.awt.Color;

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
            }
        }

        if (isMoving) { // if we ARE moving, ignore inputs and force player to finish the step (this disables the player being inside 1/2 of a tile or 1/3 of a tile)
            if (direction.equals("up")) setY(getY() - getSpeed()); // if pressed w move y up
            if (direction.equals("down")) setY(getY() + getSpeed()); // if pressed s move y down
            if (direction.equals("left")) setX(getX() - getSpeed()); // if pressed a move x left
            if (direction.equals("right")) setX(getX() + getSpeed()); // if pressed d move x right
            pixelCounter += getSpeed(); // increase pixelCounter by speed until it is equal to one tile
            if (pixelCounter >= gamePanel.getTileSize()) { // if the pixel counter is exactly one tile
                isMoving = false; // unlock the listener again
                pixelCounter = 0; // reset it to count again
            }
        }
    }

    public void draw (Graphics2D graphics2D) {
        graphics2D.setColor(Color.WHITE); // change the paint color to white
        graphics2D.fillRect(getX(), getY(), gamePanel.getTileSize(), gamePanel.getTileSize()); // fill accordingly
    }

}
