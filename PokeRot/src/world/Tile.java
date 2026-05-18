package world;

import java.awt.image.BufferedImage;


public class Tile {
    private BufferedImage image;
    private boolean collision = false; // COLLISION FOR CERTAIN TILES
    private boolean encounter = false; // IF TILE IS ENCOUNTERABLE WITH BRAINROT
    private boolean renderable = true; // USED FOR MULTI TILE SKIP IF NOT
    private int height; // HEIGHT OF MULTI TILED IMAGE
    private int width; // WIDTH OF MULTI TILED IMAGE


    public Tile() { // FOR MULTI TILE IMAGES
        this.renderable = true;
        this.height = 64; // BASE IS 64 PIXELS
        this.width = 64;
    }
    
    public boolean getEncounter() { return this.encounter; }


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
