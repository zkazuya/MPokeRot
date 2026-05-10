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
        tile = new Tile[42]; // THE ARRAY STORES 10 TILES CHANGEABLE
        mapTileNumber = new int[gamePanel.getMaxScreenColumn()][gamePanel.getMaxScreenRow()]; // TILE NUMBER'S MAX SIZE
                                                                                              // IS JUST THE WHOLE MAP
        getTileImage(); // PUTS EVERY TILE INTO THE ARRAY
        loadMap("Assets/Maps/FinalMap - Map save updated final.txt"); // THIS METHOD IS LOADED ONCE, BUT SEPARATE
                                                                      // METHODS
        // COULD CALL THIS METHOD
    }

    public void getTileImage() {
        try {
            // grass
            tile[0] = new Tile();
            tile[0].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/grass tiles_00.png"))); // base-grass

            tile[1] = new Tile();
            tile[1].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/grass tiles_03.png"))); // grass-with-tae

            tile[2] = new Tile();
            tile[2].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/grass tiles_09.png"))); // grass-with-rock

            tile[3] = new Tile();
            tile[3].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/grass tiles_14.png")));// grass-with-flower

            // corner road tiles
            tile[4] = new Tile();
            tile[4].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/corner tiles_0.png"))); // vert-LS-road

            tile[5] = new Tile();
            tile[5].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/corner tiles_1.png"))); // vert-RS-road

            tile[6] = new Tile();
            tile[6].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/corner tiles_2.png"))); // upper-r-road

            tile[7] = new Tile();
            tile[7].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/corner tiles_3.png"))); // lower-l-road

            tile[8] = new Tile();
            tile[8].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/corner tiles_4.png")));// horizon-lower-side-road

            tile[9] = new Tile();
            tile[9].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/corner tiles_5.png")));// horizon-upper-side-road

            tile[10] = new Tile();
            tile[10].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/corner tiles_6.png"))); // lower-r-road

            tile[11] = new Tile();
            tile[11].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/corner tiles_7.png"))); // upper-l-road

            // tall grass
            tile[12] = new Tile();
            tile[12].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Tall grass_0.png"))); // TALL-GRASS

            // highway tiles
            tile[13] = new Tile();
            tile[13].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/highway tile_00.png"))); // base-road

            tile[14] = new Tile();
            tile[14].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/highway tile_03.png"))); // -hori-ped-line

            tile[15] = new Tile();
            tile[15].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/highway tile_06.png"))); // vert-ped-line

            tile[16] = new Tile();
            tile[16].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/highway tile_16.png"))); // hori-dash-line

            tile[17] = new Tile();
            tile[17].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/highway tile_17.png"))); // vert-dash-line

            // path/road
            tile[18] = new Tile();
            tile[18].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/revised road tile_1.png"))); // road-block-in-campus

            // FENCES
            tile[19] = new Tile();
            tile[19].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/pokewalls_1.png"))); // wall-from-ls-campus-pole-to-right
            tile[19].setCollision(true);

            tile[20] = new Tile();
            tile[20].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/pokewalls_3.png"))); // vert-fence-to-right
            tile[20].setCollision(true);

            tile[21] = new Tile();
            tile[21].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/pokewalls_4.png"))); // vert-fence-to-left
            tile[21].setCollision(true);

            tile[22] = new Tile();
            tile[22].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/pokewalls_6.png"))); // wall-from-rs-campus-pole-to-left
            tile[22].setCollision(true);

            // FOR BLANK // OCCUPIED TILES PART OF BUILDINGS AND TULTI TILE IMAGES
            tile[23] = new Tile();
            tile[23].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/blank.png"))); // tile-for-multi-tiled-images
            tile[23].setCollision(true);
            tile[23].setRenderable(false);

            // ======================================
            // BUILDINGS MULTI TILESSS
            // ======================================
            tile[24] = new Tile();
            tile[24].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Buildings/AS building_1.png"))); // AS-bldg-right-door
            tile[24].setCollision(true);
            tile[24].setRenderable(true);

            tile[25] = new Tile();
            tile[25].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Buildings/AS building_2.png"))); // AS-bldg-left-door
            tile[25].setCollision(true);
            tile[25].setRenderable(true);

            tile[26] = new Tile();
            tile[26].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Buildings/basketball court.png"))); // bball-court
            tile[26].setCollision(true);
            tile[26].setRenderable(true);

            tile[27] = new Tile();
            tile[27].setImage(ImageIO
                    .read(new FileInputStream("Assets/Tiles/Buildings/final building (long no door 67) #4.png"))); // big-bldg-67
            tile[27].setCollision(true);
            tile[27].setRenderable(true);

            tile[28] = new Tile();
            tile[28].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Buildings/final building (long) #3.png"))); // big-bldg-normal
            tile[28].setCollision(true);
            tile[28].setRenderable(true);

            tile[29] = new Tile();
            tile[29].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Buildings/final building #2.png"))); // red-bldg-normal
            tile[29].setCollision(true);
            tile[29].setRenderable(true);

            tile[30] = new Tile();
            tile[30].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Buildings/final classroom_0.png"))); // class-room-3-window
            tile[30].setCollision(true);
            tile[30].setRenderable(true);

            tile[31] = new Tile();
            tile[31].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Buildings/final classroom_1.png"))); // class-room-2-window
            tile[31].setCollision(true);
            tile[31].setRenderable(true);

            tile[32] = new Tile();
            tile[32].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Buildings/final classroom_2.png"))); // class-room-67
            tile[32].setCollision(true);
            tile[32].setRenderable(true);

            tile[33] = new Tile();
            tile[33].setImage(
                    ImageIO.read(new FileInputStream("Assets/Tiles/Buildings/oble with stand full final.png"))); // oble-statue
            tile[33].setCollision(true);
            tile[33].setRenderable(true);

            tile[34] = new Tile();
            tile[34].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Buildings/final classroom_2.png"))); // class-room-67
            tile[34].setCollision(true);
            tile[34].setRenderable(true);

            tile[35] = new Tile();
            tile[35].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Buildings/pokerot tree(big).png"))); // tree-big-3x5
            tile[35].setCollision(true);
            tile[35].setRenderable(true);

            tile[36] = new Tile();
            tile[36].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Buildings/pokerot tree1.png"))); // tree-small-2x3
            tile[36].setCollision(true);
            tile[36].setRenderable(true);

            tile[37] = new Tile();
            tile[37].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Buildings/SL building final.png"))); // AS-bldg-w/aircon
            tile[37].setCollision(true);
            tile[37].setRenderable(true);

            tile[38] = new Tile();
            tile[38].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/other corner tiles_0.png"))); // ROAD-INTERSECT-LOW-L

            tile[39] = new Tile();
            tile[39].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/other corner tiles_l.png"))); // ROAD-INTERSECT-LOW-R

            tile[40] = new Tile();
            tile[40].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/other corner tiles_2.png"))); // ROAD-INTERSECT-UP-L

            tile[41] = new Tile();
            tile[41].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/other corner tiles_3.png"))); // ROAD-INTERSECT-UP-R

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
                if (line == null) {
                    break;
                }
                while (column < gamePanel.getMaxScreenColumn()) { // as long as column doesn't overlp max column
                    String[] numbers = line.trim().split("\\s+"); // array of numbers as string split from spaces
                    int currentNumber = Integer.parseInt(numbers[column]); // cast the specific string of
                                                                           // numbers[column] and store it (this changes
                                                                           // each loop)

                    int tileIndex = currentNumber;
                    switch (currentNumber) {
                        case 62: // big-tree
                            mapTileNumber[column][row] = 35;
                            break;

                        case 80: // blank-bldg-tile
                            mapTileNumber[column][row] = 23;
                            break;

                        case 70: // blank-bldg-tile
                            mapTileNumber[column][row] = 23;
                            break;

                        case 61: // smol-tree
                            mapTileNumber[column][row] = 36;
                            break;

                        // GRASS
                        case 6: // grass
                            mapTileNumber[column][row] = 2;
                            break;

                        case 9: // grass-w-flower
                            mapTileNumber[column][row] = 3;
                            break;

                        case 11: // grass-rock
                            mapTileNumber[column][row] = 2;
                            break;

                        case 13: // grass-w-flower
                            mapTileNumber[column][row] = 3;
                            break;

                        case 67: // grass-tae
                            mapTileNumber[column][row] = 1;
                            break;

                        // PATH BLOCK
                        case 55: // NORMAL-PATH
                            mapTileNumber[column][row] = 18;
                            break;

                        // CORNERING-PATH
                        case 41: // UP-R
                            mapTileNumber[column][row] = 6;
                            break;

                        case 42: // LOW-R
                            mapTileNumber[column][row] = 10;
                            break;

                        case 43: // UP-L
                            mapTileNumber[column][row] = 11;
                            break;

                        case 44: // LOW-L
                            mapTileNumber[column][row] = 7;
                            break;

                        // ROAD-CORNER-HORI-&-VERT
                        case 30: // VERT-LEFT-S-ROAD
                            mapTileNumber[column][row] = 4;
                            break;

                        case 31: // VERT-RIGHT-S-ROAD
                            mapTileNumber[column][row] = 5;
                            break;

                        case 4: // HORI-LOWER-S-ROAD
                            mapTileNumber[column][row] = 8;
                            break;

                        case 5: // HORI-UPPER-S-ROAD
                            mapTileNumber[column][row] = 9;
                            break;

                        // ROAD-INTERSECTION-CORNER
                        case 21: //
                            mapTileNumber[column][row] = 40;
                            break;

                        case 22: //
                            mapTileNumber[column][row] = 38;
                            break;

                        case 23: //
                            mapTileNumber[column][row] = 41;
                            break;

                        case 24: //
                            mapTileNumber[column][row] = 39;
                            break;

                        // FENCES
                        case 56: // RIGHT-S-POLE
                            mapTileNumber[column][row] = 19;
                            break;

                        case 57: // LEFT-S-POLE
                            mapTileNumber[column][row] = 22;
                            break;

                        case 7: // FENCE-NEAR-BOTANICAL
                            mapTileNumber[column][row] = 22;
                            break;

                        case 58: // VERT-F-LEFT
                            mapTileNumber[column][row] = 21;
                            break;

                        case 59: // VERT-F-RIGHT
                            mapTileNumber[column][row] = 20;
                            break;

                        // ROAD TILES
                        case 50: //
                            mapTileNumber[column][row] = 13;
                            break;

                        case 14: //
                            mapTileNumber[column][row] = 16;
                            break;

                        case 49: //
                            mapTileNumber[column][row] = 15;
                            break;

                        case 3: //
                            mapTileNumber[column][row] = 14;
                            break;

                        case 17: //
                            mapTileNumber[column][row] = 17;
                            break;
                        // ========================================
                        // BUILDINGS
                        case 90: // ROOM-W-67
                            mapTileNumber[column][row] = 20;
                            break;

                        case 92: // ROOM-OTHER
                            mapTileNumber[column][row] = 20;
                            break;

                        case 93: // OBLE
                            mapTileNumber[column][row] = 20;
                            break;

                        case 96: // BBALL-COURT
                            mapTileNumber[column][row] = 20;
                            break;

                        case 97: // RED-BLDG-SMOL
                            mapTileNumber[column][row] = 20;
                            break;

                        case 98: // RED-BLDG-BIG
                            mapTileNumber[column][row] = 20;
                            break;

                        case 99: // RED-BLDG-67
                            mapTileNumber[column][row] = 20;
                            break;

                        case 91: // AS-BLDG-LEFT-D
                            mapTileNumber[column][row] = 20;
                            break;

                        case 94: // AS-BLDG-RIGHT-D
                            mapTileNumber[column][row] = 20;
                            break;

                        case 95: // AS-BLDG-RIGHT-D-AC
                            mapTileNumber[column][row] = 20;
                            break;

                        default:
                            tileIndex = currentNumber;
                    }
                    mapTileNumber[column][row] = tileIndex; // put that number as the map tile at specific
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

            if (tileNumber < 0 || tileNumber >= tile.length)
                continue;

            Tile t = tile[tileNumber];

            if (t == null || !t.isRenderable() || t.getImage() == null) {
                continue;
            }

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
