package entity;

import java.awt.image.BufferedImage;

public class Entity { 
    protected int x, y; 
    protected int speed; 
    protected int animationSpeed; 

    protected BufferedImage[] upSprites = new BufferedImage[4];
    protected BufferedImage[] downSprites = new BufferedImage[4];
    protected BufferedImage[] leftSprites = new BufferedImage[4];
    protected BufferedImage[] rightSprites = new BufferedImage[4];

    protected int spriteCounter = 0; 
    protected int spriteNumber = 0; 

    public int getX () { return this.x; }
    public int getY () { return this.y; }
    public int getSpeed () { return this.speed; }
    public int getAnimationSpeed(){ return this.animationSpeed; }
    public void setX (int x) { this.x = x; }
    public void setY (int y) { this.y = y; }
    public void setSpeed (int speed) { this.speed = speed; }
    public void setAnimationSpeed(int animSpeed){ this.animationSpeed = animSpeed; }
}
