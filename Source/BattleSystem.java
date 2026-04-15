import java.util.ArrayList;

public class BattleSystem {
    private GamePanel gamePanel;
    private int optionSelected = 0;
    private int keyCooldown = 0;
    private int battleSubState = 0; // 0 = Main Menu, 1 = Move Menu, 2 = Message Queue
    private ArrayList<String> messageQueue = new ArrayList<>();

    public BattleSystem(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void queueMessage(String message) {
        messageQueue.add(message);
    }

    public String getCurrentMessage() {
        if (messageQueue.size() > 0)
            return messageQueue.get(0);
        return "";
    }

    public void update() {
        if (keyCooldown > 0) {
            keyCooldown--;
        } else {
            // --- MAIN MENU ---
            if (battleSubState == 0) {
                if (gamePanel.keyHandler.getUpPressed()) {
                    optionSelected -= 2;
                    if (optionSelected < 0)
                        optionSelected += 4;
                    keyCooldown = 12;
                } else if (gamePanel.keyHandler.getDownPressed()) {
                    optionSelected += 2;
                    if (optionSelected > 3)
                        optionSelected -= 4;
                    keyCooldown = 12;
                } else if (gamePanel.keyHandler.getLeftPressed()) {
                    if (optionSelected == 1 || optionSelected == 3)
                        optionSelected--;
                    keyCooldown = 12;
                } else if (gamePanel.keyHandler.getRightPressed()) {
                    if (optionSelected == 0 || optionSelected == 2)
                        optionSelected++;
                    keyCooldown = 12;
                } else if (gamePanel.keyHandler.getEnterPressed()) {
                    if (optionSelected == 0) {
                        battleSubState = 1;
                        optionSelected = 0;
                    }
                    keyCooldown = 30;
                }
            }
            // --- MOVE MENU ---
            else if (battleSubState == 1) {
                if (gamePanel.keyHandler.getUpPressed()) {
                    optionSelected -= 2;
                    if (optionSelected < 0)
                        optionSelected += 4;
                    keyCooldown = 12;
                } else if (gamePanel.keyHandler.getDownPressed()) {
                    optionSelected += 2;
                    if (optionSelected > 3)
                        optionSelected -= 4;
                    keyCooldown = 12;
                } else if (gamePanel.keyHandler.getLeftPressed()) {
                    if (optionSelected == 1 || optionSelected == 3)
                        optionSelected--;
                    keyCooldown = 12;
                } else if (gamePanel.keyHandler.getRightPressed()) {
                    if (optionSelected == 0 || optionSelected == 2)
                        optionSelected++;
                    keyCooldown = 12;
                } else if (gamePanel.keyHandler.getEscPressed()) {
                    battleSubState = 0;
                    optionSelected = 0;
                    keyCooldown = 30;
                } else if (gamePanel.keyHandler.getEnterPressed()) {
                    PokeRot player = gamePanel.playerParty[0];
                    PokeRot enemy = gamePanel.enemyParty[0];
                    Move selectedMove = player.getMove(optionSelected);

                    if (selectedMove != null) {
                        queueMessage(player.getName() + " used " + selectedMove.getName() + "!");
                        int playerDamage = selectedMove.getDamage() + (player.getAttack() / 5);
                        enemy.takeDamage(playerDamage);

                        if (enemy.getCurrentHP() > 0) {
                            Move enemyMove = enemy.getMove(0);
                            queueMessage(enemy.getName() + " retaliated with " + enemyMove.getName() + "!");
                            int enemyDamage = enemyMove.getDamage() + (enemy.getAttack() / 5);
                            player.takeDamage(enemyDamage);
                        } else {
                            queueMessage(enemy.getName() + " fainted!");
                        }

                        battleSubState = 2; // Jump to reading messages!
                        optionSelected = 0;
                    }
                    keyCooldown = 30;
                }
            }
            // --- READING MESSAGES ---
            else if (battleSubState == 2) {
                if (gamePanel.keyHandler.getEnterPressed()) {
                    if (messageQueue.size() > 0) {
                        messageQueue.remove(0); // Delete message just read
                    }
                    if (messageQueue.size() == 0) {
                        battleSubState = 0; // Back to main menu when done
                    }
                    keyCooldown = 20;
                }
            }
        }
    }

    public int getOptionSelected() {
        return this.optionSelected;
    }

    public int getBattleSubState() {
        return this.battleSubState;
    }
}