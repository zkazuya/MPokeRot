import java.awt.*;

public class Dialogue {
    private GamePanel gp;
    private String[] currentDialogue;
    private int dialogueIndex = 0;
    private boolean enterKeyHandled = false;
    private NPC currentNPC; // Remembers who we are talking to

    public Dialogue(GamePanel gp) {
        this.gp = gp;
    }

    public void startDialogue(String[] lines, NPC npc) {
        this.currentDialogue = lines;
        this.dialogueIndex = 0;
        this.currentNPC = npc;
        gp.gameState = GameState.TALKINGSTATE; 
    }

    public void update(KeyHandler keyHandler) {
        if (currentDialogue == null) return;
        if (keyHandler.getEnterPressed() && !enterKeyHandled) {
            dialogueIndex++; // GO TO NEXT DIALOGUE
            if (dialogueIndex >= currentDialogue.length) { // IF WE RAN OUT OF DIALOG LINE
                dialogueIndex = 0; // RESET FOR NEXT DIALOGUE NPC ENCOUNTER

                // IF WE'RE TALKING TO AN NPC
                if (currentNPC != null) {
                    if (!currentNPC.isDefeated()) { // AND HE ISN'T DEFEATED YET
                        gp.battleSystem.startEncounter(currentNPC.getNPCParty(), currentNPC); // START THE BATTLE
                        gp.gameState = GameState.BATTLESTATE;
                    } else {
                        // JUST GO BACK TO ROAMING STATE IF THEY'RE ALREADY DEFEATED
                        gp.gameState = GameState.ROAMSTATE;
                    }
                    currentNPC = null; // CLEAR THE MEMORY
                } else {
                    // IF CURRENT NPC IS NULL JUST ROAM
                    gp.gameState = GameState.ROAMSTATE;
                }
            }
            enterKeyHandled = true;
        } else if (!keyHandler.getEnterPressed()) {
            enterKeyHandled = false;
        }
    }

    public void draw(Graphics2D g2) {
        if (gp.gameState == GameState.TALKINGSTATE && currentDialogue != null) {
            drawDialogueWindow(g2);
        }
    }

    private void drawDialogueWindow(Graphics2D g2) {
        int x = 10, y = 550, width = 700, height = 100;
        g2.setColor(new Color(0, 0, 0, 210));
        g2.fillRoundRect(x, y, width, height, 35, 35);
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(x, y, width, height, 25, 25);

        g2.setFont(new Font("Arial", Font.PLAIN, 14));
        g2.drawString(currentDialogue[dialogueIndex], x + 20, y + 40);

        g2.setFont(new Font("Arial", Font.BOLD, 16));
        g2.drawString("Press Enter>", x + 590, y + 85);
    }
}