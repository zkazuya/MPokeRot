import java.awt.*;

public class Dialogue {
    private GamePanel gp;
    private final int playState = 0;
    private final int dialogueState = 1;
    private int gameState = playState;

    private String[] IntroDialogue = {
        "Welcome to UP!",
        "The CS building's servers just leaked—PokeRots are jumping out of the screens and onto the campus!",
        "Our sanity is at stake. Explore the map, stay woke and catch 'em all before the PokeRots go viral!",
        "Once you enter, you cannot go back..."
    };

    private int dialogueIndex = 0;
    private boolean fKeyHandled = false;

    public Dialogue(GamePanel gp) {
        this.gp = gp;
    }

    public void update(KeyHandler keyHandler) {
        if (keyHandler.getFPressed() && !fKeyHandled) {
            if (gameState == playState && dialogueIndex == 0) {
                gameState = dialogueState;
            } else if (gameState == dialogueState) {
                dialogueIndex++;
                if (dialogueIndex >= IntroDialogue.length) {
                    gameState = playState;
                    dialogueIndex = 0;
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
            drawDialogueWindow(g2);
        }
    }

    private void drawDialogueWindow(Graphics2D g2) {
        int x = 20, y = 180, width = 360, height = 100;
        g2.setColor(new Color(0, 0, 0, 210));
        g2.fillRoundRect(x, y, width, height, 35, 35);
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(x, y, width, height, 25, 25);

        g2.setFont(new Font("Arial", Font.PLAIN, 14));
        g2.drawString(IntroDialogue[dialogueIndex], x + 20, y + 40);

        g2.setFont(new Font("Arial", Font.BOLD, 16));
        g2.drawString("Press F>", x + 250, y + 85);
    }
}


