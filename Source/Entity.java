import java.awt.image.BufferedImage;

public class Entity { // instances are protected such that inheritinng classes can modify them
    protected int x, y; // entity coordinates x and y
    protected int speed; // movement speed

    protected BufferedImage[] upSprites = new BufferedImage[4];
    protected BufferedImage[] downSprites = new BufferedImage[4];
    protected BufferedImage[] leftSprites = new BufferedImage[4];
    protected BufferedImage[] rightSprites = new BufferedImage[4];

    protected int spriteCounter = 0; // ensures no glitches on animation
    protected int spriteNumber = 0; // tracks which frame of animation were on

    public int getX () {
        return this.x; // getter for x
    }

    public int getY () {
        return this.y; // getter for y
    }
    
    public int getSpeed () {
        return this.speed; // getter for speed
    }

    public void setX (int x) {
        this.x = x; // setter for x
    }

    public void setY (int y) {
        this.y = y; // setter for y
    }

    public void setSpeed (int speed) {
        this.speed = speed; // setter for speed
    }

}
