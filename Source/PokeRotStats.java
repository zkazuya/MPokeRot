import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class PokeRotStats {
    private KeyHandler keyHandler;
    private Pause paused;
    private GamePanel gp;
    private ArrayList <PokeRot> playerParty = new ArrayList<>();
    public PokeRotStats(GamePanel gp, Pause paused){
        this.gp=gp;
        this.paused=paused;
    }

    public void drawTemplate(Graphics2D g2){
        int x=15;
        int y=50;
        int width=125;
        int height=125;

        //drawProfile
        //draw NAME, STATS from battleUI bars(hp,exp) and attack

        g2.setColor(new Color(255,255,255));//THIS IS FOR POKEROT PROFILE BORDER
        g2.setStroke(new BasicStroke(4));
        g2.drawRoundRect(x,y,width, height, 25,25);
        
        g2.drawString("Name: "+playerParty.get(0).getName(), x+150, y);

    }
    public void update(KeyHandler keyHandler){
        if(keyHandler.getEnterPressed()){
            paused.update(keyHandler);
            keyHandler.setEnterPressed(false);
        }
    }
    public void drawProfile(){
        
 
    }
    public void draw(Graphics2D g2){ //in a loop ,5 pokerots
        // for(int i =0;i<3;i++){
        drawTemplate(g2);
        // }
    }

}
