import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class NPC extends Entity{
    int type; // INDEX OF THE NPCS IN THE ARRAY OF NPC
    private String name;
    private boolean isDefeated = false; // BY DEFAULT ALL NPCS ARE NOT DEFEATED (MEANS BATTLEABLE)
    private ArrayList <PokeRot> npcParty; // THE ARRAYLIST OF POKEROTS OF THE ENEMY NPC
    GamePanel gamePanel;
    private String[] preBattleDialogue;
    private String[] postBattleDialogue;

    public NPC(GamePanel gamePanel, int x, int y, int type, String name){ //char variable is for what npc
        this.x = x;
        this.y = y;
        this.type = type;
        this.gamePanel = gamePanel;
        this.name = name;
        this.npcParty = new ArrayList<>();
        loadImages();
    }

    public void addPokeRot (PokeRot pokeRot) {
        if (npcParty.size() < 3 && pokeRot != null) npcParty.add(pokeRot);
    }

    public PokeRot getFirstAlivePokeRot () {
        for (PokeRot eachPokeRot : npcParty) {
            if (eachPokeRot.getCurrentHP() > 0) return eachPokeRot;
        }
        return null;
    }

    public void loadImages(){
        try { //NO NEED TO INITIALIZE DOWNSPRITES SINCE IT'S BEEN INITIALIZED IN ENTITY
        //LIST OF NPCs by int
            npcParty = new ArrayList<>();
            downSprites = new BufferedImage[5];
            downSprites[0] = ImageIO.read(getClass().getResourceAsStream("/Assets/NPCRoam/Healer_Overworld.png"));
            downSprites[1] = ImageIO.read(getClass().getResourceAsStream("/Assets/NPCRoam/PokeGIVER.png"));
            downSprites[2] = ImageIO.read(getClass().getResourceAsStream("/Assets/NPCRoam/Prof._mmY.png"));
            downSprites[3] = ImageIO.read(getClass().getResourceAsStream("/Assets/NPCRoam/Sir_Sam_NPC.png"));
           // downSprites[4] = ImageIO.read(getClass().getResourceAsStream("/Assets/NPCRoam/Champ_Therese.png"));
            
        } catch(Exception e) {e.printStackTrace();}        
    }

    public void draw(Graphics2D g2) {
        if (downSprites[type]!= null) {
            int screenX = x - gamePanel.getPlayer().getX() + gamePanel.getPlayer().getScreenX();
            int screenY = y - gamePanel.getPlayer().getY() + gamePanel.getPlayer().getScreenY();

            if (x + gamePanel.getTileSize() > gamePanel.getPlayer().getX() - gamePanel.getPlayer().getScreenX() &&
                x - gamePanel.getTileSize() < gamePanel.getPlayer().getX() + gamePanel.getPlayer().getScreenX() &&
                y + gamePanel.getTileSize() > gamePanel.getPlayer().getY() - gamePanel.getPlayer().getScreenY() &&
                y - gamePanel.getTileSize() < gamePanel.getPlayer().getY() + gamePanel.getPlayer().getScreenY()) {
                    g2.drawImage(downSprites[type], screenX, screenY, (int)(gamePanel.getTileSize() * 1.3), (int)(gamePanel.getTileSize() * 1.3), null);
            }
        }
    }

    public void setDialogues (String[] pre, String[] post) {
        this.preBattleDialogue = pre;
        this.postBattleDialogue = post;
    }

    public String getName () { return this.name; }
    public boolean isDefeated () { return this.isDefeated; }
    public void setDefeated (boolean status) { this.isDefeated = status; }
    public ArrayList <PokeRot> getNPCParty () { return this.npcParty; }
    public String[] getPreBattleDialogue () { return this.preBattleDialogue; }
    public String[] getPostBattleDialogue () { return this.postBattleDialogue; }
}