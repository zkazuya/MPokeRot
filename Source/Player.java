import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;

public class Player extends Entity {
    GamePanel gamePanel;
    KeyHandler keyHandler;
    private boolean isMoving = false;
    private int pixelCounter = 0; // THIS VARIABLE IS IMPORTANT TO MAKE SURE WE GO EXACTLY ONE TILE EACH AWSD MOVEMENT
    private String direction = "down"; // DIRECTION THE PLAYER IS FACING
    private ArrayList <PokeRot> playerParty = new ArrayList<>();
    private final int screenX;
    private final int screenY;

    public Player (GamePanel gamePanel, KeyHandler keyHandler) {
        screenX = gamePanel.getScreenWidth() / 2 - (gamePanel.getTileSize() / 2); // CENTER X POSITION OF SCREEN
        screenY = gamePanel.getScreenHeight() / 2 - (gamePanel.getTileSize() / 2); // CENTER Y POSITION OF SCREEN
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        setX(gamePanel.getTileSize() * 15); // Y COORDINATE OF PLAYER AT SPAWN CHANGEABLE
        setY(gamePanel.getTileSize() * 8); // X COORDINATE OF PLAYER AT SPAWN CHANGEABLE
        setSpeed(4); // MOVEMENT SPEED IS 4 CHANGEABLE
        setAnimationSpeed(9); // DETERMINES ANIMATION SPEED
        getPlayerImage(); // LOAD ALL PLAYER SPRITES TO THE ARRAY
        PokeRot starterOne = gamePanel.pokeRotRegistry.getSpecificPokeRot("Tralalelo Tralala");
        PokeRot secondPokeRot = gamePanel.pokeRotRegistry.getSpecificPokeRot("Udin Din Din Dun");
        if (starterOne != null) playerParty.add(starterOne);
        if (secondPokeRot != null) playerParty.add(secondPokeRot);
    }

    public void update() {

        if (keyHandler.getFPressed() && !isMoving) { // IF PRESSED F AND NOT MOVING
            keyHandler.setFPressed(false); // CONSUME THE KEY SO IT DOESN'T SPAM

            int playerColumn = getX() / gamePanel.getTileSize();
            int playerRow = getY() / gamePanel.getTileSize();

            int facingColumn = playerColumn;
            int facingRow = playerRow;

            switch (direction) {
                case "up" -> facingRow--;
                case "down" -> facingRow++;
                case "left" -> facingColumn--;
                case "right" -> facingColumn++;
            }

            for (NPC targetNPC : gamePanel.npcManager.npc) {
                if (targetNPC != null) {
                    int npcColumn = targetNPC.getX() / gamePanel.getTileSize();
                    int npcRow = targetNPC.getY() / gamePanel.getTileSize();

                    if (facingColumn == npcColumn && facingRow == npcRow) {
                        if (targetNPC.isDefeated()) {
                            gamePanel.dialogue.startDialogue(targetNPC.getPostBattleDialogue(), targetNPC);
                        } else {
                            gamePanel.dialogue.startDialogue(targetNPC.getPreBattleDialogue(), targetNPC);
                        }
                        break;
                    }
                }
            }
        }

        if (isMoving == false) { // IF WE ARE NOT MOVING, DECIDE OUR CURRENT DIRECTION
            if (keyHandler.getUpPressed()) direction = "up";
            else if (keyHandler.getDownPressed()) direction = "down";
            else if (keyHandler.getLeftPressed()) direction = "left";
            else if (keyHandler.getRightPressed()) direction = "right";
            else spriteNumber = 0;
        }

        boolean upDownRightLeft = keyHandler.getUpPressed() || keyHandler.getDownPressed() || keyHandler.getLeftPressed() || keyHandler.getRightPressed();
        if (upDownRightLeft && !isMoving) {
            // FIGURE OUT WHICH COORDINATE WE'RE STANDING ON
            int targetColumn = getX() / gamePanel.getTileSize();
            int targetRow = getY() / gamePanel.getTileSize();

            // PREDICT WHERE WE ARE GOING TO STEP NEXT BASED ON DIRECTION
            switch (direction) {
                case "up" -> targetRow--;
                case "down" -> targetRow++;
                case "left" -> targetColumn--;
                case "right" -> targetColumn++;
            }

            // BOUNDARY CHECK, IF WE'RE GOING OUTSIDE THE MAP
            if (targetColumn >= 0 && targetColumn < gamePanel.getMaxWorldColumn() &&
                    targetRow >= 0 && targetRow < gamePanel.getMaxWorldRow()) {

                // TILE COLLISION CHECK
                int targetTileID = gamePanel.tileManager.getTileNumber(targetColumn, targetRow);
                boolean tileCollision = gamePanel.tileManager.getTile(targetTileID).getCollision();

                // NPC COLLISION CHECK
                boolean npcCollisionCheck = false;
                for (NPC targetNPC : gamePanel.npcManager.npc) {
                    if (targetNPC != null) {
                        int npcColumn = targetNPC.getX() / gamePanel.getTileSize();
                        int npcRow = targetNPC.getY() / gamePanel.getTileSize();
                        if (targetColumn == npcColumn && targetRow == npcRow) {
                            npcCollisionCheck = true; // WE FOUND AN NPC IN THE WAY
                            break;
                        }
                    }
                }
                if (tileCollision == false && npcCollisionCheck == false) {
                    isMoving = true;
                }
            }
        }

        if (isMoving) { // IF WE ARE MOVING IGNORE INPUT UNTIL WE WALK EXACTLY ONE TILE
            if (direction.equals("up"))
                setY(getY() - getSpeed());
            if (direction.equals("down"))
                setY(getY() + getSpeed());
            if (direction.equals("left"))
                setX(getX() - getSpeed());
            if (direction.equals("right"))
                setX(getX() + getSpeed());
            pixelCounter += getSpeed();

            spriteCounter++;
            if (spriteCounter > getAnimationSpeed()) {
                spriteNumber++;
                if (spriteNumber > 3)
                    spriteNumber = 0;
                spriteCounter = 0;
            }

            if (pixelCounter >= gamePanel.getTileSize()) { // IF THE PIXEL COUNTER IS NOW EXACTLY ONE TILE
                isMoving = false; // UNLOCK LISTENER AGAIN
                pixelCounter = 0; // RESET IT TO COUNT AGAIN

                int playerColumn = getX() / gamePanel.getTileSize();
                int playerRow = getY() / gamePanel.getTileSize();
                int currentTileID = gamePanel.tileManager.getTileNumber(playerColumn, playerRow);

                if (currentTileID == 3 || currentTileID == 13 || currentTileID == 16) {
                    gamePanel.encounterManager.checkEncounter();
                }
            }
        }
    }

    public void getPlayerImage () {
        try {
            downSprites[0] = ImageIO.read(getClass().getResourceAsStream("/Assets/Player/updown_0.png"));
            downSprites[1] = ImageIO.read(getClass().getResourceAsStream("/Assets/Player/updown_1.png"));
            downSprites[2] = ImageIO.read(getClass().getResourceAsStream("/Assets/Player/updown_2.png"));
            downSprites[3] = ImageIO.read(getClass().getResourceAsStream("/Assets/Player/updown_3.png"));
            upSprites[0] = ImageIO.read(getClass().getResourceAsStream("/Assets/Player/updown_4.png"));
            upSprites[1] = ImageIO.read(getClass().getResourceAsStream("/Assets/Player/updown_5.png"));
            upSprites[2] = ImageIO.read(getClass().getResourceAsStream("/Assets/Player/updown_6.png"));
            upSprites[3] = ImageIO.read(getClass().getResourceAsStream("/Assets/Player/updown_7.png"));  
            rightSprites[0] = ImageIO.read(getClass().getResourceAsStream("/Assets/Player/leftright_0.png"));
            rightSprites[1] = ImageIO.read(getClass().getResourceAsStream("/Assets/Player/leftright_1.png"));
            rightSprites[2] = ImageIO.read(getClass().getResourceAsStream("/Assets/Player/leftright_2.png"));
            rightSprites[3] = ImageIO.read(getClass().getResourceAsStream("/Assets/Player/leftright_3.png"));
            leftSprites[0] = ImageIO.read(getClass().getResourceAsStream("/Assets/Player/leftright_4.png"));
            leftSprites[1] = ImageIO.read(getClass().getResourceAsStream("/Assets/Player/leftright_5.png"));
            leftSprites[2] = ImageIO.read(getClass().getResourceAsStream("/Assets/Player/leftright_6.png"));
            leftSprites[3] = ImageIO.read(getClass().getResourceAsStream("/Assets/Player/leftright_7.png"));
        } catch (IOException ioE) {
            ioE.printStackTrace();
        }
    }

    public void draw (Graphics2D graphics2D) {
        BufferedImage image = null;
        switch (direction) {
            case "up" -> image = upSprites[spriteNumber];
            case "down" -> image = downSprites[spriteNumber];
            case "left" -> image = leftSprites[spriteNumber];
            case "right" -> image = rightSprites[spriteNumber];
        }

        int visualSize = (int) (gamePanel.getTileSize() * 1.2);
        int offSet = (gamePanel.getTileSize() - visualSize) / 2;
        graphics2D.drawImage(image, screenX + offSet, screenY + offSet, visualSize, visualSize, null);

    }

    public boolean hasUsablePokeRot () { // THIS METHOD IS USEFUL TO DETERMINE IF WE CAN STILL BATTLE OR NOT
        for (PokeRot eachPokeRot : playerParty) { // FOR INSTANCE WE CAN CAUSE EVENTS IF WE CAN NO LONGER BATTLE
            if (eachPokeRot.getCurrentHP() > 0) return true; // IT RETURNS TRUE IF EVEN ONE IS STILL ALIVE
        }
        return false;
    }

    public PokeRot getFirstAlivePokeRot () { // THIS METHOD IS RESPONSIBLE FOR SELECTING THE VERY FIRST POKEROT
        for (PokeRot eachPokeRot : playerParty) { // INSIDE OF THE PLAYER'S PARTY
            if (eachPokeRot.getCurrentHP() > 0) return eachPokeRot; // THE CATCH: VERY FIRST POKEROT ALIVE
        }
        return null;
    }

    public void triggerBlackout () { // THIS METHOD IS TRIGGERED IN BATTLE SYSTEM WHEN ALL OUR POKEROT DIES
        this.setX(0); // THE PLAYER SPAWNS BACK TO 0, 0 (X, Y) RESPECTIVELY
        this.setY(0); // THE FIRST POKEROT IS THE ONLY ONE LEFTOVER AND IS RESET TO LVL 1 AND STATS ACCORDINGLY
        if (!playerParty.isEmpty()) playerParty.get(0).resetToLevelOne(); // THE REST OF THE POKEROT IS THROWN
        while (playerParty.size() > 1) playerParty.remove(playerParty.size() - 1); // IN THE GARBAGE COLLECTION
        gamePanel.gameState = GameState.TALKINGSTATE;
    }

    public ArrayList <PokeRot> getPlayerParty () { return this.playerParty; }
    public int getScreenX () { return this.screenX; }
    public int getScreenY () { return this.screenY; }

}
