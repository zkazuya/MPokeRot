package Tile;

import java.awt.image.BufferedImage;

public class Tile {
    private BufferedImage image; // these are what renders the tile from a file "buffered"
    private boolean collision = false; // this sets if collision is true for specific tiles

    public BufferedImage getImage() {
        return this.image; // getter for image
    }

    public boolean getCollision () {
        return this.collision; // getter for collision
    }

    public void setImage (BufferedImage image) {
        this.image = image; // setter for image
    }

    public void setCollision (boolean collision) {
        this.collision = collision; // setter for collision
    }

}
