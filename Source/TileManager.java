import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import javax.imageio.ImageIO;

public class TileManager {
    private GamePanel gamePanel;
    private Tile[] tile; // THE TILES INSTANTIATED IN TILE IS STORED IN AN ARRAY
    private int[][] mapTileNumber; // THIS WILL SCAN WHAT THE TILE IS AT SPECIFIC ROW & COLUMN IT CHANGES

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tile = new Tile[24]; // THE ARRAY STORES 10 TILES CHANGEABLE
        mapTileNumber = new int[gamePanel.getMaxScreenColumn()][gamePanel.getMaxScreenRow()]; // TILE NUMBER'S MAX SIZE
                                                                                              // IS JUST THE WHOLE MAP
        getTileImage(); // PUTS EVERY TILE INTO THE ARRAY
        loadMap("Assets/Maps/FinalMap - Map save updated final"); // THIS METHOD IS LOADED ONCE, BUT SEPARATE METHODS
                                                                  // COULD CALL THIS METHOD
    }

    public void getTileImage() {
        try {
            // grass
            tile[0] = new Tile();
            tile[0].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/grass tiles_00.png")));

            tile[1] = new Tile();
            tile[1].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/grass tiles_03.png")));

            tile[2] = new Tile();
            tile[2].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/grass tiles_09.png")));

            tile[3] = new Tile();
            tile[3].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/grass tiles_14.png")));

            // corner road tiles
            tile[4] = new Tile();
            tile[4].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/corner tiles_0.png")));

            tile[5] = new Tile();
            tile[5].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/corner tiles_1.png")));

            tile[6] = new Tile();
            tile[6].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/corner tiles_2.png")));

            tile[7] = new Tile();
            tile[7].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/corner tiles_3.png")));

            tile[8] = new Tile();
            tile[8].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/corner tiles_4.png")));

            tile[9] = new Tile();
            tile[9].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/corner tiles_5.png")));

            tile[10] = new Tile();
            tile[10].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/corner tiles_6.png")));

            tile[11] = new Tile();
            tile[11].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/corner tiles_7.png")));

            // tall grass
            tile[12] = new Tile();
            tile[12].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Tall grass_0.png")));

            // highway tiles
            tile[13] = new Tile();
            tile[13].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/highway tile_00.png")));

            tile[14] = new Tile();
            tile[14].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/highway tile_03.png")));

            tile[15] = new Tile();
            tile[15].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/highway tile_06.png")));

            tile[16] = new Tile();
            tile[16].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/highway tile_16.png")));

            tile[17] = new Tile();
            tile[17].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/highway tile_17.png")));

            // path/road
            tile[18] = new Tile();
            tile[18].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/revised road tile_1.png")));

            // walls
            tile[19] = new Tile();
            tile[19].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/pokewalls_1.png")));
            tile[19].setCollision(true);

            tile[20] = new Tile();
            tile[20].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/pokewalls_3.png")));
            tile[20].setCollision(true);

            tile[21] = new Tile();
            tile[21].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/pokewalls_4.png")));
            tile[21].setCollision(true);

            tile[22] = new Tile();
            tile[22].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/pokewalls_6.png")));
            tile[22].setCollision(true);

            // special blocks
            tile[23] = new Tile();
            tile[23].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/blank.png")));
            tile[23].setCollision(true);

            // tile[2] = new Tile();
            // tile[2].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/ ")));
            // tile[2].setCollision(true);

        } catch (IOException ioE) {
            ioE.printStackTrace();
        }
    }

    public void loadMap(String filePath) {
        try { // wrapped try catch so program won't crash when folders are missing
            InputStream inputStream = new FileInputStream(filePath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)); // makes a file
                                                                                                    // reader, pass a
                                                                                                    // InputStreamReader
                                                                                                    // object
            int column = 0; // these change, keep tracks of y coords
            int row = 0; // keep track of x coords (of the map)
            while (column < gamePanel.getMaxScreenColumn() && row < gamePanel.getMaxScreenRow()) { // as long as were
                                                                                                   // inside the map
                                                                                                   // coords do what's
                                                                                                   // inside
                String line = bufferedReader.readLine(); // gets the whole row of numbers "1023424 of the map"
                while (column < gamePanel.getMaxScreenColumn()) { // as long as column doesn't overlap max column
                    String[] numbers = line.split(" "); // array of numbers as string split from spaces
                    int currentNumber = Integer.parseInt(numbers[column]); // cast the specific string of
                                                                           // numbers[column] and store it (this changes
                                                                           // each loop)
                    mapTileNumber[column][row] = currentNumber; // put that number as the map tile at specific
                                                                // [column][row]
                    column++; // go to the next column and repeat (stops at max column)
                }
                if (column == gamePanel.getMaxScreenColumn()) { // if we hit max column
                    column = 0; // reset back to the first column
                    row++; // but this time we analyze the next row of columns
                }
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("Error Loading Map Text File");
            e.printStackTrace(); // prints what went wrong and the lines trace
        }
    }

    public void draw(Graphics2D graphics2D) {
        int column = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (column < gamePanel.getMaxScreenColumn() && row < gamePanel.getMaxScreenRow()) { // while inside the map
                                                                                               // keep looping
            int tileNumber = mapTileNumber[column][row]; // tileNumber changes each loop, it loads from our 2D array
                                                         // which we set earlier
            graphics2D.drawImage(tile[tileNumber].getImage(), x, y, gamePanel.getTileSize(), gamePanel.getTileSize(),
                    null); // draw the tile we loaded
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

    public int getTileNumber(int column, int row) {
        return this.mapTileNumber[column][row];
    }

    public Tile getTile(int index) {
        return this.tile[index];
    }
}
