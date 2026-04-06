package tile;

import main.GamePanel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;

public class TileManager {
    
    GamePanel gp;
    Tile[] tile;
    int mapTileNumber[][];

    public TileManager (GamePanel gp) {

        this.gp = gp;
        tile = new Tile[10]; // storing tiles
        mapTileNumber = new int[gp.maxScreenColumn][gp.maxScreenRow]; // storing the whole map 
        getTileImage();
        loadMap();
    }

    public void getTileImage () {

        try {

            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/floor01.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/grass01.png"));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/water01.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap () {

        try {

            InputStream iS = getClass().getResourceAsStream("/res/maps/map1.txt");
            BufferedReader bR = new BufferedReader(new InputStreamReader(iS));

            int column = 0;
            int row = 0;

            while (column < gp.maxScreenColumn && row < gp.maxScreenRow) {
                String line = bR.readLine();

                while (column < gp.maxScreenColumn) {
                    String numbers[] = line.split(" ");

                    int number = Integer.parseInt(numbers[column]);

                    mapTileNumber[column][row] = number;
                    column++;
                }

                if (column == gp.maxScreenColumn) {
                    column = 0;
                    row++;
                }
            }
            bR.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw (Graphics2D g2) {

        int column = 0;
        int row = 0;
        int x = 0;
        int y = 0;
        
        while (column < gp.maxScreenColumn && row < gp.maxScreenRow) {
            int tileNumber = mapTileNumber[column][row];

            g2.drawImage(tile[tileNumber].image, x, y, gp.tileSize, gp.tileSize, null);
            column++;

            x += gp.tileSize;

            if (column == gp.maxScreenColumn) {
                column = 0;
                x = 0;
                row++;
                y += gp.tileSize;
            }
        }

    }
}
