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

    public StarterSelection(GamePanel gp) {
        this.gp = gp;
        starters[0] = gp.pokeRotRegistry.getSpecificPokeRot("Tralalelo Tralala");
        starters[1] = gp.pokeRotRegistry.getSpecificPokeRot("Skibidi Toilet");
        starters[2] = gp.pokeRotRegistry.getSpecificPokeRot("Tung Tung Sahur");
    }

    public void update(KeyHandler keyHandler) {
        if (keyCooldown > 0) {
            keyCooldown--;
            return;
        }
        
        if (keyHandler.getRightPressed()) {
            if (option < 2) option++;
            keyCooldown = 12;
        } else if (keyHandler.getLeftPressed()) {
            if (option > 0) option--;
            keyCooldown = 12;
        } else if (keyHandler.getEnterPressed()) {
            // ADD THE CHOSEN POKEROT TO THE PLAYER'S EMPTY PARTY
            keyHandler.setEnterPressed(false);
            gp.getPlayer().getPlayerParty().clear();
            gp.getPlayer().getPlayerParty().add(starters[option]);
            
            // FIND THE FIRST NPC AND INITIATE THE TUTORIAL BATTLE DIALOGUE!
            gp.gameState = GameState.ROAMSTATE;
            NPC targetGiver = null;
            for (NPC targetNPC : gp.npcManager.npc) {
                if (targetNPC != null && targetNPC.getName().equals("Nagpatakas an ayam")) {
                    targetGiver = targetNPC;
                    break;
                }
            }

            if (targetGiver != null) {
                gp.dialogue.startDialogue(targetGiver.getPreBattleDialogue(), targetGiver);
            } else {
                gp.gameState = GameState.ROAMSTATE; // Failsafe
            }
            keyCooldown = 16;
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
        
        g2.setFont(new Font("Arial", Font.BOLD, 28));
        g2.drawString("Prof._mmY:", dx + 30, dy + 40);
        g2.setFont(new Font("Arial", Font.PLAIN, 24));
        g2.drawString("Welcome to UP! Choose your starting PokeRot carefully!", dx + 30, dy + 80);
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        g2.drawString("Press A/D to select, ENTER to confirm >", dx + dWidth - 400, dy + 100);

        // DRAW THE 3 STARTER BLOCKS HORIZONTALLY
        for (int i = 0; i < 3; i++) {
            int blockX = 80 + (i * 475); 
            int blockY = 250;
            
            if (i == option) { // HIGHLIGHT THE CURRENTLY SELECTED POKEROT
                g2.setColor(new Color(255, 255, 0, 80)); 
                g2.fillRoundRect(blockX - 10, blockY - 10, 420, 200, 25, 25);
                g2.setColor(Color.YELLOW);
                g2.drawRoundRect(blockX - 10, blockY - 10, 420, 200, 25, 25);
            }
            drawTemplate(g2, starters[i], blockX, blockY);
        }
    }

    public void drawTemplate(Graphics2D g2, PokeRot pokerot, int x, int y) {
        int width = 180;
        int height = 180;

        drawProfile(g2, pokerot, x, y, width, height);
   
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(4));
        g2.drawRoundRect(x, y, width, height, 25, 25);

        int textX = x + width + 15;         
        int barWidth = 180;
        int barHeight = 15;

        g2.setColor(Color.WHITE);  
        g2.setFont(new Font("Arial", Font.BOLD, 22));
        g2.drawString(pokerot.getName(), textX, y + 40);

        g2.setFont(new Font("Arial", Font.PLAIN, 18));
        g2.drawString("HP: " + pokerot.getCurrentHP() + "/" + pokerot.getMaxHP(), textX, y + 80);   
        
        g2.setColor(Color.GRAY);
        g2.fillRoundRect(textX, y + 90, barWidth, barHeight, 5, 5);
        double hpWidth = ((double)pokerot.getCurrentHP()/pokerot.getMaxHP()) * barWidth;
        g2.setColor(Color.GREEN);
        g2.fillRoundRect(textX, y + 90, (int)hpWidth, barHeight, 5, 5);

        g2.setColor(Color.WHITE);
        g2.drawString("Attack: " + pokerot.getAttack(), textX, y + 140);   
    }

    public void drawProfile(Graphics2D g2, PokeRot pokerot,  int x, int y, int w, int h) { 
        try{
            String name = pokerot.getName();
            String pokerotFileName = name.replace(" ", "_") + ".png";
            BufferedImage pokeImage = ImageIO.read(new FileInputStream("Assets/PokeRots/" + pokerotFileName));
            g2.drawImage(pokeImage, x + 5, y + 5, w - 10, h - 10, null);
        } catch (IOException ioE){
            g2.setColor(Color.DARK_GRAY);
            g2.fillRect(x+5, y+5, w-10, h-10);
        }
    }
}