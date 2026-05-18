package battle;

import entity.Player;
import java.util.ArrayList;
import main.GamePanel;
import pokerot.PokeRot;

public class EncounterManager {
    private GamePanel gamePanel;
    private int encounterCooldown = 0;
    private Player player;

    public EncounterManager (GamePanel gamePanel, Player player) {
        this.gamePanel = gamePanel;
        this.player = player;
    }

    public void update () { // CALLED EVERY FRAME TO TICK DOWN THE TIMER
        if (encounterCooldown > 0) encounterCooldown--;
    }

    public void startCooldown (int frames) { // THIS METHOD GRANT INVINCIBILITY AFTER BATTLE
        this.encounterCooldown = frames;
    } 

    public void checkEncounter () {
        if (encounterCooldown > 0) return; // IF COOLDOWN IS ACTIVE IGNORE THE GRASS (IN THIS CASE THIS METHOD)
        if (player.getPlayerParty().isEmpty()) return;
        int randomChance = (int)(Math.random() * 100); // THIS VARIABLE MEANS SCALE TO 99%
        if (randomChance < 10) {

            PokeRot playerRot = player.getFirstAlivePokeRot();
            if (playerRot == null) return;
            PokeRot wildRot = gamePanel.pokeRotRegistry.generateRandomPokeRot();
            int playerLevel = playerRot.getLevel();
            int levelOffSet = (int) (Math.random() * 3) - 1;
            int targetLevel = playerLevel + levelOffSet;
            if (targetLevel < 1) targetLevel = 1;
            wildRot.jumpToLevel(targetLevel);
            ArrayList <PokeRot> wildParty = new ArrayList<>();
            wildParty.add(wildRot);
            gamePanel.battleSystem.startEncounter(wildParty, null);
            gamePanel.startTransition();
        }
    }


}