
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;




public class TileManager {
    private GamePanel gamePanel;
    private Tile[] tile; // THE TILES INSTANTIATED IN TILE IS STORED IN AN ARRAY
    private int[][] mapTileNumber; // THIS WILL SCAN WHAT THE TILE IS AT SPECIFIC ROW & COLUMN IT CHANGES


    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tile = new Tile[45]; // THE ARRAY STORES 10 TILES CHANGEABLE
        mapTileNumber = new int[gamePanel.getMaxWorldColumn()][gamePanel.getMaxWorldRow()];
        getTileImage();
        loadMap("Assets/Maps/FinalMap - Map save updated final.txt");
    }


    public void getTileImage() {
        try {
            // grass
            tile[0] = new Tile();
            tile[0].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Grass_Tiles_00.png"))); // base-grass
            tile[1] = new Tile();
            tile[1].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Grass_Tiles_03.png"))); // grass-with-tae
            tile[2] = new Tile();
            tile[2].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Grass_Tiles_09.png"))); // grass-with-rock
            tile[3] = new Tile();
            tile[3].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Grass_Tiles_14.png")));// grass-with-flower
            tile[3].setRenderable(false);
            // corner road tiles
            tile[4] = new Tile();
            tile[4].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Corner_Tiles_0.png"))); // vert-LS-road
            tile[5] = new Tile();
            tile[5].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Corner_Tiles_1.png"))); // vert-RS-road
            tile[6] = new Tile();
            tile[6].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Corner_Tiles_2.png"))); // upper-r-road
            tile[7] = new Tile();
            tile[7].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Corner_Tiles_3.png"))); // lower-l-road
            tile[8] = new Tile();
            tile[8].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Corner_Tiles_4.png")));// horizon-lower-side-road
            tile[9] = new Tile();
            tile[9].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Corner_Tiles_5.png")));// horizon-upper-side-road
            tile[10] = new Tile();
            tile[10].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Corner_Tiles_6.png"))); // lower-r-road
            tile[11] = new Tile();
            tile[11].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Corner_Tiles_7.png"))); // upper-l-road
            // tall grass
            tile[12] = new Tile();
            tile[12].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Tall_Grass_0.png"))); // TALL-GRASS
            // highway tiles
            tile[13] = new Tile();
            tile[13].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Highway_Tile_00.png"))); // base-road
            tile[14] = new Tile();
            tile[14].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Highway_Tile_03.png"))); // -hori-ped-line
            tile[15] = new Tile();
            tile[15].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Highway_Tile_06.png"))); // vert-ped-line
            tile[16] = new Tile();
            tile[16].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Highway_Tile_16.png"))); // hori-dash-line
            tile[17] = new Tile();
            tile[17].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Highway_Tile_17.png"))); // vert-dash-line
            // path/road
            tile[18] = new Tile();
            tile[18].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Revised_Road_Tile_1.png"))); // road-block-in-campus
            // FENCES
            tile[19] = new Tile();
            tile[19].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Pokewalls_1.png"))); // wall-from-ls-campus-pole-to-right
            tile[19].setCollision(true);
            tile[20] = new Tile();
            tile[20].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Pokewalls_3.png"))); // vert-fence-to-right
            tile[20].setCollision(true);
            tile[21] = new Tile();
            tile[21].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Pokewalls_4.png"))); // vert-fence-to-left
            tile[21].setCollision(true);
            tile[22] = new Tile();
            tile[22].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Pokewalls_6.png"))); // wall-from-rs-campus-pole-to-left
            tile[22].setCollision(true);
            // FOR BLANK // OCCUPIED TILES PART OF BUILDINGS AND TULTI TILE IMAGES
            tile[23] = new Tile();
            tile[23].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Blank.png"))); // tile-for-multi-tiled-images
            tile[23].setCollision(true);
            tile[23].setRenderable(false);
            // ======================================
            // BUILDINGS MULTI TILESSS
            // ======================================
            tile[24] = new Tile();
            tile[24].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Buildings/AS_Building_1.png"))); // AS-bldg-right-door
            tile[24].setCollision(true);
            tile[24].setRenderable(true);
            tile[24].setWidth(gamePanel.getTileSize() * 15);
            tile[24].setHeight(gamePanel.getTileSize() * 6);


            tile[25] = new Tile();
            tile[25].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Buildings/AS_Building_2.png"))); // AS-bldg-left-door
            tile[25].setCollision(true);
            tile[25].setRenderable(true);
            tile[25].setWidth(gamePanel.getTileSize() * 15);
            tile[25].setHeight(gamePanel.getTileSize() * 6);


            tile[26] = new Tile();
            tile[26].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Buildings/Basketball_Court.png"))); // bball-court
            tile[26].setRenderable(true);
            tile[26].setWidth(gamePanel.getTileSize() * 20);
            tile[26].setHeight(gamePanel.getTileSize() * 10);


            tile[27] = new Tile();
            tile[27].setImage(
                    ImageIO.read(new FileInputStream("Assets/Tiles/Buildings/Final_Building(Long_No_Door_67)#4.png"))); // big-bldg-67
            tile[27].setCollision(true);
            tile[27].setRenderable(true);
            tile[27].setWidth(gamePanel.getTileSize() * 7); // edit
            tile[27].setHeight(gamePanel.getTileSize() * 12);


            tile[28] = new Tile();
            tile[28].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Buildings/Final_Building(Long)#3.png"))); // big-bldg-normal
            tile[28].setCollision(true);
            tile[28].setRenderable(true);
            tile[28].setWidth(gamePanel.getTileSize() * 7);
            tile[28].setHeight(gamePanel.getTileSize() * 12); // edited


            tile[29] = new Tile();
            tile[29].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Buildings/Final_Building_#2.png"))); // red-bldg-normal
            tile[29].setCollision(true);
            tile[29].setRenderable(true);
            tile[29].setWidth(gamePanel.getTileSize() * 7);
            tile[29].setHeight(gamePanel.getTileSize() * 5); // edited


            tile[30] = new Tile();
            tile[30].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Buildings/Final_Classroom_0.png"))); // class-room-3-window
            tile[30].setCollision(true);
            tile[30].setRenderable(true);
            tile[30].setWidth(gamePanel.getTileSize() * 8);
            tile[30].setHeight(gamePanel.getTileSize() * 4); // d


            tile[31] = new Tile();
            tile[31].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Buildings/Final_Classroom_1.png"))); // class-room-2-window
            tile[31].setCollision(true);
            tile[31].setRenderable(true);
            tile[31].setWidth(gamePanel.getTileSize() * 8);
            tile[31].setHeight(gamePanel.getTileSize() * 4); // d


            tile[32] = new Tile();
            tile[32].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Buildings/Final_Classroom_2.png"))); // class-room-67
            tile[32].setCollision(true);
            tile[32].setRenderable(true);
            tile[32].setWidth(gamePanel.getTileSize() * 8);
            tile[32].setHeight(gamePanel.getTileSize() * 4); // d


            tile[33] = new Tile();
            tile[33].setImage(
                    ImageIO.read(new FileInputStream("Assets/Tiles/Buildings/Oble_With_Stand_Full_Final.png"))); // oble-statue
            tile[33].setCollision(true);
            tile[33].setRenderable(true);
            tile[33].setWidth(gamePanel.getTileSize() * 3);
            tile[33].setHeight(gamePanel.getTileSize() * 6); // d


            tile[34] = new Tile();
            tile[34].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Buildings/Final_Classroom_2.png"))); // class-room-67
            tile[34].setCollision(true);
            tile[34].setRenderable(true);
            tile[34].setWidth(gamePanel.getTileSize() * 8);
            tile[34].setHeight(gamePanel.getTileSize() * 4); // d


            tile[35] = new Tile();
            tile[35].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Buildings/Pokerot_Tree(Big).png"))); // tree-big-3x5
            tile[35].setCollision(true);
            tile[35].setRenderable(true);
            tile[35].setWidth(gamePanel.getTileSize() * 3);
            tile[35].setHeight(gamePanel.getTileSize() * 5); // d


            tile[36] = new Tile();
            tile[36].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Buildings/Pokerot_Tree1.png"))); // tree-small-2x3
            tile[36].setCollision(true);
            tile[36].setRenderable(true);
            tile[36].setWidth(gamePanel.getTileSize() * 2);
            tile[36].setHeight(gamePanel.getTileSize() * 3); // d


            tile[37] = new Tile();
            tile[37].setImage(ImageIO.read(new FileInputStream("Assets/Tiles/Buildings/SL_Building_Final.png"))); // AS-bldg-w/aircon
            tile[37].setCollision(true);
            tile[37].setRenderable(true);
            tile[37].setWidth(gamePanel.getTileSize() * 15);
            tile[37].setHeight(gamePanel.getTileSize() * 6); // d


            tile[38] = new Tile();
            tile[38].setImage(ImageIO.read(new FileInputStream("Assets\\Tiles\\Other_Corner_Tiles_0.png"))); // ROAD-INTERSECT-LOW-L
            tile[39] = new Tile();
            tile[39].setImage(ImageIO.read(new FileInputStream("Assets\\Tiles\\Other_Corner_Tiles_1.png"))); // ROAD-INTERSECT-LOW-R
            tile[40] = new Tile();
            tile[40].setImage(ImageIO.read(new FileInputStream("Assets\\Tiles\\Other_Corner_Tiles_2.png"))); // ROAD-INTERSECT-UP-L
            tile[41] = new Tile();
            tile[41].setImage(ImageIO.read(new FileInputStream("Assets\\Tiles\\Other_Corner_Tiles_3.png"))); // ROAD-INTERSECT-UP-R
            tile[42] = new Tile();
            tile[42].setImage(ImageIO.read(new FileInputStream("Assets\\Tiles\\TallGrass_01.png"))); // NEGA GRASS


            // tile[43] = new Tile();
            // tile[43].setImage(ImageIO.read(new
            // FileInputStream("Assets/Tiles/Grass_Tiles_00.png")));
            // tile[43].setCollision(true);
            // tile[43].setRenderable(true);
        } catch (IOException ioE) {
            ioE.printStackTrace();
        }
    }


    public void loadMap(String filePath) {
        try {
            InputStream inputStream = new FileInputStream(filePath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            int column = 0;
            int row = 0;
            while (column < gamePanel.getMaxWorldColumn() && row < gamePanel.getMaxWorldRow()) {
                String line = bufferedReader.readLine();
                if (line == null)
                    break;
                String[] numbers = line.trim().split("\\s+");


                while (column < gamePanel.getMaxWorldColumn() && column < numbers.length) {
                    int currentNumber = Integer.parseInt(numbers[column]);
                    int tileIndex = currentNumber;
                    switch (currentNumber) {
                        case 62 -> tileIndex = 35; // BIG TREE
                        case 80 -> tileIndex = 23; // BLANK BUILDING TILE EDIT KO LA 23 IT HIYA
                        case 70 -> tileIndex = 23; // BLANK BUILDING TILE
                        case 61 -> tileIndex = 36; // SMALL TREE
                        case 6 -> tileIndex = 2; // GRASS
                        case 9 -> tileIndex = 3; // GRASS WITH FLOWER
                        case 11 -> tileIndex = 2; // GRASS WITH ROCK
                        case 13 -> tileIndex = 2; // GRASS WITH FLOWER
                        case 67 -> tileIndex = 1; // GRASS NGA MAYADA TOBOL
                        case 55 -> tileIndex = 18; // NORMAL PATH
                        case 41 -> tileIndex = 6; // UP R
                        case 42 -> tileIndex = 10; // LOW R
                        case 43 -> tileIndex = 11; // UP L
                        case 44 -> tileIndex = 7; // LOW L
                        case 30 -> tileIndex = 4; // VERTICAL LEFT S ROAD
                        case 31 -> tileIndex = 5; // VERTICAL RIGHT S ROAD
                        case 4 -> tileIndex = 8; // HORIZONTAL LOWER S ROAD
                        case 5 -> tileIndex = 9; // HORIZONTAL UPPER S ROAD
                        // ROAD-INTERSECTION-CORNER
                        case 21 -> tileIndex = 40;
                        case 22 -> tileIndex = 38;
                        case 23 -> tileIndex = 41;
                        case 24 -> tileIndex = 39;
                        // FENCES
                        case 56 -> tileIndex = 19; // RIGHT S POLE
                        case 57 -> tileIndex = 22; // LEFT S POLE
                        case 7 -> tileIndex = 22; // FENCE NEAR BOTANICAL
                        case 58 -> tileIndex = 21; // VERTICAL F LEFT
                        case 59 -> tileIndex = 20; // VERTICAL F RIGHT
                        // ROAD TILES
                        case 50 -> tileIndex = 13;
                        case 14 -> tileIndex = 16;
                        case 49 -> tileIndex = 15;
                        case 3 -> tileIndex = 14;
                        case 17 -> tileIndex = 17;
                        // BUILDINGS
                        case 90 -> tileIndex = 32; // ROOM WITH 67
                        case 92 -> tileIndex = 31; // ROOM OTHER
                        case 93 -> tileIndex = 33; // OBLE
                        case 96 -> tileIndex = 26; // BASKETBALL COURT
                        case 97 -> tileIndex = 29; // RED BUILDING SMALL
                        case 98 -> tileIndex = 28; // RED BUILDING BIG
                        case 99 -> tileIndex = 27; // RED BUILDING 67
                        case 91 -> tileIndex = 25; // AS BUILDING LEFT D
                        case 94 -> tileIndex = 24; // AS BUILDING RIGHT D
                        case 95 -> tileIndex = 37; // AS BUILDING RIGHT D AC
                        case 0 -> tileIndex = 42;
                        default -> tileIndex = currentNumber;
                    }
                    mapTileNumber[column][row] = tileIndex;
                    column++;
                }
                if (column == gamePanel.getMaxWorldColumn() || column == numbers.length) {
                    column = 0;
                    row++;
                }
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void draw(Graphics2D graphics2D) {
        int tilePositionColumn = 0; // THINK OF THIS AS J OR THE COLUMN NUMBER
        int tilePositionRow = 0; // THINK OF THIS AS I OR THE ROW NUMBER


        while (tilePositionColumn < gamePanel.getMaxWorldColumn() && tilePositionRow < gamePanel.getMaxWorldRow()) {
            int tileNumber = mapTileNumber[tilePositionColumn][tilePositionRow]; // GETS WHAT TILE WE SHOULD DRAW THIS
                                                                                 // CHANGES EACH ITERATION
            Tile drawThisTile = tile[tileNumber];


            if (drawThisTile == null) {
                tilePositionColumn++;


                if (tilePositionColumn == gamePanel.getMaxWorldColumn()) {
                    tilePositionColumn = 0;
                    tilePositionRow++;
                }


                continue;
            }
            // THESE WORLDX AND WORLDY ARE ABSOLUTE POSITION FOR THE TILES AND THEY DO NOT
            // CHANGE
            int worldX = tilePositionColumn * gamePanel.getTileSize(); // THIS IS THE EXACT PIXEL LOCATION OF THE TILE
                                                                       // OUT IN THE MASSIVE MAP
            int worldY = tilePositionRow * gamePanel.getTileSize(); // THIS IS ABSOLUTE POSITION, WHEN DRAWN THERE IT'S
                                                                    // THERE


            // THIS IS FOR DRAWING ONLY THE getPlayer() (HE DOESN'T MOVE HE'S ALWAYS DRAWN IN
            // SCREENX AND SCREENY OR CENTER OF THE SCREEN)
            int screenX = worldX - gamePanel.getPlayer().getX() + gamePanel.getPlayer().getScreenX(); // WHERE IT MUST BE DRAWN IN
                                                                                            // THE SCREEN
            int screenY = worldY - gamePanel.getPlayer().getY() + gamePanel.getPlayer().getScreenY(); // OFFSET BY THE getPlayer()'S
                                                                                            // CURRENT POSITION
            int renderBuffer = gamePanel.getTileSize() * 15;


            // if (worldX + gamePanel.getTileSize() > gamePanel.getPlayer().getX() -
            // gamePanel.getPlayer().getScreenX() &&
            // worldX - gamePanel.getTileSize() < gamePanel.getPlayer().getX() +
            // gamePanel.getPlayer().getScreenX() &&
            // worldY + gamePanel.getTileSize() > gamePanel.getPlayer().getY() -
            // gamePanel.getPlayer().getScreenY() &&
            // worldY - gamePanel.getTileSize() < gamePanel.getPlayer().getY() +
            // gamePanel.getPlayer().getScreenY()) {
            int drawWidth = drawThisTile.getWidth() > 0
                    ? drawThisTile.getWidth()
                    : gamePanel.getTileSize();


            int drawHeight = drawThisTile.getHeight() > 0
                    ? drawThisTile.getHeight()
                    : gamePanel.getTileSize();


            if (worldX + drawWidth > gamePanel.getPlayer().getX() - gamePanel.getPlayer().getScreenX() - renderBuffer &&
                    worldX - drawWidth < gamePanel.getPlayer().getX() + gamePanel.getPlayer().getScreenX() + renderBuffer &&
                    worldY + drawHeight > gamePanel.getPlayer().getY() - gamePanel.getPlayer().getScreenY() - renderBuffer &&
                    worldY - drawHeight < gamePanel.getPlayer().getY() + gamePanel.getPlayer().getScreenY() + renderBuffer) {


                // if (tileNumber >= 0 && tileNumber < tile.length) {
                // // Tile drawThisTile = tile[tileNumber];
                // if (drawThisTile != null && drawThisTile.isRenderable() &&
                // drawThisTile.getImage() != null) {
                // // graphics2D.drawImage(drawThisTile.getImage(), screenX, screenY,
                // // gamePanel.getTileSize(),
                // // gamePanel.getTileSize(), null);
                // graphics2D.drawImage(
                // drawThisTile.getImage(), screenX, screenY,
                // drawThisTile.getWidth(), drawThisTile.getHeight(), null);
                // }
                // }


                if (drawThisTile.isRenderable() && drawThisTile.getImage() != null) {


                    graphics2D.drawImage(
                            drawThisTile.getImage(),
                            screenX,
                            screenY,
                            drawWidth,
                            drawHeight,
                            null);
                }
            }
            tilePositionColumn++;
            if (tilePositionColumn == gamePanel.getMaxWorldColumn()) {
                tilePositionColumn = 0;
                tilePositionRow++;
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
