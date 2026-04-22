import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import javax.imageio.ImageIO;

public class TileManager {
    private GamePanel gamePanel;
    private Tile[] tile; // the tiles instantiated from Tile class are stored in array
    private int[][] mapTileNumber; // this will scan what the tile is at specific row & column IT CHANGES

    public TileManager (GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tile = new Tile[10]; // the array stores 10 tiles CHANGEABLE
        mapTileNumber = new int[gamePanel.getMaxScreenColumn()][gamePanel.getMaxScreenRow()]; // the tile number's max size is just the whole map
        getTileImage(); // puts every tiles into tile array when tile manager is instantiated
        loadMap("Assets/Maps/map1.txt"); // tile manager is created once so this loads once! separate methods can call this method
    }

    public void getTileImage () {
        try { // wrapped this in try catch so the program won't crash also to show the error
            tile[0] = new Tile();
            tile[0].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/floor01.png"))); 
            tile[1] = new Tile();
            tile[1].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/grass01.png"))); 
            tile[2] = new Tile();
            tile[2].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/water01.png"))); 
            tile[2].setCollision(true);
            tile[3] = new Tile();
            tile[3].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/tallgrass_01.png")));
            tile[3].setEncounter(true);
        } catch (IOException ioE) {
            ioE.printStackTrace();
        }
    }

    public void loadMap (String filePath) {
        try { // wrapped try catch so program won't crash when folders are missing
            InputStream inputStream = new FileInputStream(filePath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)); // makes a file reader, pass a InputStreamReader object
            int column = 0; // these change, keep tracks of y coords
            int row = 0; // keep track of x coords (of the map)
            while (column < gamePanel.getMaxScreenColumn() && row < gamePanel.getMaxScreenRow()) { // as long as were inside the map coords do what's inside
                String line = bufferedReader.readLine(); // gets the whole row of numbers "1023424 of the map"
                while (column < gamePanel.getMaxScreenColumn()) { // as long as column doesn't overlap max column
                    String[] numbers = line.split(" "); // array of numbers as string split from spaces
                    int currentNumber = Integer.parseInt(numbers[column]); // cast the specific string of numbers[column] and store it (this changes each loop)
                    mapTileNumber[column][row] = currentNumber; // put that number as the map tile at specific [column][row]
                    column++; // go to the next column and repeat (stops at max column)
                }
                if (column == gamePanel.getMaxScreenColumn()) { // if we hit max column
                    column = 0; // reset back to the first column
                    row++; // but this time we analyze the next row of columns
                }
            }
            bufferedReader.close(); // save resource
        } catch (Exception e) {
            System.out.println("Error Loading Map Text File");
            e.printStackTrace(); // prints what went wrong and the lines trace
        }
    }

    public void draw (Graphics2D graphics2D) {
        int column = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (column < gamePanel.getMaxScreenColumn() && row < gamePanel.getMaxScreenRow()) { // while inside the map keep looping
            int tileNumber = mapTileNumber[column][row]; // tileNumber changes each loop, it loads from our 2D array which we set earlier
            graphics2D.drawImage(tile[tileNumber].getImage(), x, y, gamePanel.getTileSize(), gamePanel.getTileSize(), null); // draw the tile we loaded
            column++; // go to next column
            x += gamePanel.getTileSize(); // this ensures drawing is 32x32 on the x-axis
            if (column == gamePanel.getMaxScreenColumn()) { // if reaches the rightmost column
                column = 0; // go back to left most
                x = 0; // go back to the left most
                row++; // but this time we're moving down the next row
                y += gamePanel.getTileSize(); // ensures drawing is 32x32 on the y-axis
            }
        }
    }

    public int getTileNumber (int column, int row) { return this.mapTileNumber[column][row]; }
    public Tile getTile (int index) { return this.tile[index]; }
}
