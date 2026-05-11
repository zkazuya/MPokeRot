import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class TitlePanel {
    private GamePanel gp;
    private int commandNum  =0;//default at option 0:create new game
    private boolean isOnSave = false;
    private boolean isOnLoad = false;
    private boolean isTyping = false;
    private String playerName = "";
 

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
        if((commandNum<3 ||commandNum>=0) && isOnLoad == false && isOnSave == false){
            if (keyHandler.getEnterPressed()) {
                switch(commandNum){
                    case 0:
                        isOnSave = true;
                        commandNum = 0;
                        keyCooldown = 8;
                        break;
                    case 1:
                        isOnLoad = true;
                        commandNum = 1;
                        keyCooldown = 8;
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

            //This is when you pick the Create new Save and where you have to enter your name
            else if(isOnSave == true){
                if (commandNum == 0){
                    keyHandler.setIfTypingTrue();
                    isTyping = true; //toggles that the user will be typing and will softlock the user
                    if (keyHandler.isCharTyped() && keyCooldown == 0){
                        char c = keyHandler.getTypedChar();
                        keyCooldown = 5;
                        if (Character.isLetterOrDigit(c)){
                            if(playerName.length() < 12){
                                playerName += c;
                                
                            }
                            
                        }
                        keyHandler.resetTypedChar();
                        
                    }
                    if (keyHandler.getEnterPressed()){
                        keyHandler.setIfTypingFalse();
                        isTyping = false;
                        commandNum = 1;
                        keyCooldown = 8;
                    }
                }
                else if(keyHandler.getEnterPressed() && isTyping == false){
                    switch(commandNum){    
                        case 1: 
                            gp.gameState = GameState.ROAMSTATE;
                            break;
                        case 2: 
                            isOnSave = false;
                            keyCooldown = 8;
                            break;
                    }
                }else if (keyHandler.getDownPressed() && isTyping == false){
                    if (commandNum < 2){
                        commandNum++;
                        keyCooldown = 8;
                    }
                }else if (keyHandler.getUpPressed() && isTyping == false){
                    if (commandNum > 0){
                        commandNum--;
                        keyCooldown = 8;
                    }
                }
            
            }

            else if((commandNum > 0 || commandNum < 4) && isOnLoad == true){
                if(keyHandler.getEnterPressed()){
                    switch(commandNum){
                        case 0: 
                            gp.gameState = GameState.ROAMSTATE;
                            break;
                        case 1:
                            gp.gameState = GameState.ROAMSTATE;
                            break;
                        case 2:
                            gp.gameState = GameState.ROAMSTATE;
                            break;
                        case 3: 
                            isOnLoad = false;
                            keyCooldown = 8;
                            commandNum = 0;
                            break;
                    }
                }else if (keyHandler.getDownPressed()){
                    if (commandNum < 3){
                        commandNum++;
                        keyCooldown = 8;
                    }
                }else if (keyHandler.getUpPressed()){
                    if (commandNum > 0){
                        commandNum--;
                        keyCooldown = 8;
                    }
                }
            }
        }
    }

    public void draw(Graphics2D g2) {

        g2.setColor(new Color(18, 50, 77));
        g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());
        //Button Sizes
        int width = 250;
        int height = 150;
        int xB = (gp.getScreenWidth() / 2) - (width / 2);
        int yBase = (gp.getScreenHeight() / 2) + 70;


        if (isOnLoad != true && isOnSave != true){
            g2.drawImage(Ui.get(6), 0, 0, gp.getScreenWidth(), gp.getScreenHeight(), null);
       
        //Button Sizes
            for(int i = 0; i < 3; i++) {
                int yB = yBase + (i * 80);//spacing 80

                if (commandNum == i) {
                    g2.drawImage(Ui.get(i+3), xB, yB, width, height, null);
                }else {
                    g2.drawImage(Ui.get(i), xB, yB, width, height, null);
                } 
            }
        }else if (isOnSave == true){
            g2.drawImage(Ui.get(14), 0, 0, gp.getScreenWidth(), gp.getScreenHeight(), null);

            g2.drawImage(Ui.get(8), gp.getScreenWidth() / 2 - 290, gp.getScreenHeight() / 2 - 64, width + 350, height + 20, null);

            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.BOLD, 32));

            g2.drawString(playerName, gp.getScreenWidth() / 2 - 200, gp.getScreenHeight() / 2 + 10);

            for(int i = 0; i < 2; i++){
                int yB = yBase + (i * 80);//spacing 80

                if (commandNum == i){

                    g2.drawImage(Ui.get(i + 12), xB, yB, width, height, null);
                }else {
                    g2.drawImage(Ui.get(i + 10), xB, yB, width, height, null);
                }
            }
        }else if (isOnLoad == true){
            g2.drawImage(Ui.get(7), 0, 0, gp.getScreenWidth(), gp.getScreenHeight(), null);

            for (int i = 0; i < 4; i++){
                int yB = (yBase + (i * 90) - 250); //pag center lang

                if (commandNum == i){
                    if(commandNum == 3){
                    
                    g2.drawImage(Ui.get(13), xB, yB + 50, width, height, null);
                    }else {
                    g2.drawImage(Ui.get(9), xB - 150, yB, width + 300, height + 50, null);
                    }
                }else {
                    if (i == 3){
                        g2.drawImage(Ui.get(11), xB, yB + 50, width, height, null);
                    }else{
                    g2.drawImage(Ui.get(8), xB - 150, yB, width + 300, height + 50, null);
                    }
                }
            }
        }


    }

    public void loadUi(){
        try {
            for(int i = 1; i <= 15; i++){ //load everything in the UI
                BufferedImage uiPic = ImageIO.read(new File("Assets/Ui/" + i + ".png"));
                Ui.add(uiPic);
            }
        } catch (IOException e) {
        }
    }

    public void setTitleState(){
        gp.gameState = GameState.TITLESCREEN;
        isOnSave = false;
        isOnLoad = false;
        commandNum = 0;
    }

    
}