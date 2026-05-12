
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;



public class StarterSelection {
    private GamePanel gp;
    private int option = 0;
    private PokeRot[] starters = new PokeRot[3];
    private int keyCooldown = 0;
    
    // --- NEW: STATE MACHINE FOR STORY PROGRESSION ---
    private int selectionState = 0; // 0 = Story Intro, 1 = Picking Starter
    private int storyIndex = 0;
    private String[] storyText;

    public StarterSelection(GamePanel gp) {
        this.gp = gp;
        starters[0] = gp.pokeRotRegistry.getSpecificPokeRot("Tralalelo Tralala");
        starters[1] = gp.pokeRotRegistry.getSpecificPokeRot("Skibidi Toilet");
        starters[2] = gp.pokeRotRegistry.getSpecificPokeRot("Tung Tung Sahur");
    }

    public void update(KeyHandler keyHandler) {
        // Dynamically load the story text here so it can grab the player's saved name!
        if (storyText == null) {
            String pName = gp.titlePanel.getPlayerName();
            if (pName == null || pName.isEmpty()) pName = "Student";
            
            storyText = new String[] {
                "Pookie: Welcome to UP, " + pName + "!",
                "Pookie: Disaster has struck! The CS Building servers just leaked!",
                "Pookie: The brainrots—I mean, PokeRots—have escaped and are running wild!",
                "Pookie: We need you to catch them all and find the one who did this.",
                "Pookie: It's dangerous to go out there alone... Take one of these!"
            };
        }

        if (keyCooldown > 0) {
            keyCooldown--;
            return;
        }

        if (selectionState == 0) { // --- STORY PHASE ---
            if (keyHandler.getEnterPressed()) {
                storyIndex++;
                if (storyIndex >= storyText.length) {
                    selectionState = 1; // Move to the selection phase!
                }
                keyHandler.setEnterPressed(false);
                keyCooldown = 16;
            }
            
        } else if (selectionState == 1) { // --- PICKING PHASE ---
            if (keyHandler.getRightPressed()) {
                if (option < 2) option++;
                keyCooldown = 12;
            } else if (keyHandler.getLeftPressed()) {
                if (option > 0) option--;
                keyCooldown = 12;
            } else if (keyHandler.getEnterPressed()) {
                keyHandler.setEnterPressed(false);
                gp.getPlayer().getPlayerParty().clear();
                gp.getPlayer().getPlayerParty().add(starters[option]);
                
                gp.gameState = GameState.ROAMSTATE;
                
                NPC targetGiver = null;
                for (NPC targetNPC : gp.npcManager.npc) {
                    if (targetNPC != null && targetNPC.getName().equals("Nagpatakas an ayam")) {
                        targetGiver = targetNPC;
                        break;
                    }
                }

                if (targetGiver != null) {
                    gp.dialogue.startDialogue(targetGiver.getPreBattleDialogue(), null);
                } else {
                    gp.gameState = GameState.ROAMSTATE; // Failsafe
                }
                keyCooldown = 16;
            }
        }
    }

    public void draw(Graphics2D g2) {
        // DARKEN BACKGROUND
        g2.setColor(new Color(0, 0, 0, 230));
        g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());

        // DIALOGUE BOX AT THE BOTTOM
        int dx = 20, dy = gp.getScreenHeight() - 150, dWidth = gp.getScreenWidth() - 40, dHeight = 120;
        g2.setColor(new Color(0, 0, 0, 210));
        g2.fillRoundRect(dx, dy, dWidth, dHeight, 35, 35);
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(dx, dy, dWidth, dHeight, 25, 25);
        
        if (selectionState == 0) {
            // DRAW STORY TEXT
            g2.setFont(new Font("Arial", Font.BOLD, 16));

            if (storyText != null && storyIndex < storyText.length) {
                g2.drawString(storyText[storyIndex], dx + 30, dy + 60);
            }

            g2.setFont(new Font("Arial", Font.BOLD, 18));
            g2.drawString("Press ENTER >", dx + dWidth - 180, dy + 100);
            
        } else if (selectionState == 1) {
            // DRAW STARTER SELECTION INSTRUCTIONS
            g2.setFont(new Font("Arial", Font.BOLD, 28));
            g2.drawString("Pookie:", dx + 30, dy + 40);
            g2.setFont(new Font("Arial", Font.PLAIN, 24));
            g2.drawString("Choose your starting PokeRot carefully!", dx + 30, dy + 80);
            g2.setFont(new Font("Arial", Font.BOLD, 18));
            g2.drawString("Press A/D to select, ENTER to confirm >", dx + dWidth - 400, dy + 100);

            // --- FIXED: PERFECTLY BALANCED HORIZONTAL MATH ---
            int cardWidth = 240; 
            int totalBoxesWidth = 3 * cardWidth; 
            int remainingSpace = gp.getScreenWidth() - totalBoxesWidth;
            int gap = remainingSpace / 4; // Distributes space evenly across 4 gaps

            for (int i = 0; i < 3; i++) {
                int blockX = gap + (i * (cardWidth + gap)); 
                int blockY = (gp.getScreenHeight() / 2) - 170; // Centered vertically
                
                if (i == option) { // HIGHLIGHT THE CURRENTLY SELECTED POKEROT
                    g2.setColor(new Color(255, 255, 0, 80)); 
                    g2.fillRoundRect(blockX - 10, blockY - 10, cardWidth + 20, 300, 25, 25);
                    g2.setColor(Color.YELLOW);
                    g2.drawRoundRect(blockX - 10, blockY - 10, cardWidth + 20, 300, 25, 25);
                }
                drawTemplate(g2, starters[i], blockX, blockY, cardWidth);
            }
        }
    }

    public void drawTemplate(Graphics2D g2, PokeRot pokerot, int x, int y, int width) {
        int height = 280; // Changed to a vertical "Trading Card" shape

        // Draw dark card background
        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRoundRect(x, y, width, height, 25, 25);

        // Draw profile Image centered at the top
        drawProfile(g2, pokerot, x + 40, y + 10, 160, 160);
   
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(4));
        g2.drawRoundRect(x, y, width, height, 25, 25);

        int textX = x + 20;         
        int barWidth = 200;
        int barHeight = 15;

        // Draw stats vertically underneath the image
        g2.setColor(Color.WHITE);  
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        g2.drawString(pokerot.getName(), textX, y + 190);

        g2.setFont(new Font("Arial", Font.PLAIN, 16));
        g2.drawString("HP: " + pokerot.getCurrentHP() + "/" + pokerot.getMaxHP(), textX, y + 215);   
        
        g2.setColor(Color.GRAY);
        g2.fillRoundRect(textX, y + 225, barWidth, barHeight, 5, 5);
        double hpWidth = ((double)pokerot.getCurrentHP()/pokerot.getMaxHP()) * barWidth;
        g2.setColor(Color.GREEN);
        g2.fillRoundRect(textX, y + 225, (int)hpWidth, barHeight, 5, 5);

        g2.setColor(Color.WHITE);
        g2.drawString("Attack: " + pokerot.getAttack(), textX, y + 265);   
    }

    public void drawProfile(Graphics2D g2, PokeRot pokerot,  int x, int y, int w, int h) { 
        try {
            String name = pokerot.getName();
            String pokerotFileName = name.replace(" ", "_") + ".png";
            BufferedImage pokeImage = ImageIO.read(new FileInputStream("Assets/PokeRots/" + pokerotFileName));
            g2.drawImage(pokeImage, x, y, w, h, null);
        } catch (IOException ioE) {
            g2.setColor(Color.DARK_GRAY);
            g2.fillRect(x, y, w, h);
        }
    }
}