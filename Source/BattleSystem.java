//import java.util.ArrayList;

public class BattleSystem {
    private GamePanel gamePanel;
    private int optionSelected = 0;
    private int keyCooldown = 0;
    private int battleSubState = 0; // 0 = Main Menu, 1 = Move Menu, 2 = Message Queue
    private String dialogText = "";
    private Move playerMoveToUse;
    private int expGainedThisBattle = 0; // temporarily stores this changing variable in each battle
    private boolean leveledUpThisRound = false;

    private PokeRot activePlayer;
    private PokeRot activeEnemy;

    public BattleSystem(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public String getCurrentMessage () { return this.dialogText; }

    public void startEncounter (PokeRot playerCurrentRot, PokeRot wildRot) {
        this.activePlayer = playerCurrentRot;
        this.activeEnemy = wildRot;
        this.battleSubState = 0;
        this.optionSelected = 0;
    }

    // Getters so the UI ccan draw the correct fighters
    public PokeRot getActivePlayer () { return this.activePlayer; }
    public PokeRot getActiveEnemy () { return this.activeEnemy; }

    public void update() {
        if (activePlayer != null) activePlayer.update();
        if (activeEnemy != null) activeEnemy.update();
        if (keyCooldown > 0) {
            keyCooldown--;
        } else {
            // STATE 0 MAIN MENU 
            if (battleSubState == 0) { // it battlesubstate na 0 meaning ada kita ha "fight", "swap", "bag", "run" na menu
                if (gamePanel.keyHandler.getUpPressed()) { // if w is pressed
                    optionSelected -= 2; // we start at top left! which is "fight" so we just go down to "swap"
                    if (optionSelected < 0) optionSelected += 4; // if we went down to swap already, just tp back to "fight"
                    keyCooldown = 12; // becuz we paint many times per second we add this so the cursor won't go to mars
                } else if (gamePanel.keyHandler.getDownPressed()) { // if s is pressed
                    optionSelected += 2; // we're at "swap" so just move up to "fight"
                    if (optionSelected > 3) optionSelected -= 4; // if we're already at "fight" tp back to "swap" 
                    keyCooldown = 12; // cooldown ako
                }  else if (gamePanel.keyHandler.getLeftPressed()) { // if a is pressed
                    if (optionSelected == 1 || optionSelected == 3) optionSelected --; // if nasa left or right it cursor bawasan la para na alternate
                    keyCooldown = 12; // cooldown ako
                } else if (gamePanel.keyHandler.getRightPressed()) { // if d is pressed
                    if (optionSelected == 0 || optionSelected == 2) optionSelected++; // if nasa left or right pero "d" an pinindot dugngan la
                    keyCooldown = 12; // cooldown
                } else if (gamePanel.keyHandler.getEnterPressed()) { // if nag enter na, makadto next substate tapos mareset it selection ha top left
                    if (optionSelected == 0) {
                        battleSubState = 1; // move to attack selection state
                        optionSelected = 0; // reset it cursor ha top left
                    }
                    keyCooldown = 30; // kabug-usan na cooldown after mag switch it substates
                }
            } // SUBSTATE 1 MOVE SELECTION STATE
            else if (battleSubState == 1) { // move selection state ini it 1: example "tackle"
            // actually similar la ha igbaw ini na mga keyhandler, except now we're checking han moves na
                if (gamePanel.keyHandler.getUpPressed()) {  
                    optionSelected -= 2;
                    if (optionSelected < 0) optionSelected += 4;
                    keyCooldown = 12;
                } else if (gamePanel.keyHandler.getDownPressed()) {
                    optionSelected += 2;
                    if (optionSelected > 3) optionSelected -= 4;
                    keyCooldown = 12;
                } else if (gamePanel.keyHandler.getLeftPressed()) {
                    if (optionSelected == 1 || optionSelected == 3) optionSelected--;
                    keyCooldown = 12;
                } else if (gamePanel.keyHandler.getRightPressed()) {
                    if (optionSelected == 0 || optionSelected == 2) optionSelected++;
                    keyCooldown = 12;
                } else if (gamePanel.keyHandler.getEscPressed()) {
                    battleSubState = 0;
                    optionSelected = 0;
                    keyCooldown = 30;
                } else if (gamePanel.keyHandler.getEnterPressed()) { // if naka pili na hin move ma execute na ini
                    playerMoveToUse = activePlayer.getMove(optionSelected);
                    if (playerMoveToUse != null) { // if naexist an move na napili meaning diri "-"
                        dialogText = activePlayer.getName() + " used " + playerMoveToUse.getName() + "!"; // amo ini an "pokerot used tackle!"
                        battleSubState = 2; // move to next battle state which is an damage calculation
                        optionSelected = 0; // reset cursor top left
                    }
                    keyCooldown = 30; // kabug-usan na cooldown para bawal mag spam ha pagswittch hin substate
                }
            }
            // STATE 2 PLAYER AND ENEMY CALCULATION STATE
            else if (battleSubState == 2) {
                if (gamePanel.keyHandler.getEnterPressed()) {
                    // an mga dmg calculation la ito ha ubos hehe
                    int playerDamage = playerMoveToUse.getDamage() + (activePlayer.getAttack() / 5);
                    activeEnemy.takeDamage(playerDamage);
                    battleSubState = 3; // sunod kay animation state (after ma damage an pokerot an kalaban)
                    keyCooldown = 30; // cooldown in the big 2026
                }
            }
            // STATE 3 WAITING FOR ENEMY POKEROT HP TO DRAIN
            else if (battleSubState == 3) { // nag ddraw la ini nga state
                if (activeEnemy.getDrawnHP() == activeEnemy.getCurrentHP()) {
                    if (activeEnemy.getCurrentHP() <= 0) { // if nag zero an hp an kalaban execute baba
                        dialogText = activeEnemy.getName() + " fainted";
                        battleSubState = 6; // skip to victory state if namatay an kalaban
                    } else { // enemy survives and prepare their attack
                        Move enemyMove = activeEnemy.getMove(0); // 0 ngada kay diri man hira player nga nakakapili pero at some point ig rrandomize ko
                        dialogText = activeEnemy.getName() + " retaliated with " + enemyMove.getName() + "!"; // same shi kanina pero kanan enemy
                        battleSubState = 4; // jump to enemy turn state
                    }
                } 
            }
            // STATE 4 ENEMY'S TURN
            else if (battleSubState == 4) {
                if (gamePanel.keyHandler.getEnterPressed()) { // if nag enter ka after nag drain an hp
                    Move enemyMove = activeEnemy.getMove(0); // pati ini
                    int enemyDamage = enemyMove.getDamage() + (activeEnemy.getAttack() / 5); // dmg is calculated like that
                    activePlayer.takeDamage(enemyDamage); // call takeDamage method tas ofc ipapasa ta enemyDamage
                    battleSubState = 5; // animation state (our pokerot taking dmg naka drawing)
                    keyCooldown = 30;
                }
            }
            // STATE 5 WAIT FOR OUR POKEROT'S HP TO DRAIN
            else if (battleSubState == 5) {
                if (activePlayer.getDrawnHP() == activePlayer.getCurrentHP()) {
                    if (activePlayer.getCurrentHP() <= 0) {
                        dialogText = activePlayer.getName() + " fainted...";
                        battleSubState = 7; // go to defeat state
                    } else {
                        // we died so battle is over and we go bac kto substate 0 which is main menu
                        battleSubState = 0; // main menu
                        optionSelected = 0; // reset cursor top left
                    }
                }
            }
            // SUBSTATE 6 VICTORY ENEMY DEFEATED
            else if (battleSubState == 6) {
                if (gamePanel.keyHandler.getEnterPressed()) {
                    expGainedThisBattle = activeEnemy.getBaseExpYield();
                    dialogText = activePlayer.getName() + " gained " + expGainedThisBattle + " EXP!";
                    
                    leveledUpThisRound = activePlayer.gainExp(expGainedThisBattle);
                    battleSubState = 8;
                    keyCooldown = 30;
                }
            }
            // SUBSTATE 7 DEFEAT WE GOT COOKED
            else if (battleSubState == 7) {
                if (gamePanel.keyHandler.getEnterPressed()) {
                    battleSubState = 0;
                    optionSelected = 0;

                    gamePanel.gameState = GameState.ROAMSTATE;
                    keyCooldown = 30;
                }
            }
            // SUBSTATE 8 EARNING XP
            else if (battleSubState == 8) {
                if (activePlayer.getDrawnExp() == activePlayer.getExp()) {
                    if (gamePanel.keyHandler.getEnterPressed()) {
                        if (leveledUpThisRound) {
                            dialogText = activePlayer.getName() + " has lvled up to " + activePlayer.getLevel() + "!"; 
                            battleSubState = 9;
                        } else {
                            battleSubState = 0;
                            optionSelected = 0;
                            gamePanel.gameState = GameState.ROAMSTATE;
                        }
                        keyCooldown = 30;
                    }
                }
            }
            else if (battleSubState == 9) {
                if (gamePanel.keyHandler.getEnterPressed()) {
                    battleSubState = 0;
                    optionSelected = 0;
                    gamePanel.gameState = GameState.ROAMSTATE;
                    keyCooldown = 30;
                }
            }
        }
    }

    public int getOptionSelected() { return this.optionSelected; }
    public int getBattleSubState() { return this.battleSubState; }
}