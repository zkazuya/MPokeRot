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

        npc[0].setDialogues(
                new String[] { "Hello there, Carl!", "Let's see how well you've trained your PokeRots!" },
                new String[] { "Impressive tracking metrics!", "You're ready to explore the rest of the campus core." });

        // npc[1] - Trainer_Peej
        npc[1].setDialogues(
                new String[] { "Heh, you stepped right into my vision field!", "Prepare to get completely flushed!" },
                new String[] { "Womp womp...", "Your synergy architecture is unironically clear." });

        // npc[2] - Trainer_Allen
        npc[2].setDialogues(
                new String[] { "Are your algorithms optimized for combat?", "Let's run a live test scenario!" },
                new String[] { "Ah, a critical execution exception...", "Take this loss confirmation token." });

        // npc[3] - Rival_Kazuya
        npc[3].setDialogues(
                new String[] { "Took you long enough to trace my location, Carl.", "My custom party builds are strictly tier-one.", "Let's settle this calculation right now!" },
                new String[] { "Tsk, a simple rounding variance error.", "Don't get overly confident, I'm already refactoring my assets." });

        // npc[4] - Champ_Reese
        npc[4].setDialogues(
                new String[] { "Welcome to the server core.", "Only absolute peak compilation states survive here.", "Show me your data structures!" },
                new String[] { "Magnificent deployment process!", "You have completely viralized the local network ecosystem." });
        

    }

    public void draw(Graphics2D g2) {
        for (int i = 0; i < npc.length; i++) {
            if (npc[i] != null) {
                npc[i].draw(g2);
            }
        }
    }
}