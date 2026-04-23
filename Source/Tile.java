import java.awt.image.BufferedImage;

public class Tile {
    private BufferedImage image; // these are what renders the tile from a file "buffered"
    private boolean collision = false; // this sets if collision is true for specific tiles
    private boolean encounter = false; // if tile is encounterable with a pokerot

    public boolean getEncounter () { return this.encounter; }
    public BufferedImage getImage() { return this.image; }
    public boolean getCollision () { return this.collision; }
    public void setImage (BufferedImage image) { this.image = image; }
    public void setCollision (boolean collision) { this.collision = collision; }
    public void setEncounter (boolean encounter) { this.encounter = encounter; }
}
