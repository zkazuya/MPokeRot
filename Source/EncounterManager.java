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
        int randomChance = (int)(Math.random() * 100); // THIS VARIABLE MEANS SCALE TO 99%
        if (randomChance < 10) {
            gamePanel.gameState = GameState.BATTLESTATE;

            PokeRot playerRot = player.getFirstAlivePokeRot();
            PokeRot wildRot = gamePanel.pokeRotRegistry.generateRandomPokeRot();
            gamePanel.battleSystem.startEncounter(playerRot, wildRot);
        }
    }
}
