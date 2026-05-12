import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class Pause{
    private GamePanel gp;
    private GameState gameState;
    private Dialogue dialogue;
    private TitlePanel titlePanel;
    private PokeRotStats pokerotStats;
    private KeyHandler keyHandler;
    private boolean showingStats;
    int padding = 20;//Padding to not touch edges
    int x=5;
    int y;
    int choice;
    String pauseString = "Press F \u275A\u275A";


    public Pause(GamePanel gp){
        this.gp=gp;
        y=gp.getHeight()/2;
        this.pokerotStats = new PokeRotStats(gp, this);
    }
    private void drawPauseLabel(Graphics2D g2) {
        String pauseString = "Game Paused";
        g2.setFont(new Font("Arial", Font.PLAIN, 14));
        FontMetrics fm = g2.getFontMetrics();   //Imported fontmetrics for better values in placing graphics/strings
        int textWidth = fm.stringWidth(pauseString); //String dimensions
        int textHeight = fm.getHeight();
        int windowWidth = textWidth + (padding * 2);
        int windowHeight = textHeight + (padding * 2);
        int x = 450; //LOCATION OF LABEL
        int yP = 125; 

        g2.setColor(new Color(0, 0, 0));
        g2.fillRoundRect(x, yP, windowWidth, windowHeight, 35, 35);
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(x, yP, windowWidth, windowHeight, 35, 35);
        g2.drawString(pauseString,       x + padding,       yP + fm.getAscent() + padding);
    }

    public void drawOptions(Graphics2D g2){    
    String[] options = {"Resume", "Pokérot", "Save Game", "Main Menu"};
        int btnWidth = 150; 
        int btnHeight = 40;
        int spacing = 50;
        int startX = 20; 
        int startY = gp.getHeight() / 2 - 50; 

        g2.setFont(new Font("Arial", Font.PLAIN, 14));
        FontMetrics fm = g2.getFontMetrics();
        for (int i = 0; i < options.length; i++) {
            int currY = startY + (i * spacing);
            g2.setColor(new Color(0, 0, 0, 220));
            g2.fillRoundRect(startX, currY, btnWidth, btnHeight, 20, 20);

            if (i == choice){   //THIS IS CHECKER FOR CHOICE
                g2.setColor(Color.YELLOW);
            } else {
                g2.setColor(Color.WHITE); 
                g2.setStroke(new BasicStroke(2));
            }

            g2.drawRoundRect(startX, currY, btnWidth, btnHeight, 20, 20);
            int textX = startX + (btnWidth / 2) - (fm.stringWidth(options[i]) / 2);
            int textY = currY + (btnHeight / 2) + (fm.getAscent() / 2) - 2;
            g2.drawString(options[i], textX, textY);
        }
    }

    public void update(KeyHandler keyHandler){
        if (keyHandler.getEscPressed()) {
            if (gp.gameState == GameState.ROAMSTATE) {
                gp.gameState = GameState.PAUSESTATE;
            } else if (gp.gameState == GameState.PAUSESTATE && !showingStats) {
                gp.gameState = GameState.ROAMSTATE;
            }
            keyHandler.setEscPressed(false);
        }

        // paused main menu logic only if game is paused
        if (gp.gameState == GameState.PAUSESTATE) {
            
            if (showingStats) {
                // Logic for the Stats Screen
                if (keyHandler.getEnterPressed()) {
                    showingStats = false;
                    keyHandler.setEnterPressed(false);
                } else {
                    pokerotStats.update(keyHandler);
                }
            } else {
                if (keyHandler.getDownPressed()) {
                    if (choice < 3) choice++;
                    keyHandler.setDownPressed(false);
                }
                if (keyHandler.getUpPressed()) {
                    if (choice > 0) choice--;
                    keyHandler.setUpPressed(false);
                }
                if (keyHandler.getEnterPressed()) {
                    keyHandler.setEnterPressed(false);
                    switch (choice) {
                        case 0: gp.gameState = GameState.ROAMSTATE; break;
                        case 1: showingStats = true; break;            
                        case 2: /* SAVEEE */ break;
                        case 3: /* main menu */ break;
                    }
                }
            }
        }
    }
    public void draw(Graphics2D g2){
        if(showingStats){
            pokerotStats.draw(g2);
        }else{
            drawPauseLabel(g2);
            drawOptions(g2);
        }
    }
}