package world;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;

import main.GamePanel;

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

    private void loadTile(int index, String path, boolean collision) throws IOException {
            tile[index] = new Tile();
            InputStream is = getClass().getClassLoader().getResourceAsStream(path);
            if (is != null) {
                tile[index].setImage(ImageIO.read(is));
                tile[index].setCollision(collision);
                tile[index].setRenderable(true);
            } else {
                System.out.println("MISSING ASSET: " + path);
            }
        }


    public void getTileImage() {
            try {
                // grass
                loadTile(0, "Assets/Tiles/Grass_Tiles_00.png", false);
                loadTile(1, "Assets/Tiles/Grass_Tiles_03.png", false);
                loadTile(2, "Assets/Tiles/Grass_Tiles_09.png", false);
                loadTile(3, "Assets/Tiles/Grass_Tiles_14.png", false);
                tile[3].setRenderable(false);
                
                // corner road tiles
                loadTile(4, "Assets/Tiles/Corner_Tiles_0.png", false);
                loadTile(5, "Assets/Tiles/Corner_Tiles_1.png", false);
                loadTile(6, "Assets/Tiles/Corner_Tiles_2.png", false);
                loadTile(7, "Assets/Tiles/Corner_Tiles_3.png", false);
                loadTile(8, "Assets/Tiles/Corner_Tiles_4.png", false);
                loadTile(9, "Assets/Tiles/Corner_Tiles_5.png", false);
                loadTile(10, "Assets/Tiles/Corner_Tiles_6.png", false);
                loadTile(11, "Assets/Tiles/Corner_Tiles_7.png", false);
                
                // tall grass
                loadTile(12, "Assets/Tiles/Tall_Grass_0.png", false);
                
                // highway tiles
                loadTile(13, "Assets/Tiles/Highway_Tile_00.png", false);
                loadTile(14, "Assets/Tiles/Highway_Tile_03.png", false);
                loadTile(15, "Assets/Tiles/Highway_Tile_06.png", false);
                loadTile(16, "Assets/Tiles/Highway_Tile_16.png", false);
                loadTile(17, "Assets/Tiles/Highway_Tile_17.png", false);
                
                // path/road
                loadTile(18, "Assets/Tiles/Revised_Road_Tile_1.png", false);
                
                // FENCES
                loadTile(19, "Assets/Tiles/Pokewalls_1.png", true);
                loadTile(20, "Assets/Tiles/Pokewalls_3.png", true);
                loadTile(21, "Assets/Tiles/Pokewalls_4.png", true);
                loadTile(22, "Assets/Tiles/Pokewalls_6.png", true);
                
                // BLANK / OCCUPIED
                loadTile(23, "Assets/Tiles/Blank.png", true);
                tile[23].setRenderable(false);

                // BUILDINGS
                loadTile(24, "Assets/Tiles/Buildings/AS_Building_1.png", true);
                tile[24].setWidth(gamePanel.getTileSize() * 15);
                tile[24].setHeight(gamePanel.getTileSize() * 6);

                loadTile(25, "Assets/Tiles/Buildings/AS_Building_2.png", true);
                tile[25].setWidth(gamePanel.getTileSize() * 15);
                tile[25].setHeight(gamePanel.getTileSize() * 6);

                loadTile(26, "Assets/Tiles/Buildings/Basketball_Court.png", false);
                tile[26].setWidth(gamePanel.getTileSize() * 20);
                tile[26].setHeight(gamePanel.getTileSize() * 10);

                loadTile(27, "Assets/Tiles/Buildings/Final_Building(Long_No_Door_67)#4.png", true);
                tile[27].setWidth(gamePanel.getTileSize() * 7);
                tile[27].setHeight(gamePanel.getTileSize() * 12);

                loadTile(28, "Assets/Tiles/Buildings/Final_Building(Long)#3.png", true);
                tile[28].setWidth(gamePanel.getTileSize() * 7);
                tile[28].setHeight(gamePanel.getTileSize() * 12);

                loadTile(29, "Assets/Tiles/Buildings/Final_Building_#2.png", true);
                tile[29].setWidth(gamePanel.getTileSize() * 7);
                tile[29].setHeight(gamePanel.getTileSize() * 5);

                loadTile(30, "Assets/Tiles/Buildings/Final_Classroom_0.png", true);
                tile[30].setWidth(gamePanel.getTileSize() * 8);
                tile[30].setHeight(gamePanel.getTileSize() * 4);

                loadTile(31, "Assets/Tiles/Buildings/Final_Classroom_1.png", true);
                tile[31].setWidth(gamePanel.getTileSize() * 8);
                tile[31].setHeight(gamePanel.getTileSize() * 4);

                loadTile(32, "Assets/Tiles/Buildings/Final_Classroom_2.png", true);
                tile[32].setWidth(gamePanel.getTileSize() * 8);
                tile[32].setHeight(gamePanel.getTileSize() * 4);

                loadTile(33, "Assets/Tiles/Buildings/Oble_With_Stand_Full_Final.png", true);
                tile[33].setWidth(gamePanel.getTileSize() * 3);
                tile[33].setHeight(gamePanel.getTileSize() * 6);

                loadTile(34, "Assets/Tiles/Buildings/Final_Classroom_2.png", true);
                tile[34].setWidth(gamePanel.getTileSize() * 8);
                tile[34].setHeight(gamePanel.getTileSize() * 4);

                loadTile(35, "Assets/Tiles/Buildings/Pokerot_Tree(Big).png", true);
                tile[35].setWidth(gamePanel.getTileSize() * 3);
                tile[35].setHeight(gamePanel.getTileSize() * 5);

                loadTile(36, "Assets/Tiles/Buildings/Pokerot_Tree1.png", true);
                tile[36].setWidth(gamePanel.getTileSize() * 2);
                tile[36].setHeight(gamePanel.getTileSize() * 3);

                loadTile(37, "Assets/Tiles/Buildings/SL_Building_Final.png", true);
                tile[37].setWidth(gamePanel.getTileSize() * 15);
                tile[37].setHeight(gamePanel.getTileSize() * 6);

                // INTERSECTIONS
                loadTile(38, "Assets/Tiles/Other_Corner_Tiles_0.png", false);
                loadTile(39, "Assets/Tiles/Other_Corner_Tiles_1.png", false);
                loadTile(40, "Assets/Tiles/Other_Corner_Tiles_2.png", false);
                loadTile(41, "Assets/Tiles/Other_Corner_Tiles_3.png", false);
                loadTile(42, "Assets/Tiles/Tall_Grass_0.png", false); // Fixed path typo

            } catch (IOException ioE) {
                ioE.printStackTrace();
            }
        }


    public void loadMap(String filePath) {
        try {
            InputStream inputStream = getClass().getResourceAsStream((filePath));
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
