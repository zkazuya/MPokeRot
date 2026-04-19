import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;

public class BattleUI {
    private GamePanel gamePanel;
    private BufferedImage battleBackground;
    private Font dynamicFont;
    private BufferedImage playerRot;
    private BufferedImage enemyRot;

    public BattleUI (GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        int fontSize = gamePanel.getTileSize() / 3;
        dynamicFont = new Font("Arial", Font.PLAIN, fontSize);
        try {
            playerRot = ImageIO.read(new FileInputStream("Assets/PokeRots/Chimpanzini_Bananini.png"));
            enemyRot = ImageIO.read(new FileInputStream("Assets/PokeRots/Tralalelo_Tralala.png"));
            battleBackground = ImageIO.read(new FileInputStream("Assets/Battle/battle_bg.png"));
        } catch (IOException ioE) {
            ioE.printStackTrace();
        }
    }

    public void drawBattleScreen (Graphics2D graphics2D) {
        int tileSize = gamePanel.getTileSize(); // shortcut variable
        int boxHeight = tileSize * 3; // box height is 3 tiles tall
        int boxY = gamePanel.getScreenHeight() - boxHeight; // the box's y is pinned at the bottom
        graphics2D.drawImage(battleBackground, 0, 0, gamePanel.getScreenWidth(), gamePanel.getScreenHeight() - boxHeight, null); // this makes it so the background is not full screen, it's sliced by box height to leave for a room for the box at bottom
        
        int pokerotSize = tileSize * 4; // size of pokerot is 4 tiles
        int playerX = tileSize * 2; // coords X for pokerot
        int playerY = boxY - pokerotSize + (tileSize / 2); // coords Y for pokerot
        graphics2D.drawImage(playerRot, playerX, playerY, pokerotSize, pokerotSize, null); // draw on bottom left

        int enemyX = gamePanel.getScreenWidth() - (tileSize * 7); // 7 tiles from the right edge
        int enemyY = tileSize * 2; // 1 tile from the top ceiling
        graphics2D.drawImage(enemyRot, enemyX, enemyY, pokerotSize, pokerotSize, null);

        PokeRot activePlayer = gamePanel.battleSystem.getActivePlayer();
        PokeRot activeEnemy = gamePanel.battleSystem.getActiveEnemy();

        int enemyBoxX = tileSize / 2;
        int enemyBoxY = tileSize / 2;

        drawHealthBar(graphics2D, enemyBoxX, enemyBoxY, activeEnemy);

        int playerBoxX = gamePanel.getScreenWidth() - (int)(tileSize * 5);
        int playerBoxY = boxY - (int)(tileSize * 1.8);

        drawHealthBar(graphics2D, playerBoxX, playerBoxY, activePlayer);

        int rightBoxWidth = tileSize * 5; // right box width is 5 tiles long
        int rightBoxX = gamePanel.getScreenWidth() - rightBoxWidth; // it right box's x is kasunod it width it rightbox
        int leftBoxWidth = gamePanel.getScreenWidth() - rightBoxWidth; // it kadako it leftbox it salin na gin occupy ni right box
        int leftBoxX = 0; // pinaka left it x it left box
        
        drawSubWindow(graphics2D, leftBoxX, boxY, leftBoxWidth, boxHeight);
        drawSubWindow(graphics2D, rightBoxX, boxY, rightBoxWidth, boxHeight);

        graphics2D.setFont(dynamicFont);
        graphics2D.setColor(Color.WHITE);

        int textX = leftBoxX + (tileSize / 2); // pads text by half a tile
        int textY = boxY + tileSize; // pushed down by 1 full tile

        int menuX = rightBoxX + tileSize;
        int menuY = boxY + tileSize;
        int xSpace = tileSize * 2;
        int ySpace = (int) (tileSize * 0.75);

        int subState = gamePanel.battleSystem.getBattleSubState();
        int currentOption = gamePanel.battleSystem.getOptionSelected();
        int cursorOffset = tileSize / 3;

        if (subState == 0) {
            graphics2D.drawString("What will", textX, textY);
            graphics2D.drawString(activePlayer.getName() + " do?", textX, textY + (tileSize / 2));

            graphics2D.drawString("FIGHT", menuX, menuY);
            graphics2D.drawString("BAG", menuX + xSpace, menuY);
            graphics2D.drawString("SWAP", menuX, menuY + ySpace);
            graphics2D.drawString("RUN", menuX + xSpace, menuY + ySpace);
        } else if (subState == 1) {
            graphics2D.drawString("Select a move!", textX, textY);

            if (activePlayer.getMove(0) != null) graphics2D.drawString(activePlayer.getMove(0).getName(), menuX, menuY);
            else graphics2D.drawString("-", menuX, menuY);
            if (activePlayer.getMove(1) != null) graphics2D.drawString(activePlayer.getMove(1).getName(), menuX + xSpace, menuY);
            else graphics2D.drawString("-", menuX + xSpace, menuY);
            if (activePlayer.getMove(2) != null) graphics2D.drawString(activePlayer.getMove(2).getName(), menuX, menuY + ySpace);
            else graphics2D.drawString("-", menuX, menuY + ySpace);
            if (activePlayer.getMove(3) != null) graphics2D.drawString(activePlayer.getMove(3).getName(), menuX + xSpace, menuY + ySpace);
            else graphics2D.drawString("-", menuX + xSpace, menuY + ySpace);

        } else if (subState >= 2) {
            String currentMessage = gamePanel.battleSystem.getCurrentMessage();
            graphics2D.drawString(currentMessage, textX, textY);
            if (subState == 2 || subState == 4 || subState == 6 || subState == 7) {
                graphics2D.drawString("Press Enter", menuX, menuY + (ySpace / 2));
            }
        }
        if (subState == 0 || subState == 1) {
            if (currentOption == 0)
                graphics2D.drawString(">", menuX - cursorOffset, menuY);
            if (currentOption == 1)
                graphics2D.drawString(">", menuX + xSpace - cursorOffset, menuY);
            if (currentOption == 2)
                graphics2D.drawString(">", menuX - cursorOffset, menuY + ySpace);
            if (currentOption == 3)
                graphics2D.drawString(">", menuX + xSpace - cursorOffset, menuY + ySpace);
        }
    }


    public void drawSubWindow(Graphics2D graphics2D, int x, int y, int width, int height) {
        int tileSize = gamePanel.getTileSize();
        int arcSize = tileSize / 3; // I did not define archeight and arcwidth because it's a square so we use the same variable
        int borderThickness = Math.max(1, tileSize / 20);

        Color blackBox = new Color(0, 0, 0, 210); // 210 makes it slightly see-through
        graphics2D.setColor(blackBox);
        graphics2D.fillRoundRect(x, y, width, height, arcSize, arcSize);

        Color whiteBorder = new Color(255, 255, 255);
        graphics2D.setColor(whiteBorder);
        graphics2D.setStroke(new java.awt.BasicStroke(borderThickness));
        graphics2D.drawRoundRect(x + borderThickness, y + borderThickness, width - (borderThickness * 2), height - (borderThickness * 2), arcSize - borderThickness, arcSize - borderThickness);
    }

    public void drawHealthBar (Graphics2D graphics2D, int x, int y, PokeRot pokerot) {
        int tileSize = gamePanel.getTileSize();
        int borderThickness = Math.max(1, tileSize / 20);
        int boxWidth = (int) (tileSize * 4.5);
        int boxHeight = (int) (tileSize * 1.5);
        drawSubWindow(graphics2D, x, y, boxWidth, boxHeight);
        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(dynamicFont);
        int textX = x + (tileSize / 2);
        int textY = y + (tileSize / 2);
        graphics2D.drawString(pokerot.getName() + " Lvl. " + pokerot.getLevel(), textX, textY);
        int barX = textX;
        int barY = textY + (tileSize / 3);
        int barWidth = boxWidth - tileSize;
        int barHeight = tileSize / 4;

        graphics2D.setColor(new Color(50, 50, 50));
        graphics2D.fillRect(barX, barY, barWidth, barHeight);

        double hpRatio = (double) pokerot.getDrawnHP() / pokerot.getMaxHP();
        int currentBarWidth = (int) (barWidth * hpRatio);
        graphics2D.setColor(Color.GREEN);
        graphics2D.fillRect(barX, barY, currentBarWidth, barHeight);

        graphics2D.setColor(Color.WHITE);
        graphics2D.setStroke(new java.awt.BasicStroke(borderThickness));
        graphics2D.drawRect(barX, barY, barWidth, barHeight);
    }
}