import java.util.ArrayList;

public class BattleSystem {
    private GamePanel gamePanel;
    private int optionSelected = 0; // START SELECTION AT TOP LEFT
    private int keyCooldown = 0; // GLOBAL COOLDOWN FROM SWITCHING KEYS
    private int battleSubState = 0; // THIS IS THE ONE BEING SWITCHED
    private String dialogText = ""; // THIS CHANGES ACCORDING TO FLOW
    private Move playerMoveToUse; 
    private Move enemyMoveToUse;
    private int expGainedThisBattle = 0; // TEMPORARILY STORE THIS VARIABLE ON EACH BATTLE
    private boolean leveledUpThisRound = false; // THIS VARIABLE IS USED FOR TRIGGERING EVENTS
    private ArrayList <PokeRot> activeEnemyParty;
    private NPC currentNPCOpponent; // IF THE ENEMY IS AN NPC, WE USE THIS INSTEAD OF THE WILD POKEROT
    private PokeRot activePlayer;
    private PokeRot activeEnemy;

    public BattleSystem(GamePanel gamePanel) { this.gamePanel = gamePanel; }

    public String getCurrentMessage () { return this.dialogText; }

    public void startEncounter (ArrayList <PokeRot> enemyParty, NPC opponent) {
        this.activeEnemyParty = enemyParty;
        this.currentNPCOpponent = opponent;

        this.activePlayer = gamePanel.getPlayer().getFirstAlivePokeRot();
        this.activeEnemy = getFirstAliveEnemyPokeRot();
        this.battleSubState = 0;
        this.optionSelected = 0;
        this.dialogText = "";
        gamePanel.battleUI.loadFighterImages(this.activePlayer, this.activeEnemy);
    }

    public void update() {
        if (activePlayer != null) activePlayer.update();
        if (activeEnemy != null) activeEnemy.update();
        if (keyCooldown > 0) {
            keyCooldown--;
        } else {
            // UTILIZE OOP AND MADE THE WHOLE LOGIC USE ENHANCED SWITCH
            switch (battleSubState){
                case 0 -> initiateActionSelectionState();
                case 1 -> initiateMoveSelectionState();
                case 2 -> initiateCalculateEnemyDamageReceivedState();
                case 3 -> initiateEnemyTakingDamageAnimationState();
                case 4 -> initiateEnemyTurnToAttackState();
                case 5 -> initiateOurPokerotTakingDamageAnimationState();
                case 6 -> initiateVictoryState();
                case 7 -> initiateWeGotCookedState();
                case 8 -> initiateEarningExpAnimationState();
                case 9 -> initiateExitBattleState();
                case 10 -> initiateRunState();
                case 11 -> initiateSwapSelectionState();
                case 12 -> initiateBagSelectionState();
            }
        }
    }

    public void twoByTwoSelectionHelper () { // SELECTION FUNCTION
        if (gamePanel.keyHandler.getUpPressed()) { // THIS IS FOR THE "W" KEY
            optionSelected -= 2; // MOVE UP IF W IS PRESSED (REMEMBER Y IS DECREASING AS WE GO UP)
            if (optionSelected < 0) optionSelected += 4; // IF WE'RE ALREADY BELOW, GO UP (SUBTRACTING BY -2 & -2 GETS US BACK TO 0, SO ADDING 4 RESETS IT BACK TO UP)
            keyCooldown = 8; // IF WE DO NOT ADD KEYCOOLDOWN SOMETIMES WHEN WE PRESS W ONCE IT GLITCHES AND IT EXECUTES TWICE
        } else if (gamePanel.keyHandler.getDownPressed()) { // THIS IS FOR THE "S" KEY
            optionSelected += 2; // MOVE DOWN IF S IS PRESSED (ADDING TO Y ACTUALLY PULLS US DOWN)
            if (optionSelected > 3) optionSelected -= 4; // IF WE CROSS Y > 3 WE GO BACK TO 0 (ADDING BY +2 & +2 GETS US BACK TO 0)
            keyCooldown = 8;
        } else if (gamePanel.keyHandler.getLeftPressed()) { // THIS IS FOR "A" KEY
            if (optionSelected % 2 == 0) optionSelected += 1; // IF WE ARE AT EVEN OPTIONSELECTED (0 OR 4) MOVE TO RIGHT (1 OR 3)
            else optionSelected -= 1; // BY MOVE WE MEAN SUBTRACT ONE
            keyCooldown = 8;
        } else if (gamePanel.keyHandler.getRightPressed()) { // THIS IS FOR "D" KEY
            if (optionSelected % 2 == 0) optionSelected += 1; // THE SAME LOGIC APPLIES FOR THE "D" KEY BECAUSE WE ARE WRAPPING AROUND 0-3
            else optionSelected -= 1;
            keyCooldown = 8;
        }
    }

    public void initiateActionSelectionState () { // FIRST STATE IN BATTLE "RUN", "FIGHT", "SWAP", "BAG"
        twoByTwoSelectionHelper(); // CALL THE SELECTION FUNCTION
        if (gamePanel.keyHandler.getEnterPressed()) { // MEANING FINALLY CHOSEN SOMETHING
            if (optionSelected == 0) { // MEANING CHOSEN "FIGHT" CUZ 0 REPRESENTS TOP LEFT
                battleSubState = 1; // MOVE TO MOVE SELECTION STATE
                optionSelected = 0; // RESET CURSOR TO TOP LEFT
            } else if (optionSelected == 1) { // THIS IS THE BAG
                dialogText = ""; // CLEAR PREVIOUS TEXTS
                battleSubState = 12; // GO TO BAG SELECTION STATE
                optionSelected = 0; // RESET CURSOR TOP LEFT
            } else if (optionSelected == 2) { // THIS IS THE SWAP
                dialogText = ""; // CLEAR ANY TEXT
                battleSubState = 11; // GO TO SWAP POKEROT STATE
                optionSelected = 0; // RESET CURSOR TOP LEFT
            } else if (optionSelected == 3) { // IF RUN OPTION IS SELECTED
                if (currentNPCOpponent != null) dialogText = "No! There's no running from a trainer battle!";
                else {
                    int runChance = (int) (Math.random() * 100);
                    if (runChance < 40) dialogText = "The brainrot got you in headlock, you cannot run!";
                    else dialogText = "Got away safely!";
                }
                battleSubState = 10; // SEND TO RUN STATE TO DISPLAY THE TEXT
                optionSelected = 0; // RESET CURSOR TO TOP LEFT
            }
            keyCooldown = 16; // GLOBAL COOLDOWN IF THIS IS GONE WE PRESS ENTER ONCE AND SKIP TWO SELECTION STATES
        }
    }

    public void initiateMoveSelectionState () { // THIS STATE IS WHEN WE'RE INSIDE MOVE SELECTION
        twoByTwoSelectionHelper(); // CALL THE SELECTION FUNCTION

        if (gamePanel.keyHandler.getEscPressed()) {
            battleSubState = 0;
            optionSelected = 0;
            keyCooldown = 16;
            return;
        }

        if (gamePanel.keyHandler.getEnterPressed()) { // IF PLAYER MADE A CHOICE, CAPTURE HIS CHOICE
            playerMoveToUse = activePlayer.getMove(optionSelected);
            if (playerMoveToUse != null) { // IF SELECTED MOVE EXISTS (PLAYER DID NOT CHOOSE "-")
                dialogText = activePlayer.getName() + " used " + playerMoveToUse.getName() + "!"; // EX: TRALALALEO USED TACKLE
                battleSubState = 2; // MOVE TO NEXT BATTLE SUBSTATE WHICH IS DMG CALCULATION
                optionSelected = 0; // RESET CURSOR TOP LEFT
                keyCooldown = 16;
            }
        }
    }

    public void initiateCalculateEnemyDamageReceivedState () { // THIS STATE IS WHEN WE CALCULATE DAMAGE RECEIVED ON ENEMY POKEROT
        if (gamePanel.keyHandler.getEnterPressed()) { // REQUIRE PLAYER TO PRESS ENTER BEFORE DOING THE CALCULATION
            int playerDamage = playerMoveToUse.getDamage() + (activePlayer.getAttack() / 5); // CALCULATE PLAYER DAMAGE BASED ON POKEROT STATS
            activeEnemy.takeDamage(playerDamage); // RUN POKEROT CLASS TAKE DAMAGE METHOD ON ACTIVENEMY OBJECT
            battleSubState = 3; // INITIATE ENEMY TAKING DAMAGE ANIMATION STATE
            keyCooldown = 16;
        }
    }

    public void initiateEnemyTakingDamageAnimationState() { // THIS STATE IS JUST RENDERING THE HP BAR OF ENEMY POKEROT DRAINING
        if (activeEnemy.getDrawnHP() == activeEnemy.getCurrentHP()) {
            if (activeEnemy.getCurrentHP() <= 0) { // IF THE ENEMY DIED
                // STOP AND SHOW FAINTED DIALOGUE
                if (!dialogText.contains("fainted")) {
                    dialogText = activeEnemy.getName() + " fainted!";
                    keyCooldown = 16;
                    return;
                }
                
                if (gamePanel.keyHandler.getEnterPressed() && keyCooldown == 0) {
                    expGainedThisBattle = activeEnemy.getBaseExpYield();
                    dialogText = activePlayer.getName() + " gained " + expGainedThisBattle + " EXP!";
                    leveledUpThisRound = activePlayer.gainExp(expGainedThisBattle);
                    battleSubState = 6;
                    keyCooldown = 16;
                }

            } else { // IF THE ENEMY IS ALIVE OR HP > 0

                // Wait for the player to press Enter before the enemy retaliates
                if (gamePanel.keyHandler.getEnterPressed() && keyCooldown == 0) {

                    // FIXED: Properly roll the random move and save it to enemyMoveToUse!
                    int randomMoveIndex = (int) (Math.random() * activeEnemy.getHowManyMoves());
                    enemyMoveToUse = activeEnemy.getMove(randomMoveIndex);

                    dialogText = activeEnemy.getName() + " retaliated with " + enemyMoveToUse.getName() + "!";
                    battleSubState = 4; // JUMP TO ENEMY TURN STATE CUZ THEY SURVIVED
                    keyCooldown = 16;
                }
            }
        }
    }

    public void initiateEnemyTurnToAttackState () { // THIS STATE IS WHEN WE CALCULATE DAMAGE RECEIVED ON OUR OWN POKEROT
        if (gamePanel.keyHandler.getEnterPressed()) { // REQUIRE PLAYER TO PRESS ENTER BEFORE GETTING ATTACKED
            int enemyDamage = enemyMoveToUse.getDamage() + (activeEnemy.getAttack() / 5); // USE THE SAVED MOVE
            activePlayer.takeDamage(enemyDamage); // RUN POKEROT TAKEDAMAGE METHOD ON OUR POKEROT OBJECT
            battleSubState = 5; // INITIATE OUR POKEROT TAKING DAMAGE ANIMATION STATE
            keyCooldown = 16;
        }
    }

    public void initiateOurPokerotTakingDamageAnimationState() { // THIS STATE RENDERS OUR HP BAR MOVING (DAMAGE & HEAL)

        // 1. CATCH THE SWAP CONFIRMATION FIRST BEFORE ANY HP CHECKS!
        if (dialogText.startsWith("Swapping")) {
            if (gamePanel.keyHandler.getEnterPressed() && keyCooldown == 0) {
                gamePanel.battleUI.loadFighterImages(activePlayer, activeEnemy);
                dialogText = "";
                battleSubState = 0;
                optionSelected = 0;
                keyCooldown = 16;
            }
            return; // EXIT EARLY SO WE DON'T DO THE REST OF THE METHOD
        }

        // 2. Wait until HP bar stops moving visually before letting the player progress
        if (activePlayer.getDrawnHP() == activePlayer.getCurrentHP()) {

            // IF THE ACTIVE POKEROT FAINTED (HP <= 0)
            if (activePlayer.getCurrentHP() <= 0) {
                if (!dialogText.contains("fainted")) {
                    dialogText = activePlayer.getName() + " fainted!";
                    keyCooldown = 16;
                    return;
                }
                if (gamePanel.keyHandler.getEnterPressed() && keyCooldown == 0) {
                    if (gamePanel.getPlayer().hasUsablePokeRot()) {
                        PokeRot nextBackup = gamePanel.getPlayer().getFirstAlivePokeRot();
                        dialogText = "Swapping to the next PokeRot in your bag! Go, " + nextBackup.getName() + "!";
                        activePlayer = nextBackup;
                        keyCooldown = 16;
                    } else {
                        battleSubState = 7;
                        keyCooldown = 16;
                    }
                }
            }
            // IF OUR POKEROT SURVIVED / JUST HEALED (HP > 0)
            else {
                if (gamePanel.keyHandler.getEnterPressed() && keyCooldown == 0) {
                    if (dialogText.startsWith("Used Potion!")) {
                        int randomMoveIndex = (int) (Math.random() * activeEnemy.getHowManyMoves());
                        enemyMoveToUse = activeEnemy.getMove(randomMoveIndex);
                        dialogText = activeEnemy.getName() + " retaliated with " + enemyMoveToUse.getName() + "!";
                        battleSubState = 4;
                    } else {
                        battleSubState = 0;
                        optionSelected = 0;
                    }
                    keyCooldown = 16;
                }
            }
        }
    }

    public void initiateVictoryState () { // THIS STATE IS POKEROT GAINING EXP AND DETECTING IF LVL UP
        if (gamePanel.keyHandler.getEnterPressed()) {
            if (leveledUpThisRound) {
                dialogText = activePlayer.getName() + " has leveled up to " + activePlayer.getLevel() + "!";
                battleSubState = 8; // ROUTE TO LEVEL DISPLAY
            } else {
                checkNextEnemy(); // HELPER METHOD TO SEE IF BATTLE CONTINUES OR ENDS 
            }
            keyCooldown = 16;
        }
    }

    public void initiateWeGotCookedState () { // THIS STATE IS WHEN WE COMPLETELY LOST
        if (gamePanel.keyHandler.getEnterPressed()) { // REQUIRE PLAYER TO PRESS ENTER TO PROCEED
            battleSubState = 0; // GO BACK TO MAIN MENU
            optionSelected = 0; // RESET CURSOR TOP LEFT
            gamePanel.encounterManager.startCooldown(120);
            gamePanel.getPlayer().triggerBlackout();
            keyCooldown = 16;
        }
    }

    public void initiateEarningExpAnimationState () { // THIS STATE IS JUST RENDERING PLAYER GETTING EXP ANIMATION
        if (gamePanel.keyHandler.getEnterPressed()) {
            checkNextEnemy();
            keyCooldown = 16;
        }
    }

    public void initiateExitBattleState () { // THIS STATE IS CONVENIENCE FOR EXITING FROM BATTLE OR COMPLETING BATTLE
        if (gamePanel.keyHandler.getEnterPressed()) {
            if (dialogText.contains("sent out") || dialogText.contains("appeared")) {
                dialogText = "";
                battleSubState = 0;
                optionSelected = 0;
            } else {
                battleSubState = 0;
                optionSelected = 0;
                gamePanel.encounterManager.startCooldown(120);
                gamePanel.gameState = GameState.ROAMSTATE;
                if (currentNPCOpponent != null) currentNPCOpponent.setDefeated(true);
            }
            keyCooldown = 16;
        }
    }

    public void initiateRunState () {
        if (gamePanel.keyHandler.getEnterPressed()) {
            if (currentNPCOpponent != null) { // IF IT WAS A TRAINER DO NOT EXIT JUST GO TO MAIN MENU
                battleSubState = 0; // GO TO MAIN MENU
                optionSelected = 0; // RESET CURSOR TO TOP LEFT
            } else { // IF IT WAS A WILD POKEROT EXIT TO MAP SAFELY
                if (dialogText.equals("Got away safely!")) {
                    battleSubState = 0;
                    optionSelected = 0;
                    gamePanel.encounterManager.startCooldown(120);
                    gamePanel.gameState = GameState.ROAMSTATE;
                } else {
                    int randomMoveIndex = (int) (Math.random() * activeEnemy.getHowManyMoves());
                    enemyMoveToUse = activeEnemy.getMove(randomMoveIndex);
                    dialogText = activeEnemy.getName() + " attacks with " + enemyMoveToUse.getName() + "!";
                    battleSubState = 4;
                }
            }
            keyCooldown = 16;
        }
    }

    public void initiateSwapSelectionState () {
        twoByTwoSelectionHelper();
        if (gamePanel.keyHandler.getEscPressed()) {
            battleSubState = 0;
            optionSelected = 0;
            keyCooldown = 16;
            return;
        }

        if (gamePanel.keyHandler.getEnterPressed()) {
            ArrayList <PokeRot> party = gamePanel.getPlayer().getPlayerParty(); // GET A COPY OF THE PLAYER'S PARTY
            if (optionSelected < party.size()) {
                PokeRot selectedRot = party.get(optionSelected);
                if (selectedRot == activePlayer) dialogText = selectedRot.getName() + " is already in battle!";
                else if (selectedRot.getCurrentHP() <= 0) dialogText = selectedRot.getName() + " has fainted, cannot be swapped!";
                else {
                    activePlayer = selectedRot;
                    gamePanel.battleUI.loadFighterImages(activePlayer, activeEnemy);
                    int randomMoveIndex = (int) (Math.random() * activeEnemy.getHowManyMoves());
                    enemyMoveToUse = activeEnemy.getMove(randomMoveIndex);
                    dialogText = "Go! " + activePlayer.getName() + ", " + activeEnemy.getName() + " attacks with " + enemyMoveToUse.getName() + "!";
                    battleSubState = 4; // ENEMY ATTACK STATE BECAUSE YOU SWAPPED POKEROT
                    optionSelected = 0; // RESET CURSOR TOP LEFT
                }
            } else {
                dialogText = "There's no PokeRot in that slot!"; // THEY PICKED AN EMPTY SLOT
            }
            keyCooldown = 16;
        }
    }

    public void initiateBagSelectionState () {
        twoByTwoSelectionHelper();
        if (gamePanel.keyHandler.getEscPressed()) {
            battleSubState = 0;
            optionSelected = 0;
            keyCooldown = 16;
            return;
        }

        if (gamePanel.keyHandler.getEnterPressed()) {
            if (optionSelected == 0) { // OPTION SELECTED IN THIS STATE IS THE POTION
                PokeRot targetRot = activePlayer;
                if (targetRot.getCurrentHP() >= targetRot.getMaxHP()) dialogText = targetRot.getName() + " is already at full health!";
                else {
                    int healAmount = 50;
                    targetRot.heal(healAmount);
                    // DYNAMICALLY HEAL UP TO MAX HP
                    dialogText = "Used Malunggay Pandesal! Restored " + healAmount + " HP to " + targetRot.getName() + "!";
                    battleSubState = 5; // GO TO POKEROT TAKING DAMAGE CUZ AFTER HEALING IT'S ENEMY'S TURN
                    optionSelected = 0;
                }
            } else if (optionSelected == 1) { // PLUNGER SELECTED
                if (currentNPCOpponent != null) { // IF ENEMY IS AN NPC
                    dialogText = "You cannot catch another trainer's PokeRot!";
                    battleSubState = 12; // STAY IN BAG MENU
                } else {
                    dialogText = "You threw a Plunger at " + activeEnemy.getName() + "!";
                    double missingHPRatio = 1.0 - ((double) activeEnemy.getCurrentHP() / activeEnemy.getMaxHP());
                    double finalCatchChance = 60.0 + (20.0 * missingHPRatio);
                    int roll = (int) (Math.random() * 100);
                    if (roll < finalCatchChance) { // SUCCESS
                        ArrayList <PokeRot> playerParty = gamePanel.getPlayer().getPlayerParty();
                        if (playerParty.size() < 3) { // IF PARTY IS STILL NOT FULL
                            playerParty.add(activeEnemy);
                            dialogText = "All right! " + activeEnemy.getName() + " was caught with the Plunger!";
                            battleSubState = 9; // EXIT BATTLE AFTER CATCH
                        } else {
                            dialogText = "Your PokeRot party is full! Catch failed.";
                            battleSubState = 4; // TURN LOST, ENEMY ATTACKS
                        }
                    } else { // CAPTURE FAILED
                        int randomMoveIndex = (int) (Math.random() * activeEnemy.getHowManyMoves());
                        enemyMoveToUse = activeEnemy.getMove(randomMoveIndex);
                        dialogText = "Shoot! " + activeEnemy.getName() + " kicked the Plunger! It attacks with " + enemyMoveToUse.getName() + "!";
                        battleSubState = 4; // TURN LOST, ENEMY ATTACKS
                    }
                }
                optionSelected = 0;
            } else {
                dialogText = "Slot Empty!";
            }
            keyCooldown = 16;
        }
    }

    // HELPER METHOD TO FIND THE NEXT ALIVE ENEMY
    private PokeRot getFirstAliveEnemyPokeRot () {
        for (PokeRot eachPokeRot : activeEnemyParty ) {
            if (eachPokeRot.getCurrentHP() > 0) return eachPokeRot;
        }
        return null;
    }

    private void checkNextEnemy () {
        PokeRot nextEnemy = getFirstAliveEnemyPokeRot();
        if (nextEnemy != null) {
            activeEnemy = nextEnemy;
            gamePanel.battleUI.loadFighterImages(activePlayer, activeEnemy);
            if (currentNPCOpponent != null) {
                dialogText = currentNPCOpponent.getName() + " sent out " + activeEnemy.getName() + "!";
            } else {
                dialogText = "A wild " + activeEnemy.getName() + " appeared!";
            }
            battleSubState = 9; // ROUTE TO TRANSITION SCREEN
        } else {
            if (currentNPCOpponent != null) {
                dialogText = "You defeated " + currentNPCOpponent.getName() + "!";
            } else {
                dialogText = "You won the battle!";
            }
            battleSubState = 9;
        }
    }

    public int getOptionSelected() { return this.optionSelected; }
    public int getBattleSubState() { return this.battleSubState; }
    public PokeRot getActivePlayer() { return this.activePlayer; }
    public PokeRot getActiveEnemy() { return this.activeEnemy; }

}