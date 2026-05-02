//import java.util.ArrayList;

public class BattleSystem {
    private GamePanel gamePanel;
    private int optionSelected = 0; // START SELECTION AT TOP LEFT
    private int keyCooldown = 0; // GLOBAL COOLDOWN FROM SWITCHING KEYS
    private int battleSubState = 0; // 0 = Main Menu, 1 = Move Menu, 2 = Message Queue
    private String dialogText = ""; // THIS CHANGES ACCORDING TO FLOW
    private Move playerMoveToUse;
    private int expGainedThisBattle = 0; // TEMPORARILY STORE THIS VARIABLE ON EACH BATTLE
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
            } else if (optionSelected == 1) {
                // waray pa
            } else if (optionSelected == 2) {
                //  waray pa
            } else if (optionSelected == 3) { // IF RUN OPTION IS SELECTED
                battleSubState = 0; // TO AVOID BUGS CHANGE SUBSTATE TO 0 EFFECTIVELY RESETTING EVERYTHING
                optionSelected = 0; // RESET CURSOR TO TOP LEFT
                gamePanel.gameState = GameState.ROAMSTATE; // GO BACK TO THE OVERWORLD
            }
            keyCooldown = 16; // GLOBAL COOLDOWN IF THIS IS GONE WE PRESS ENTER ONCE AND SKIP TWO SELECTION STATES
        }
    }

    public void initiateMoveSelectionState () { // THIS STATE IS WHEN WE'RE INSIDE MOVE SELECTION
        twoByTwoSelectionHelper(); // CALL THE SELECTION FUNCTION
        if (gamePanel.keyHandler.getEnterPressed()) { // IF PLAYER MADE A CHOICE, CAPTURE HIS CHOICE
            playerMoveToUse = activePlayer.getMove(optionSelected);
            if (playerMoveToUse != null) { // IF SELECTED MOVE EXISTS (PLAYER DID NOT CHOOSE "-")
                dialogText = activePlayer.getName() + " used " + playerMoveToUse.getName() + "!"; // EX: TRALALALEO USED TACKLE
                battleSubState = 2; // MOVE TO NEXT BATTLE SUBSTATE WHICH IS DMG CALCULATION
                optionSelected = 0; // RESET CURSOR TOP LEFT
            }
            keyCooldown = 16;
        }
    }

    public void initiateCalculateEnemyDamageReceivedState () { // THIS STATE IS WHEN WE CALCULATE DAMAGE RECEIVED ON ENEMY POKEROT
        if (gamePanel.keyHandler.getEnterPressed()) { // REQUIRE PLAYER TO PRESS ENTER BEFORE DOING THE CALCULATION
            int playerDamage = playerMoveToUse.getDamage() + (activePlayer.getAttack() / 5); // CALCULATE PLAYER DAMAGE BASED ON POKEROT STATS
            activeEnemy.takeDamage(playerDamage); // RUN POKEROT CLASS TAKE DAMAGE METHOD ON ACTIVENEMY OBJECT
            battleSubState = 3; // INITIATE ENEMY TAKING DAMAGE ANIMATION STATE
        }
        keyCooldown = 16;
    }

    public void initiateEnemyTakingDamageAnimationState () { // THIS STATE IS JUST RENDERING THE HP BAR OF ENEMY POKEROT DRAINING
        if (activeEnemy.getDrawnHP() == activeEnemy.getCurrentHP()) { // THIS CONDITION MEANS IF THE DRAWN HP MATCHES THEIR CURRENT HP
            if (activeEnemy.getCurrentHP() <= 0) { // IF THE ENEMY DIED OR HP IS 0
                dialogText = activeEnemy.getName() + " fainted!"; // EX: TRALALELO FAINTED!
                battleSubState = 6; // SKIP TO SUBSTATE 6 WHICH IS VICTORY STATE
            } else { // IF THE ENEMY IS ALIVE OR HP > 0
                Move enemyMove = activeEnemy.getMove(0); // PREPARE A REFERENCE TO THEIR MOVE
                dialogText = activeEnemy.getName() + " retaliated with " + enemyMove.getName() + "!"; // USE THAT REFERENCE HERE TO CHANGE DIALOG
                battleSubState = 4; // JUMP TO ENEMY TURN STATE CUZ THEY SURVIVED
            }
        }
        keyCooldown = 16;
    }

    public void initiateEnemyTurnToAttackState () { // THIS STATE IS WHEN WE CALCULATE DAMAGE RECEIVED ON OUR OWN POKEROT
        if (gamePanel.keyHandler.getEnterPressed()) { // REQUIRE PLAYER TO PRESS ENTER BEFORE GETTING ATTACKED
            Move enemyMove = activeEnemy.getMove(0); // GET A REFERENCE TO THE ENEMY BRAINROT MOVE
            int enemyDamage = enemyMove.getDamage() + (activeEnemy.getAttack() / 5); // CALCULATE THEIR DAMAGE
            activePlayer.takeDamage(enemyDamage); // RUN POKEROT TAKEDAMAGE METHOD ON OUR POKEROT OBJECT
            battleSubState = 5; // INITIATE OUR POKEROT TAKING DAMAGE ANIMATION STATE
        }
        keyCooldown = 16;
    }

    public void initiateOurPokerotTakingDamageAnimationState () { // THIS STATE IS RENDERING OUR POKEROT LOSING HP BAR ANIMATION
        if (activePlayer.getDrawnHP() == activePlayer.getCurrentHP()) { // THIS CONDITION MEANS IF OUR DRAWNHP MATCHES OUR CURRENT HP
            if (activePlayer.getCurrentHP() <= 0) { // IF OUR POKEROT DIED
                dialogText = activePlayer.getName() + " fainted!"; // EX: TRALALELO FAINTED!
                battleSubState = 7; // SKIP TO WE GOT DEFEATED STATE
            } else { // IF WE DIDN'T DIE
                battleSubState = 0; // GO BACK TO MAIN MENU TO ACTION STATE AGAIN FOR ANOTHER ATTACK
                optionSelected = 0; // RESET CURSOR TOP LEFT
            }
        }
        keyCooldown = 16;
    }

    public void initiateVictoryState () { // THIS STATE IS POKEROT GAINING EXP AND DETECTING IF LVL UP
        if (gamePanel.keyHandler.getEnterPressed()) { // REQUIRE PLAYER TO PRESS ENTER BEFORE GETTING ATTACKED
            expGainedThisBattle = activeEnemy.getBaseExpYield(); // CALCULATE EXP GAINED DEPENDING ON ENEMY DEFEATED
            dialogText = activePlayer.getName() + " gained " + expGainedThisBattle + " EXP!"; // UPDATE DIALOG
            leveledUpThisRound = activePlayer.gainExp(expGainedThisBattle); // REMEMBER gainExp() METHOD RETURNS TRUE IF WE LVLED UP WHILE GIVING XP
            battleSubState = 8; // JUMP TO EARNING EXP STATE
        }
        keyCooldown = 16;
    }

    public void initiateWeGotCookedState () { // THIS STATE IS WHEN WE COMPLETELY LOST
        if (gamePanel.keyHandler.getEnterPressed()) { // REQUIRE PLAYER TO PRESS ENTER TO PROCEED
            battleSubState = 0; // GO BACK TO MAIN MENU
            optionSelected = 0; // RESET CURSOR TOP LEFT
            gamePanel.gameState = GameState.ROAMSTATE; // GO BACK TO THE OVERWORLD
        }
        keyCooldown = 16;
    }

    public void initiateEarningExpAnimationState () { // THIS STATE IS JUST RENDERING PLAYER GETTING EXP ANIMATION
        if (gamePanel.keyHandler.getEnterPressed()) { // REQUIRE PLAYER TO PRESS ENTER BEFORE ANIMATION STARTS
            if (leveledUpThisRound) { // IF THIS VARIABLE BECOMES TRUE
                dialogText = activePlayer.getName() + " has leveled up to " + activePlayer.getLevel() + "!";
                battleSubState = 9; // GO TO EXIT BATTLE STATE
            } else {
                battleSubState = 0; // REFRESH AND RESET THESE VARIABLES FOR WHEN WE ENCOUNTER NEW ENEMIES AGAIN
                optionSelected = 0; // THESE ARE IMPORTANT SO THAT WE START FROM SCRATCH ON A NEW ENEMY
                gamePanel.gameState = GameState.ROAMSTATE;
            }
        }
        keyCooldown = 16;
    }

    public void initiateExitBattleState () { // THIS STATE IS CONVENIENCE FOR EXITING FROM BATTLE OR COMPLETING BATTLE
        if (gamePanel.keyHandler.getEnterPressed()) { // REQUIRES PLAYER TO PRESS ENTER BEFORE THIS IS DONE
            battleSubState = 0; // RESET MAIN OPTION TO VERY START
            optionSelected = 0; // RESET CURSOR TOP LEFT
            gamePanel.gameState = GameState.ROAMSTATE; // GO TO OVERWORLD
        }
        keyCooldown = 16; // GLOBAL COOLDOWN
    }

    public int getOptionSelected() { return this.optionSelected; }
    public int getBattleSubState() { return this.battleSubState; }
}