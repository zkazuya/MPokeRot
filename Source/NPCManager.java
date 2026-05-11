import java.awt.Graphics2D;

public class NPCManager {
    GamePanel gamePanel;
    public NPC[] npc; 

    public NPCManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.npc = new NPC[5];
    }

    public void setUpNPC() {
        npc[0] = new NPC(gamePanel, gamePanel.getTileSize() * 15, gamePanel.getTileSize() * 10, 0, "Prof._mmY");
        npc[1] = new NPC(gamePanel, gamePanel.getTileSize() * 20, gamePanel.getTileSize() * 20, 1, "Trainer_Peej");
        npc[2] = new NPC(gamePanel, gamePanel.getTileSize() * 25, gamePanel.getTileSize() * 25, 2, "Trainer_Allen");
        npc[3] = new NPC(gamePanel, gamePanel.getTileSize() * 30, gamePanel.getTileSize() * 30, 3, "Rival_Kazuya");
        npc[4] = new NPC(gamePanel, gamePanel.getTileSize() * 35, gamePanel.getTileSize() * 35, 4, "Champ_Reese");

        npc[0].addPokeRot(gamePanel.pokeRotRegistry.getSpecificPokeRot("Holeee"));
        npc[0].addPokeRot(gamePanel.pokeRotRegistry.getSpecificPokeRot("Boneca Ambalabu"));
        npc[1].addPokeRot(gamePanel.pokeRotRegistry.getSpecificPokeRot("Skibidi Toilet"));
        npc[2].addPokeRot(gamePanel.pokeRotRegistry.getSpecificPokeRot("Frulli Frulla"));
        npc[3].addPokeRot(gamePanel.pokeRotRegistry.getSpecificPokeRot("Boneca Ambalabu"));
        npc[4].addPokeRot(gamePanel.pokeRotRegistry.getSpecificPokeRot("Brr Brr Patapim"));

        npc[0].setDialogues(new String[]{"hello there", "yoou da real nigga"}, new String[]{"damn you beat me bro"});
        

    }

    public void draw(Graphics2D g2) {
        for (int i = 0; i < npc.length; i++) {
            if (npc[i] != null) {
                npc[i].draw(g2);
            }
        }
    }
}