package pokerot;

import battle.BattleUI;
import entity.Player;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import main.GamePanel;
import system.KeyHandler;
import ui.Pause;

public class PokeRotStats {
    private KeyHandler keyHandler;
    private Pause paused;
    private GamePanel gp;
    private Player player;
    private BattleUI battleUI;
    public PokeRotStats(GamePanel gp, Pause paused){
        this.gp=gp;
        this.paused=paused;
    }

    public void drawTemplate(Graphics2D g2, PokeRot pokerot, int x, int y) {
        int width = 200;
        int height = 200;

        drawProfile(g2, pokerot, x, y, width, height);
   
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(4));
        g2.drawRoundRect(x, y, width, height, 25, 25);

        int textX = x + width + 20;         //STATS
        int barWidth = 150;
        int barHeight = 15;

        g2.setColor(Color.WHITE);  //name
        g2.drawString("Name: " + pokerot.getName(), textX, y + 30);

        g2.drawString("HP: "+pokerot.getCurrentHP(), textX, y + 55);   //hp
        g2.setColor(Color.GRAY);//gray barz
        g2.fillRoundRect(textX, y + 65, barWidth, barHeight, 5, 5);

        double hpWidth = ((double)pokerot.getCurrentHP()/pokerot.getMaxHP()) * barWidth;
        g2.setColor(Color.GREEN);
        g2.fillRoundRect(textX, y + 65, (int)hpWidth, barHeight, 5, 5);

        g2.setColor(Color.WHITE);
        g2.drawString("EXP: "+pokerot.getExp(), textX, y + 95);
        g2.setColor(Color.GRAY);
        g2.fillRoundRect(textX, y + 105, barWidth, barHeight, 5, 5);

        double expWidth = ((double)pokerot.getDrawnExp() / pokerot.getExpNeeded()) * barWidth;
        g2.setColor(Color.CYAN);
        g2.fillRoundRect(textX, y + 105, (int)expWidth, barHeight, 5, 5);

        g2.setColor(Color.WHITE);
        g2.drawString("Attack: "+pokerot.getAttack(), textX, y + 145);   //hp
    }
    public void update(KeyHandler keyHandler){
        if(keyHandler.getEnterPressed()){
            keyHandler.setEnterPressed(false);
        }
    }
    public void drawProfile(Graphics2D g2, PokeRot pokerot,  int x, int y, int w, int h){ //FROM BATTLEUI LOADFIGHTERIMAGES METHOD
        try{
        String name = pokerot.getName();
        String pokerotFileName = name.replace(" ", "_") + ".png";
        BufferedImage pokeImage = ImageIO.read(new FileInputStream("Assets/PokeRots/" + pokerotFileName));
        g2.drawImage(pokeImage, x + 5, y + 5, w - 10, h - 10, null);
        }catch (IOException ioE){ioE.printStackTrace();}
 
    }
    public void draw(Graphics2D g2){ //in a loop ,5 pokerots
        ArrayList<PokeRot> pokerotParty = gp.player.getPlayerParty();
        if (pokerotParty.isEmpty()) return; //CATCGES IF EVER OKEROTS AREN'T YET REGISTERED
        drawBG(g2);
        int count = Math.min(pokerotParty.size(), 3); //takes all pokerots available, max 3
        for (int i = 0; i < count; i++) {
            int dynamicY = 50 + (i * 220); 
            drawTemplate(g2, pokerotParty.get(i), 30, dynamicY);
        }
    }
    public void drawBG(Graphics2D g2){
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());
    }

}
