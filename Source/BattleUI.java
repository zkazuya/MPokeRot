import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class BattleUI {
    private GamePanel gamePanel;
    private BufferedImage battleBackground;
    private Font dynamicFont;
    private Font swapFont;
    private Font moveFont;
    private BufferedImage playerRot;
    private BufferedImage enemyRot;

    public BattleUI (GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        int fontSize = gamePanel.getTileSize() / 4; // FONT SIZE IS DYNAMIC
        dynamicFont = new Font("Arial", Font.PLAIN, fontSize);
        moveFont = new Font("Arial", Font.PLAIN, (int)(fontSize * 0.75));
        swapFont = new Font("Arial", Font.PLAIN, (int)(fontSize *0.55));
        try { // LOAD BATTLE BACKGROUND  
            battleBackground = ImageIO.read(new FileInputStream("Assets/Battle/battle_bg.png"));
        } catch (IOException ioE) {
            ioE.printStackTrace();
        }
    }

    public void drawBattleScreen (Graphics2D graphics2D) {
        int tileSize = gamePanel.getTileSize(); // SHORTCUT VARIABLE
        int boxHeight = tileSize * 3; // BOX HEIGHT IS 3 TILES TALL
        int boxY = gamePanel.getScreenHeight() - boxHeight; // BOX'S Y IS THE SPACE LEFTOVER FROM BOXHEIGHT
        // DRAW BATTLE BACKGROUND ACCORDING TO CONFIGURATIONS ABOVE
        graphics2D.drawImage(battleBackground, 0, 0, gamePanel.getScreenWidth(), boxY, null);
        
        int pokerotSize = tileSize * 4; // SIZE OF POKEROT IS 4 TILES
        int playerX = tileSize * 2; // COORDS X FOR POKEROT
        int playerY = boxY - pokerotSize + (tileSize / 2); // COORDS Y FOR POKEROT
        // DRAW PLAYER'S POKEROT DEPENDING ON CONFIGURATION ABOVE (BOTTOM LEFT)
        graphics2D.drawImage(playerRot, playerX, playerY, pokerotSize, pokerotSize, null);

        int enemyX = gamePanel.getScreenWidth() - (tileSize * 7); // COORDS X FOR ENEMY
        int enemyY = tileSize * 2; // COORDS Y FOR ENEMY
        // DRAW PLAYER'S POKEROT DEPENDING ON CONFIGURATION ABOVE (TOP RIGHT)
        graphics2D.drawImage(enemyRot, enemyX, enemyY, pokerotSize, pokerotSize, null);

        PokeRot activePlayer = gamePanel.battleSystem.getActivePlayer();
        PokeRot activeEnemy = gamePanel.battleSystem.getActiveEnemy();

        int enemyBoxX = tileSize / 2; // COORDS X FOR HEALTHBAR ENEMY
        int enemyBoxY = tileSize / 2; // COORDS Y FOR HEALTHBAR ENEMY
        // DRAW HEALTH BAR FOR ENEMY (SEE drawHealthBar() METHOD BELOW)
        drawHealthBar(graphics2D, enemyBoxX, enemyBoxY, activeEnemy, false);

        int playerBoxX = gamePanel.getScreenWidth() - (int)(tileSize * 5); // COORDS X FOR HEALTHBAR PLAYER
        int playerBoxY = boxY - (int)(tileSize * 1.8); // COORDS Y FOR HEALTHBAR PLAYER

        // DRAW HEALTH BAR FOR PLAYER (SEE drawHealthBar() METHOD BELOW)
        drawHealthBar(graphics2D, playerBoxX, playerBoxY, activePlayer, true);

        int rightBoxWidth = tileSize * 5; // WIDTH OF RIGHT BOX (CONTAINER FOR FIGHT, RUN, SWITCH, BAG)
        int rightBoxX = gamePanel.getScreenWidth() - rightBoxWidth; // COORDS X IS LEFTOVER SPACE FROM LEFT CONTAINER
        int leftBoxWidth = gamePanel.getScreenWidth() - rightBoxWidth; // WIDTH OF LEFT BOX IS LEFTOVER OF RIGHT CONTAINER
        int leftBoxX = 0; // COORDS X OF LEFT CONTAINER (LEFTMOST)
        
        // DRAW THE TWO SQUARES OR HIGHLIGHT BOXES OF LEFT AND RIGHT CONTAINER (SEE drawSubWindow() METHOD BELOW)
        drawSubWindow(graphics2D, leftBoxX, boxY, leftBoxWidth, boxHeight);
        drawSubWindow(graphics2D, rightBoxX, boxY, rightBoxWidth, boxHeight);

        graphics2D.setFont(dynamicFont);
        graphics2D.setColor(Color.WHITE);

        int textX = leftBoxX + (tileSize / 2); // STARTING HORIZONTAL POSITION FOR TEXT (LEFT BY HALF A TILE RELATIVE TO LEFT BOXX)
        int textY = boxY + tileSize; // STARTING VERTICAL POSITION FOR TEXT (UP BY ONE TILE RELATIVE TO BOXY)
        int menuX = rightBoxX + tileSize; // INITIAL X POSITION OF SELECTABLES ON RIGHT CONTAINER
        int menuY = boxY + tileSize; // INITIAL Y POSITION OF SELECTABLES ON RIGHT CONTAINER
        int xSpace = tileSize * 2; // HORIZONTAL PADDING
        int ySpace = (int) (tileSize * 0.75); // VERTICAL PADDING

        int subState = gamePanel.battleSystem.getBattleSubState(); // CONTROLS WHAT UI WILL BE DISPLAYED (subState 1 UNTIL 9)
        int currentOption = gamePanel.battleSystem.getOptionSelected(); // JUST GETS OPTION FROM BATTLE SYSTEM (0-3)
        int cursorOffset = tileSize / 3; // THIS IS THE POSITION OF ">" ON RIGHT CONTAINER

        if (subState == 0) { // IN BATTLESYSTEM.JAVA THIS IS initiateActionSelectionState();
            // WE USE OUR CONFIGURATIONS ABOVE TO DRAW THE OPTIONS FOR LEFT AND RIGHT CONTAINER
            graphics2D.drawString("What will", textX, textY);
            graphics2D.drawString(activePlayer.getName() + " do?", textX, textY + (tileSize / 2));
            graphics2D.drawString("FIGHT", menuX, menuY);
            graphics2D.drawString("BAG", menuX + xSpace, menuY);
            graphics2D.drawString("SWAP", menuX, menuY + ySpace);
            graphics2D.drawString("RUN", menuX + xSpace, menuY + ySpace);
        } else if (subState == 1) { // IN BATTLEYSYSTEM THIS IS initiateMoveSelectionState();
            // NOTE IF A POKEROT DOES NOT HAVE FULL 4 MOVES DRAW A DASH "-" OPTION INSTEAD
            graphics2D.drawString("Select a move!", textX, textY);
            graphics2D.setFont(moveFont);
            if (activePlayer.getMove(0) != null) graphics2D.drawString(activePlayer.getMove(0).getName(), menuX, menuY);
            else graphics2D.drawString("-", menuX, menuY);
            if (activePlayer.getMove(1) != null) graphics2D.drawString(activePlayer.getMove(1).getName(), menuX + xSpace, menuY);
            else graphics2D.drawString("-", menuX + xSpace, menuY);
            if (activePlayer.getMove(2) != null) graphics2D.drawString(activePlayer.getMove(2).getName(), menuX, menuY + ySpace);
            else graphics2D.drawString("-", menuX, menuY + ySpace);
            if (activePlayer.getMove(3) != null) graphics2D.drawString(activePlayer.getMove(3).getName(), menuX + xSpace, menuY + ySpace);
            else graphics2D.drawString("-", menuX + xSpace, menuY + ySpace);

        } else if (subState >= 2 && subState <= 10) { // IN BATTLESYSTEM THIS IS initiateCalculateEnemyDamageReceivedState();
            String currentMessage = gamePanel.battleSystem.getCurrentMessage();
            // WHILE ENEMY IS "TAKING DMG" FROM ANIMATION JUST DRAW THE TEXT AS PRESS ENTER
            graphics2D.drawString(currentMessage, textX, textY);
            if (subState == 2 || subState == 4 || subState == 6 || subState == 7 || subState == 9 || subState == 10) {
                graphics2D.drawString("Press Enter", menuX, menuY + (ySpace / 2));
            } else if (subState == 8 && activePlayer.getDrawnExp() == activePlayer.getExp()) {
                graphics2D.drawString("Press Enter", menuX, menuY + (ySpace / 2));
            } else if (subState == 3 && activeEnemy.getDrawnHP() == activeEnemy.getCurrentHP()) {
                graphics2D.drawString("Press Enter", menuX, menuY + (ySpace / 2));
            } else if (subState == 5 && activePlayer.getDrawnHP() == activePlayer.getCurrentHP()) {
                graphics2D.drawString("Press Enter", menuX, menuY + (ySpace / 2));
            }
        } else if (subState == 11) { // THIS IS SWAPPING UI
            if (gamePanel.battleSystem.getCurrentMessage().isEmpty()) graphics2D.drawString("Swap with who?", textX, textY);
            else graphics2D.drawString(gamePanel.battleSystem.getCurrentMessage(), textX, textY);
        graphics2D.setFont(moveFont); // USING SMALLER FONT TO FIT NAMES AND HP
        ArrayList <PokeRot> party = gamePanel.getPlayer().getPlayerParty();

        int swapMenuX = rightBoxX + (tileSize / 2);
        int swapXSpace = (int) (tileSize * 2.3);

        for (int i = 0; i < 4; i++) {
            int drawX = swapMenuX;
            int drawY = menuY;
            if (i % 2 != 0) drawX += swapXSpace; // MOVE RIGHT FOR SLOT 1 AND 3
            if (i >= 2) drawY += ySpace; // MOVE DOWN FOR SLOT 2 AND 3
            if (i < party.size()) {
                PokeRot playerRot = party.get(i);
                String label = playerRot.getName();
                if (playerRot == activePlayer) label += " (In)";
                else if (playerRot.getCurrentHP() <= 0) label += " (Fnt)";
                else label += " (" + playerRot.getCurrentHP() + "HP)";
                graphics2D.drawString(label, drawX, drawY);
            } else {
                graphics2D.drawString("-", drawX, drawY);
                }
            }
        } else if (subState == 12) {
            if (gamePanel.battleSystem.getCurrentMessage().isEmpty()) {
                graphics2D.drawString("Open Bag:", textX, textY);
            } else {
                graphics2D.drawString(gamePanel.battleSystem.getCurrentMessage(), textX, textY);
            }
            graphics2D.setFont(moveFont);
            graphics2D.drawString("Malunggay Pandesal (x99)", menuX, menuY);
            graphics2D.drawString("-", menuX + xSpace, menuY);
            graphics2D.drawString("Plunger (x99)", menuX, menuY + ySpace);
            graphics2D.drawString("-", menuX + xSpace, menuY + ySpace);
        }
        // IF AT initiateActionSelectionState(); OR initiateMoveSelectionState(); READ THE ">" CURSOR OFFSET AND DRAW IT
        if (subState == 0 || subState == 1 || subState == 12) {
            if (currentOption == 0) graphics2D.drawString(">", menuX - cursorOffset, menuY);
            if (currentOption == 1) graphics2D.drawString(">", menuX + xSpace - cursorOffset, menuY);
            if (currentOption == 2) graphics2D.drawString(">", menuX - cursorOffset, menuY + ySpace);
            if (currentOption == 3) graphics2D.drawString(">", menuX + xSpace - cursorOffset, menuY + ySpace);
        } else if (subState == 11) {
            int swapMenuX = rightBoxX + (tileSize / 2);
            int swapXSpace = (int) (tileSize * 2.3);
            if (currentOption == 0) graphics2D.drawString(">", swapMenuX - cursorOffset, menuY);
            if (currentOption == 1) graphics2D.drawString(">", swapMenuX + swapXSpace - cursorOffset, menuY);
            if (currentOption == 2) graphics2D.drawString(">", swapMenuX - cursorOffset, menuY + ySpace);
            if (currentOption == 3) graphics2D.drawString(">", swapMenuX + swapXSpace - cursorOffset, menuY + ySpace);
        }
    }

    // THIS METHOD IS RESPONSIBLE FOR DRAWING THE LEFT AND RIGHT CONTANIER (HIGHLIGHT)
    public void drawSubWindow(Graphics2D graphics2D, int x, int y, int width, int height) {
        int tileSize = gamePanel.getTileSize(); // SHORTCUT VARIABLE
        int arcSize = tileSize / 3; // I DID NOT DEFINE ARCHEIGHT ARCWIDTH CUZ IT'S SQUARE AND USES SAME DIMENSION
        int borderThickness = Math.max(1, tileSize / 20); // SM ARBITRARY VALUE FOR THE THICKNESS

        Color blackBox = new Color(0, 0, 0, 210); // 210 MAKES IT SLIGHTLY SEE THROUGH
        graphics2D.setColor(blackBox);
        graphics2D.fillRoundRect(x, y, width, height, arcSize, arcSize);

        Color whiteBorder = new Color(255, 255, 255);
        graphics2D.setColor(whiteBorder);
        graphics2D.setStroke(new BasicStroke(borderThickness));
        graphics2D.drawRoundRect(x + borderThickness, y + borderThickness, width - (borderThickness * 2), height - (borderThickness * 2), arcSize - borderThickness, arcSize - borderThickness);
    }

    // THIS METHOD IS RESPONSIBLE FOR DRAWING BOTH PLAYER AND ENEMY HEALTH BARS DEPENDING ON WHAT IS CALLED
    // IT FOLLOWS THE SAME CALCULATION PROCEDURE I DID ABOVE 
    public void drawHealthBar (Graphics2D graphics2D, int x, int y, PokeRot pokerot, boolean isPlayer) {
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
        graphics2D.setStroke(new BasicStroke(borderThickness));
        graphics2D.drawRect(barX, barY, barWidth, barHeight);

        if (isPlayer) {
            int expBarY = barY + barHeight + (tileSize / 10);
            int expBarHeight = tileSize / 8;

            graphics2D.setColor(new Color(50, 50, 50));
            graphics2D.fillRect(barX, expBarY, barWidth, expBarHeight);

            double expRatio = pokerot.getDrawnExp() / pokerot.getExpNeeded();
            int currentExpWidth = (int) (barWidth * expRatio);

            graphics2D.setColor(Color.CYAN);
            graphics2D.fillRect(barX, expBarY, currentExpWidth, expBarHeight);

            graphics2D.setColor(Color.WHITE);
            graphics2D.setStroke(new BasicStroke(1));
            graphics2D.drawRect(barX, expBarY, barWidth, expBarHeight);
        }
    }

    // THIS METHOD IS RESPONSIBLE FOR CHANGING THE UI POKEROT TO WHOMEVER IS FIGHTING, IT CHANGES
    public void loadFighterImages (PokeRot player, PokeRot enemy) {
        try {
            String playerFileName = player.getName().replace(" ", "_") + "_Back.png";
            String enemyFileName = enemy.getName().replace(" ", "_") + ".png";

            playerRot = ImageIO.read(new FileInputStream("Assets/PokeRotBack/" + playerFileName));
            enemyRot = ImageIO.read(new FileInputStream("Assets/PokeRots/" + enemyFileName));
        } catch (IOException ioE) {
            ioE.printStackTrace();
        }
    }

}