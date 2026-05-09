import java.awt.Graphics2D;
import javax.imageio.ImageIO;


public class NPC extends Entity{
    int type; //to keep track of NPC
    public NPC(int x, int y, int type){ //char variable is for what npc
        this.x=x;
        this.y=y;
        this.type=type;
        loadImages();
    }


    public void loadImages(){
        try { //NO NEED TO INITIALIZE DOWNSPRITES SINCE IT'S BEEN INITIALIZED IN ENTITY
        //LIST OF NPCs by int
            downSprites[0] = ImageIO.read(getClass().getResourceAsStream("/Assets/NPCRoam/mmY.png"));
            downSprites[1] = ImageIO.read(getClass().getResourceAsStream("Assets/NPCRoam/peej.png"));
            downSprites[2] = ImageIO.read(getClass().getResourceAsStream("Assets/NPCRoam/allen.png"));
            downSprites[3] = ImageIO.read(getClass().getResourceAsStream("Assets/NPCRoam/kasooya.png"));
            downSprites[4] = ImageIO.read(getClass().getResourceAsStream("Assets/NPCRoam/reese.png"));
            
        } catch(Exception e) {e.printStackTrace();}        
    }

    public void draw(Graphics2D g2) {
        if (downSprites[type]!= null) { //COPIED U GUYS USING 56x56
            g2.drawImage(downSprites[type], x, y, 72, 72, null);

        }
    }
}