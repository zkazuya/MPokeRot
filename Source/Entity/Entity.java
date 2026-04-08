package Entity;

public class Entity { // instances are protected such that inheritinng classes can modify them
    protected int x, y; // entity coordinates x and y
    protected int speed; // movement speed

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
