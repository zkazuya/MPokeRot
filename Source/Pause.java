import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class Pause{
    private GamePanel gp;
    private GameState gameState;
    private Dialogue dialogue;
    String pauseString = "Press F \u275A\u275A";

    public Pause(GamePanel gp){this.gp=gp;}
    private void drawPauseLabel(Graphics2D g2) {
        String pauseString = "Game Paused"; // Example text
        g2.setFont(new Font("Arial", Font.PLAIN, 14));
        
        FontMetrics fm = g2.getFontMetrics();   //Imported fontmetrics for better values in placing graphics/strings
        int textWidth = fm.stringWidth(pauseString); //String dimensions
        int textHeight = fm.getHeight();
        int padding = 20;//Padding to not touch edges
        int windowWidth = textWidth + (padding * 2);
        int windowHeight = textHeight + (padding * 2);
        int x = 450; //LOCATION OF LABEL
        int y = 50; 

        g2.setColor(new Color(0, 0, 0, 210));
        g2.fillRoundRect(x, y, windowWidth, windowHeight, 35, 35);
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(x, y, windowWidth, windowHeight, 35, 35);

        g2.drawString(pauseString,       x + padding,       y + fm.getAscent() + padding);
    }
    public void update(KeyHandler keyHandler){
        if(keyHandler.getFPressed()){
            gp.gameState=GameState.PAUSESTATE;
        }
    }
    public void draw(Graphics2D g2){
        drawPauseLabel(g2);
    }
}