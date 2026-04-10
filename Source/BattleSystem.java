public class BattleSystem {
    private GamePanel gamePanel;
    private int optionSelected = 0;
    private int keyCooldown = 0;

    public BattleSystem (GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void update () {
        if (keyCooldown > 0) {
            keyCooldown--;
        } else {
            if (gamePanel.keyHandler.getUpPressed()) {
                optionSelected -= 2;
                if (optionSelected < 0) optionSelected += 4;
                keyCooldown = 12;
            } else if (gamePanel.keyHandler.getDownPressed()) {
                optionSelected += 2;
                if (optionSelected > 3) optionSelected -=4;
                keyCooldown = 12;
            } else if (gamePanel.keyHandler.getLeftPressed()) {
                if (optionSelected == 1 || optionSelected == 3) optionSelected--;
                keyCooldown = 12;
            } else if (gamePanel.keyHandler.getRightPressed()) {
                if (optionSelected == 0 || optionSelected == 2) optionSelected++;
                keyCooldown = 12;
            }
        }
    }

    public int getOptionSelected () {
        return this.optionSelected;
    }
}
