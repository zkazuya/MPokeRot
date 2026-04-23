import java.awt.*;

public class Dialogue {
    private GamePanel gp;
    private final int playState = 0;
    private final int dialogueState = 1;
    private int gameState = playState;
    private boolean doneIntro = false; // checks if Intro has already been shown

    private String[] IntroDialogue = {
            "Welcome to UP!",
            "The CS building's servers just leaked—PokeRots are jumping out of the screens and unto the campus !",
            "Our sanity is at stake. Explore the map, stay woke and catch 'em all before the PokeRots go viral!",
            "Once you enter, you cannot go back..."
    };

    private int dialogueIndex = 0;
    private boolean fKeyHandled = false;

    public Dialogue(GamePanel gp) {
        this.gp = gp;
    }

    public void update(KeyHandler keyHandler) {
        if (keyHandler.getFPressed() && !fKeyHandled && !doneIntro) {
            if (gameState == playState) {
                gameState = dialogueState;
            } else if (gameState == dialogueState) {
                dialogueIndex++;
                if (dialogueIndex >= IntroDialogue.length) {
                    gameState = playState;
                    dialogueIndex = 0;
                    gp.gameState = GameState.ROAMSTATE; // Exit dialogue and start roaming
                    doneIntro = true;
                }
            }
            fKeyHandled = true;
        } else if (!keyHandler.getFPressed()) {
            fKeyHandled = false;
        }
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        // g2.drawString("CONTROLS: Press 'X' to Talk | 'ENTER' to Advance", 180, 250);
        if (gameState == dialogueState) {
            if (!doneIntro) {
                drawIntroDialogueWindow(g2);
            }
        }
    }

    private void drawIntroDialogueWindow(Graphics2D g2) {
        int x = 10, y = 550, width = 700, height = 100;
        g2.setColor(new Color(0, 0, 0, 210));
        g2.fillRoundRect(x, y, width, height, 35, 35);
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(x, y, width, height, 25, 25);

        g2.setFont(new Font("Arial", Font.PLAIN, 14));
        g2.drawString(IntroDialogue[dialogueIndex], x + 20, y + 40);

        g2.setFont(new Font("Arial", Font.BOLD, 16));
        g2.drawString("Press F>", x + 600, y + 85);
    }
}
