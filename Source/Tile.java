import java.awt.image.BufferedImage;

public class Tile {
    private BufferedImage image; // these are what renders the tile from a file "buffered"
    private boolean collision = false; // this sets if collision is true for specific tiles
    private boolean encounter = false; // if tile is encounterable with a pokerot

    private boolean renderable = true; // used for multi tiles // false then skip drawing tile
    private int height; // for multi tiled image
    private int width; // for multi tiled image

    public Tile() { // for multi tile images
        this.renderable = true;

        this.height = 64; // base is 32 pixels
        this.width = 64;
    }

    public boolean getEncounter() {
        return this.encounter;
    }

    public BufferedImage getImage() {
        return this.image;
    }

    public boolean getCollision() {
        return this.collision;
    }

    public boolean isRenderable() {
        return this.renderable;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public void setEncounter(boolean encounter) {
        this.encounter = encounter;
    }

    public void setRenderable(boolean renderable) {
        this.renderable = renderable;
    }

}
