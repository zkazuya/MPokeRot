public class EncounterManager {
    private GamePanel gamePanel;
    private int encounterCooldown = 0;

    public EncounterManager (GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void update () { // CALLED EVERY FRAME TO TICK DOWN THE TIMER
        if (encounterCooldown > 0) encounterCooldown--;
    }

    public void startCooldown (int frames) { // THIS METHOD GRANT INVINCIBILITY AFTER BATTLE
        this.encounterCooldown = frames;
    } 

    public void checkEncounter () {
        if (encounterCooldown > 0) return; // IF COOLDOWN IS ACTIVE IGNORE THE GRASS (IN THIS CASE THIS METHOD)
        int randomChance = (int)(Math.random() * 100); // THIS VARIABLE MEANS SCALE TO 99%
        if (randomChance < 10) {
            gamePanel.gameState = GameState.BATTLESTATE;

        }
    }
}
