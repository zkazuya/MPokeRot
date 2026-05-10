import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class TitlePanel {
    private GamePanel gp;
    private int commandNum  =0;//default at option 0:create new game

    private ArrayList<BufferedImage> Ui = new ArrayList<>();
    private int keyCooldown;

    

    // constructor
    public TitlePanel(GamePanel gp) {
        this.gp = gp;
        loadUi();
    }

    public void update(KeyHandler keyHandler) {
        if (keyCooldown > 0){
            keyCooldown--;
        }else{
        if(commandNum<3||commandNum>=0){
            if (keyHandler.getEnterPressed()) {
                switch(commandNum){
                    case 0:
                        gp.gameState = GameState.TALKINGSTATE; break;
                    case 1:
                        //LOAD SAVED Game
                        break;
                    case 2:
                        System.exit(0); break;
                }    
            }else if(keyHandler.getDownPressed()){
                if(commandNum<2){
                commandNum++;
                keyCooldown = 8;
                }//keyHandler.setDownPressed(false); //hehe
            }else if(keyHandler.getUpPressed()){
                if(commandNum>0){
                commandNum--;
                keyCooldown = 8;
                    }//keyHandler.setUpPressed(false);
                }
            }
        }
    }

    public void draw(Graphics2D g2) {
        g2.setColor(new Color(18, 50, 77));
        g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());
        g2.drawImage(Ui.get(6), 0, 0, gp.getScreenWidth(), gp.getScreenHeight(), null);
       
        //Button Sizes
        int width = 250;
        int height = 150;
        int xB = (gp.getScreenWidth() / 2) - (width / 2);
        int yBase = (gp.getScreenHeight() / 2) + 70;

        
        for(int i = 0; i < 3; i++) {
            int yB = yBase + (i * 80);//spacing 60


            if (commandNum == i) {
                g2.drawImage(Ui.get(i+3), xB, yB, width, height, null);
            } else {
                g2.drawImage(Ui.get(i), xB, yB, width, height, null);
            }

            
        }


    }

    public void loadUi(){
        try {
            for(int i = 1; i <= 10; i++){ //load everything in the UI
                BufferedImage uiPic = ImageIO.read(new File("Assets/Ui/" + i + ".png"));
                Ui.add(uiPic);
            }
        } catch (IOException e) {
        }
    }
    
}